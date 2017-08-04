package geometry

import org.scalatest.{MustMatchers, WordSpec}

class PolygonSpec extends WordSpec with MustMatchers {

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
  val boundary = Line(p1, p2, p3, p4)
  val ring = Line(p6, p7, p8, p6)
  val polyWithHole = Polygon(boundary, ring)

  "A Polygon" must {
    "be valid" in {
      polygon.isValid mustBe true
      polygonFromLine.isValid mustBe true
      hole.isValid mustBe true
    }
    "have a perimeter" in {
      polygon.perimeter.toLong must be > 0L
      polygonFromLine.perimeter.toLong must be > 0L
    }
    "have an area" in {
      polygon.area.toLong must be > 0L
      polygonFromLine.area.toLong must be > 0L
    }
    "be able to contain holes" in {
      polyWithHole.isValid mustBe true
    }
    "serialize to WKT" in {
      polygon.wkt mustBe "POLYGON ((-77 39, -76 40, -75 38, -77 39))"
      polygonFromLine.wkt mustBe "POLYGON ((-77 39, -76 40, -75 38, -77 39))"
    }
    "get external boundary as line" in {
      polygon.boundary mustBe Line(p1, p2, p3, p1)
    }
    "get interior rings as list of lines" in {
      polyWithHole.holes.length mustBe 1
    }
  }

}
