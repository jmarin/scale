
resolvers += "OSGeo Repository" at "http://repo.opengeo.org"


libraryDependencies ++= {
  val jtsVersion = "1.13"
  val proj4jVersion = "0.1.0"
  Seq(
    "com.vividsolutions" % "jts" % jtsVersion,
    "org.osgeo" % "proj4j" % proj4jVersion
  )
}
