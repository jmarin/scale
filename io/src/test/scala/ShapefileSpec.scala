package io.shapefile

import java.nio.file.FileSystems
import geometry.Envelope
import org.specs2.mutable.Specification

class ShapefileHeaderSpec extends Specification {

  val pointShp = FileSystems.getDefault.getPath("io/src/test/resources", "Point.shp").toFile.getAbsolutePath
  val lineShp = FileSystems.getDefault.getPath("io/src/test/resources", "Line.shp").toFile.getAbsolutePath
  val polyShp = FileSystems.getDefault.getPath("io/src/test/resources", "Polygon.shp").toFile.getAbsolutePath

  "A Shapefile" should {
    "Have the correct header" in {
      val pointHeader = ShapefileHeader(pointShp)
      val lineHeader = ShapefileHeader(lineShp)
      val polyHeader = ShapefileHeader(polyShp)
      val pointEnv = Envelope(-98.65384615384608, 64.82672995375012, 158.8846153846156, -15.134808507788364)
      val lineEnv = Envelope(-111.11538461538454, -29.154039277019137, -45.17307692307682, 55.480576107596306)
      val polyEnv = Envelope(-78.53365384615375, 42.82432610759628, -64.90384615384605, 48.73057610759628)
      pointHeader.envelope must be equalTo (pointEnv)
      lineHeader.envelope must be equalTo (lineEnv)
      polyHeader.envelope must be equalTo (polyEnv)
      pointHeader.shapeType must be equalTo (Point())
      lineHeader.shapeType must be equalTo (PolyLine())
      polyHeader.shapeType must be equalTo (Polygon())

    }
    "Read correct number of records" in {
      val shpPoint = ShapefileReader(pointShp)
      shpPoint.geometries.size must be equalTo (3)
    }
  }

}
