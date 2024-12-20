package other

object Wlaschin {

  def main(args: Array[String]) = {

    // Low level operation
    def fruitUpperCase(number: Int): String = if(number == 1) number.toString.toUpperCase else ""

    //Composition
    val add3: Int => Int = x => x + 3
    val multiplyBy2: Int => Int = x => x * 2

    val combinedFunction = add3.compose(multiplyBy2)

    println(combinedFunction(5))

    val appleToBanana: String => String = _ => "banana"
    val bananaToCherry: String => String = _ => "cherry"

    val appleToCherry: String => String = appleToBanana.andThen(bananaToCherry)

    println(appleToCherry("apple"))

    //Low level operation
    def returnAddress(): String = "Some Street 13"
    def validateAddress(address: String => Boolean): Boolean = address(returnAddress())
    val doValidation: String => Boolean = address => address.contains("12")
    println(validateAddress(doValidation))

    //Functions as parameters
    val listInt = List(1,2,3,4,5,6)
    val listString = List("A", "B", "C")
    def printList[T](list: List[T]): Unit = list.foreach(println)
    printList(listString)

    //Strategy
    def add(a: Int, b: Int) = a + b
    def multiply(a: Int, b: Int) = a * b
    def calculate(a: Int, b: Int, strategy: (Int, Int) => Int) = strategy(a, b)

//    println(calculate(6,6, multiply))

    // Декоратор
    val addF: (Int, Int) => Int = (a, b) => a + b

    val logDecorator: ((Int, Int) => Int) => (Int, Int) => Int = (f: (Int, Int) => Int) => (a: Int, b: Int) => {
      println(s"Adding $a and $b")
      val result = f(a, b)
      println(s"Result: $result")
      result
    }

    // Применяем декоратор
    val decoratedAdd = logDecorator(addF)

    // Вызываем декорированную функцию
    println(decoratedAdd(2, 3))

  }

}
