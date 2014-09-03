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

  implicit def jts2Point(p: jts.Point): Point = apply(p)

}

case class Point(p: jts.Point) {

  val x: Double = p.getX

  val y: Double = p.getY

  val z: Double = p.getCoordinate.z

}
