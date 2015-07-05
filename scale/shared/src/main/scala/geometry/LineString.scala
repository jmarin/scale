package geometry

import scala.collection.GenSeq

object LineString {
  implicit val lineStringWriter = ???

  implicit val lineStringReader = ???


}

case class LineString(points: scala.collection.immutable.Seq[Point]) {
  def numPoints: Int = points.size
  def length: Double = ???
  def startPoint: Point = points.head
  def endPoint: Point = points.reverse.head
  def isClosed: Boolean = if (this.startPoint == this.endPoint) true else false
  def isRing: Boolean = ???
  def reverse: LineString = LineString(points.reverse)
  def pointAtDist(d: Double): Point = ???
  def pointAtDistWithOffset(d: Double, offset: Double): Point = ???
}
