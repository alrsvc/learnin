package part7

object part7 {

  def main(args: Array[String]) = {

    case class User(name: String)
    case class Artist(name: String)
    case class Song(artist: Artist, title: String)
    case class Location(name: String)

    sealed trait MusicGenre
    case object MetalMusic extends MusicGenre
    case object PopMusic extends MusicGenre
    case object FolkMusic extends MusicGenre

    sealed trait PlayListKind
    case class CuratedByUser(user: User) extends PlayListKind
    case class BasedOnArtist(artist: Artist) extends PlayListKind
    case class BasedOnGenres(genres: Set[MusicGenre]) extends PlayListKind

    case class Playlist(name: String, kind: PlayListKind, songs: List[Song])

    val xArtist = Artist("X")
    val playlist1 = Playlist(
      "This is Foo Fighters",
      BasedOnArtist(xArtist),
      List(Song(xArtist, "Breakout"),
        Song(xArtist, "Learn To Fly")
      ))

    val playlist2 = Playlist(
      "Deep Focus",
      BasedOnGenres(Set(MetalMusic, PopMusic)),
      List(Song(Artist("Daft Punk"), "One More Time"),
        Song(Artist("The Chemical Brothers"), "Hey Boy Hey Girl")
      ))

    val playlist3 = Playlist(
      "My Playlist", CuratedByUser(User("Michał Płachta")),
      List(Song(xArtist, "My Hero"),
        Song(Artist("Iron Maiden"), "The Trooper")))

    def gatherSongs(playlists: List[Playlist], artist: Artist, genre: MusicGenre): List[Song] =
      playlists.foldLeft(List.empty[Song])((songs, playlist) => {
        val matchingSongs = playlist.kind match {
          case CuratedByUser(user) => playlist.songs.filter(_.artist == artist)
          case BasedOnArtist(playlistArtist) => if (playlistArtist == artist) playlist.songs else List.empty
          case BasedOnGenres(genres) => if (genres.contains(genre)) playlist.songs else List.empty
        }
        songs.appendedAll(matchingSongs)
      }
      )

    sealed trait SearchCondition
    case class SearchByGenre(genres: List[MusicGenre]) extends SearchCondition
    case class SearchByOrigin(locations: List[Location]) extends SearchCondition
    case class SearchByActiveYears(start: Int, end: Int) extends SearchCondition
    case class SearchByLength(from: Int, until: Int) extends SearchCondition

    //    def searchArtists(artists: List[Artist],requiredConditions: List[SearchCondition]): List[Artist] =artists.filter(artist =>requiredConditions.forall(condition => ???))

    //    case class Artist(name: String, genre: MusicGenre,origin: Location, yearsActive: YearsActive)

//    def searchArtists(artists: List[Artist], requiredConditions: List[SearchCondition]): List[Artist] =
//      artists.filter(artist => requiredConditions.forall(condition =>
//        condition match {
//          case SearchByGenre(genres)=> genres.contains(artist.genre)
//          case SearchByOrigin(locations)=> locations.contains(artist.origin)
//          case SearchByActiveYears(start, end) => wasArtistActive(artist, start, end)
//        }
//      ))


    val artists = List(Artist("A"), Artist("B"), Artist("C"))

//    val rty = searchArtists(
//      artists,
//      List(
//        SearchByGenre(List(PopMusic)),
//        SearchByOrigin(List(Location("England"))),
//        SearchByActiveYears(1950, 2022))
//    )

    println(List(
      SearchByGenre(List(PopMusic)),
      SearchByOrigin(List(Location("England"))),
      SearchByActiveYears(1950, 2022))
    )

    case class Period(start: Int, end: Int)

    sealed trait ActiveYears
    case class Active(since: Int, previous: List[Period]) extends ActiveYears
    case class Past(periods: List[Period]) extends ActiveYears


    //    val artists = List(
    //      Artist("Metallica", "Heavy Metal", "U.S.", 1981, true, 0),
    //      Artist("Led Zeppelin", "Hard Rock", "England", 1968, false, 1980),
    //      Artist("Bee Gees", "Pop", "England", 1958, false, 2003)
    //    )

    //    case class Artist(name: String, genre: String, origin: String)
    //    case class Artist(name: String, genre: String, origin: String, yearsActiveStart: Int, isActive: Boolean, yearsActiveEnd: Int)

    //    def searchArtists(
    //                       artists: List[Artist],
    //                       genres: List[String],
    //                       locations: List[String],
    //                       searchByActiveYears: Boolean,
    //                       activeAfter: Int,
    //                       activeBefore: Int
    //                     ): List[Artist] =
    //      artists.filter(artist => (genres.isEmpty || genres.contains(artist.genre))
    //        && (locations.isEmpty || locations.contains(artist.origin))
    //        && (!searchByActiveYears || ((artist.isActive || artist.yearsActiveEnd >= activeAfter)
    //        && (artist.yearsActiveStart <= activeBefore))))

    //    println(searchArtists(artists, List("Pop"), List("England"), true, 1950, 2022))

    //    case class Artist(name: String, genre: Genre, origin: Location, yearsActiveStart: Int, isActive: Boolean, yearsActiveEnd: Int)

    //    val a = Artist("Led Zeppelin", Genre("Hard Rock"), Location("England"), YearsActiveStart(1968), true, Some(1980))

    //    case class Artist(name: String, genre: String, origin: Location, yearsActiveStart: Int, yearsActiveEnd: Option[Int])

    //    def searchArtists(artists: List[Artist], genres: List[String], locations: List[String], searchByActiveYears: Boolean, activeAfter: Int, activeBefore: Int): List[Artist] =
    //      artists.filter(artist => (
    //        genres.isEmpty || genres.contains(artist.genre))
    //        && (locations.isEmpty || locations.contains(artist.origin.name))
    //        && (!searchByActiveYears || ((artist.yearsActiveEnd.forall(_ >= activeAfter))
    //        && (artist.yearsActiveStart <= activeBefore))))
    //
    //  }

    //  case class User(name: String, city: Option[String], favoriteArtists: List[String])
    //
    //  val users = List(
    //    User("Alice", Some("Melbourne"), List("Bee Gees")),
    //    User("Bob", Some("Lagos"), List("Bee Gees")),
    //    User("Eve", Some("Tokyo"), List.empty),
    //    User("Mallory", None, List("Metallica", "Bee Gees")),
    //    User("Trent", Some("Buenos Aires"), List("Led Zeppelin")))

    //Excercises
    //  def noCityOrLiveInMelbourne(users: List[User]): List[User] = users.filter(user => user.city.isEmpty || user.city.contains("Melbourne"))
    //  def liveInLagos(user: List[User]): List[User] = users.filter(_.city.contains("Lagos"))
    //  def loveBeeGees(users: List[User]): List[User] = users.filter(_.favoriteArtists.contains("Bee Gees"))
    //  def cityStartWithT(users: List[User]): List[User] = users.filter(_.city.exists(_.startsWith("T")))
    //  def longerThen8Characters(users: List[User]): List[User] = users.filter(_.favoriteArtists.exists(_.length > 8))
    //  def nameStartWithM(users: List[User]): List[User] = users.filter(_.favoriteArtists.exists(_.startsWith("M")))
    //
    //  def existsAndStartWith(artists: List[String], char: String): Boolean = artists.exists(_.startsWith(char))
    //  def nameStartWithMREFACTORED(users: List[User], char: String): List[User] = users.filter(user => existsAndStartWith(user.favoriteArtists, char))

    //  def f1(users: List[User]): List[User] =users.filter(_.city.forall(_ == "Melbourne"))

    //  case class PeriodInYears(start: Int, end: Option[Int])

    //  println(noCityOrLiveInMelbourne(users))
    //  println(liveInLagos(users))
    //  println(loveBeeGees(users))
    //  println(cityStartWithT(users))
    //  println(nameStartWithM(users))

    //Enum alternative
    //  sealed trait MusicGenre
    //
    //  case object HeavyMetal extends MusicGenre
    //
    //  case object Popcase extends MusicGenre
    //
    //  case object HardRock extends MusicGenre
    //
    //  sealed trait YearsActive
    //  case class StillActive(since: Int) extends YearsActive
    //  case class ActiveBetween(start: Int, end: Int) extends YearsActive
    //  case class PeriodInYears(start: Int, end: Option[Int]) extends YearsActive
    //
    //  case class Artist(name: String, genre: MusicGenre, origin: Location, yearsActive: PeriodInYears)
    //
    //  val artists = List(
    //    Artist("Metallica", HeavyMetal, Location("U.S."), PeriodInYears(1981, None)),
    //    Artist("Led Zeppelin", HardRock, Location("England"), PeriodInYears(1968, Some(1980))),
    //    Artist("Bee Gees", Popcase, Location("England"), PeriodInYears(1958, Some(2003))))
    //
    //  Artist("Metallica", HeavyMetal, Location("U.S."), PeriodInYears(1981, None))
    //  Artist("Led Zeppelin", HardRock, Location("England"), PeriodInYears(1968, Some(1980)))


    //  def searchArtists(artists: List[Artist], genres: List[String],locations: List[String], searchByActiveYears: Boolean,activeAfter: Int, activeBefore: Int): List[Artist] =
    //    artists.filter(artist =>(genres.isEmpty || genres.contains(artist.genre))
    //      && (locations.isEmpty || locations.contains(artist.origin))
    //      && (!searchByActiveYears || ((artist.isActive || artist.yearsActiveEnd >= activeAfter)
    //      && (artist.yearsActiveStart <= activeBefore))))


    //  def activeLength(artist: Artist, currentYear: Int): Int = artist.yearsActive match {
    //    case StillActive(since) => currentYear - since
    //    case ActiveBetween(start, end) => end - start
    //  }

  }
}


//case class Location(val value: String) {
//  def name: String = value
//}
//
//case class Genre(val value: String) {
//  def genre: String = value
//}
//
//case class YearsActiveStart(val value: Int) {
//  def genre: Int = value
//}
//
//case class YearsActiveEnd(val value: Int) {
//  def genre: Int = value
//}
//

