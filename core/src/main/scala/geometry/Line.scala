package geometry

import com.vividsolutions.jts.{ geom => jts }
import jts.GeometryFactory
import jts.LineString
import jts.impl.CoordinateArraySequence

object Line {

  private val geomFactory = new jts.GeometryFactory

  def apply(points: Array[Point]): Line = {
    val coords = Util.points2JTSCoordinateArray(points)
    val coordSequence = new CoordinateArraySequence(coords)
    val lineString = new LineString(coordSequence, geomFactory)
    Line(lineString)
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
}
