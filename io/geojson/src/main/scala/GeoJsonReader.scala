package io.geojson

import java.nio.file.Files.readAllBytes
import java.nio.file.Paths.get
import java.nio.charset.Charset;
import feature._
import spray.json._
import io.geojson.FeatureJsonProtocol._

object GeoJsonReader {

  def apply(path: String): GeoJsonReader = {
    val json = new String(readAllBytes(get(path)))
    val fc = json.parseJson.convertTo[FeatureCollection]
    GeoJsonReader(fc)
  }
}

case class GeoJsonReader(fc: FeatureCollection)
