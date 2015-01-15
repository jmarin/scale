import sbt._
import Keys._
import com.typesafe.sbt.SbtScalariform._
import sbtprotobuf.{ProtobufPlugin=>PB}

object BuildSettings {
  val buildOrganization = "scale"
  val buildVersion = "0.0.1-SNAPSHOT"
  val buildScalaVersion = "2.11.4"

  val buildSettings = Defaults.defaultSettings ++ 
    scalariformSettings ++
    org.scalastyle.sbt.ScalastylePlugin.Settings ++
    Seq(
      organization := buildOrganization,
      version      := buildVersion,
      scalaVersion := buildScalaVersion,
      scalacOptions ++= Seq("-deprecation","-unchecked","-feature")
    )
}

object Resolvers {
  val boundlessResolver = Seq("Boundless Repository" at "http://repo.boundlessgeo.com/main")
}

object Dependencies {
  val specs2Version = "2.4.2"
  val scalacheckVersion = "1.11.6"
  val jtsVersion    = "1.13"
  val proj4jVersion = "0.1.0"
  val sprayJsonVersion = "1.3.0"
  val dbfVersion = "0.4.0"
  val elasticsearchVersion = "1.4.2"
  val configVersion = "1.2.1"

  //Core dependencies
  val specs2 = "org.specs2" %% "specs2" % specs2Version % "test" 
  val scalacheck = "org.scalacheck" %% "scalacheck" % scalacheckVersion % "test"
  val jts    = "com.vividsolutions" %  "jts" % jtsVersion
  val proj4j = "org.osgeo" % "proj4j" % proj4jVersion
  
  //Serialization Dependencies
  val sprayJson = "io.spray" %% "spray-json" % sprayJsonVersion

  //io dependencies
  val dbf = "com.linuxense" % "javadbf" % dbfVersion
  val elasticsearch = "org.elasticsearch" % "elasticsearch" % elasticsearchVersion
  val config = "com.typesafe" % "config" % configVersion
}

object ScaleBuild extends Build {
  import Resolvers._
  import Dependencies._
  import BuildSettings._

  val commonDeps = Seq(specs2, scalacheck)
  val coreDeps = commonDeps ++ Seq(jts,proj4j)
  val serializeDeps = coreDeps ++ Seq(sprayJson)
  val shpDeps = coreDeps ++ Seq(dbf)
  val esDeps = coreDeps ++ Seq(elasticsearch, config)

  lazy val scale = Project(
    "scale",
    file("."),
    settings = buildSettings
  ).aggregate(core, serialization, shp, es)

  lazy val core = Project(
    "scale-core",
    file("core"),
    settings = buildSettings ++ Seq(resolvers := boundlessResolver,
                                    libraryDependencies ++= coreDeps) 
  )

  lazy val serialization = Project(
    "scale-serialization",
    file("serialization"),
    settings = buildSettings 
                ++ PB.protobufSettings
                ++ Seq(
                  resolvers := boundlessResolver,
                  javaSource in PB.protobufConfig <<= (sourceDirectory in Compile)(_ / "java"),
                  libraryDependencies ++= serializeDeps)
  ).dependsOn(core)

  lazy val shp = Project(
    "scale-shapefile",
    file("io/shapefile"),
    settings = buildSettings ++ Seq(libraryDependencies ++= shpDeps)
  ).dependsOn(core)

  lazy val es = Project(
    "scale-elasticsearch",
    file("io/elasticsearch"),
    settings = buildSettings ++ Seq(libraryDependencies ++= esDeps)
  ).dependsOn(core)

}
