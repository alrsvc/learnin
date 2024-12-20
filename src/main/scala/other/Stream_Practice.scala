package other
import fs2.{Pure, Stream}

object Stream_Practice {

  def main(args: Array[String]) = {

    println(recursion)

  }

  def multiplyElements(numbers: Stream[fs2.Pure, Int]) = {
    numbers.map(_ * 2).compile.toList
  }

  def toChunk(numbers: Stream[fs2.Pure, Int]) = {
    numbers.chunkN(3).toList
  }

  def compileToList(numbers: Stream[fs2.Pure, Int]) = {
    numbers.compile.toList
  }

  def doSliding(numbers: Stream[fs2.Pure, Int]) = {
    numbers.sliding(3).toList
  }

  def doZip(s1: Stream[fs2.Pure, Int], s2: Stream[fs2.Pure, String]): Unit = {
    s1.zip(s2).compile.toList
  }

  def recursion() = Stream.iterate(1)(_ + 1).take(10).toList

}
