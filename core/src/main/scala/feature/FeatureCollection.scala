package feature

import geometry._

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
    val rtree = RTree(features)
    val polys = rtree.query(p.envelope)
    if (polys.nonEmpty)
      Some(polys)
    else
      None
  }

}
