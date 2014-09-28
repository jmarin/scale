package feature

import org.specs2.mutable.Specification
import geometry._

class FeatureTraversableSpec extends Specification {

  val p1 = Point(-77, 39)
  val p2 = Point(-76, 40)
  val p3 = Point(-75, 38)
  val p4 = Point(-77, 39)
  val p6 = Point(-75.7, 39.2)
  val p7 = Point(-76.5, 39)
  val p8 = Point(-76, 38.5)
  val polygon = Polygon(p1, p2, p3, p4)
  val ring = Line(p6, p7, p8, p6)
  val boundary = Line(p1, p2, p3, p4)
  val polyWithHoles = Polygon(boundary, ring)
  val id = "1"
  val values = Map("DESCRIPTION" -> "First Point")
  val fp = Feature(id, polygon, values)
  val fph = Feature(id, polyWithHoles, values)
  val ft = FeatureTraversable(fp, fph)

  "A FeatureTraversable" should {
    "get a list of features" in {
      val list = ft.toList
      list must be equalTo (List(fp, fph))
    }
    "produce a sequence" in {
      val seq = FeatureSeq(fp, fph)
      ft.toSeq must be equalTo (seq)
    }
    "project to different crs" in {
      val proj = ft.map(f => f.project(3857))
      proj.head.crs.getName must be equalTo ("EPSG:3857")
    }
  }

}
