package geometry

import utest._

object PointTest extends TestSuite{
  val tests = TestSuite {
    'round{
      val p = Point(-77.12345, 38.12345, 0)
      val expected = Point(-77.12, 38.12, 0)
      val rounded = p.roundCoordinates(2)
      assert(rounded == expected)
    }
  }

}
