/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.brightwindanalysis

import java.util.UUID

import akka.actor.ActorSystem
import akka.event.Logging
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.brightwindanalysis.cassandra.SkeletonService
import com.brightwindanalysis.http.Web
import com.brightwindanalysis.setting.Settings
import org.joda.time.{DateTime, DateTimeZone}

object Main extends Web with App {

  protected[this] implicit val actorSystem = ActorSystem("scala-akka-boilerplate")
  protected[this] implicit val materializer = ActorMaterializer()
  protected[this] implicit val executionContext = actorSystem.dispatcher

  private[this] lazy val httpConfig = Settings(actorSystem).Http
  protected[this] implicit val timeout: Timeout = httpConfig.timeout

  private[this] val log = Logging(actorSystem, getClass.getName)

  bindAndHandleHttp {
    log.debug("onStart")
    log.error("ignore me: slack test")
    //exampleCassandra
  }

  private[this] def exampleCassandra = {
    (for {
      _ <- SkeletonService.initSchema
    } yield {

      val skeletonId = UUID.randomUUID()
      SkeletonService.saveOrUpdate(ExampleSkeletonModel(skeletonId, DateTime.now(DateTimeZone.UTC), 1))

      SkeletonService.retrieve(skeletonId) map { skeleton =>
        log.debug(s"retrieve skeleton: $skeleton")
      }

    }) recover {
      case error: Throwable => log.error(s"unable to setup cassandra: $error")
    }
  }
}
