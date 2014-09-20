package geometry

import com.vividsolutions.jts.{ geom => jts }

object GeometryCollection {

  private val geomFactory = new jts.GeometryFactory

  def apply(geometries: Geometry*): GeometryCollection = {
    apply(geometries.toList)
  }

  def apply(geometries: List[Geometry]): GeometryCollection = {
    val geoms = geometries.map(g => g.jtsGeometry).toArray
    GeometryCollection(new jts.GeometryCollection(geoms, geomFactory))
  }

}

case class GeometryCollection(jtsGeometry: jts.GeometryCollection) extends Geometry {

  def area = {
    jtsGeometry.getArea
  }

}
