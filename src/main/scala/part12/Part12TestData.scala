package part12

import scala.util.Random

object Part12TestData {

  def main(args: Array[String]) = {

    println(calculus(7, 9, add))

  }

  def add(x: Int, y: Int): Int = x + y
  def multiply(x: Int,y: Int): Int = x * y
  def divide(x: Int,y: Int): Int = x / y

  def verifyBonus(random: Int): Int = {
    if (random < 7) 100 else random
  }

  def castRandom(): Int = {
    val random = new Random()
    random.nextInt(10) + 1
  }

  def bigNumbers(x: Int, random: Int): Int = {
    if (random * x < 300) 777 else 666
  }

  def calculus(a: Int, b: Int, operation: (Int, Int) => Int) = {
    operation(a, b)
  }

  def getSomeString(): String = {
    "some String"
  }

  def bullshitException(int: Int): Int = {
    if (int == 5) 555
    else throw new Exception("WRONG!")
  }

}

