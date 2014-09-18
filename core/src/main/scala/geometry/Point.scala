package geometry

import scala.language.implicitConversions
import com.vividsolutions.jts.{ geom => jts }
import jts.Coordinate
import jts.GeometryFactory
import jts.PrecisionModel

object Point {

  private val geomFactory = new jts.GeometryFactory

  def apply(x: Double, y: Double): Point = {
    Point(geomFactory.createPoint(new jts.Coordinate(x, y, 0)))
  }

  def apply(x: Double, y: Double, srid: Int): Point = {
    val gf = new jts.GeometryFactory(new PrecisionModel, srid)
    Point(gf.createPoint(new jts.Coordinate(x, y, 0)))
  }

  def apply(x: Double, y: Double, z: Double): Point = {
    Point(geomFactory.createPoint(new jts.Coordinate(x, y, z)))
  }

  def apply(x: Double, y: Double, z: Double, srid: Int): Point = {
    val gf = new jts.GeometryFactory(new PrecisionModel, srid)
    Point(gf.createPoint(new jts.Coordinate(x, y, z)))
  }

  implicit def jtsToPoint(jtsGeom: jts.Point): Point = {
    apply(jtsGeom)
  }

}

case class Point(jtsGeometry: jts.Point) extends Geometry {

  def x: Double = jtsGeometry.getX

  def y: Double = jtsGeometry.getY

  def z: Double = jtsGeometry.getCoordinate.z

}
