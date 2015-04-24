lazy val commonSettings = Defaults.coreDefaultSettings ++
  scalariformSettings ++
  Seq(
    organization := "com.github.jmarin",
    version := "0.0.3-SNAPSHOT",
    scalaVersion := "2.11.6"
  )


val library = crossProject
  .settings(commonSettings: _*)
  .settings(
  libraryDependencies += "com.lihaoyi" %%% "utest" % "0.3.0",
  testFrameworks += new TestFramework("utest.runner.Framework")
).jsSettings(
    // JS-specific settings
  ).jvmSettings(
    // JVM-specific settings
  )

lazy val js = library.js

lazy val jvm = library.jvm