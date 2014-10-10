package geometry

import org.specs2.mutable.Specification
import org.specs2.ScalaCheck
import org.scalacheck.Prop
import org.scalacheck.Prop.forAll

class MultiPointSpec extends Specification with ScalaCheck with GeometryGenerators {

  val p1 = Point(-77, 39)
  val p2 = Point(-77, 40)
  val p3 = Point(-76, 40)
  val p4 = Point(-76, 39)
  val mp = MultiPoint(p1, p2, p3, p4, p1)

  def isValid = Prop.forAll(multipoints) { (mp: MultiPoint) =>
    mp.isValid must beTrue
  }

  def numPoints = Prop.forAll(multipoints) { (mp: MultiPoint) =>
    mp.numGeometries must beGreaterThanOrEqualTo(0)
  }

  "A MultiPoint" should {
    "be valid" ! isValid
    "have a number of Points" ! numPoints
    "serialize to WKT" in {
      mp.wkt must be equalTo ("MULTIPOINT ((-77 39), (-77 40), (-76 40), (-76 39), (-77 39))")
    }
  }

}

