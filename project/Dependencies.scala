import sbt._
import Version._

object Dependencies {
  val scalatest = "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
  val scalacheck = "org.scalacheck" %% "scalacheck" % scalacheckVersion % "test"
  val jts = "com.vividsolutions" % "jts" % jtsVersion
  val proj4j = "org.osgeo" % "proj4j" % proj4jVersion
  val sprayJson = "io.spray" %% "spray-json" % sprayJsonVersion
  val dbf = "com.linuxense" % "javadbf" % dbfVersion
}
