package part3

object part3 {


  def main(args: Array[String]) = {

  val cities = List("Riga", "Liepaja", "Ventspils", "Daugavpils", "Jurmala")

    println(replan(cities, "Kuldiga", "Ventspils"))

    println(firstTwo(cities) == List("Riga", "Liepaja"))
    println(lastTwo(cities) == List("Daugavpils", "Jurmala"))

    println(insertedBeforeLast(cities, "Cesis"))

  }

  def replan(plan: List[String], newCity: String, beforeCity: String): List[String] = {
    val beforeCityIndex = plan.indexOf(beforeCity)
    val citiesBefore = plan.slice(0, beforeCityIndex)
    val citiesAfter = plan.slice(beforeCityIndex, plan.size)
    citiesBefore.appended(newCity).appendedAll(citiesAfter)
  }

  def firstTwo(list: List[String]): List[String] = list.slice(0, 2)
  def lastTwo(list: List[String]): List[String] = list.slice(list.length - 2, list.length)
  def movedFirstTwoToTheEnd(list: List[String]): List[String] = {
    val firstTwo = list.slice(0, 2)
    val slicedFirstTwo = list.slice(2, list.size)
    slicedFirstTwo.appendedAll(firstTwo)
  }
  def insertedBeforeLast(list: List[String], before: String) = {
    val lastElement = list.slice(list.size - 1, list.size)
    val noLast = list.slice(0, list.size - 1)
    noLast.appended(before).appendedAll(lastElement)
  }
}


