package io.shapefile

abstract class ShapeType
case class NullShape(value: Int = 0) extends ShapeType
case class Point(value: Int = 1) extends ShapeType
case class PolyLine(value: Int = 3) extends ShapeType
case class Polygon(value: Int = 5) extends ShapeType
case class MultiPoint(value: Int = 8) extends ShapeType
case class PointZ(value: Int = 11) extends ShapeType
case class PolyLineZ(value: Int = 13) extends ShapeType
case class PolygonZ(value: Int = 15) extends ShapeType
case class MultiPointZ(value: Int = 18) extends ShapeType
case class PointM(value: Int = 21) extends ShapeType
case class PolyLineM(value: Int = 23) extends ShapeType
case class PolygonM(value: Int = 25) extends ShapeType
case class MultiPointM(value: Int = 28) extends ShapeType
case class MultiPatch(value: Int = 31) extends ShapeType

