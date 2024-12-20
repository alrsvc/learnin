import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import part12.Part12TestData
import part12.Part12TestData._


class PersonalTests extends AnyFunSuite with ScalaCheckPropertyChecks {

  test("Simple test") {
    assert(Part12TestData.add(10, 10) == 20)
    assert(Part12TestData.add(10, -20) < 20)
  }

  test("Random test") {
    if(bigNumbers(200, 10) < 30) assert(bigNumbers(200, 10) == 777)
    if(bigNumbers(700, 10) < 30) assert(bigNumbers(700, 10) == 666)
  }

  test("add should satisfy commutative property") {
    forAll { (a: Int, b: Int) =>
      assert(Part12TestData.add(a, b) == Part12TestData.add(b, a))
    }
  }

  test("do add calculus") {
    assert(calculus(7, 9, add) == 16)
  }

  test("do multiply calculus") {
    assert(calculus(10, 5, multiply) == 50)
  }

  test("do divide calculus") {
    assert(calculus(10, 5, divide) == 2)
    assert(calculus(10, 5, divide) != 45)
  }

  test("get some string") {
    assert(getSomeString() == "some String")
  }

  test("do assertResult ") {
    assertResult("some String")(getSomeString())
  }

  test("do exception") {
    assertResult(555)(bullshitException(5))
    assertThrows[Exception](bullshitException(2))
  }


  test("bigNumbers test") {
    forAll { a: Int =>
      val random = castRandom()
      val result = bigNumbers(a, random)

      if (random * a < 300) {
        assert(result == 777)
      } else {
        assert(result == 666)
      }
    }
  }


}


