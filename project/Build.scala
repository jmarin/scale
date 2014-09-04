import sbt._
import Keys._
import com.typesafe.sbt.SbtScalariform

object BuildSettings {
  val buildOrganization = "scale"
  val buildVersion = "0.0.1-SNAPSHOT"
  val buildScalaVersion = "2.11.2"

  val buildSettings = Defaults.defaultSettings ++ SbtScalariform.defaultScalariformSettings ++
  Seq(
    organization := buildOrganization,
    version      := buildVersion,
    scalaVersion := buildScalaVersion,
    scalacOptions ++= Seq("-deprecation","-unchecked","-feature")
  )

}

object Resolvers {
  val opengeoResolver = Seq("OpenGeo Repository" at "http://repo.opengeo.org")
}

object Dependencies {
  val specs2Version = "2.4.2"
  val jtsVersion    = "1.13"
  val proj4jVersion = "0.1.0"

  val specs2 = "org.specs2"         %% "specs2" % specs2Version % "test" 
  val jts    = "com.vividsolutions" %  "jts"    % jtsVersion
  val proj4j = "org.osgeo"          %  "proj4j" % proj4jVersion
}

object ScaleBuild extends Build {
  import Resolvers._
  import Dependencies._
  import BuildSettings._

  val commonDeps = Seq(specs2)

  val coreDeps = commonDeps ++ Seq(jts,proj4j)

  lazy val scale = Project(
    "scale",
    file("."),
    settings = buildSettings
  ).aggregate(core)

  lazy val core = Project(
    "core",
    file("core"),
    settings = buildSettings ++ Seq(resolvers := opengeoResolver,
                                    libraryDependencies ++= coreDeps) 
  )

}
