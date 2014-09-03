name := "scale"

version := "0.0.1"

scalaVersion := "2.11.2"

organization := "scale"

scalacOptions in ThisBuild ++= Seq("-unchecked", "-deprecation", "-feature")

lazy val root = project.in(file(".")).
  settings(scalariformSettings: _*).
  aggregate(core)

lazy val core = project.in(file("core")).
  settings(scalariformSettings: _*)

libraryDependencies ++= {
  val specs2Version = "2.3.11"
  Seq(
    "org.specs2" %% "specs2" % specs2Version % "test"  
  )
}
