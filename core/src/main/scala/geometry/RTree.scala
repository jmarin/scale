package geometry

import java.util

import com.vividsolutions.jts.index.strtree.STRtree
import feature.Feature

import scala.collection.JavaConverters

object RTree {

  def apply(features: Traversable[Feature]): RTree = {
    val strtree = new STRtree()
    features.foreach { f =>
      val e = f.geometry.envelope
      strtree.insert(e.jtsEnvelope, f)
    }
    RTree(strtree)
  }

}

case class RTree(jtsRTree: STRtree) {

  def query(envelope: Envelope): Iterable[Feature] = {
    val e = envelope.jtsEnvelope
    val list: util.ArrayList[Feature] =
      jtsRTree.query(e).asInstanceOf[util.ArrayList[Feature]]
    JavaConverters.collectionAsScalaIterable(list)
  }

}
