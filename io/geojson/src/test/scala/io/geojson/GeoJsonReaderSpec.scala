package io.geojson

import java.nio.file.FileSystems

import feature._
import geometry._
import org.scalatest.{MustMatchers, WordSpec}

class GeoJsonReaderSpec extends WordSpec with MustMatchers {

  val pointGeoJson = FileSystems.getDefault
    .getPath("io/geojson/src/test/resources", "Point.geojson")
    .toFile
    .getAbsolutePath
  val lineGeoJson = FileSystems.getDefault
    .getPath("io/geojson/src/test/resources", "Line.geojson")
    .toFile
    .getAbsolutePath
  val polyGeoJson = FileSystems.getDefault
    .getPath("io/geojson/src/test/resources", "Polygon.geojson")
    .toFile
    .getAbsolutePath

  val schema = Schema(
    List(Field("geometry", GeometryType()),
         Field("id", StringType()),
         Field("Desc", StringType())))

  val p1 = Point(-98.653846153846075, 53.403653030673212)
  val props1 = Map("id" -> 1, "Desc" -> "One")
  val f1 = Feature(4326, schema, props1)

  "GeoJsonReader" should {
    "read Point collection" in {
      val json = GeoJsonReader(pointGeoJson)
      json.fc.count mustBe 3
    }
  }

}
