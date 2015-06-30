package feature

import org.specs2.mutable.Specification
import geometry._

class FeatureCollectionSpec extends Specification {

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
  val values = Map("geometry" -> polygon, "DESCRIPTION" -> "First Point")
  val valuesWithHole = Map("geometry" -> polyWithHoles, "DESCRIPTION" -> "First Point")
  val schema = Schema(List(Field("geometry", GeometryType()), Field("DESCRIPTION", StringType())))
  val fp = Feature(schema, values)
  val fph = Feature(schema, valuesWithHole)
  val fc = FeatureCollection(fp, fph)

  "A FeatureCollection" should {
    "get a list of features" in {
      val list = fc.features.toList
      list must be equalTo (List(fp, fph))
    }
    "project to different crs" in {
      val proj = fc.features.map(f => f.project(3857))
      proj.head.crs.getName must be equalTo ("EPSG:3857")
    }
    "have an envelope" in {
      val env = fc.envelope
      env must be equalTo (polygon.envelope)
    }
    "do point in poly search" in {
      val polys = fc.pointInPoly(polygon.centroid).getOrElse(Nil)
      polys.head must be equalTo (fp)
      val empty = fc.pointInPoly(Point(0, 0)).getOrElse(Nil)
      empty must be equalTo (None)
    }

  }

}

