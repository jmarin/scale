package geometry

import org.specs2.mutable.Specification

class PolygonSpec extends Specification {

  val p1 = Point(-77, 39)
  val p2 = Point(-76, 40)
  val p3 = Point(-75, 38)
  val p4 = Point(-77, 39)
  val p5 = Point(-78, 39.1)
  val p6 = Point(-75.7, 39.2)
  val p7 = Point(-76.5, 39)
  val p8 = Point(-76, 38.5)
  val polygon = Polygon(p1, p2, p3, p4)
  val polygonFromLine = Polygon(Line(Array(p1, p2, p3, p4)))
  val hole = Polygon(p6, p7, p8, p6)

  "A Polygon" should {
    "be valid" in {
      polygon.isValid must beTrue
      polygonFromLine.isValid must beTrue
      hole.isValid must beTrue
    }
    "have a perimeter" in {
      polygon.perimeter must be greaterThan (0)
      polygonFromLine.perimeter must be greaterThan (0)
    }
    "have an area" in {
      polygon.area must be greaterThan (0)
      polygonFromLine.area must be greaterThan (0)
    }
    "be able to contain holes" in {
      val boundary = Line(p1, p2, p3, p4)
      val ring = Line(p6, p7, p8, p6)
      val poly = Polygon(boundary, ring)
      poly.isValid must beTrue
    }
    "serialize to WKT" in {
      polygon.toWKT must be equalTo ("POLYGON ((-77 39, -76 40, -75 38, -77 39))")
      polygonFromLine.toWKT must be equalTo ("POLYGON ((-77 39, -76 40, -75 38, -77 39))")
    }
  }

}
