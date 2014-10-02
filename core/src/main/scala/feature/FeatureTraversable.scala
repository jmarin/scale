package feature

import scala.collection._
import scala.collection.generic._
import scala.collection.mutable.ListBuffer
import geometry.Envelope

object FeatureTraversable extends TraversableFactory[FeatureTraversable] {

  implicit def canBuildFrom[Feature]: CanBuildFrom[Coll, Feature, FeatureTraversable[Feature]] =
    new GenericCanBuildFrom[Feature]

  def newBuilder[Feature] = new scala.collection.mutable.LazyBuilder[Feature, FeatureTraversable[Feature]] {
    def result = {
      val data = parts.foldLeft(List[Feature]()) { (l, n) => l ++ n }
      new FeatureTraversable(data: _*)
    }
  }

}

class FeatureTraversable[Feature](seq: Feature*)
    extends FeatureCollection[Feature]
    with Serializable
    with Traversable[Feature]
    with GenericTraversableTemplate[Feature, FeatureTraversable]
    with TraversableLike[Feature, FeatureTraversable[Feature]] {

  override def features = seq.toIterable

  override def count = seq.toSeq.size

  override def companion = FeatureTraversable

  def foreach[U](f: Feature => U) = seq.toSeq.foreach(f)

  def toSeq[Feature] = new FeatureSeq(seq).flatten

}

