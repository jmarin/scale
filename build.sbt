val commonSettings =
    scalariformSettings ++
    Seq(
    organization := "com.github.jmarin",
    version := "0.0.3-SNAPSHOT",
    scalaVersion := "2.11.6"
  )


val scale = crossProject
  .settings(commonSettings: _ *)
  .settings(
  libraryDependencies ++= Seq(
    "com.lihaoyi" %%% "utest" % "0.3.1" % "test",
    "com.lihaoyi" %%% "upickle" % "0.2.8"),
  testFrameworks += new TestFramework("utest.runner.Framework")
).jsSettings(
    // JS-specific settings

  ).jvmSettings(
    // JVM-specific settings

  )

lazy val js = scale.js

lazy val jvm = scale.jvm
