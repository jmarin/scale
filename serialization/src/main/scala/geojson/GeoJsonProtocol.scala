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
      toCoords(l.points.toVector, "LineString")
    }

    def read(json: JsValue): Line = {
      json.asJsObject.getFields("type", "coordinates") match {
        case Seq(JsString(t), JsArray(p)) =>
          val points = toPoints(p)
          Line(points)
        case _ => throw new DeserializationException("LineString GeoJSON expected")
      }
    }
  }

  implicit object PolygonFormat extends JsonFormat[Polygon] {
    def write(p: Polygon): JsValue = {
      val pext = p.boundary.points
      val holes = p.holes
      val ptsExt = pext.map { k =>
        JsArray(JsNumber(k.x), JsNumber(k.y), JsNumber(k.z))
      }
      p.jtsGeometry.getNumInteriorRing match {
        case 0 =>
          JsObject(
            "type" -> JsString("Polygon"),
            "coordinates" -> JsArray(JsArray(ptsExt.toVector))
          )
        case _ => JsObject()
      }

    }
    def read(json: JsValue): Polygon = {
      json.asJsObject.getFields("type", "coordinates") match {
        case Seq(JsString(t), JsArray(p)) =>
          val lines = toLines(p).toList
          Polygon(lines.head, lines.tail.toArray)
        case _ => throw new DeserializationException("Polygon GeoJSON expected")
      }
    }
  }

  implicit object MultiPointFormat extends JsonFormat[MultiPoint] {
    def write(p: MultiPoint): JsValue = {
      toCoords(p.points.toVector, "MultiPoint")
    }
    def read(json: JsValue): MultiPoint = {
      json.asJsObject.getFields("type", "coordinates") match {
        case Seq(JsString(t), JsArray(p)) =>
          val points = toPoints(p)
          MultiPoint(points)
        case _ => throw new DeserializationException("MultiPoint GeoJSON expected")
      }
    }
  }

  implicit object MultiLineFormat extends JsonFormat[MultiLine] {
    def write(ml: MultiLine): JsValue = {
      val lines = ml.geometries
      JsObject(
        "type" -> JsString("MultiLineString"),
        "coordinates" -> JsArray(
          lines.map { l =>
            JsArray(
              l.getCoordinates.map { c =>
                JsArray(JsNumber(c.x), JsNumber(c.y), JsNumber(c.z))
              }.toVector
            )
          }.toVector
        )
      )
    }
    def read(json: JsValue): MultiLine = {
      json.asJsObject.getFields("type", "coordinates") match {
        case Seq(JsString(t), JsArray(l)) =>
          val lines = toLines(l)
          MultiLine(lines.toArray)
        case _ => throw new DeserializationException("MultiLineString GeoJSON expected")
      }
    }
  }

  implicit object MultiPolygonFormat extends JsonFormat[MultiPolygon] {
    def write(p: MultiPolygon): JsValue = ???
    def read(json: JsValue): MultiPolygon = ???
  }

  private def toPoints(coords: Vector[JsValue]): Vector[Point] = {
    coords.map { x =>
      val point = JsObject(
        "type" -> JsString("Point"),
        "coordinates" -> x
      )
      point.convertTo[Point]
    }
  }

  private def toLines(coords: Vector[JsValue]): Vector[Line] = {
    coords.map { x =>
      val line = JsObject(
        "type" -> JsString("LineString"),
        "coordinates" -> x
      )
      line.convertTo[Line]
    }
  }

  private def toCoords(points: Vector[Point], `type`: String): JsValue = {
    val coords = points.map { p =>
      JsArray(JsNumber(p.x), JsNumber(p.y), JsNumber(p.z))
    }.toVector
    JsObject(
      "type" -> JsString(`type`),
      "coordinates" -> JsArray(coords)
    )
  }

}
