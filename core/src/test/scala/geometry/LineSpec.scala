package geometry

import org.specs2.mutable.Specification

class LineSpec extends Specification {

  val p1 = Point(-77, 39)
  val p2 = Point(-76, 40)
  val p3 = Point(-75, 38)
  val p4 = Point(-77, 39)
  val p5 = Point(-78, 39.1)
  val line = Line(Array(p1, p2, p3))
  val closedLine = Line(Array(p1, p2, p3, p4))

  "A Line" should {
    "be valid" in {
      line.isValid must beTrue
      line.isSimple must beTrue
    }
    "always have some points" in {
      line.numPoints must be greaterThan (0)
    }
    "have a start point" in {
      line.startPoint must be equalTo (p1)
    }
    "have an end point" in {
      line.endPoint must be equalTo (p3)
    }
    "have a positive length" in {
      line.length must be greaterThan (0)
    }
    "report if it is closed or not" in {
      line.isClosed must beFalse
      closedLine.isClosed must beTrue
    }
    "determine if it is a ring" in {
      line.isRing must beFalse
      closedLine.isRing must beTrue
    }
    "compute the reverse" in {
      val rev = line.reverse
      rev.startPoint must be equalTo (line.endPoint)
      rev.endPoint must be equalTo (line.startPoint)
      rev.length must be equalTo (line.length)
      rev.isValid must beTrue
    }
    "get nth point" in {
      line.pointAt(2) must be equalTo (p3)
    }
    "check if point is coordinate" in {
      line.isCoordinate(p1) must beTrue
      line.isCoordinate(p5) must beFalse
    }
    "Serialize to WKT" in {
      line.toWKT must be equalTo ("LINESTRING (-77 39, -76 40, -75 38)")
    }
  }

}
