package feature

import scala.collection._
import scala.collection.generic._
import scala.collection.mutable.ArrayBuffer

object FeatureSeq extends SeqFactory[FeatureSeq] {

  implicit def canBuildFrom[Feature]: CanBuildFrom[Coll, Feature, FeatureSeq[Feature]] =
    new GenericCanBuildFrom[Feature]

  def newBuilder[Feature] = new scala.collection.mutable.LazyBuilder[Feature, FeatureSeq[Feature]] {
    def result = {
      val data = parts.foldLeft(List[Feature]()) { (l, n) => l ++ n }
      new FeatureSeq(data: _*)
    }
  }

}

class FeatureSeq[Feature](seq: Feature*)
    extends Serializable
    with Seq[Feature]
    with GenericTraversableTemplate[Feature, FeatureSeq]
    with SeqLike[Feature, FeatureSeq[Feature]] {

  override def companion = FeatureSeq

  def iterator: Iterator[Feature] = seq.iterator

  def apply(idx: Int): Feature = {
    if (idx < 0 || idx >= length) throw new IndexOutOfBoundsException
    seq(idx)
  }

  def length: Int = seq.size

}
