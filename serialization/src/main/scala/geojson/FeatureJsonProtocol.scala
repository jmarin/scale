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
          values.keys
            .filter((k: String) => k != "geometry")
            .map { k =>
              k.toString -> toJsValue(values.get(k))
            }.toMap
        )
      )
    }

    def read(json: JsValue): Feature = {
      json.asJsObject.getFields("type", "geometry", "properties") match {
        case Seq(JsString("Feature"), geom: JsValue, props: JsValue) =>
          val geometry = toGeometry(geom)
          val fields = props.asJsObject.fields.map { x =>
            val k = x._1
            val v = x._2 match {
              case JsString(s) => StringType()
              case JsNull => StringType()
              case JsTrue => BooleanType()
              case JsFalse => BooleanType()
              case JsNumber(n) => DoubleType()
              case JsArray(_) => StringType()
              case JsObject(_) => StringType()
            }
            Field(k, v)
          }
          val fieldList = Field("geometry", GeometryType()) :: fields.toList
          val schema = Schema(fieldList.toArray)
          val values = props.asJsObject.fields.map { x =>
            val v = x._2 match {
              case JsString(s) => s.toString
              case JsNull => None
              case JsTrue => true
              case JsFalse => false
              case JsNumber(n) => n
              case _ => None
            }
            (x._1, v)
          }
          val geomValue = Map("geometry" -> geometry)
          Feature(schema, geomValue ++ values)
        case _ =>
          Feature(Point(0, 0))
      }

    }

  }

  private def toJsValue[T](s: T): JsValue = s match {
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

  private def fromJsValue(v: JsValue): Any = v match {
    case JsString(s) => s.toString
    case JsNumber(s) => s
    case JsTrue => true
    case JsFalse => false
    case JsNull => None
    case _ => v.toString
  }

  private def toGeometry(json: JsValue): Geometry = {
    json.asJsObject.getFields("type", "coordinates") match {
      case Seq(JsString(geomType), props: JsValue) =>
        geomType match {
          case "Point" => json.toJson.convertTo[Point]
          case "Line" => json.toJson.convertTo[Line]
          case "Polygon" => json.toJson.convertTo[Polygon]
          case "MultiPoint" => json.toJson.convertTo[MultiPoint]
          case "MultiLine" => json.toJson.convertTo[MultiLine]
          case "MultiPolygon" => json.toJson.convertTo[MultiPolygon]
        }
      case _ => throw new DeserializationException("GeoJSON expected")
    }
  }

}
