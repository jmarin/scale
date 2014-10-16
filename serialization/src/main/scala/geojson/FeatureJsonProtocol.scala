package geojson

import com.vividsolutions.jts.{ geom => jts }
import spray.json._
import geometry._
import feature._

object FeatureJsonProtocol extends DefaultJsonProtocol with NullOptions {

  import GeometryJsonProtocol._

  implicit object FeatureFormat extends RootJsonFormat[Feature] {
    def write(f: Feature): JsValue = {
      val geom = f.geometry.jtsGeometry
      val geometry = f.geometry.geometryType match {
        case "Point" => Point(geom.asInstanceOf[jts.Point]).toJson
        case "Line" => Line(geom.asInstanceOf[jts.LineString]).toJson
        case "Polygon" => Polygon(geom.asInstanceOf[jts.Polygon]).toJson
        case "MultiPoint" => MultiPoint(geom.asInstanceOf[jts.MultiPoint]).toJson
        case "MultiLineString" => MultiLine(geom.asInstanceOf[jts.MultiLineString]).toJson
        case "MultiPolygon" => MultiPolygon(geom.asInstanceOf[jts.MultiPolygon]).toJson
      }
      val values = f.values

      JsObject(
        "type" -> JsString("Feature"),
        "geometry" -> geometry,
        "properties" -> JsObject(
          values.keys.map { k =>
            println(values.get(k))
            k.toString -> jsValue(values.get(k))
          }.toMap
        )
      )
    }

    def read(json: JsValue): Feature = {
      json.asJsObject.getFields("geometry", "properties") match {
        case Seq(JsString(geometry), JsArray(properties)) =>
          Feature("1", Point(0, 0), Map("desc" -> "ONE"))
      }
    }

  }

  private def jsValue[T](s: T): JsValue = s match {
    case Some(v) => v match {
      case _: Int => JsNumber(v.asInstanceOf[Int])
      case _: String => JsString(v.asInstanceOf[String])
      case _: BigDecimal => JsNumber(v.asInstanceOf[BigDecimal])
      case _: BigInt => JsNumber(v.asInstanceOf[BigInt])
      case _: Double => JsNumber(v.asInstanceOf[Double])
      case _: Long => JsNumber(v.asInstanceOf[Long])
      case _: Boolean => JsBoolean(v.asInstanceOf[Boolean])
      case _: Array[Char] => JsString(v.toString)
      case _ => JsNull
    }
    case None => JsNull
  }

}
