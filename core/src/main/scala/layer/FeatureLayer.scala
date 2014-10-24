package layer

import feature._
import org.osgeo.proj4j.{
  CRSFactory,
  CoordinateReferenceSystem,
  CoordinateTransformFactory,
  CoordinateTransform,
  ProjCoordinate
}

object FeatureLayer {

  lazy val crsFactory = new CRSFactory

  def apply(name: String, fc: FeatureCollection): FeatureLayer = {
    val crs = crsFactory.createFromName("EPSG:4326")
    FeatureLayer(name, crs, fc)
  }

}

case class FeatureLayer(name: String, crs: CoordinateReferenceSystem, fc: FeatureCollection) {

  def numFeatures: Int = fc.count
}
