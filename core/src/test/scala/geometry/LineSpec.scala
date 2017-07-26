package geometry

import org.scalatest.prop.PropertyChecks
import org.scalatest.{MustMatchers, PropSpec}

class LineSpec
    extends PropSpec
    with PropertyChecks
    with MustMatchers
    with GeometryGenerators {
  val p1 = Point(-77, 39)
  val p2 = Point(-76, 40)
  val p3 = Point(-75, 38)
  val p4 = Point(-77, 39)
  val p5 = Point(-78, 39.1)
  val line = Line(Array(p1, p2, p3))
  val closedLine = Line(Array(p1, p2, p3, p4))

  property("A Line always has some points") {
    forAll(lines) { l =>
      l.numPoints must be > 2
    }
  }

  property("A Line always has endpoints") {
    forAll(lines) { l =>
      val p1 = l.points(0)
      val p2 = l.points(l.points.length - 1)
      l.startPoint mustBe p1
      l.endPoint mustBe p2
    }
  }

  property("A Line has positive length") {
    forAll(closedLines) { l =>
      l.length.toLong must be >= 0L
    }
  }

  property("A line is always closed") {
    forAll(closedLines) { l =>
      l.isClosed mustBe true
    }
  }

  property("The reverse of a Line must be valid") {
    forAll(lines) { (l: Line) =>
      l.reverse.startPoint mustBe l.endPoint
      l.reverse.endPoint mustBe l.startPoint
      round(l.reverse.length) mustBe round(l.length)
      l.reverse.isValid mustBe true
    }
  }

  property("Check if a point is a vertex of a line") {
    forAll(lines) { (l: Line) =>
      val p = l.startPoint
      l.isCoordinate(p) mustBe true
      l.isCoordinate(Point(1000, 1000)) mustBe false
    }
  }

  property("A Line must be valid") {
    line.isValid mustBe true
    line.isSimple mustBe true
  }

  property("A Line must report if it is a ring") {
    line.isClosed mustBe false
    closedLine.isClosed mustBe true
  }

  property("A Line must get the nth point") {
    line.pointAt(2) mustBe p3
  }

  property("A Line must serialize to WKT") {
    line.wkt mustBe "LINESTRING (-77 39, -76 40, -75 38)"
  }

  property("Linear referencing (extract points at certain distances") {
    line.pointAtDist(0.6) mustBe Point(-76.57573593128807, 39.42426406871193)
    line.pointAtDist(-0.6) mustBe Point(-75.26832815729998, 38.53665631459995)
  }

}
