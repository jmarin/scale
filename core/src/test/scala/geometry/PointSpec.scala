package geometry

import org.specs2.mutable.Specification

class PointSpec extends Specification {

  "A Point" should {
    "always have coordinates" in {
      val p = Point(-77, 39)
      p.x must be equalTo (-77.0)
      p.y must be equalTo (39.0)
      p.z must be equalTo (0)
    }
  }

}
