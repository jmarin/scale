package geometry

import collection.JavaConversions._
import com.vividsolutions.jts.index.strtree.STRtree
import com.vividsolutions.{jts => jts}
import feature.Feature

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

  def query(envelope: Envelope): Traversable[Feature] = {
    val e = envelope.jtsEnvelope
    jtsRTree.query(e).toTraversable.asInstanceOf[Traversable[Feature]]
  }

}
