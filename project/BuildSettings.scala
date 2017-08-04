import sbt.Defaults
import sbt.Keys._
import sbt._

object BuildSettings {
  val buildOrganization = "com.github.jmarin"
  val buildVersion = "0.0.3-SNAPSHOT"
  val buildScalaVersion = "2.12.3"

  val scaleSettings = Defaults.coreDefaultSettings ++
    xerial.sbt.Sonatype.sonatypeSettings ++
    Seq(
      organization := buildOrganization,
      version := buildVersion,
      scalaVersion := buildScalaVersion,
      crossScalaVersions := Seq("2.11.5", buildScalaVersion),
      scalacOptions ++= Seq("-Xlint", "-deprecation", "-unchecked", "-feature"),
      scalacOptions in Test ++= Seq("-Yrangepos"),
      publishMavenStyle := true,
      publishArtifact in Test := false,
      publishTo := Some(
        if (isSnapshot.value)
          Opts.resolver.sonatypeSnapshots
        else
          Opts.resolver.sonatypeStaging
      ),
      pomIncludeRepository := { _ =>
        false
      },
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
