package feature

import org.specs2.mutable.Specification
import org.osgeo.proj4j.CRSFactory
import geometry._

class FeatureSpec extends Specification {

  lazy val crsFactory = new CRSFactory

  val p1 = Point(-77, 39)
  val p2 = Point(-76, 40)
  val p3 = Point(-75, 38)
  val p4 = Point(-77, 39)
  val polygon = Polygon(p1, p2, p3, p4)
  val id = "1"
  val values = Map("geometry" -> polygon, "DESCRIPTION" -> "First Point")
  val schema = Schema(List(Field("geometry", GeometryType()), Field("DESCRIPTION", StringType())))
  val crs = crsFactory.createFromName("EPSG:4326")

  "A Feature" should {
    "be instantiated with correct CRS" in {
      val f = Feature(crs, schema, values)
      f.srid must be equalTo (4326)
    }
  }

}
