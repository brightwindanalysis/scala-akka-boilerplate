/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

name := "scala-akka-boilerplate"

scalaVersion := "2.12.1"

lazy val V = new {
  val akka = "2.4.17"
  val akkaHttp = "10.0.4"
  val logback = "1.2.1"

  val akkaHttpTestkit = "10.0.4"
  val scalatest = "3.0.1"
}

lazy val N = new {
  val typesafe = "com.typesafe.akka"
}

libraryDependencies ++= Seq(
  N.typesafe %% "akka-actor" % V.akka,
  N.typesafe %% "akka-http" % V.akkaHttp,
  N.typesafe %% "akka-slf4j" % V.akka,
  "ch.qos.logback" % "logback-classic" % V.logback,

  N.typesafe %% "akka-http-testkit" % V.akkaHttpTestkit % "test",
  "org.scalatest" %% "scalatest" % V.scalatest % "test"
)

lazy val dockerConfig = project.in(file("."))
  .enablePlugins(JavaServerAppPackaging)
  .settings(
    dockerExposedPorts := Seq(3000)
  )
