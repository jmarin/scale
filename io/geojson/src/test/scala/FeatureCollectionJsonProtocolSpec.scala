package io.geojson

import geometry._
import feature._
import org.scalatest.{MustMatchers, WordSpec}
import spray.json._

class FeatureCollectionJsonProtocolSpec extends WordSpec with MustMatchers {

  import io.geojson.FeatureJsonProtocol._

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
  val valuesWithHole =
    Map("geometry" -> polyWithHoles, "DESCRIPTION" -> "First Point")
  val schema = Schema(
    List(Field("geometry", GeometryType()), Field("DESCRIPTION", StringType())))
  val fp = Feature(schema, values)
  val fph = Feature(schema, valuesWithHole)
  val fc = FeatureCollection(fp, fph)
  val fcJson =
    """{"type":"FeatureCollection","features":[{"type":"Feature","geometry":{"type":"Polygon","coordinates":[[[-77.0,39.0],[-76.0,40.0],[-75.0,38.0],[-77.0,39.0]]]},"properties":{"DESCRIPTION":"First Point"}},{"type":"Feature","geometry":{"type":"Polygon","coordinates":[[[-77.0,39.0],[-76.0,40.0],[-75.0,38.0],[-77.0,39.0]],[[-75.7,39.2],[-76.5,39.0],[-76.0,38.5],[-75.7,39.2]]]},"properties":{"DESCRIPTION":"First Point"}}]}"""

  "A FeatureCollection" must {
    "write features to GeoJSON" in {
      fc.toJson.toString mustBe fcJson
    }
    "read GeoJSON into FeatureCollection" in {
      val parsedFC = fc.toJson.convertTo[FeatureCollection]
      val actual0 = fc.features.head
      val actual1 = fc.features.toList(1)
      val expected0 = parsedFC.features.head
      val expected1 = parsedFC.features.toList(1)
      expected0.geometry mustBe actual0.geometry
      expected0.values mustBe actual0.values
      expected1.geometry mustBe actual1.geometry
      expected0.values mustBe actual0.values
    }
  }

}
