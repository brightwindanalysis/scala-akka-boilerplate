/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.brightwindanalysis

import akka.actor.ActorSystem
import akka.event.Logging
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.brightwindanalysis.http.Web
import com.brightwindanalysis.setting.Settings

object Main extends Web with App {

  protected[this] implicit val actorSystem = ActorSystem("scala-akka-boilerplate")
  protected[this] implicit val materializer = ActorMaterializer()
  protected[this] implicit val executionContext = actorSystem.dispatcher

  private[this] lazy val httpConfig = Settings(actorSystem).Http
  protected[this] implicit val timeout: Timeout = httpConfig.timeout

  private[this] val log = Logging(actorSystem, getClass.getName)

  bindAndHandleHttp {
    log.error("ignore me: slack test")
  }

}
