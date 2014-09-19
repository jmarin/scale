package geometry

import scala.language.implicitConversions
import com.vividsolutions.jts.{ geom => jts }

object MultiPoint {

  private val geomFactory = new jts.GeometryFactory

  def apply(points: Point*): MultiPoint = {
    apply(points.toArray)
  }

  def apply(points: Array[Point]): MultiPoint = {
    val jtsPoints = points.map(p => p.jtsGeometry)
    MultiPoint(new jts.MultiPoint(jtsPoints, geomFactory))
  }

}

case class MultiPoint(jtsGeometry: jts.MultiPoint) extends Geometry {

  def boundary: Geometry = {
    val result = jtsGeometry.getBoundary
    Util.geometryType(result)
  }
}

