package geometry

import org.specs2.mutable.Specification

class MultiPolygonSpec extends Specification {

  val p1 = Point(-77, 39)
  val p2 = Point(-76, 40)
  val p3 = Point(-75, 38)
  val p4 = Point(-77, 39)
  val p5 = Point(-78, 32)
  val p6 = Point(-77, 32)
  val p7 = Point(-76, 35)
  val p8 = Point(-75, 31)
  val polygon1 = Polygon(p1, p2, p3, p4)
  val polygon2 = Polygon(p6, p7, p8, p6)
  val mp = MultiPolygon(polygon1, polygon2)

  "A MultiPolygon" should {
    "be valid" in {
      mp.isValid must beTrue
    }
    "have a number of Polygons" in {
      mp.numGeometries must be equalTo (2)
    }
  }

}
