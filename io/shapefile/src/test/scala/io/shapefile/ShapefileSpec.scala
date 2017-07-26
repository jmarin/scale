package io.shapefile

import java.nio.file.FileSystems

import geometry.Envelope
import org.scalatest.{MustMatchers, WordSpec}

class ShapefileSpec extends WordSpec with MustMatchers {

  val pointShp = FileSystems.getDefault
    .getPath("io/shapefile/src/test/resources", "Point.shp")
    .toFile
    .getAbsolutePath
  val pointDbf = FileSystems.getDefault
    .getPath("io/shapefile/src/test/resources", "Point.dbf")
    .toFile
    .getAbsolutePath
  val lineShp = FileSystems.getDefault
    .getPath("io/shapefile/src/test/resources", "Line.shp")
    .toFile
    .getAbsolutePath
  val polyShp = FileSystems.getDefault
    .getPath("io/shapefile/src/test/resources", "Polygon.shp")
    .toFile
    .getAbsolutePath

  "A Shapefile" must {
    "Have the correct header" in {
      val pointHeader = ShapefileHeader(pointShp)
      val lineHeader = ShapefileHeader(lineShp)
      val polyHeader = ShapefileHeader(polyShp)

      val pointEnv = Envelope(-98.65384615384608,
                              64.82672995375012,
                              158.8846153846156,
                              -15.134808507788364)

      val lineEnv = Envelope(-75.94530094304724,
                             38.82821502216727,
                             -65.0545603735206,
                             52.43181154583593)

      val polyEnv = Envelope(-78.53365384615375,
                             42.82432610759628,
                             -64.90384615384605,
                             48.73057610759628)

      pointHeader.envelope mustBe pointEnv
      lineHeader.envelope mustBe lineEnv
      polyHeader.envelope mustBe polyEnv
      pointHeader.shapeType mustBe Point()
      lineHeader.shapeType mustBe PolyLine()
      polyHeader.shapeType mustBe Polygon()
    }

    "Read correct number of records" in {
      val shpPoint = ShapefileReader(pointShp)
      shpPoint.fc.count mustBe 3
      val shpLine = ShapefileReader(lineShp)
      shpLine.fc.count mustBe 3
      val shpPolygon = ShapefileReader(polyShp)
      shpPolygon.fc.count mustBe 3
    }
  }

}
