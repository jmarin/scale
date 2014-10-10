package geometry

import org.specs2.mutable.Specification
import org.specs2.ScalaCheck
import org.scalacheck.Prop
import org.scalacheck.Prop.forAll
import com.vividsolutions.jts.{ geom => jts }

class PointSpec extends Specification with ScalaCheck with GeometryGenerators {

  def isValid = Prop.forAll(points) { (p: Point) => p.isValid must beTrue }

  def intersectSelf = Prop.forAll(points) { (p: Point) =>
    p.intersects(p) must beTrue
  }

  def intersectOther = Prop.forAll(points, points) { (p1: Point, p2: Point) =>
    p1.intersects(p2) must beFalse
  }

  def serializeWKT = Prop.forAll(points) { (p: Point) =>
    val x = p.x
    val y = p.y
    p.wkt must be equalTo (s"POINT ($x $y)")
  }

  "A Point" should {
    "be valid" ! isValid

    "always have coordinates" in {
      val p = Point(-77, 39)
      p.x must be equalTo (-77.0)
      p.y must be equalTo (39.0)
      p.z must be equalTo (0)
    }
    "intersect with itself" ! intersectSelf
    "not intersect with other point" ! intersectOther
    "serialize to WKT" in {
      val p = Point(-77, 39)
      p.wkt must be equalTo ("POINT (-77 39)")
    }
  }

}
