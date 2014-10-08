package geojson

import org.specs2.mutable.Specification
import geometry._
import feature._
import spray.json._

class GeoJsonProtocolSpec extends Specification {

  import geojson.GeoJsonProtocol._

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
  val h2 = Point(100.8, 0, 2)
  val h3 = Point(100.8, 0.8)
  val exterior = Line(ph1, ph2, ph3, ph4, ph1)
  val hole = Line(h1, h2, h3, h1)
  val polyWithHole = Polygon(exterior, hole)
  val pointJson = """{"type":"Point","coordinates":[-77.1,38.5,0.0]}"""
  val lineJson = """{"type":"LineString","coordinates":[[-77.1,38.5,0.0],[-102.2,45.8,0.0]]}"""
  val polyJson = """{"type":"Polygon","coordinates":[[[-77.1,38.5,0.0],[-102.2,45.8,0.0],[-85.1,39.1,0.0],[-77.1,38.5,0.0]]]}"""
  val polyWithHoleJson = """{"type":"Polygon","coordinates":[[[100.0,0.0,0.0],[101.0,0.0,0.0],[101.0,1.0,0.0],[100.0,1.0,0.0],[100.0,0.0,0.0]],[[100.2,0.2,0.0],[100.8,0.2,0.0],[100.8,0.8,0.0],[100.2,0.8,0.0],[100.2,0.2,0.0]]]}"""
  val mpJson = """{"type":"MultiPoint","coordinates":[[-77.1,38.5,0.0],[-102.2,45.8,0.0]]}"""
  val mlJson = """{"type":"MultiLineString","coordinates":[[[-77.1,38.5,0.0],[-102.2,45.8,0.0]],[[-102.2,45.8,0.0],[-85.1,39.1,0.0]]]}"""
  val mpolyJson = """{"type":"MultiPolygon","coordinates":}"""

  "The JSON protocol" should {
    "write Point to GeoJSON" in {
      p1.toJson.toString must be equalTo (pointJson)
    }
    "read GeoJSON into Point" in {
      p1.toJson.convertTo[Point] must be equalTo (p1)
      p1.jtsGeometry.isValid must beTrue
    }
    "write Line to GeoJSON" in {
      l1.toJson.toString must be equalTo (lineJson)
    }
    "read GeoJSON into Line" in {
      l1.toJson.convertTo[Line] must be equalTo (l1)
      l1.jtsGeometry.isValid must beTrue
    }
    "write Polygon to GeoJSON" in {
      poly1.toJson.toString must be equalTo (polyJson)
    }
    "read GeoJSON into Polygon" in {
      poly1.toJson.convertTo[Polygon] must be equalTo (poly1)
    }
    "write Polygon with hole" in {
      polyWithHole.toJson.toString must be equalTo (polyWithHoleJson)
    }
    "read GeoJSON into Polygon with hole" in {
      polyWithHole.toJson.convertTo[Polygon] must be equalTo (polyWithHole)
    }
    "write MultiPoint to GeoJSON" in {
      mp.toJson.toString must be equalTo (mpJson)
    }
    "read GeoJSON into MultiPoint" in {
      mp.toJson.convertTo[MultiPoint] must be equalTo (mp)
    }
    "write MultiLine to GeoJSON" in {
      ml.toJson.toString must be equalTo (mlJson)
    }
    "read GeoJSON into MultiLine" in {
      ml.toJson.convertTo[MultiLine] must be equalTo (ml)
    }
    "read GeoJSON into MultiPolygon" in {
      mpoly.toJson.toString must be equalTo (mpolyJson)
    }
    "write MultiPolygon to GeoJSON" in {
      mpolyJson.toJson.convertTo[MultiPolygon] must be equalTo (mpoly)
    }
  }
}
