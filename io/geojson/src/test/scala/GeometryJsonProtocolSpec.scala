package io.geojson

import geometry._
import org.scalatest.{MustMatchers, WordSpec}
import spray.json._

class GeometryJsonProtocolSpec extends WordSpec with MustMatchers {

  import io.geojson.GeometryJsonProtocol._

  val p1 = Point(-77.1, 38.5)
  val p2 = Point(-102.2, 45.8)
  val p3 = Point(-85.1, 39.1)
  val p4 = Point(-78, 32)
  val p5 = Point(-77, 32)
  val p6 = Point(-76, 35)
  val l1 = Line(p1, p2)
  val l2 = Line(p2, p3)
  val poly1 = Polygon(p1, p2, p3, p1)
  val poly2 = Polygon(p4, p5, p6, p4)
  val mp = MultiPoint(p1, p2)
  val ml = MultiLine(l1, l2)
  val mpoly = MultiPolygon(poly1, poly2)
  val ph1 = Point(100, 0)
  val ph2 = Point(101, 0)
  val ph3 = Point(101, 1)
  val ph4 = Point(100, 1)
  val h1 = Point(100.2, 0.2)
  val h2 = Point(100.8, 0.2)
  val h3 = Point(100.8, 0.8)
  val exterior = Line(ph1, ph2, ph3, ph4, ph1)
  val hole = Line(h1, h2, h3, h1)
  val polyWithHole = Polygon(exterior, hole)
  val pointJson = """{"type":"Point","coordinates":[-77.1,38.5]}"""
  val lineJson =
    """{"type":"LineString","coordinates":[[-77.1,38.5],[-102.2,45.8]]}"""
  val polyJson =
    """{"type":"Polygon","coordinates":[[[-77.1,38.5],[-102.2,45.8],[-85.1,39.1],[-77.1,38.5]]]}"""
  val polyWithHoleJson =
    """{"type":"Polygon","coordinates":[[[100.0,0.0],[101.0,0.0],[101.0,1.0],[100.0,1.0],[100.0,0.0]],[[100.2,0.2],[100.8,0.2],[100.8,0.8],[100.2,0.2]]]}"""
  val mpJson =
    """{"type":"MultiPoint","coordinates":[[-77.1,38.5],[-102.2,45.8]]}"""
  val mlJson =
    """{"type":"MultiLineString","coordinates":[[[-77.1,38.5],[-102.2,45.8]],[[-102.2,45.8],[-85.1,39.1]]]}"""
  val mpolyJson =
    """{"type":"MultiPolygon","coordinates":[[[[-77.1,38.5],[-102.2,45.8],[-85.1,39.1],[-77.1,38.5]],[[-78.0,32.0],[-77.0,32.0],[-76.0,35.0],[-78.0,32.0]]]]}"""

  "The JSON protocol" should {
    "write Point to GeoJSON" in {
      p1.toJson.toString mustBe pointJson
    }
    "read GeoJSON into Point" in {
      p1.toJson.convertTo[Point] mustBe p1
    }
    "write Line to GeoJSON" in {
      l1.toJson.toString mustBe lineJson
    }
    "read GeoJSON into Line" in {
      l1.toJson.convertTo[Line] mustBe (l1)
    }
    "write Polygon to GeoJSON" in {
      poly1.toJson.toString mustBe polyJson
    }
    "read GeoJSON into Polygon" in {
      poly1.toJson.convertTo[Polygon] mustBe poly1
    }
    "write Polygon with hole" in {
      polyWithHole.toJson.toString mustBe polyWithHoleJson
    }
    "read GeoJSON into Polygon with hole" in {
      polyWithHole.toJson.convertTo[Polygon] mustBe polyWithHole
    }
    "write MultiPoint to GeoJSON" in {
      mp.toJson.toString mustBe mpJson
    }
    "read GeoJSON into MultiPoint" in {
      mp.toJson.convertTo[MultiPoint] mustBe mp
    }
    "write MultiLine to GeoJSON" in {
      ml.toJson.toString mustBe mlJson
    }
    "read GeoJSON into MultiLine" in {
      ml.toJson.convertTo[MultiLine] mustBe ml
    }
    "write MultiPolygon to GeoJSON" in {
      mpoly.toJson.toString mustBe mpolyJson
    }
    "read GeoJSON into MultiPolygon" in {
      mpoly.toJson.convertTo[MultiPolygon] mustBe mpoly
    }
  }
}
