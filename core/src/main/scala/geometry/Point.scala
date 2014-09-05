package geometry

import scala.language.implicitConversions
import com.vividsolutions.jts.{ geom => jts }
import jts.Coordinate
import jts.GeometryFactory

object Point {

  private val geomFactory = new jts.GeometryFactory

  def apply(x: Double, y: Double): Point = {
    Point(geomFactory.createPoint(new jts.Coordinate(x, y, 0)))
  }

  def apply(x: Double, y: Double, z: Double): Point = {
    Point(geomFactory.createPoint(new jts.Coordinate(x, y, z)))
  }

}

case class Point(jtsGeometry: jts.Point) extends Geometry {

  val x: Double = jtsGeometry.getX

  val y: Double = jtsGeometry.getY

  val z: Double = jtsGeometry.getCoordinate.z

}
