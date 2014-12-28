/**
 * http://www.esri.com/library/whitepapers/pdfs/shapefile.pdf
 */

package io.shapefile

import geometry.Envelope
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.Path
import java.nio.ByteBuffer
import java.nio.ByteOrder

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

object ShapefileHeader {

  def apply(path: String): ShapefileHeader = {
    val paths = Paths.get(path)
    val bytes = Files.readAllBytes(paths)
    val bb = ByteBuffer.wrap(bytes)
    apply(bb)
  }

  def apply(bb: ByteBuffer): ShapefileHeader = {
    bb.order(ByteOrder.BIG_ENDIAN)
    val fileCode = bb.getInt(0)
    require(fileCode == 9994, "This is not a shapefile, wrong File Code")

    val length = bb.getInt(24)
    bb.order(ByteOrder.LITTLE_ENDIAN)
    val version = bb.getInt(28)
    require(version == 1000, "This is not a shapefile, wrong Version")

    val shapeCode = bb.getInt(32)
    val shapeType = shapeCode match {
      case 0 => NullShape()
      case 1 => Point()
      case 3 => PolyLine()
      case 5 => Polygon()
      case 8 => MultiPoint()
      case 11 => PointZ()
      case 13 => PolyLineZ()
      case 15 => PolygonZ()
      case 18 => MultiPointZ()
      case 21 => PointM()
      case 23 => PolyLineM()
      case 25 => PolygonM()
      case 28 => MultiPointM()
      case 31 => MultiPatch()
    }
    val xmin = bb.getDouble(36)
    val ymin = bb.getDouble(44)
    val xmax = bb.getDouble(52)
    val ymax = bb.getDouble(60)
    val zmin = bb.getDouble(68)
    val zmax = bb.getDouble(76)
    val mmin = bb.getDouble(84)
    val mmax = bb.getDouble(92)
    val envelope = Envelope(xmin, ymin, xmax, ymax)
    bb.position(100) //Move ByteBuffer to position 100 (length of header)
    ShapefileHeader(length, shapeType, envelope)
  }

}

case class ShapefileHeader(length: Int, shapeType: ShapeType, envelope: Envelope)
