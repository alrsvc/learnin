package part_1

object part1 {

  def main(args: Array[String]) = {
    println(increment(10));
    println(getFirstCharacter("Evolution"));
    println(wordScore("Evolution Gaming"));
  }

  def increment(x: Int): Int = x + 1

  def getFirstCharacter(s: String): Char = s.charAt(0)

  def wordScore(word: String): Int = word.length

}
