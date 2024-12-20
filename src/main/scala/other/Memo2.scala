package other

import cats.effect.IO
import cats.effect.unsafe.implicits.global

import java.nio.file.Paths
import scala.io.Source
import scala.util.matching.Regex

object Memo2 {

  def main(args: Array[String]) = {

    def show(x: Any, switcher: Boolean): Unit = if(switcher) println(x)

    // Variables
    var x0 = 0
    var x0_1: String = "0"
    val x1 = 10
    val x2: Int = 20

    // Functions
    val x3: Int => Int = {x => x * 2}
    val x3_1: Int => Int = {_ * 2}
    val x4 = (x: Int => Int) => x
    val x5 = (x: Int, y: Int) => x * y
    val x6 = () => "...sup?"
    val builder = (value: String) => (xValue: String, yValue: String) => s"$value : $xValue  + $yValue"
    val builderUsage = builder("A")
    show(builderUsage("B", "C"), false)

    // Methods
    def add(x: Int, y: Int): Int = x * y
    def addWithMultiplier(x: Int, y: Int)(multiplier: Int) = (x * y) * (multiplier * 0.2)
    def name: String = "nameString"
    def squareString(amount: Double): String = {
      val square = amount * amount
      amount.toString
    }
    def someLikeSomething(x: Int => String, y: Int => String => Double): String = {
      val resultX = x(10)
      val resultY = y(20)("Hello")
      resultX + " , " + resultY
    }
    def parent(x: String) = {
      def child(y: String, z: String): String = {
        x + y  + z
      }
      child("b", "s")
    }
    show(parent("Z"), false)
    def calculate(input: => Int) = input * 37
    show(calculate(20), false)
    def id[T](x: T) = x
    val q = id(10.0)
    show(q, false)


    val x: Int => String = (n: Int) => s"Number: $n"
    val y: Int => String => Double = (n: Int) => (s: String) => s.length * n.toDouble



    // Classes
    class Greeter(prefix: String, suffix: String = "") {

      private var _x = 0

      def greet(name: String, showInConsole: Boolean): Unit = if (showInConsole) println(prefix + " " + suffix + ". From name value: " + name)

    }

    val greeteng = new Greeter("X", "Y")
    greeteng.greet("Example", false)

    // Case Classes
    case class Point(x: Double, y: Double, z: Double)
    val point = Point(10.0, 20.0, 30.0)

    // Objects
    object IncrementFactory {
      private var counter = 0
      def create(): Int = {
        counter += 1
        counter
      }
    }

    val if1 = IncrementFactory.create()
    val if2 = IncrementFactory.create()
    val if3 = IncrementFactory.create()

    show(if3, false)

    // Traits
    trait Shalom {
      def call(name: String): Unit = name
    }

    // Tuples
    val ingredient = ("Some", 10.0)
    show(ingredient._1 + " " + ingredient._2, false)

    // High-order functions
    val salaries = List(100, 200, 300)
    val doubleSalary = (x: Int) => x * 2
    val newSalary = salaries.map(doubleSalary)
    show(newSalary, false)

    //Pattern matching
    sealed trait Notification
    case class Email(value: String) extends Notification
    case class SMS(value: String) extends Notification
    case class SomeNotification(value: String) extends Notification

    def showNotification(notification: Notification, switcher: Boolean): Unit = {
      notification match {
        case Email(value) => show(s"Email has been executed with value $value", switcher)
        case SMS(value) => show(s"SMS has been executed with value $value", switcher)
        case _ => show("No idea", switcher)
      }
    }

    showNotification(SMS("AS"), false)

    //Regular Expression
    val numberPattern: Regex = "[0-9]".r

    numberPattern.findFirstIn("ASD1") match {
      case Some(_) => show("Some", false)
      case None => show("None", false)
    }

    // For Comprehensions
    case class Book(name: String, authors: List[String])
    val books = List(
      Book("Scala for Beginners", List("Author A", "Author B")),
      Book("Advanced Scala", List("Author C"))
    )

    val comprehensionResult: List[String] = for {
      book <- books
      author <- book.authors
    } yield s"Book name is: ${book.name} , author: $author"

    show(comprehensionResult, false)

    // Collections
    val z1 = (x: Int) => x * 3
    val collect1 = List(List(10, 20), List(30, 40), List(50, 77, 90, 113))
    val collect2 = List(2, 5, 7, 9, 10, 13)
    show(collect2.map(z1), false)
    show(collect1.flatMap(word => word.filter(a => a > 30)), false)
    show(collect2.head, false)
    show(collect2.last, false)
    show(collect2.headOption, false)
    show(collect2.lastOption, false)
    show(collect2.tail, false)

    //IO
    val hello: IO[Unit] = IO(show("Hello, world!", false))
    hello.unsafeRunSync()

    val readFile: IO[String] = IO {
      val filePath = Paths.get(getClass.getResource("/sample.txt").toURI)
      val source = Source.fromFile(filePath.toFile)
      try source.getLines().mkString("\n") finally source.close()
    }

    val content: String = readFile.unsafeRunSync()
    println(content)

    def url(a: String): String = s"https://api.chucknorris.io/jokes/$a"

    val booksList = List("A", "B", "C", "DA", "F", "E", "EA")

    def addDiscount(book: String): String = if (book.contains("A")) book + " disscount!" else book

    def findBook(books: List[String]) = books.map(addDiscount)

    show(findBook(booksList), true)
    
  }

}
