package feature

import scala.collection._
import scala.collection.generic._
import scala.collection.mutable.ArrayBuffer

object FeatureSeq extends SeqFactory[FeatureSeq] {

  implicit def canBuildFrom[F]: CanBuildFrom[Coll, F, FeatureSeq[F]] =
    new GenericCanBuildFrom[F]

  def newBuilder[F] = new scala.collection.mutable.LazyBuilder[F, FeatureSeq[F]] {
    def result = {
      val data = parts.foldLeft(List[F]()) { (l, n) => l ++ n }
      new FeatureSeq(data: _*)
    }
  }

}

class FeatureSeq[F](seq: F*)
    extends Serializable
    with Seq[F]
    with GenericTraversableTemplate[F, FeatureSeq]
    with SeqLike[F, FeatureSeq[F]] {

  override def companion = FeatureSeq

  def iterator: Iterator[F] = seq.iterator

  def apply(idx: Int): F = {
    if (idx < 0 || idx >= length) throw new IndexOutOfBoundsException
    seq(idx)
  }

  def length: Int = seq.size

}
