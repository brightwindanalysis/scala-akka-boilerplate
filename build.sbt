/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

name := "scala-akka-boilerplate"

scalaVersion := "2.12.3"

import com.typesafe.config.{Config, ConfigFactory}

val typesafeConfig = settingKey[Config]("Typesafe config")
typesafeConfig := {
  ConfigFactory.parseFileAnySyntax((resourceDirectory in Compile).value / "application").resolve()
}

lazy val V = new {
  val akka = "2.5.3"
  val akkaHttp = "10.0.9"
  val logback = "1.2.3"
  val logentries = "1.1.38"

  val circe = "0.8.0"
  val dispatch = "0.13.1"

  val phantom = "2.13.0"
  val jodaTime = "2.9.9"

  val akkaHttpTestkit = "10.0.9"
  val scalatest = "3.0.3"
}

lazy val N = new {
  val typesafe = "com.typesafe.akka"
  val circe = "io.circe"
}

libraryDependencies ++= Seq(
  N.typesafe %% "akka-actor" % V.akka,
  N.typesafe %% "akka-stream" % V.akka,
  N.typesafe %% "akka-http" % V.akkaHttp exclude("com.typesafe.akka", "akka-stream_2.12"),
  N.typesafe %% "akka-slf4j" % V.akka,
  "ch.qos.logback" % "logback-classic" % V.logback,
  "com.logentries" % "logentries-appender" % V.logentries,

  N.circe %% "circe-core" % V.circe,
  N.circe %% "circe-generic" % V.circe,
  N.circe %% "circe-parser" % V.circe,
  N.circe %% "circe-java8" % V.circe,
  "net.databinder.dispatch" %% "dispatch-core" % V.dispatch,

  "com.outworkers" %% "phantom-dsl" % V.phantom,
  "joda-time" % "joda-time" % V.jodaTime,

  N.typesafe %% "akka-testkit" % V.akka % "test",
  N.typesafe %% "akka-http-testkit" % V.akkaHttpTestkit % "test",
  "org.scalatest" %% "scalatest" % V.scalatest % "test"
)

lazy val dockerConfig = project.in(file("."))
  .enablePlugins(JavaServerAppPackaging)
  .settings(
    dockerExposedPorts := Seq(typesafeConfig.value.getInt("application.docker.port"))
  )

coverageMinimum := 70
coverageFailOnMinimum := false
coverageHighlighting := true
