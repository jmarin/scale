package feature

import org.osgeo.proj4j.{ CRSFactory, CoordinateReferenceSystem }
import geometry._

object Feature {

  def apply[K, V](id: String, geometry: Geometry): Feature[String, Any] = {
    val crsFactory = new CRSFactory
    val crs = crsFactory.createFromName("EPSG:4326")
    Feature(id, crs, geometry, Map.empty[String, Any])
  }

  def apply[K, V](id: String, srid: Int, geometry: Geometry): Feature[String, Any] = {
    val crsFactory = new CRSFactory
    val crs = crsFactory.createFromName(s"EPSG:$srid")
    Feature(id, crs, geometry, Map.empty[String, Any])
  }

  def apply[K, V](id: String, geometry: Geometry, values: Map[K, V]): Feature[K, V] = {
    val crsFactory = new CRSFactory
    val crs = crsFactory.createFromName("EPSG:4326")
    Feature(id, crs, geometry, values)
  }

  def apply[K, V](id: String, srid: Int, geometry: Geometry, values: Map[K, V]): Feature[K, V] = {
    val crsFactory = new CRSFactory
    val crs = crsFactory.createFromName(s"EPSG:$srid")
    Feature(id, crs, geometry, values)
  }

}

/**
 * The last parameter `values` is a map of (fieldName --> value)
 */
case class Feature[K, V](id: String, crs: CoordinateReferenceSystem, geometry: Geometry, values: Map[K, V]) {

  def countFields: Int = values.size

  def addOrUpdate(k: K, v: V): Feature[K, V] = Feature(id, crs, geometry, values.updated(k, v))

}
