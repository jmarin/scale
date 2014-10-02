package geojson

import spray.json._
import geometry.Point
import feature._

object GeoJsonProtocol extends DefaultJsonProtocol with NullOptions {

  implicit object PointFormat extends JsonFormat[Point] {
    def write(p: Point): JsValue =
      JsObject(
        "type" -> JsString("Point"),
        "coordinates" -> JsArray(JsNumber(p.x), JsNumber(p.y), JsNumber(p.z)))

    def read(j: JsValue): Point = {
      j.asJsObject.getFields("coordinates") match {
        case Seq(JsArray(Vector(JsNumber(x), JsNumber(y), JsNumber(z)))) =>
          Point(x.toDouble, y.toDouble, z.toDouble)
        case _ => throw new DeserializationException("Point GeoJSON expected")
      }
    }
  }

}
