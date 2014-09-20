package geometry

import org.specs2.mutable.Specification

class MultiLineSpec extends Specification {

  val p1 = Point(-77, 39)
  val p2 = Point(-76, 40)
  val p3 = Point(-75, 38)
  val p4 = Point(-77, 39)
  val p5 = Point(-78, 39.1)
  val line = Line(Array(p1, p2, p3))
  val closedLine = Line(Array(p1, p2, p3, p4))
  val l = MultiLine(line, closedLine)

  "A MultiLine" should {
    "be valid" in {
      l.isValid must beTrue
    }
    "compute the reverse" in {
      l.reverse.reverse must be equalTo (l)
    }
    "have a number of Lines" in {
      l.numGeometries must be equalTo (2)
    }
    "serialize to WKT" in {
      l.wkt must be equalTo ("MULTILINESTRING ((-77 39, -76 40, -75 38), (-77 39, -76 40, -75 38, -77 39))")
    }
  }

}
