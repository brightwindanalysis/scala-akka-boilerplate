/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.brightwindanalysis

import akka.actor.ActorSystem
import akka.testkit.TestKit
import org.scalatest.{Matchers, WordSpecLike}

class SettingsSpec extends TestKit(ActorSystem("settings-test")) with WordSpecLike with Matchers {

  val settings = Settings(system)

  object ConfigTest {
    private[this] val applicationConfig = system.settings.config getConfig "application"
    private[this] val logConfig = applicationConfig getConfig "log"
    private[this] val dockerConfig = applicationConfig getConfig "docker"

    val applicationName: String = applicationConfig getString "name"
    val logName: String = logConfig getString "name"
    val dockerPort: Int = dockerConfig getInt "port"
  }

  "application name" should {
    "be equal to scala-akka-boilerplate" in {
      ConfigTest.applicationName shouldEqual "scala-akka-boilerplate"
    }
  }

  "log file name" should {
    "be equal to application name" in {
      ConfigTest.logName shouldEqual ConfigTest.applicationName
    }
  }

  "exposed docker port" should {
    "be equal to application http port" in {
      ConfigTest.dockerPort shouldEqual settings.Http.port
    }
  }

}
