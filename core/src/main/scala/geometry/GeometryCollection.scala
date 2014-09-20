package geometry

import com.vividsolutions.jts.{ geom => jts }

trait GeometryCollection extends Geometry {

  def geometryAt(n: Int) = jtsGeometry.getGeometryN(n)

  def numGeometries: Int = jtsGeometry.getNumGeometries

  def area = {
    jtsGeometry.getArea
  }

}
