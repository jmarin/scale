package feature

import com.vividsolutions.jts.{ geom => jts }
import geometry._
import feature._

object FeatureCollection {

  def apply[K, V](features: Feature[K, V]*): FeatureCollection[K, V] = {
    FeatureCollection(features.toIterable)
  }
}

case class FeatureCollection[K, V](features: Traversable[Feature[K, V]]) {

  def count: Int = features.size

  def envelope: Envelope = {
    val envs = features.map(f => f.geometry.envelope)
    envs.reduceLeft[Envelope] { (l, r) =>
      l union r
    }
  }
}
