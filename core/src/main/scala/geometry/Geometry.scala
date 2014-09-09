package geometry

import com.vividsolutions.jts.{ geom => jts }

trait Geometry {

  val jtsGeometry: jts.Geometry

  def isValid: Boolean = jtsGeometry.isValid

  def isSimple: Boolean = jtsGeometry.isSimple

  def isEmpty: Boolean = jtsGeometry.isEmpty

  def contains(that: Geometry): Boolean = {
    jtsGeometry.contains(that.jtsGeometry)
  }

  def covers(that: Geometry): Boolean = {
    jtsGeometry.covers(that.jtsGeometry)
  }

  def crosses(that: Geometry): Boolean = {
    jtsGeometry.crosses(that.jtsGeometry)
  }

  def disjoint(that: Geometry): Boolean = {
    jtsGeometry.disjoint(that.jtsGeometry)
  }

  def equal(that: Geometry): Boolean = {
    jtsGeometry.equalsExact(that.jtsGeometry)
  }

  def almostEqual(that: Geometry, tolerance: Double): Boolean = {
    jtsGeometry.equalsExact(that.jtsGeometry, tolerance)
  }

  def intersects(that: Geometry): Boolean = {
    jtsGeometry.intersects(that.jtsGeometry)
  }

  def touches(that: Geometry): Boolean = {
    jtsGeometry.touches(that.jtsGeometry)
  }

  def isWithinDistance(that: Geometry, distance: Double): Boolean = {
    jtsGeometry.isWithinDistance(that.jtsGeometry, distance: Double)
  }

  def centroid: Point = Point(jtsGeometry.getCentroid)

  def vertices: Array[jts.Coordinate] = jtsGeometry.getCoordinates

  def toWKT: String = jtsGeometry.toText

}
