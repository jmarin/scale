name := "Scale root project"

lazy val root = project.in(file(".")).
  aggregate(scaleJS, scaleJVM).
  settings(
    publish := {},
    publishLocal := {}
  )

lazy val scale = crossProject.in(file("scale")).
  settings(
    name := "scale",
    version := "0.0.3-SNAPSHOT",
    scalaVersion := "2.11.6",
    libraryDependencies ++= Seq(
    "com.lihaoyi" %%% "utest" % "0.3.1" % "test",
    "com.lihaoyi" %%% "upickle" % "0.2.8"),
    testFrameworks += new TestFramework("utest.runner.Framework")
  ).
  jvmSettings(
    // Add JVM-specific settings here
    name := "scaleJVM"
  ).
  jsSettings(
    // Add JS-specific settings here
    name := "scaleJS",
    scalaJSStage in Global := FastOptStage
  )

lazy val scaleJVM = scale.jvm
lazy val scaleJS = scale.js
