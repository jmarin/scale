package geojson

import org.specs2.mutable.Specification
import org.specs2.ScalaCheck
import org.scalacheck.Prop
import org.scalacheck.Prop.forAll
import geometry._
import feature._
import spray.json._

class FeatureJsonProtocolSpec extends Specification {

  import geojson.FeatureJsonProtocol._

  val schema = Schema(
    Field("geometry", GeometryType()),
    Field("Description", StringType()),
    Field("id", StringType()),
    Field("Value", DoubleType()))
  val p1 = Point(-77, 39)
  val values = Map("geometry" -> p1, "Description" -> "First Point", "id" -> "0000-0000", "Value" -> 1.5)
  val f1 = Feature(schema, values)
  val pJson = """{"type":"Feature","geometry":{"type":"Point","coordinates":[-77.0,39.0,0.0]},"properties":{"Description":"First Point","id":"0000-0000","Value":1.5}}"""

  "The JSON protocol" should {
    "write feature to GeoJSON" in {
      f1.toJson.toString must be equalTo (pJson)
    }
    "read GeoJSON into a Feature" in {
      val f = f1.toJson.convertTo[Feature]
      f.geometry must be equalTo (f1.geometry)
      f.schema must be equalTo (f1.schema)
      f.values must be equalTo (f1.values)
    }
  }

}
