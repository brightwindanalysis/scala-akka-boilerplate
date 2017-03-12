/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.brightwindanalysis.http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.brightwindanalysis.Settings

import scala.concurrent.Future

trait Web extends Routes {

  def bindAndHandleHttp()(implicit system: ActorSystem, materializer: ActorMaterializer): Future[Http.ServerBinding] = {
    val httpConfig = Settings(system).Http

    Http().bindAndHandle(routes, httpConfig.host, httpConfig.port)
  }

}
