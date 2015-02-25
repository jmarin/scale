package feature

import com.vividsolutions.jts.{ geom => jts }
import geometry._
import feature._

object FeatureCollection {

  def apply(features: Feature*): FeatureCollection = {
    FeatureCollection(features.toIterable)
  }
}

case class FeatureCollection(features: Traversable[Feature]) {

  def count: Int = features.size

  def envelope: Envelope = {
    val envs = features.map(f => f.geometry.envelope)
    envs.reduceLeft[Envelope] { (l, r) =>
      l union r
    }
  }

  def pointInPoly(p: Point): Option[Traversable[Feature]] = {
    features.foreach { f =>
      val geomType = f.geometry.geometryType
      require(geomType == "Polygon" || geomType == "MultiPolygon")
    }
    val polys = features.filter(f => f.geometry.contains(p))
    if (polys.size > 0) Some(polys) else None
  }
}
