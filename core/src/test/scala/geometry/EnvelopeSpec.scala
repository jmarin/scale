package geometry

import org.scalatest.{MustMatchers, WordSpec}

class EnvelopeSpec extends WordSpec with MustMatchers {
  val p1 = Point(-77, 39)
  val p2 = Point(-76, 40)
  val p3 = Point(-76.5, 39.5)
  val p4 = Point(-75, 41)
  val env1 = Envelope(p1, p2)
  val env2 = Envelope(p3, p4)
  val intEnv = Envelope(p3, p2)
  val uniEnv = Envelope(p1, p4)

  "An Envelope" must {
    "have dimensions" in {
      env1.width mustBe 1.0
      env1.height mustBe 1.0
      env1.xmin mustBe p1.x
      env1.ymin mustBe p1.y
      env1.xmax mustBe p2.x
      env1.ymax mustBe p2.y
    }
    "have dimensions when constructed from 4 coordinates" in {
      val env = Envelope(-77, 39, -76, 40)
      env.width mustBe 1.0
      env.height mustBe 1.0
      env.xmin mustBe p1.x
      env.ymin mustBe p1.y
      env.xmax mustBe p2.x
      env.ymax mustBe p2.y
    }
    "have a center" in {
      env1.centroid mustBe p3
    }
  }
  "contain its own center" in {
    env1.intersects(env1.centroid) mustBe true
  }
  "intersect with other envelope" in {
    env1 intersection env2 mustBe intEnv
  }
  "union two envelopes to form a larger one" in {
    env1 union env2 mustBe uniEnv
  }
  "convert to Polygon" in {
    env1.toPolygon.wkt mustBe "POLYGON ((-77 39, -77 40, -76 40, -76 39, -77 39))"
  }

}
