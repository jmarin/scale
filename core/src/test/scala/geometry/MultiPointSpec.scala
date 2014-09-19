package geometry

import org.specs2.mutable.Specification

class MultiPointSpec extends Specification {

  val p1 = Point(-77, 39)
  val p2 = Point(-77, 40)
  val p3 = Point(-76, 40)
  val p4 = Point(-76, 39)
  val mp = MultiPoint(p1, p2, p3, p4, p1)

  "A MultiPoint" should {
    "be valid" in {
      mp.isValid must beTrue
    }
  }

}

