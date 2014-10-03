package geojson

import org.specs2.mutable.Specification
import geometry._
import feature._
import spray.json._

class GeoJsonProtocolSpecification extends Specification {

  import geojson.GeoJsonProtocol._

  val p1 = Point(-77.1, 38.5)
  val p2 = Point(-102.2, 45.8)
  val l1 = Line(p1, p2)
  val pointJson = """{"type":"Point","coordinates":[-77.1,38.5,0.0]}"""
  val lineJson = """{"type":"LineString","coordinates":[[-77.1,38.5,0.0],[-102.2,45.8,0.0]]}"""

  "The JSON protocol" should {
    "write Point to GeoJSON" in {
      p1.toJson.toString must be equalTo (pointJson)
    }
    "read GeoJSON into Point" in {
      p1.toJson.convertTo[Point] must be equalTo (p1)
    }
    "write Line to GeoJSON" in {
      l1.toJson.toString must be equalTo (lineJson)
    }
    "read GeoJSON into Line" in {
      l1.toJson.convertTo[Line] must be equalTo (l1)
    }
  }
}
