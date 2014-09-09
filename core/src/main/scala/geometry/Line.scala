package geometry

import scala.language.implicitConversions
import com.vividsolutions.jts.{ geom => jts }
import jts.GeometryFactory
import jts.LineString
import jts.impl.CoordinateArraySequence

object Line {

  private val geomFactory = new jts.GeometryFactory

  def apply(points: Point*): Line = {
    apply(points.toList)
  }

  def apply(points: Traversable[Point]): Line = {
    Line(geomFactory.
      createLineString(points.
        map(_.jtsGeometry.getCoordinate).toArray))
  }
  implicit def jtsToLine(jtsGeom: jts.LineString): Line = {
    apply(jtsGeom)
  }
}

case class Line(jtsGeometry: jts.LineString) extends Geometry {

  def length: Double = {
    jtsGeometry.getLength
  }

  def numPoints: Int = {
    jtsGeometry.getNumPoints
  }

  def startPoint: Point = {
    Point(jtsGeometry.getStartPoint)
  }

  def endPoint: Point = {
    Point(jtsGeometry.getEndPoint)
  }

  def isClosed: Boolean = {
    jtsGeometry.isClosed
  }

  def isRing: Boolean = {
    jtsGeometry.isRing
  }

  def reverse: Line = {
    Line(jtsGeometry.reverse.asInstanceOf[LineString])
  }

  def pointAt(n: Int): Point = {
    Point(jtsGeometry.getPointN(n))
  }

}
