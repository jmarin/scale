import sbt._
import Keys._
import com.typesafe.sbt.SbtScalariform._
import sbtprotobuf.{ProtobufPlugin=>PB}

object BuildSettings {
  val buildOrganization = "com.github.jmarin"
  val buildVersion = "0.0.1"
  val buildScalaVersion = "2.11.5"

  val buildSettings = Defaults.coreDefaultSettings ++ 
    scalariformSettings ++
    org.scalastyle.sbt.ScalastylePlugin.Settings ++
    xerial.sbt.Sonatype.sonatypeSettings ++
    Seq(
      organization := buildOrganization,
      version      := buildVersion,
      scalaVersion := buildScalaVersion,
      scalacOptions ++= Seq("-deprecation","-unchecked","-feature"),
      publishMavenStyle := true,
      publishArtifact in Test := false,
      publishTo := {
       val nexus = "https://oss.sonatype.org/"
       if (isSnapshot.value)
         Some("snapshots" at nexus + "content/repositories/snapshots")
       else
         Some("releases"  at nexus + "service/local/staging/deploy/maven2")
      },
      pomIncludeRepository := { _ => false },
      pomExtra := (
        <url>http://jmarin.github.io/scale</url>  
        <licenses>
          <license>
            <name>Apache License, Version 2.0</name>
            <url>http://www.apache.org/licenses/LICENSE-2.0</url>
            <distribution>repo</distribution>
          </license>
        </licenses>
        <scm>
          <url>https://github.com/jmarin/scale.git</url>
          <connection>scm:git:git@github.com:jmarin/scale.git</connection>
        </scm>
        <developers>
          <developer>
            <id>jmarin</id>
            <name>Juan Marin Otero</name>
            <url>http://jmarin.github.io/</url>
          </developer>
        </developers>
      )
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

  val specs2 = "org.specs2" %% "specs2" % specs2Version % "test" 
  val scalacheck = "org.scalacheck" %% "scalacheck" % scalacheckVersion % "test"
  val jts    = "com.vividsolutions" %  "jts" % jtsVersion
  val proj4j = "org.osgeo" % "proj4j" % proj4jVersion
  val sprayJson = "io.spray" %% "spray-json" % sprayJsonVersion

  val dbf = "com.linuxense" % "javadbf" % dbfVersion
}

object ScaleBuild extends Build {
  import Resolvers._
  import Dependencies._
  import BuildSettings._

  val commonDeps = Seq(specs2, scalacheck)

  val coreDeps = commonDeps ++ Seq(jts,proj4j)

  val serializeDeps = coreDeps ++ Seq(sprayJson)
  val shpDeps = coreDeps ++ Seq(dbf)
  val geojsonDeps = coreDeps ++ Seq(sprayJson)

  lazy val scale = Project(
    "scale",
    file("."),
    settings = buildSettings
 ).aggregate(core, shp, geojson)

  lazy val core = Project(
    "scale-core",
    file("core"),
    settings = buildSettings ++ Seq(resolvers := boundlessResolver,
                                    libraryDependencies ++= coreDeps) 
  )

  lazy val shp = Project(
    "scale-shapefile",
    file("io/shapefile"),
    settings = buildSettings ++ Seq(libraryDependencies ++= shpDeps)
  ).dependsOn(core)

  lazy val geojson = Project(
    "scale-geojson",
    file("io/geojson"),
    settings = buildSettings ++ Seq(libraryDependencies ++= geojsonDeps)
  ).dependsOn(core)

}
