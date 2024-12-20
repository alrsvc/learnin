package part4

object part4 {

  def main(args: Array[String]) = {

    //    println(ascendingSort(List("TypeScript","JavaScript", "Rust", "Java", "Ada", "Golang")))
    //    println(sortAscendingByLetter(List("rust", "ada", "JavaScript"), "s"))
    //    println(sortDescendingByLetter(List("rust", "ada", "JavaScript"), "s"))
    //
    //    println(sortAscendingByLetterImproved(List("rust", "ada", "JavaScript"), "s"))
    //    println(sortDescendingByLetterImproved(List("rust", "ada", "JavaScript"), "s"))
    //    println(descendingOrder(List(1, 5, 7, 3, 4)))
    //
    //    val ll = List("TypeScript","JavaScript", "Rust", "Java", "Ada", "Golang")
    //    println(ll.sortBy(len))

    //  println(rankedWords(w => score(w) + bonus(w) + penaltyWord(w), words))
//    println(wordScores(w => score(w) + bonus(w) - penaltyWord(w), words))

    //PRACTICE MAP
//    println(words.map(len))
//    println(words.map(numbersOfS))
//    println(List(10, 2, 5, 7).map(negative))
//    println(words.map(negativeNumbersOfS))

    //PRACTICES FILTER
//    println(words.filter(word => len(word) < 5))
//    println(words.filter(word => numbersOfS(word) > 2))
//
//    def odd(i: Int): Boolean = i % 2 == 1
//    println(List(10, 2, 5, 7).filter(odd))
//
//    def largerThenFour(number: Int): Boolean = number > 4
//    println(List(10, 2, 5, 7).filter(largerThenFour))

//    println(highScoringWords(w => score(w) + bonus(w) - penaltyWord(w), words, 5))

//    def largerThan(n: Int): Int => Boolean = i => i > n
//    def divisibleByFive(divide: Int): Int => Boolean = i => i % divide == 0
//    def shorterThan(shortener: Int): String => Boolean = word => word.length < shortener
//    def moreThenTwoLetters(moreThan: Int): String => Boolean = s => numbersOfS(s) > moreThan

//    println(List(1,2,3,4,5,6,7,8,9).filter(largerThan(4)))
//    println(List(1,2,3,4,5,6,7,8,9).filter(largerThan(1)))
//    println(List(1,2,3,4,5,6,7,8,9).filter(divisibleByFive(5)))
//    println(List(1,2,3,4,5,6,7,8,9).filter(divisibleByFive(2)))
//    println(List("ada", "scala").filter(shorterThan(4)))
//    println(List("ada", "scala").filter(shorterThan(7)))
//    println(List("rust", "ada").filter(moreThenTwoLetters(2)))
//    println(List("rust", "ada").filter(moreThenTwoLetters(0)))

    val words = List("java", "scala", "ada", "rust", "haskell", "typeScript")
    val wordsWithScoreHigherThan: Int => List[String] => List[String] = highScoringWords(w => score(w) + bonus(w) - penaltyWord(w))

    //currying practice
    println(List(5,1,2,4,0).filter(divisibleBy(4)))
    println(words.filter(containsS(2)))

    println(wordsWithScoreHigherThan(1)(words))

    println(List(5,2,3).map(myTest(4)))

    println(List(5,1,2,4,100).foldLeft(0)((sum, i) => sum + i))
    println(List("scala", "rust", "ada").foldLeft(0)((total, s) => total + len(s)))
    println(List("scala", "haskell", "rust", "ada").foldLeft(0)((total, str) => total + numbersOfS(str)))
    println(List(5, 1, 2, 4, 15).foldLeft(Int.MinValue)((max, i) => if (i > max) i else max))

    val javalang= ProgrammingLanguage("Java", 1995)
    println(javalang.name)
    println(javalang.year)

  }
  //currying
    def largerThan(n: Int)(i: Int): Boolean = i > n
    def divisibleBy(n: Int)(divider: Int): Boolean = divider % n == 0
    def shorterThan(n: Int)(s: String): Boolean = s.length < n
    def containsS(moreThan: Int)(s: String): Boolean = numbersOfS(s) > moreThan

    def myTest(a: Int)( b: Int): Int = a + b
  //

  //foldLeft
  def cumulativeScore(wordScore: String => Int,words: List[String]): Int = {words.foldLeft(0)((total, word) => total + wordScore(word))}
  //

  def score(word: String): Int = word.replaceAll("a", "").length

  def bonus(word: String): Int = if (word.contains("c")) 5 else 0

  def penaltyWord(word: String): Int = if (word.contains("s")) 7 else 0

  def rankedWords(wordScore: String => Int, words: List[String]): List[String] = {
    words.sortBy(wordScore).reverse
  }

//  def highScoringWords(wordScore: String => Int, words: List[String], higherThan: Int): List[String] = words.filter(word => wordScore(word) > higherThan)
//  def highScoringWords(wordScore: String => Int): Int => List[String] => List[String] = higherThan => words => words.filter(word => wordScore(word) > higherThan)
def highScoringWords(wordScore: String => Int)(higherThan: Int)(words: List[String]): List[String] = {words.filter(word => wordScore(word) > higherThan)}


  def wordScores(wordScore: String => Int, words: List[String]): List[Int] = words.map(wordScore)

  //Excercies
  def ascendingSort(list: List[String]): List[String] = list.sorted

  def descendingOrder(list: List[Int]): List[Int] = list.sorted.reverse

  def sortAscendingByLetter(list: List[String], letter: String): List[String] = list.sortBy(word => word.contains(letter))

  def sortDescendingByLetter(list: List[String], letter: String): List[String] = list.sortBy(word => word.contains(letter)).reverse

  //Self-improved excercies
  def sortByWordWithLetter(list: List[String], letter: String) = list.sortBy(word => word.contains(letter))

  def sortAscendingByLetterImproved(list: List[String], letter: String): List[String] = sortByWordWithLetter(list, letter)

  def sortDescendingByLetterImproved(list: List[String], letter: String): List[String] = sortByWordWithLetter(list, letter).reverse

  //From book
  def len(s: String): Int = s.length

  def numbersOfS(s: String): Int = s.length - s.replaceAll("s", "").length

  def negative(i: Int): Int = -i

  def negativeNumbersOfS(s: String): Int = -numbersOfS(s)


}

case class ProgrammingLanguage(name: String, year: Int)
