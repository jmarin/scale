package geometry

import org.specs2.mutable.Specification

class MultiPointSpec extends Specification {

  val p1 = Point(-77, 39)
  val p2 = Point(-105, 39)
  val p3 = Point(-90, 41)
  val mp = MultiPoint(p1, p2, p3)

  "A MultiPoint" should {
    "be valid" in {
      mp.isValid must beTrue
    }
  }

}

