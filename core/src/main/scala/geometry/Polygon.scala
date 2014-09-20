package geometry

import scala.language.implicitConversions
import com.vividsolutions.jts.{ geom => jts }
import jts.GeometryFactory

object Polygon {

  private val geomFactory = new jts.GeometryFactory

  def apply(points: Point*): Polygon = {
    apply(points.toList)
  }

  def apply(points: Traversable[Point]): Polygon = {
    val ring = geomFactory.createLinearRing(Util.points2JTSCoordinates(points).toArray)
    Polygon(new jts.Polygon(ring, null, geomFactory))
  }

  def apply(line: Line): Polygon = {
    apply(line.points)
  }

  def apply(exterior: Line, interior: Line*): Polygon = {
    apply(exterior, interior.toArray)
  }

  def apply(exterior: Line, interior: Array[Line]): Polygon = {
    val exteriorRing = geomFactory.createLinearRing(
      Util.points2JTSCoordinates(exterior.points).toArray)
    val holes = interior.map(i => geomFactory.createLinearRing(
      Util.points2JTSCoordinates(i.points).toArray))
    Polygon(new jts.Polygon(exteriorRing, holes, geomFactory))
  }

  def apply(exterior: Line, interior: Array[Line], srid: Int): Polygon = {
    val gf = new jts.GeometryFactory(null, srid)
    val exteriorRing = gf.createLinearRing(
      Util.points2JTSCoordinates(exterior.points).toArray)
    val holes = interior.map(i => gf.createLinearRing(
      Util.points2JTSCoordinates(i.points).toArray))
    Polygon(new jts.Polygon(exteriorRing, holes, gf))

  }

  implicit def jtsToPolygon(jtsGeom: jts.Polygon): Polygon = {
    apply(jtsGeom)
  }

}

case class Polygon(jtsGeometry: jts.Polygon) extends Geometry {

  def perimeter: Double = {
    jtsGeometry.getLength
  }

  def area: Double = {
    jtsGeometry.getArea
  }

  def isRectangle: Boolean = {
    jtsGeometry.isRectangle
  }

}
