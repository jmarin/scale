package io.shapefile

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.ByteBuffer
import java.nio.ByteOrder

import geometry.Envelope
import scala.collection.mutable.ArrayBuffer
import com.linuxense.javadbf._

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

  def readPolyLines(bb: ByteBuffer): List[geometry.Line] = {
    def loop(list: List[geometry.Line], n: Int): List[geometry.Line] = n match {
      case 0 => list
      case _ => {
        val header = readRecordHeader(bb)
        val shapeType = bb.getInt()
        val xmin = bb.getDouble()
        val ymin = bb.getDouble()
        val xmax = bb.getDouble()
        val ymax = bb.getDouble()
        val envelope = Envelope(xmin, ymin, xmax, ymax)
        val numParts = bb.getInt()
        val numPoints = bb.getInt()
        for (i <- 1 to numParts) {
          bb.getInt()
        }

        def loopPoints(points: List[geometry.Point], a: Int): List[geometry.Point] = a match {
          case 0 => points
          case _ => {
            val x = bb.getDouble()
            val y = bb.getDouble()
            val point = geometry.Point(x, y)
            loopPoints(point :: points, a - 1)
          }
        }

        val points = loopPoints(Nil, numPoints)
        val line = geometry.Line(points)
        loop(line :: list, bb.remaining)
      }
    }
    loop(Nil, bb.remaining)
  }

  def readPolygons(bb: ByteBuffer): List[geometry.Polygon] = {
    def loop(list: List[geometry.Polygon], n: Int): List[geometry.Polygon] = n match {
      case 0 => list
      case _ =>
        val header = readRecordHeader(bb)
        val shapeType = bb.getInt()
        val xmin = bb.getDouble()
        val ymin = bb.getDouble()
        val xmax = bb.getDouble()
        val ymax = bb.getDouble()
        val envelope = Envelope(xmin, ymin, xmax, ymax)
        val numParts = bb.getInt()
        val numPoints = bb.getInt()
        for (i <- 1 to numParts) {
          bb.getInt()
        }

        def loopPoints(points: List[geometry.Point], a: Int): List[geometry.Point] = a match {
          case 0 => points
          case _ => {
            val x = bb.getDouble()
            val y = bb.getDouble()
            val point = geometry.Point(x, y)
            loopPoints(point :: points, a - 1)
          }
        }

        val points = loopPoints(Nil, numPoints)
        val polygon = geometry.Polygon(points)
        loop(polygon :: list, bb.remaining)
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
        throw new Exception("NullShape not supported")
        Nil
      case Point(1) =>
        readPoints(bb)
      case PolyLine(3) =>
        readPolyLines(bb)
      case Polygon(5) =>
        readPolygons(bb)
      case MultiPoint(8) =>
        throw new Exception("MultiPoint not supported yet")
        Nil
      case PointZ(11) =>
        throw new Exception("PointZ not supported yet")
        Nil
      case PolyLineZ(13) =>
        throw new Exception("PolyLineZ not supported yet")
        Nil
      case PolygonZ(15) =>
        throw new Exception("PolygonZ not supported yet")
        Nil
      case MultiPointZ(18) =>
        throw new Exception("MultiPointZ not supported yet")
        Nil
    }
    //val dbfHeader = 
    ShapefileReader(header, records)
  }

}

case class ShapefileReader(header: ShapefileHeader, geometries: List[geometry.Geometry])
