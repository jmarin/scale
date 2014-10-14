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
}
