package geometry

import org.scalatest.{MustMatchers, PropSpec}
import org.scalatest.prop.PropertyChecks

class MultiLineSpec
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
  val l = MultiLine(line, closedLine)

  property("A Multiline is valid") {
    forAll(multilines) { (ml) =>
      ml.isValid mustBe true
    }
  }

  property("Multiline validity check") {
    forAll(multilines) { (ml) =>
      ml.isValid mustBe true
    }
  }

  property("Multiline reverse check") {
    forAll(multilines) { (ml) =>
      ml.reverse.reverse mustBe ml
    }
  }

  property("line must have 2 points") {
    l.numGeometries mustBe 2
  }

  property("Multiline must serialize to WKT") {
    l.wkt mustBe "MULTILINESTRING ((-77 39, -76 40, -75 38), (-77 39, -76 40, -75 38, -77 39))"
  }

}
