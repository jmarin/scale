package feature

import org.specs2.mutable.Specification
import geometry._

class FeatureSpec extends Specification {

  val p1 = Point(-77, 39)
  val p2 = Point(-76, 40)
  val p3 = Point(-75, 38)
  val p4 = Point(-77, 39)
  val p6 = Point(-75.7, 39.2)
  val p7 = Point(-76.5, 39)
  val p8 = Point(-76, 38.5)
  val line = Line(p1, p2)
  val polygon = Polygon(p1, p2, p3, p4)
  val boundary = Line(p1, p2, p3, p4)
  val ring = Line(p6, p7, p8, p6)
  val polyWithHoles = Polygon(boundary, ring)
  val id = "1"
  val values = Map("DESCRIPTION" -> "First Point")
  val f = Feature(id, p1, values)
  val fl = Feature(id, line, values)
  val fp = Feature(id, polygon, values)
  val fph = Feature(id, polyWithHoles, values)

  "A Feature" should {
    "have some values" in {
      f.countFields must be equalTo (1)
    }
    "add a field with a new value" in {
      val uf = f.addOrUpdate("NEWFIELD", "New Value")
      uf.countFields must be equalTo (2)
      val newValue = uf.get("NEWFIELD").getOrElse("")
      newValue must be equalTo ("New Value")
    }
    "update a field with a new value" in {
      val uf = f.addOrUpdate("DESCRIPTION", "Updated Value")
      uf.countFields must be equalTo (1)
      val updatedValue = uf.get("DESCRIPTION").getOrElse("")
      updatedValue must be equalTo ("Updated Value")
    }
    "have fields with different value types" in {
      val values1 = Map("DESCRIPTION" -> "First Point", "ID" -> 1)
      val f1 = Feature(id, p1, values1)
      f1.countFields must be equalTo (2)
      f1.id must be equalTo ("1")
      f1.get("ID").getOrElse("") must be equalTo (1)
    }
    "update its geometry" in {
      val p2 = Point(-105, 37)
      val fn = f.updateGeometry(p2)
      fn.geometry must be equalTo (p2)
    }
    "project a Point to another CRS" in {
      val p = f.project(3857)
      p.crs.getName must be equalTo ("EPSG:3857")
      p.geometry.wkt must be equalTo ("POINT (-8571600.791082066 4721671.572580107)")
    }
    "project a Line to another CRS" in {
      val p = fl.project(3857)
      p.crs.getName must be equalTo ("EPSG:3857")
      p.geometry.wkt must be equalTo ("LINESTRING (-8571600.791082066 4721671.572580107, -8460281.300288793 4865942.279503176)")
    }
    "project a Polygon to another CRS" in {
      val p = fp.project(3857)
      p.crs.getName must be equalTo ("EPSG:3857")
      p.geometry.wkt must be equalTo ("POLYGON ((-8571600.791082066 4721671.572580107, -8460281.300288793 4865942.279503176, -8348961.809495518 4579425.812870097, -8571600.791082066 4721671.572580107))")
    }
    "project a Polygon with holes to another CRS" in {
      val p = fph.project(3857)
      p.crs.getName must be equalTo ("EPSG:3857")
      p.geometry.wkt must be equalTo ("POLYGON ((-8571600.791082066 4721671.572580107, -8460281.300288793 4865942.279503176, -8348961.809495518 4579425.812870097, -8571600.791082066 4721671.572580107), (-8426885.453050809 4750360.4811166, -8515941.045685427 4721671.572580107, -8460281.300288793 4650301.836738959, -8426885.453050809 4750360.4811166))")
    }
  }

}
