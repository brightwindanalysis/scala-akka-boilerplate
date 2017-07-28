/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

logLevel := Level.Debug

addSbtPlugin("io.spray" % "sbt-revolver" % "0.8.0")
addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.9.0")
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.0")
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.2.0")
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.2")
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.3.1")
