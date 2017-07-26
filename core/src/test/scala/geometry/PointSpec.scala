package geometry

import org.scalatest.{MustMatchers, PropSpec}
import org.scalatest.prop.PropertyChecks

class PointSpec
    extends PropSpec
    with PropertyChecks
    with MustMatchers
    with GeometryGenerators {
  property("A Point must be valid") {
    forAll(points) { p =>
      p.isValid mustBe true
    }
  }

  property("A Point must always have coordinates") {
    val p = Point(-77, 39)
    p.x mustBe -77.0
    p.y mustBe 39.0
  }

  property("A Point must intersect with itself") {
    forAll(points) { p =>
      p.intersects(p) mustBe true
    }
  }

  property("A Point must not interact with other points") {
    forAll(points, points) { (p1, p2) =>
      p1.intersects(p2) mustBe false
    }
  }

  property("A Point must round its coordinates") {
    val p = Point(-161.66663555296856, -85.880232540029)
    p.roundCoordinates(4) mustBe Point(-161.6666, -85.8802)
  }

  property("A Point must buffer into a polygon") {
    forAll(points) { (p: Point) =>
      p.buffer(1).centroid.roundCoordinates(4) mustBe p.roundCoordinates(4)
    }
  }

  property("A Point must serialize to WKT") {
    forAll(points) { p =>
      val x = p.x
      val y = p.y
      p.wkt mustBe s"POINT ($x $y)"
    }
  }

}
