/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

name := "scala-akka-boilerplate"

version := "1.0"

scalaVersion := "2.12.1"

lazy val V = new {
  val akka = "2.4.16"
  val akkaHttp = "10.0.3"
}

lazy val N = new {
  val typesafe = "com.typesafe.akka"
}

libraryDependencies ++= Seq(
  N.typesafe %% "akka-actor" % V.akka,
  N.typesafe %% "akka-http" % V.akkaHttp
)
    