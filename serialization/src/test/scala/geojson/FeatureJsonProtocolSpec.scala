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

  val p1 = Point(-77, 39)
  val values = Map("Description" -> "First Point", "id" -> "0000-0000", "Value" -> 1.5)
  val f1 = Feature("1", p1, values)
  val pJson = """{"type":"Feature","geometry":{"type":"Point","coordinates":[-77.0,39.0,0.0]},"properties":{"Description":"First Point","id":"0000-0000","Value":1.5}}"""

  "The JSON protocol" should {
    "write feature to GeoJSON" in {
      f1.toJson.toString must be equalTo (pJson)
    }
    "read GeoJSON into a Feature" in {
      f1.toJson.convertTo[Feature] must be equalTo (f1)
    }
  }

}
