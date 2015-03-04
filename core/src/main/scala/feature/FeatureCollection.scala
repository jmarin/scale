package feature

import com.vividsolutions.jts.{ geom => jts }
import geometry._
import feature._

import java.util.Calendar
import com.vividsolutions.jts.index.strtree.STRtree

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

  def pointInPoly(p: Point): Option[Traversable[Feature]] = {

    val start = Calendar.getInstance().getTimeInMillis()

    features.foreach { f =>
      val geomType = f.geometry.geometryType
      require(geomType == "Polygon" || geomType == "MultiPolygon")
    }

    val polys = features.filter(f => f.geometry.contains(p))

    val end = Calendar.getInstance().getTimeInMillis()

    val time = end - start
    println("POINT IN POLY PROCESSING TIME: " + time + "ms")

    val start2 = Calendar.getInstance().getTimeInMillis()

    val strTree = new STRtree()
    features.foreach { f =>
      strTree.insert(f.geometry.jtsGeometry.asInstanceOf[jts.Polygon].getEnvelopeInternal, f.geometry.jtsGeometry)
    }

    strTree.build()

    val list = strTree.query(p.jtsGeometry.getEnvelopeInternal)

    val end2 = Calendar.getInstance().getTimeInMillis()

    println("POINT IN POLY STRTREE PROCESSING TIME: " + time + "ms")

    if (polys.size > 0) Some(polys) else None
  }
}
