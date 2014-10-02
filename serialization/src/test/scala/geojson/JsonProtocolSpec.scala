package geojson

import org.specs2.mutable.Specification
import geometry._
import feature._
import spray.json._

class GeoJsonProtocolSpecification extends Specification {

  import geojson.GeoJsonProtocol._

  val point = Point(-77.1, 38.5)
  val pointJson = """{"type":"Point","coordinates":[-77.1,38.5,0.0]}"""

  "The JSON protocol" should {
    "serialize Point to GeoJSON" in {
      point.toJson.toString must be equalTo (pointJson)
    }
    "read GeoJSON into Point" in {
      point.toJson.convertTo[Point] must be equalTo (point)
    }
  }
}
