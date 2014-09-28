package feature

import scala.collection._
import scala.collection.generic._
import scala.collection.mutable.ListBuffer

object FeatureTraversable extends TraversableFactory[FeatureTraversable] {

  implicit def canBuildFrom[F]: CanBuildFrom[Coll, F, FeatureTraversable[F]] =
    new GenericCanBuildFrom[F]

  def newBuilder[F] = new scala.collection.mutable.LazyBuilder[F, FeatureTraversable[F]] {
    def result = {
      val data = parts.foldLeft(List[F]()) { (l, n) => l ++ n }
      new FeatureTraversable(data: _*)
    }
  }

}

class FeatureTraversable[F](seq: F*)
    extends Serializable
    with Traversable[F]
    with GenericTraversableTemplate[F, FeatureTraversable]
    with TraversableLike[F, FeatureTraversable[F]] {

  override def companion = FeatureTraversable

  def foreach[U](f: F => U) = seq.toSeq.foreach(f)

  def toSeq[F] = new FeatureSeq(seq).flatten

}

