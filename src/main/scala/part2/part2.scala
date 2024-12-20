package part2

import part_1.part1.{getFirstCharacter, increment, wordScore}

object part2 {


  def main(args: Array[String]) = {
    val l = List("Evolution Gaming", "Any Gaming", "If Gaming", "Else Gaming")

    println(TipCalculator.getTipPercentage(List.empty))
    println(TipCalculator.getTipPercentage(l))

    //Testing
    println(getDiscountPercentage(List.empty) == 0)
    println(getDiscountPercentage(List.empty) == 1)
    println(getDiscountPercentage(List("Apple", "Book")) == 5)

  }

  // Testing
  def getDiscountPercentage(items: List[String]): Int = if(items.contains("Book")) 5 else 0

  def increment(x: Int): Int =  x + 1
  def add(a: Int, b: Int): Int =  a + b
  def wordScore(word: String): Int = word.replaceAll("a", "").length
  def getTipPercentage(names: List[String]): Int = {
    if(names.length > 5) 20
    else if(names.length > 0) 10
    else 0
  }

  //Testing 2
  val l = List("Evolution Gaming", "Any Gaming", "If Gaming", "Else Gaming")
  println(increment(-1) == 0)
  println(increment(1) == 2)
  println(increment(0) == 0)
  println(add(5,5) == 10)
  println(add(10, -10) == 0)
  println(add(0, 10) == 0)
  println(wordScore("Anna") == 2)
  println(wordScore("Chester") == 0)
  println(getTipPercentage(l) == 10)
  println(getTipPercentage(List.empty) == 10)


}

object ShoppingCart {

  def getDiscountPercentage(items: List[String]): Int = if (items.contains("Book")) 5 else 0

}

//Code rewrite from Java to Scala, page 41
object TipCalculator {

  def getTipPercentage(name: List[String]): Int = {
    if(name.length > 5) 20
    else if (name.length > 0) 10
    else 0
  }

}

