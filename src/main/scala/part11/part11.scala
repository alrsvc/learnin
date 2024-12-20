package part11

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import cats.instances.list._
import cats.syntax.traverse._
import org.apache.jena.query.{QueryFactory, QuerySolution}
import org.apache.jena.rdfconnection.{RDFConnection, RDFConnectionRemote}

import scala.jdk.javaapi.CollectionConverters



object part11 {

  def main(args: Array[String]) = {

    // Data Model
    case class LocationId(name: String)
    case class Location(id: LocationId, name: String, population: Int)
    case class Attraction(name: String, description: Option[String], location: Location)

    sealed trait PopCultureSubjects
    case class Movie(name: String) extends PopCultureSubjects
    case class Artist(name: String) extends PopCultureSubjects

    // Data Access
    case class TravelGuide(attraction: Attraction, subjects: List[PopCultureSubjects])
    case class ArtistToListenTo(name: String)

    sealed trait AttractionOrdering
    case object ByName extends AttractionOrdering
    case object ByLocationPopulation extends AttractionOrdering

    trait DataAccess {
      def findAttractions(name: String, ordering: AttractionOrdering, limit: Int): IO[List[Attraction]]
      def findArtistsFromLocation(locationId: LocationId, limit: Int): IO[List[Artist]]
      def findMoviesAboutLocation(locationId: LocationId, limit: Int): IO[List[Movie]]
    }

    // Business Logic
    def travelGuide(data: DataAccess, attractionName: String): IO[Option[TravelGuide]] = {
      for {
        attractions <- data.findAttractions(attractionName, ByLocationPopulation, 1)
        guide <- attractions.headOption match {
          case None => IO.pure(None)
          case Some(attraction) =>
            for {
              artists <- data.findArtistsFromLocation(attraction.location.id, 2)
              movies <- data.findMoviesAboutLocation(attraction.location.id, 2)
            } yield Some(TravelGuide(attraction, artists.appendedAll(movies)))
        }
      } yield guide
    }

    def execQuery(connection: RDFConnection)(query: String): IO[List[QuerySolution]] =
      IO.blocking(CollectionConverters.asScala(connection.query(QueryFactory.create(query)).execSelect()).toList)


    def parseAttraction(s: QuerySolution): IO[Attraction] = {
      IO.delay(Attraction(
        name = s.getLiteral("attractionLabel").getString,
        description = if (s.contains("description"))Some(s.getLiteral("description").getString) else None,
        location = Location(id = LocationId(s.getResource("location").getLocalName),
          name = s.getLiteral("locationLabel").getString,
          population = s.getLiteral("population").getInt))
      )
    }

    def getSparqlDataAccess(execQuery: String => IO[List[QuerySolution]]): DataAccess = new DataAccess {

      override def findAttractions(name: String, ordering: AttractionOrdering, limit: Int): IO[List[Attraction]] = {
        val orderBy = ordering match {
          case ByName => "?attractionLabel"
          case ByLocationPopulation => "DESC(?population)"
        }

        val query = s"""
  PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
  PREFIX wdt: <http://www.wikidata.org/prop/direct/>
  PREFIX wd: <http://www.wikidata.org/entity/>
  PREFIX schema: <http://schema.org/>

  SELECT DISTINCT ?attraction ?attractionLabel ?description ?location ?locationLabel ?population
  WHERE {
    ?attraction rdfs:label ?attractionLabel .
    ?attraction wdt:P31 wd:Q570655 .
    ?attraction wdt:P131 ?location .
    ?location rdfs:label ?locationLabel .
    ?location wdt:P1082 ?population .
    OPTIONAL { ?attraction schema:description ?description . }
    FILTER(LANG(?attractionLabel) = "en" && LANG(?description) = "en")
  }
  ORDER BY $orderBy
  LIMIT $limit
"""

        for {
          solutions <- execQuery(query)
          attractions <- solutions.traverse(parseAttraction)
        } yield attractions
      }

      override def findArtistsFromLocation(locationId: LocationId, limit: Int): IO[List[Artist]] = {
        val query = s"""... SELECT DISTINCT ?artist WHERE {...} LIMIT $limit"""
        for {
          solutions <- execQuery(query)
          artists <- solutions.traverse(s => IO.pure(Artist(s.getLiteral("artistName").getString)))
        } yield artists
      }

      override def findMoviesAboutLocation(locationId: LocationId, limit: Int): IO[List[Movie]] = {
        val query = s"""... SELECT DISTINCT ?movie WHERE {...} LIMIT $limit"""
        for {
          solutions <- execQuery(query)
          movies <- solutions.traverse(s => IO.pure(Movie(s.getLiteral("movieName").getString)))
        } yield movies
      }
    }

    // Main
    val connection: RDFConnection = RDFConnectionRemote.create.destination("https://query.wikidata.org/").queryEndpoint("sparql").build
    val wikidata: DataAccess = getSparqlDataAccess(execQuery(connection))
    travelGuide(wikidata, "Yosemite").unsafeRunSync()


  }

}
