package geometry

import com.vividsolutions.jts.{ geom => jts }

object Envelope {

  def apply(p1: Point, p2: Point): Envelope = {
    Envelope(new jts.Envelope(p1.jtsGeometry.asInstanceOf[jts.Point].getCoordinate,
      p2.jtsGeometry.asInstanceOf[jts.Point].getCoordinate))
  }

  def apply(xmin: Double, xmax: Double, ymin: Double, ymax: Double): Envelope = {
    Envelope(new jts.Envelope(xmin, xmax, ymin, ymax))
  }

}

case class Envelope(jtsEnvelope: jts.Envelope) {

  def width: Double = jtsEnvelope.getWidth

  def height: Double = jtsEnvelope.getHeight

  def xmin: Double = jtsEnvelope.getMinX

  def ymin: Double = jtsEnvelope.getMinY

  def xmax: Double = jtsEnvelope.getMaxX

  def ymax: Double = jtsEnvelope.getMaxY

  def centroid: Point = Point(jtsEnvelope.centre.x, jtsEnvelope.centre.y)

  def intersects(point: Point): Boolean = {
    jtsEnvelope.intersects(point.jtsGeometry.getCoordinate)
  }

  def intersection(env: Envelope): Envelope = {
    Envelope(jtsEnvelope.intersection(env.jtsEnvelope))
  }

}
