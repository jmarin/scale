package io.shapefile

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.ByteBuffer
import java.nio.ByteOrder

object ShapefileReader {

  private def readRecordHeader(bb: ByteBuffer): RecordHeader = {
    bb.order(ByteOrder.BIG_ENDIAN)
    val recordNumber = bb.getInt()
    val recordLength = bb.getInt()
    bb.order(ByteOrder.LITTLE_ENDIAN)
    RecordHeader(recordNumber, recordLength)
  }

  def readPoints(bb: ByteBuffer): List[geometry.Point] = {
    def loop(list: List[geometry.Point], n: Int): List[geometry.Point] = n match {
      case 0 => list
      case _ => {
        val header = readRecordHeader(bb)
        val shapeType = bb.getInt()
        val x = bb.getDouble()
        val y = bb.getDouble()
        val point = geometry.Point(x, y)
        loop(point :: list, bb.remaining)
      }
    }
    loop(Nil, bb.remaining)
  }

  def apply(path: String): ShapefileReader = {
    val paths = Paths.get(path)
    val bytes = Files.readAllBytes(paths)
    val bb = ByteBuffer.wrap(bytes)
    val header = ShapefileHeader(bb)
    val records: List[geometry.Geometry] = header.shapeType match {
      case NullShape(0) =>
        Nil
      case Point(1) =>
        readPoints(bb)
      case PolyLine(3) =>
        Nil
      case Polygon(5) =>
        Nil
      case MultiPoint(8) =>
        Nil
      case PointZ(11) =>
        Nil
      case PolyLineZ(13) =>
        Nil
      case PolygonZ(15) =>
        Nil
      case MultiPointZ(18) =>
        Nil
    }

    ShapefileReader(header, records)
  }

  //def readPolylines() {}

  //def readPolygons() {}

}

case class ShapefileReader(header: ShapefileHeader, geometries: List[geometry.Geometry])
