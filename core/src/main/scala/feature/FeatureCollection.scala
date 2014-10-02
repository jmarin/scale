package feature

import com.vividsolutions.jts.{ geom => jts }
import geometry._

trait FeatureCollection[Feature] {

  def features: Iterable[Feature]

  def count: Int

  def envelope(envs: Array[Envelope]): Envelope = {
    envs.reduceLeft[Envelope] { (l, r) =>
      l union r
    }
  }
}
