package part6

object part6 {

  def main(args: Array[String]) = {

    val rawShows = List("Breaking Bad (2008-2013)", "The Wire (2002-2008)", "Mad Men (2007-2015)")

    case class TvShow(title: String, start: Int, end: Int)

    def sortShows(shows: List[TvShow]): List[TvShow] = {
      shows.sortBy(tvShow => tvShow.end - tvShow.start)
        .reverse
    }

    val shows = List(TvShow("Breaking Bad", 2008, 2013), TvShow("The Wire", 2002, 2008), TvShow("Mad Men", 2007, 2015))


    //    def parseShow(rawShow: String): TvShow = {
    //      val bracketOpen = rawShow.indexOf('(')
    //      val bracketClose = rawShow.indexOf(')')
    //      val dash = rawShow.indexOf('-')
    //      val name = rawShow.substring(0, bracketOpen).trim
    //      val yearStart = Integer.parseInt(rawShow.substring(bracketOpen + 1, dash))
    //      val yearEnd = Integer.parseInt(rawShow.substring(dash + 1, bracketClose))
    //      TvShow(name, yearStart, yearEnd)
    //    }

    //    def extractYearStart(rawShow: String):Option[Int] = {
    //      val bracketOpen = rawShow.indexOf('(')
    //      val dash = rawShow.indexOf('-')
    //      if (bracketOpen != -1 && dash > bracketOpen + 1)
    //        Some(rawShow.substring(bracketOpen + 1, dash))
    //      else
    //        None
    //    }

//    def extractName(rawShow: String): Option[String] = {
//      val bracketOpen = rawShow.indexOf('(')
//      if (bracketOpen > 0)
//        Some(rawShow.substring(0, bracketOpen).trim)
//      else None
//    }

//    def extractYearStart(rawShow: String): Option[Int] = {
//      val bracketOpen = rawShow.indexOf('(')
//      val dash = rawShow.indexOf('-')
//      for {
//        yearStr <- if (bracketOpen != -1 && dash > bracketOpen + 1)
//          Some(rawShow.substring(bracketOpen + 1, dash))
//        else
//          None
//        year <- yearStr.toIntOption
//      } yield year
//    }

    def extractYearEnd(rawShow: String): Option[Int] = {
      val dash = rawShow.indexOf('-')
      val bracketClose = rawShow.indexOf(')')
      for {
        yearStr <- if (dash != -1 && bracketClose > dash + 1) Some(rawShow.substring(dash + 1, bracketClose))
        else None
        year <- yearStr.toIntOption}
      yield year
    }

//    def parseShow(rawShow: String): Option[TvShow] = {
//      for {
//        name <- extractName(rawShow)
//        yearStart <- extractYearStart(rawShow)
//        yearEnd <- extractYearEnd(rawShow)
//      } yield TvShow(name, yearStart, yearEnd)
//    }

    def extractSingleYear(rawShow: String): Option[Int] = ???

//    def extractAnyYear(rawShow: String): Option[Int] = extractYearStart(rawShow).orElse(extractYearEnd(rawShow)).orElse(extractSingleYear(rawShow))
//
//
//    //    val invalidRawShow = "Breaking Bad, 2008-2013"
//    //    println(parseShow(invalidRawShow))
//
//    def addOrResign(parsedShows: Option[List[TvShow]], newParsedShow: Option[TvShow]): Option[List[TvShow]] = for {
//      shows <- parsedShows
//      parsed <- newParsedShow
//    } yield shows.appended(parsed)

    def yearIntOption(year: String) = year.toIntOption.toRight(s"Can't parse $year")

    def extractYearStart(rawShow: String): Either[String, Int] = {
      val bracketOpen = rawShow.indexOf('(')
      val dash = rawShow.indexOf('-')
      val yearStrEither =
        if (bracketOpen != -1 && dash > bracketOpen + 1) Right(rawShow.substring(bracketOpen + 1, dash))
        else Left(s"Can't extract start year from $rawShow")
      yearStrEither.map(yearIntOption).flatten
    }

//    def extractName(rawShow: String): Either[String, String] = {
//      val bracketOpen = rawShow.indexOf('(')
//      if (bracketOpen > 0)
//        Right(Some(rawShow.substring(0, bracketOpen).trim))
//      else
//        Left(s"Can't extract name from $rawShow")
//    }

//    def parseShows(rawShows: List[String]):Option[List[TvShow]] = {
//      val initialResult: Option[List[TvShow]] = Some(List.empty)
//      rawShows.map(parseShow).foldLeft(initialResult)(addOrResign)
//    }
//
//    def addOrResign(parsedShows: Option[List[TvShow]],newParsedShow: Option[TvShow]):Option[List[TvShow]] = {
//      for {
//        shows <- parsedShowsparsedShow
//        parsedShow <- newParsedShow
//      }
//      yield shows.appended(parsedShow)
//    }


  }

}
