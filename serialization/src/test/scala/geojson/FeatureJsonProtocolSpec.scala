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

  "A Feature" should {
    "serialize to GeoJSON" in {
      val p1 = Point(-77, 39)
      val values = Map("Description" -> "First Point", "id" -> "0000-0000", "Value" -> 1.5)
      val f = Feature("1", p1, values)
      f.toJson.toString must be equalTo ("""{"type":"Feature","geometry":{"type":"Point","coordinates":[-77.0,39.0,0.0]},"properties":{"Description":"First Point","id":"0000-0000","Value":1.5}}""")
    }
  }

}
