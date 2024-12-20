package other

import scala.util.Random
import cats.effect.{IO, IOApp, Ref}
import cats.effect.unsafe.IORuntime
import cats.effect.unsafe.implicits.global

import scala.util.matching.Regex

object Check {

    def main(args: Array[String]) = {

      // Variables
      val val1 = 10
      var var1 = 20
      var var2 = val1 + var1
      val val3: Double = 10.0

      //functions
      val func1 = (x: Int) => x * 2 //!
      def defFunc(x: Int) = x * 2 //!

      val square: Double => Double = (x: Double) => x * x
      val square2: (Double, Double, Double) => Double = (x: Double, y: Double, z: Double) => x * y * z
      val doubleSquare: Double => Double => Int => Int = (x: Double) => (y: Double) => (z: Int) => (x * y * z).toInt
      def func2(x: Double): String = x.toString + " booya!"

      var counter = 0

      def byValueCounter(x: String): String = {
        counter += 1
        println(s"Evaluating byValueCounter: $x, counter: $counter")  // Evaluated once
        x
      }

      def byNameCounter(x: => String): String = {
        counter += 1
        println(s"Evaluating byNameCounter: $x, counter: $counter")  // Evaluated every time
        x
      }



      type Location = String
      def locationName(name: Location) = name + " !"
      def multiplier(x: Int, y: Int): Int = x * y
      def rtn(value: Int): String = value.toString + " is your value!"

      // Classes

      class Greeter(prefix: String, suffix: String) {
        def greet(name: String): Unit = {
          println(name + prefix + suffix)
        }
      }

      val gt = new Greeter(" One", " Two")



      class Point(var x: Int, var y: Int = 0) {

        private var _x: Int = 90

        def nothing(): Unit = println(x.toString + y.toString + _x.toString)

        override def toString: String = s"$x, $y"
      }


      val ads = new Point(10, 20)



      val examp = List(1, 2, 3, 4, 5)


      def promotion(salaries: List[Int], promotionFunction: Int => Int): List[Int] = salaries.map(promotionFunction)

      def smallPromotion(salaries: List[Int]) = promotion(salaries, salary => salary * 2)

      def urlBUilder(domainName: String): (String, String) => String = {
        val schema = domainName
        (endpoint: String, query: String) => s"$endpoint $query $schema"
      }

      def urlBUilderX = urlBUilder("ASAHI")
      val url = urlBUilderX("endpointik", "querick")

      def parent(x: Int): Int = {
        def child(x: Int, y: Int): Int = {
          x * y
        }

        child(x, 2)
      }

      val numbers = List(10, 5)
      val minValue = numbers.foldLeft(5) { (n, m) => n * m}

      def matchTest(x: Int): String = x match {
        case 1 => "one"
        case 2 => "two"
        case _ => "other"
      }


    }

  val numberPattern: Regex = "[0-9]".r


  def saveContactInformation(contact: String): Unit = {
    import scala.util.matching.Regex

    val emailPattern: Regex = """^(\w+)@(\w+(.\w+)+)$""".r
    val phonePattern: Regex = """^(\d{3}-\d{3}-\d{4})$""".r

    contact match {
      case emailPattern(localPart, domainName, _) =>
        println(s"Hi $localPart, we have saved your email address.")
      case phonePattern(phoneNumber) =>
        println(s"Hi, we have saved your phone number $phoneNumber.")
      case _ =>
        println("Invalid contact information, neither an email address nor phone number.")
    }
  }


  val userBase = List(
    User("Travis", 28),
    User("Kelly", 33),
    User("Jennifer", 44),
    User("Dennis", 23))

  val twentySomethings =
    for (user <- userBase if user.age >= 20 && user.age < 30) yield user.name



  def createGrid(columns: Int, rows: Int): Seq[(Int, Int)] = {
    for {
      col <- 0 until columns  // Range for columns
      row <- 0 until rows     // Range for rows
    } yield (col, row)
  }

  abstract class Animal {
    def name: String
  }

  abstract class Pet extends Animal {}

  class Cat extends Pet {
    override def name: String = "Cat"
  }

  class Dog extends Pet {
    override def name: String = "Dog"
  }

  class PetContainer[P <: Pet](p: P) {
    def pet: P = p
  }

  val dogContainer = new PetContainer[Dog](new Dog)
  val catContainer = new PetContainer[Cat](new Cat)



  trait Buffer {
    type T
    val element: T
  }

  abstract class SeqBuffer extends Buffer {
    type U
    type T <: Seq[U]
    def length = element.length
  }

  abstract class IntSeqBuffer extends SeqBuffer {
    type U = Int
  }

  def newIntSeqBuf(elem1: Int, elem2: Int): IntSeqBuffer =
    new IntSeqBuffer {
      type T = List[U]
      val element = List(elem1, elem2)
    }
  val buf = newIntSeqBuf(7, 8)

  def list = List(1,2,3)

  val fink = list.appended(4).appendedAll(list)

  val optA: Option[String] = Some("Example")



  def toTuple(optA: Option[String]): (String, String) = {
    (optA.zip(optA).get._1 + " first!", optA.zip(optA).get._2)
  }


  //IO
  val hello: IO[Unit] = IO(println("Hello, world!"))
  hello.unsafeRunSync()



}

case class Email(value: String) {

  val emailPattern: Regex = """^(\w+)@(\w+(.\w+)+)$""".r

  def isValid: Boolean = emailPattern.matches(value)

  def test = {
    isValid match {
      case true => println("WOW")
      case false => println("NOT WOW")
      case _ => println("_")
    }
  }

}

case class User(name: String, age: Int)