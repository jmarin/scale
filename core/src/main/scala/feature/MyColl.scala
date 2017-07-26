package feature

import scala.collection._
import scala.collection.generic._
import scala.language.higherKinds

class MyColl[A](seq: A*)
    extends Traversable[A]
    with GenericTraversableTemplate[A, MyColl] {
  override def companion = MyColl
  def foreach[U](f: A => U) = util.Random.shuffle(seq.toSeq).foreach(f)
}

object MyColl extends TraversableFactory[MyColl] {
  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, MyColl[A]] =
    new GenericCanBuildFrom[A]
  def newBuilder[A] = new scala.collection.mutable.LazyBuilder[A, MyColl[A]] {
    def result = {
      val data = parts.foldLeft(List[A]()) { (l, n) =>
        l ++ n
      }
      new MyColl(data: _*)
    }
  }
}
