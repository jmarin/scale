package io.geojson

import feature._
import geometry._
import org.scalatest.{MustMatchers, WordSpec}
import spray.json._
import io.geojson.FeatureJsonProtocol._

class FeatureJsonProtocolSpec extends WordSpec with MustMatchers {

  val schema = Schema(
    Field("geometry", GeometryType()),
    Field("Description", StringType()),
    Field("id", StringType()),
    Field("Value", DoubleType())
  )
  val p1 = Point(-77, 39)
  val values = Map("geometry" -> p1,
                   "Description" -> "First Point",
                   "id" -> "0000-0000",
                   "Value" -> 1.5)
  val f1 = Feature(schema, values)
  val pJson =
    """{"type":"Feature","geometry":{"type":"Point","coordinates":[-77.0,39.0]},"properties":{"Description":"First Point","id":"0000-0000","Value":1.5}}"""

  "The JSON protocol" must {
    "write feature to GeoJSON" in {
      f1.toJson.toString mustBe pJson
    }
    "read GeoJSON into a Feature" in {
      val f = f1.toJson.convertTo[Feature]
      f.geometry mustBe f1.geometry
      f.schema mustBe f1.schema
      f.values mustBe f1.values
    }
  }

}
