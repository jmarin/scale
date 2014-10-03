package geojson

import spray.json._
import geometry._
import feature._

object GeoJsonProtocol extends DefaultJsonProtocol with NullOptions {

  implicit object PointFormat extends JsonFormat[Point] {
    def write(p: Point): JsValue = {
      JsObject(
        "type" -> JsString("Point"),
        "coordinates" -> JsArray(JsNumber(p.x), JsNumber(p.y), JsNumber(p.z)))
    }

    def read(json: JsValue): Point = {
      json.asJsObject.getFields("type", "coordinates") match {
        case Seq(JsString(t), JsArray(Vector(JsNumber(x), JsNumber(y), JsNumber(z)))) =>
          Point(x.toDouble, y.toDouble, z.toDouble)
        case _ => throw new DeserializationException("Point GeoJSON expected")
      }
    }
  }

  implicit object LineFormat extends JsonFormat[Line] {
    def write(l: Line): JsValue = {
      val coords = l.coordinates.map { c =>
        JsArray(JsNumber(c.x), JsNumber(c.y), JsNumber(c.z))
      }.toVector
      JsObject(
        "type" -> JsString("LineString"),
        "coordinates" -> JsArray(coords)
      )
    }

    def read(json: JsValue): Line = {
      json.asJsObject.getFields("type", "coordinates") match {
        case Seq(JsString(t), JsArray(p)) =>
          val points = p.map { x =>
            val point = JsObject(
              "type" -> JsString("Point"),
              "coordinates" -> x
            )
            point.convertTo[Point]
          }
          Line(points)
        case _ => throw new DeserializationException("LineString GeoJSON expected")
      }
    }
  }

}
