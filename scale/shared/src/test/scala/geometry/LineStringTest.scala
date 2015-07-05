package geometry

import utest._
import upickle._

object LineStringTest extends TestSuite {
  val p1 = Point(0,0)
  val p2 = Point(1,1)
  val p3 = Point(1,2)
  val line1 = LineString(Vector(p1, p2, p3))
  val closedLine = LineString(Vector(p1, p2, p3, p1))
  val tests = TestSuite {
    'numPoints {
      assert(line1.numPoints == 3)
      assert(closedLine.numPoints == 4)
    }
    'startPoint {
      assert(line1.startPoint == p1)
      assert(closedLine.startPoint == p1)
    }
    'endPoint {
      assert(line1.endPoint == p3)
      assert(closedLine.endPoint == p1)
    }
    'reverse {
      val reversed = LineString(Vector(p3, p2, p1))
      assert(line1.reverse == reversed)
    }
  }

}
