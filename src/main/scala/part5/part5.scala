package part5

object part5 {

  def main(args: Array[String]) = {

//    case class Book(title: String, authors: List[String])
//
//    val books = List(
//      Book("FP in Scala", List("Chiusano", "Bjarnason")),
//      Book("The Hobbit", List("Tolkien")),
//      Book("Modern Java in Action", List("Urma", "Fusco", "Mycroft")))
//
//    val withScala = books
//      .map(_.title)
//      .filter(_.contains("Scala"))
//      .size
//
//    println(withScala)
//
//    case class Movie(title: String)
//
//    def bookAdaptations(author: String): List[Movie] =
//      if (author == "Tolkien")
//        List(Movie("An Unexpected Journey"),
//          Movie("The Desolation of Smaug"))
//      else List.empty
//
//    val authors = books.map(_.authors).flatten
//
//    println(authors.map(bookAdaptations).flatten)
//
//    val adaptted = books.flatMap(_.authors).flatMap(bookAdaptations)
//    println(adaptted)
//
//
//    def recommendedBooks(friend: String): List[Book] = {
//      val scala = List(
//        Book("FP in Scala", List("Chiusano", "Bjarnason")),
//        Book("Get Programming with Scala", List("Sfregola")))
//      val fiction = List(Book("Harry Potter", List("Rowling")),
//        Book("The Lord of the Rings", List("Tolkien")))
//      if(friend == "Alice") scala
//      else if(friend == "Bob") fiction
//      else List.empty
//    }
//
//    val friends = List("Alice", "Bob", "Charlie")
//    val friendsBooks = friends.map(recommendedBooks)
//    List(
//      List(Book("FP in Scala", List("Chiusano", "Bjarnason")),
//        Book("Get Programming with Scala",List("Sfregola"))),
//      List(Book("Harry Potter", List("Rowling")),
//        Book("The Lord of the Rings", List("Tolkien"))),List())
//
//    println(friendsBooks.flatten)
//
//    def recommendationFeed(books: List[Book]) = {
//      books.flatMap(book =>
//        book.authors.flatMap(author =>
//          bookAdaptations(author).map(movie =>
//        s"You may like ${movie.title}, " +
//        s"because you liked $author's ${book.title}")))
//    }

    // Flat map practicing
    case class Point(x: Int, y: Int)


//    val xs = List(1)
//    val ys = List(-2, 7)
//
//    val tt = for {
//      x <- xs
//      y <- ys
//    } yield Point(x, y)
//
//    println(tt)

//    case class Point3d(x: Int, y: Int, z: Int)
//
//    val xs = List(1)
//    val ys = List(-2, 7)
//    val zs = List(3, 4)
//
//    val dd = for {
//      x <- xs
//      y <- ys
//      z <- zs
//    } yield Point3d(x, y, z)
//
//    //Nested
//    val nested = xs.flatMap(x =>
//      ys.flatMap(y =>
//       zs.map(z => Point3d(x, y, z))
//      )
//    )
//
//    print(nested)

//    val points= List(Point(5, 2), Point(1, 1))
//    val radiuses = List(2, 1)
//
//    def isInside(point: Point, radius: Int): Boolean = {radius * radius >= point.x * point.x + point.y * point.y}
//
//    val rty = for {
//         r <- radiuses
//         point <- points.filter(p => isInside(p, r))
//         }
//    yield s"$point is within a radius of $r: " + isInside(point, r).toString
//
//    println(rty)
//
//    def insideFilter(point: Point, r: Int): List[Point] =
//      if(isInside(point, r)) List(point) else List.empty
//
//    val ppp = for {r<- radiuses
//         point<- points
//         inPoint <-insideFilter(point,r)
//         } yield s"$inPoint is within a radius of $r"
//
//    println(ppp)
//
//
//    def validateRadius(radius: Int): List[Int] =
//      if (radius > 0) List(radius) else List.empty
//
//    val riskyRadiuses =List(-10,0,2)
//
//   val m = for {
//      r <- riskyRadiuses
//      validRadius <- validateRadius(r)
//      point <- points
//      inPoint <-insideFilter(point, validRadius)
//    }  yield s"$inPoint is within a radius of $r"
//
//    println(m)

    val we = for {
      x <- List(1, 2, 3)
      y <- Set(5)
    } yield x * y

    val op = for {
      x <- Set(1,2,3)
      y <- List(1)
    } yield x * y

    val po = for {
      x <- List(1, 2, 3)
      y <- Set(1)
      z <- Set(0)
    } yield x * y * z


    case class Event(name: String, start: Int, end: Int)

//    def parse(name: String, start: Int, end: Int): Event =
//      if (name.size > 0 && end < 3000 & start <= end)Event(name, start, end)
//    else
//      null

//    def parse(name: String,start: Int, end: Int):Option[Event]= {
//      if (name.size > 0 && end < 3000 & start <= end)
//        Some(Event(name, start, end))
//      else
//        None
//    }

    def validateName(name: String): Option[String] = if (name.size > 0) Some(name) else None
    def validateEnd(end: Int): Option[Int] = if (end < 3000) Some(end) else None
    def validateStart(start: Int, end: Int): Option[Int] = if (start <= end) Some(start) else None

//    def parse(name: String,start: Int, end: Int): Option[Event] = for {
//      validName <- validateName(name)
//      validEnd <- validateEnd(end)
//      validStart <- validateStart(start, end)}
//    yield Event(validName, validStart, validEnd)

    def validateLength(start: Int, end: Int, minLength: Int): Option[Int] = if (end - start >= minLength ) Some(end - start) else None

    def parseLongEvent(name: String,start: Int, end: Int, minLength: Int): Option[Event] = for {
      validName <- validateName(name)
      validEnd <- validateEnd(end)
      validStart <- validateStart(start, end)
      validLength <- validateLength(start, end, minLength)
    }
    yield Event(validName, validStart, validEnd)


  }

}
