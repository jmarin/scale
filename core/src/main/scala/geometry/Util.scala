package geometry

import com.vividsolutions.jts.{ geom => jts }
import jts.Coordinate

object Util {

  def points2JTSCoordinates(points: Traversable[Point]): Traversable[Coordinate] = {
    points.map { point =>
      new Coordinate(point.x, point.y, point.z)
    }
  }

  def geometryType(geom: jts.Geometry): Geometry = {
    geom.getGeometryType match {
      case "Point" => Point(geom.asInstanceOf[jts.Point])
      case "Line" => Line(geom.asInstanceOf[jts.LineString])
      case "Polygon" => Polygon(geom.asInstanceOf[jts.Polygon])
    }
  }

}
