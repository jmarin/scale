package io.shapefile

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.ByteBuffer
import java.nio.ByteOrder

import java.io._

import geometry.Envelope
import feature._
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

  private def readPoints(bb: ByteBuffer): List[geometry.Point] = {
    def loop(list: List[geometry.Point], n: Int): List[geometry.Point] = n match {
      case 0 => list
      case _ =>
        val header = readRecordHeader(bb)
        val shapeType = bb.getInt()
        val x = bb.getDouble()
        val y = bb.getDouble()
        val point = geometry.Point(x, y)
        loop(point :: list, bb.remaining)
    }
    loop(Nil, bb.remaining)
  }

  private def readPolyLines(bb: ByteBuffer): List[geometry.Line] = {
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
          case _ =>
            val x = bb.getDouble()
            val y = bb.getDouble()
            val point = geometry.Point(x, y)
            loopPoints(point :: points, a - 1)
        }

        val points = loopPoints(Nil, numPoints)
        val line = geometry.Line(points)
        loop(line :: list, bb.remaining)
      }
    }
    loop(Nil, bb.remaining)
  }

  private def readPolygons(bb: ByteBuffer): List[geometry.Polygon] = {
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
          case _ =>
            val x = bb.getDouble()
            val y = bb.getDouble()
            val point = geometry.Point(x, y)
            loopPoints(point :: points, a - 1)
        }

        val points = loopPoints(Nil, numPoints)
        val polygon = geometry.Polygon(points)
        loop(polygon :: list, bb.remaining)
    }
    loop(Nil, bb.remaining)
  }

  private def readGeometries(path: String): List[geometry.Geometry] = {
    val paths = Paths.get(path)
    val bytes = Files.readAllBytes(paths)
    val bb = ByteBuffer.wrap(bytes)
    val header = ShapefileHeader(bb)
    header.shapeType match {
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
  }
  private def readFields(dbfPath: String): List[Field] = {
    val inputStream = new FileInputStream(dbfPath)
    try {
      val dbfReader = new DBFReader(inputStream)
      val fieldCount = dbfReader.getFieldCount
      val fieldList = Nil

      def loopFields(list: List[Field], n: Int): List[Field] = n match {
        case 0 => list
        case _ =>
          val dbfField = dbfReader.getField(n - 1)
          val fieldName = dbfField.getName
          val fieldLength = dbfField.getFieldLength
          val dbfFieldType = dbfField.getDataType.asInstanceOf[Char]
          val decimalCount = dbfField.getDecimalCount
          val fieldType = dbfFieldType match {
            case 'N' =>
              if (decimalCount == 0) IntType() else DoubleType()
            case 'F' => DoubleType()
            case 'C' => StringType()
            case 'L' => BooleanType()
            case 'D' => DateType()
          }
          val field = Field(fieldName, fieldType)
          loopFields(field :: list, n - 1)
      }
      loopFields(Nil, fieldCount)
    } finally {
      inputStream.close
    }
  }

  private def readRecords(dbfPath: String, schema: Schema): List[Map[String, Any]] = {
    val inputStream = new FileInputStream(dbfPath)
    try {
      val dbfReader = new DBFReader(inputStream)
      val count = dbfReader.getRecordCount
      val recordList = Nil

      def loopRows(list: List[Any], n: Int): List[Any] = n match {
        case 0 => list
        case _ =>
          val row = dbfReader.nextRecord()
          loopRows(row :: list, n - 1)
      }

      val rows = loopRows(Nil, count)
      val records = rows.map(row => rowToValues(row.asInstanceOf[Array[java.lang.Object]]))
      val fields = readFields(dbfPath)
      records.map { record =>
        (fields.map(field => field.name) zip record).toMap
      }

    } catch {
      case e: DBFException => throw new Exception(e)
      case _: Throwable => throw new RuntimeException("An unknown error occured")
    } finally {
      inputStream.close
    }
  }

  private def rowToValues(row: Array[java.lang.Object]): List[Any] = {
    val count = row.length
    row.map { value =>
      value match {
        case i: java.lang.Integer => i.asInstanceOf[Integer]
        case s: java.lang.String => s.asInstanceOf[String].trim
        case d: java.lang.Double => d.asInstanceOf[Double]
        case b: java.lang.Boolean => b.asInstanceOf[Boolean]
        case dd: java.util.Date => dd
      }
    }.toList
  }

  def apply(path: String): ShapefileReader = {
    val dbfPath = path.replaceAll("\\.[^.]*$", ".dbf")
    val fields = readFields(dbfPath)
    val schema = Schema(fields)
    val records = readRecords(dbfPath, schema)
    val rawGeometries = readGeometries(path)
    val geometriesList = rawGeometries.map { geometry =>
      Map("geometry" -> geometry)
    }
    require(geometriesList.size == records.size, ".shp and .dbf don't have the same number of records")
    val allValues = geometriesList zip records
    val values = allValues.map {
      case (geometries, values) => (geometries.toSeq ++ values.toSeq).toMap
    }
    val features = values.map(value => Feature(schema, value))
    val fc = FeatureCollection(features)
    ShapefileReader(fc)
  }

}

case class ShapefileReader(fc: FeatureCollection)
