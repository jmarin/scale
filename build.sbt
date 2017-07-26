import BuildSettings._
import Dependencies._
import Resolvers._

val commonDeps = Seq(scalatest, scalacheck)

val coreDeps = commonDeps ++ Seq(jts, proj4j)

val serializeDeps = coreDeps ++ Seq(sprayJson)
val shpDeps = coreDeps ++ Seq(dbf)
val geojsonDeps = coreDeps ++ Seq(sprayJson)

lazy val scale = Project(
  "scale",
  file("."),
  settings = scaleSettings
).aggregate(core, shp, geojson, vectorTiles)

lazy val core = Project(
  "scale-core",
  file("core"),
  settings = scaleSettings ++ Seq(resolvers := boundlessResolver,
                                  libraryDependencies ++= coreDeps)
)

lazy val shp = Project(
  "scale-shapefile",
  file("io/shapefile"),
  settings = scaleSettings ++ Seq(libraryDependencies ++= shpDeps)
).dependsOn(core)

lazy val geojson = Project(
  "scale-geojson",
  file("io/geojson"),
  settings = scaleSettings ++ Seq(libraryDependencies ++= geojsonDeps)
).dependsOn(core)

lazy val vectorTiles = Project(
  "scale-vector-tiles",
  file("vector-tiles"),
  settings = scaleSettings ++ Seq(
    PB.targets in Compile := Seq(
      scalapb.gen() -> (sourceManaged in Compile).value
    )
  )
)
