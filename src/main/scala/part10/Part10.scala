package part10

import cats.effect.{IO, Ref}
import cats.effect.unsafe.implicits.global
import cats.implicits.catsSyntaxParallelSequence1
import fs2.Stream
import part10.model._

object Part10 {

  def main(args: Array[String]): Unit = {
    val checkIns: Stream[IO, City] = Stream("Moscow", "New York", "London", "New York", "Moscow").covary[IO]
    processCheckIns(checkIns).unsafeRunSync()
  }

  def topCities(cityCheckIns: Map[City, Int]): List[CityStats] = {
    cityCheckIns.toList
      .map(_ match {
        case (city, checkIns) => CityStats(city, checkIns)
      })
      .sortBy(_.checkIns)
      .reverse
      .take(3)
  }

  def updateRanking(storedCheckIns: Ref[IO, Map[City, Int]],storedRanking: Ref[IO, List[CityStats]]): IO[Unit] = {
    for {
      newRanking <- storedCheckIns.get.map(topCities)
      _<- storedRanking.set(newRanking)
      _<- updateRanking(storedCheckIns, storedRanking)} yield ()
  }

  def processCheckIns(checkIns: Stream[IO, City]): IO[ProcessingCheckIns] = {
    for {
      storedCheckIns <- Ref.of[IO, Map[City, Int]](Map.empty)
      storedRanking <- Ref.of[IO, List[CityStats]](List.empty)
      rankingProgram = updateRanking(storedCheckIns, storedRanking)
      checkInsProgram = checkIns.evalMap(storeCheckIn(storedCheckIns)).compile.drain
      fiber <- List(rankingProgram, checkInsProgram).parSequence.start
    } yield ProcessingCheckIns(storedRanking.get, fiber.cancel)
  }

  def storeCheckIn(storedCheckIns: Ref[IO, Map[City, Int]])(city: City): IO[Unit] = {
    storedCheckIns.update(_.updatedWith(city)(_ match {
      case None=> Some(1)case Some(checkIns) => Some(checkIns + 1)
    }))
  }

}

object model {
  type City = String

  object City {
    def apply(name: String): City = name
    def name(city: City): String = city
  }


  case class CityStats(city: City, checkIns: Int)

  case class ProcessingCheckIns(currentRanking: IO[List[CityStats]], stop: IO[Unit])
}


