package geometry

import org.specs2.mutable.Specification

class EnvelopeSpec extends Specification {

  val p1 = Point(-77, 39)
  val p2 = Point(-76, 40)
  val p3 = Point(-76.5, 39.5)
  val p4 = Point(-75, 41)
  val env1 = Envelope(p1, p2)
  val env2 = Envelope(p3, p4)
  val intEnv = Envelope(p3, p2)

  "An Envelope" should {
    "have dimensions" in {
      env1.width must be equalTo (1.0)
      env1.height must be equalTo (1.0)
      env1.xmin must be equalTo (p1.x)
      env1.ymin must be equalTo (p1.y)
      env1.xmax must be equalTo (p2.x)
      env1.ymax must be equalTo (p2.y)
    }
    "have dimensions when constructed from 4 coordinates" in {
      val env = Envelope(-77, 39, -76, 40)
      env.width must be equalTo (1.0)
      env.height must be equalTo (1.0)
      env.xmin must be equalTo (p1.x)
      env.ymin must be equalTo (p1.y)
      env.xmax must be equalTo (p2.x)
      env.ymax must be equalTo (p2.y)
    }
    "have a center" in {
      env1.centroid must be equalTo (p3)
    }
    "contain its own center" in {
      env1.intersects(env1.centroid) must beTrue
    }
    "intersect with other envelope" in {
      env1 intersection env2 must be equalTo (intEnv)
    }

  }

}

