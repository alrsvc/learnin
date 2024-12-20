package part10

import cats.effect.unsafe.IORuntime
import cats.effect.{ExitCode, IO, IOApp, Ref}
import cats.implicits.catsSyntaxParallelSequence1
import org.scalatest.time.SpanSugar.convertIntToGrainOfTime

import scala.util.Random

object part10_excercises extends IOApp {

  def run(args: List[String]): IO[ExitCode] = {

    castTwoDies.flatMap(result => IO(println(result))).as(ExitCode.Success)
  }

  def oneHundredCasts: IO[Unit] = List.fill(100)(IO.sleep(1.second).flatMap(_ => castTheDie())).parSequence.map(_.sum)

  def DiesCast100(): IO[Int] = {
    for {
      storedCasts <- Ref.of[IO, Int](0)
      singleCast = castTheDie().flatMap(result =>if (result == 6) storedCasts.update(_ + 1) else IO.unit)
      _ <- List.fill(100)(singleCast).parSequence
      casts <- storedCasts.get
    } yield casts
  }

  def castThreeDies(): IO[List[Int]] = {
    for {
      storedCasts <- Ref.of[IO, List[Int]](List.empty)
      singleCast = castTheDie().flatMap(result => storedCasts.update(_.appended(result)))
      _<- List.fill(3)(singleCast).parSequence
      casts <- storedCasts.get
    } yield casts
  }

  def castTwoDies(): IO[List[Int]] = {
    for {
      storedCasts <- Ref.of[IO, List[Int]](List.empty)
      singleCast = castTheDie().flatMap(result => storedCasts.update(_.appended(result)))
      _ <- List(singleCast, singleCast).parSequence
      casts <- storedCasts.get
    } yield casts
  }

  def waitForCast: IO[Unit] = {
    for {
      _<- IO.sleep(1.second)
      result <- List(castTheDie(), castTheDie()).parSequence
    } yield result.sum
  }

  def castTheDieTwice(): IO[Int] = {
    for {
      firstCast <- castTheDie()
      secondCast <- castTheDie()
    } yield firstCast + secondCast
  }

  def castTheDieImpure(): Int = {
    println("The die is cast")
    val random = new Random()
    random.nextInt(6) + 1
  }

  def castTheDie(): IO[Int] = IO.delay(castTheDieImpure())

}
