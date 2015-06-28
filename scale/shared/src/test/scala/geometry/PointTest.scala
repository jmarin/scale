package geometry

import utest._
import upickle._

object PointTest extends TestSuite{
  val p = Point(-77.12345, 38.12345, 0)
  val json = """{"type":"Point","coordinates":[-77.12345,38.12345,0]}"""
val tests = TestSuite {
    'round {
      val expected = Point(-77.12, 38.12, 0)
      val rounded = p.roundCoordinates(2)
      assert(rounded == expected)
    }
    'serialize {
      assert(write(p) == json)
    }
    'desearialize {
      assert(read[Point](json) == p)
    }
  }

}
