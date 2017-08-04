package geometry

import org.scalatest.{MustMatchers, PropSpec}
import org.scalatest.prop.PropertyChecks

class MultiPointSpec
    extends PropSpec
    with PropertyChecks
    with MustMatchers
    with GeometryGenerators {

  val p1 = Point(-77, 39)
  val p2 = Point(-77, 40)
  val p3 = Point(-76, 40)
  val p4 = Point(-76, 39)
  val mp = MultiPoint(p1, p2, p3, p4, p1)

  property("A Multipoint must be valid") {
    forAll(multipoints) { (mp: MultiPoint) =>
      mp.isValid mustBe true
    }
  }

  property("A Multipoint must have a positive number of geometries") {
    forAll(multipoints) { (mp: MultiPoint) =>
      mp.numGeometries.toLong must be >= 0L
    }
  }

  property("A Multipoint must serialize to WKT") {
    mp.wkt mustBe "MULTIPOINT ((-77 39), (-77 40), (-76 40), (-76 39), (-77 39))"
  }

}
