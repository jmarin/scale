import sbt._
import Keys._
import com.typesafe.sbt.SbtScalariform._
import sbtprotobuf.{ProtobufPlugin=>PB}

object BuildSettings {
  val buildOrganization = "scale"
  val buildVersion = "0.0.1-SNAPSHOT"
  val buildScalaVersion = "2.11.2"

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
  val opengeoResolver = Seq("OpenGeo Repository" at "http://repo.opengeo.org")
}

object Dependencies {
  val specs2Version = "2.4.2"
  val scalacheckVersion = "1.11.6"
  val jtsVersion    = "1.13"
  val proj4jVersion = "0.1.0"
  val sprayJsonVersion = "1.3.0"
  val akkaVersion = "2.3.6"
  val akkaStreamVersion = "0.9"

  val specs2 = "org.specs2" %% "specs2" % specs2Version % "test" 
  val scalacheck = "org.scalacheck" %% "scalacheck" % scalacheckVersion % "test"
  val jts    = "com.vividsolutions" %  "jts" % jtsVersion
  val proj4j = "org.osgeo" % "proj4j" % proj4jVersion
  val sprayJson = "io.spray" %% "spray-json" % sprayJsonVersion
  val akkaCluster = "com.typesafe.akka" %% "akka-cluster" % akkaVersion
  val akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
  val akkaStream = "com.typesafe.akka" % "akka-stream-experimental_2.11" % akkaStreamVersion
  val akkaHttpCore = "com.typesafe.akka" % "akka-http-core-experimental_2.11" % akkaStreamVersion
  val akkaTestkit = "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test"
 
}

object ScaleBuild extends Build {
  import Resolvers._
  import Dependencies._
  import BuildSettings._

  val commonDeps = Seq(specs2, scalacheck)

  val coreDeps = commonDeps ++ Seq(jts,proj4j)

  val serializeDeps = coreDeps ++ Seq(sprayJson)

  val geometryServiceDeps = coreDeps ++ serializeDeps ++ Seq(akkaCluster, akkaSlf4j, akkaTestkit, akkaStream, akkaHttpCore)

  lazy val scale = Project(
    "scale",
    file("."),
    settings = buildSettings
  ).aggregate(core, serialization, geometry)

  lazy val core = Project(
    "core",
    file("core"),
    settings = buildSettings ++ Seq(resolvers := opengeoResolver,
                                    libraryDependencies ++= coreDeps) 
  )

  lazy val serialization = Project(
    "serialization",
    file("serialization"),
    settings = buildSettings 
                ++ PB.protobufSettings
                ++ Seq(
                  resolvers := opengeoResolver,
                  javaSource in PB.protobufConfig <<= (sourceDirectory in Compile)(_ / "java"),
                  libraryDependencies ++= serializeDeps)
  ).dependsOn(core)

  lazy val geometry = Project(
    "geometry",
    file("geometry"),
    settings = buildSettings 
      ++ Seq(
        mainClass in (Compile, run) := Some("server.GeometryServiceApp"),
        javaOptions in run ++= Seq(
      //"-Djava.library.path=./sigar",
         "-Xms128m", "-Xmx1024m"),
       libraryDependencies ++= geometryServiceDeps
      )
  ).dependsOn(serialization)

}
