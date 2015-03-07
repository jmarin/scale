package feature

import com.vividsolutions.jts.{ geom => jts }
import geometry._
import feature._

import java.util.Calendar
import com.vividsolutions.jts.index.strtree.STRtree

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
    if (polys.size > 0) Some(polys) else None
  }

}
