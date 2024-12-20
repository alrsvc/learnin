package part9

import cats.effect.IO
import fs2.Stream

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.FiniteDuration
import scala.util.Random

object part9 {

  case class Currency(name: String)

  def main(args: Array[String]) = {

    val noRates: Map[Currency, BigDecimal] = Map.empty
    val usdRates = Map(Currency("EUR") -> BigDecimal(0.82))
    val eurRates: Map[Currency, BigDecimal] = Map(Currency("USD") -> BigDecimal(1.22), Currency("JPY") -> BigDecimal(126.34))

    val m1: Map[String, String] = Map("key" -> "value")
    val m2: Map[String, String] = m1.updated("key2", "value2")
    val m3: Map[String, String] = m2.updated("key2", "another2")
    val m4: Map[String, String] = m3.removed("key")
    val valueFromM3: Option[String] = m3.get("key")
    val valueFromM4: Option[String] = m4.get("key")

    val updatedUsdRates = usdRates.updated(Currency("EUR"), BigDecimal(0.9))
    val removedUsdRates = usdRates.removed(Currency("EUR"))
    val usdRateOption = usdRates.get(Currency("EUR"))
    val nonExistentRateOption = usdRates.get(Currency("JPY"))


    def naiveTrending(rates: List[BigDecimal]): Boolean = {
      rates(0) < rates(1) && rates(1) < rates(2)
    }

    case class RatePair(previousRate: BigDecimal, rate: BigDecimal)

    val ratePairs: List[(BigDecimal, BigDecimal)] = List((BigDecimal(0.81), BigDecimal(0.82)),(BigDecimal(0.82), BigDecimal(0.83)))

    val tuple: (BigDecimal, BigDecimal) = (BigDecimal(2), BigDecimal(1))

    val caseClass: RatePair = RatePair(BigDecimal(2), BigDecimal(1))

    val ints: List[Int]= List(1, 2, 3)

    val strings: List[String] = List("a", "b", "c")

//    val rates = List(BigDecimal(0.81), BigDecimal(0.82), BigDecimal(0.83))
//    val rates2 = List(BigDecimal(0.81), BigDecimal(0.82), BigDecimal(0.83))
//    val zipping = rates.zip(rates)
//    val zipDrop = rates2.zip(rates2.drop(1))

    def castTheDieImpure(): Int = {
      println("The die is cast")
      val random = new Random()
      random.nextInt(6) + 1
    }

    def castTheDie(): IO[Int] = IO.delay(castTheDieImpure())

    val infiniteDieCasts: Stream[IO, Int] = Stream.eval(castTheDie()).repeat
    val oddNumbers = infiniteDieCasts.filter(_ % 2 != 0).take(3).compile.toList
    val firstSixAndDoubled = infiniteDieCasts.take(5).map(x => if (x == 6) 6 * 2 else x).compile.toList
    val getSum = infiniteDieCasts.take(3).compile.toList.map(_.sum)
    val dieCast = infiniteDieCasts.filter(_ == 5).take(1).append(infiniteDieCasts.take(2)).compile.toList
    val discard100 = infiniteDieCasts.take(100).compile.drain
    val threeCasts = infiniteDieCasts.take(3).append(infiniteDieCasts.take(3).map(_ * 3)).compile.toList


    def trending(rates: List[BigDecimal]): Boolean = {
      rates.size > 1 &&rates.zip(rates.drop(1)).forall(ratePair => ratePair match {
        case (previousRate, rate) => rate > previousRate
      })
    }

    def extractSingleCurrencyRate(currencyToExtract: Currency)(table: Map[Currency, BigDecimal]): Option[BigDecimal] = {
      table.filter(kv =>kv match {case (currency, rate) =>
        currency == currencyToExtract}).values.headOption
    }

    val usdExchangeTables = List(
      Map(Currency("EUR") -> BigDecimal(0.88)),
      Map(Currency("EUR") -> BigDecimal(0.89), Currency("JPY") -> BigDecimal(114.62)),
      Map(Currency("JPY") -> BigDecimal(114))
    )

    usdExchangeTables.map(extractSingleCurrencyRate(Currency("EUR")))

    def exchangeRatesTableApiCall(currency: String): Map[String, BigDecimal] = ???

    def exchangeTable(from: Currency): IO[Map[Currency, BigDecimal]] = {
      IO.delay(exchangeRatesTableApiCall(from.name)).map(table => table.map(kv =>kv match {case (currencyName, rate) => (Currency(currencyName), rate)}))
    }

    def retry[A](action: IO[A], maxRetries: Int): IO[A] = {
      List
        .range(0, maxRetries)
        .map(_ => action)
        .foldLeft(action)((program, retryAction) => {
          program.orElse(retryAction)
        })
    }

    def currencyRate(from: Currency, to: Currency): IO[BigDecimal] = {
      for {
        table <- retry(exchangeTable(from), 10)
        rate <- extractSingleCurrencyRate(to)(table) match {
          case Some(value) => IO.pure(value)
          case None        => currencyRate(from, to)
        }
      } yield rate
    }

    def lastRates(from: Currency, to: Currency, n: Int): IO[List[BigDecimal]] = {
      if (n < 1) {
        IO.pure(List.empty)
      } else {
        for {
          currencyRate <- currencyRate(from, to)
          remainingRates <-
            if (n == 1) IO.pure(List.empty)
            else lastRates(from, to, n - 1)
        } yield remainingRates.prepended(currencyRate)
      }
    }

    val delay: FiniteDuration= FiniteDuration(1, TimeUnit.SECONDS)
    val ticks: Stream[IO, Unit] = Stream.fixedRate[IO](delay)

    def rates(from: Currency, to: Currency): Stream[IO, BigDecimal] = {
      Stream
        .eval(exchangeTable(from))
        .repeat
        .map(extractSingleCurrencyRate(to))
        .unNone
    }

    val firstThreeRates:IO[List[BigDecimal]]=
      rates(Currency("USD"), Currency("EUR"))
        .zipLeft(ticks).take(3)
        .compile
        .toList

    def exchangeIfTrending(amount: BigDecimal, from: Currency, to: Currency): IO[BigDecimal] = {
      rates(from, to)
        .zipLeft(ticks)
        .sliding(3)
        .map(_.toList)
        .filter(trending)
        .map(_.last)
        .take(1)
        .compile
        .lastOrError
        .map(_ * amount)
    }


  }



}


object model {
  type Currency = String

  object Currency {
    def apply(name: String): Currency = name

    implicit class CurrencyOps(currency: Currency) {
      def name: String = currency
    }
  }
}