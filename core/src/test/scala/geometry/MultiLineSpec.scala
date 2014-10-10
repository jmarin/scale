package geometry

import org.specs2.mutable.Specification
import org.specs2.ScalaCheck
import org.scalacheck.Prop
import org.scalacheck.Prop.forAll

class MultiLineSpec extends Specification with ScalaCheck with GeometryGenerators {

  val p1 = Point(-77, 39)
  val p2 = Point(-76, 40)
  val p3 = Point(-75, 38)
  val p4 = Point(-77, 39)
  val p5 = Point(-78, 39.1)
  val line = Line(Array(p1, p2, p3))
  val closedLine = Line(Array(p1, p2, p3, p4))
  val l = MultiLine(line, closedLine)

  def isValid = Prop.forAll(multilines) { (ml) =>
    ml.isValid must beTrue
  }

  def reverse = Prop.forAll(multilines) { (ml) =>
    ml.reverse.reverse must be equalTo (ml)
  }

  "A MultiLine" should {
    "be valid" ! isValid
    "compute the reverse" ! reverse
    "have a number of Lines" in {
      l.numGeometries must be equalTo (2)
    }
    "serialize to WKT" in {
      l.wkt must be equalTo ("MULTILINESTRING ((-77 39, -76 40, -75 38), (-77 39, -76 40, -75 38, -77 39))")
    }
  }

}
