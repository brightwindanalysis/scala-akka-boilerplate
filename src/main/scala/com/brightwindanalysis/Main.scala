/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.brightwindanalysis

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http.ServerBinding
import akka.stream.ActorMaterializer
import com.brightwindanalysis.http.Web

import scala.concurrent.Future

object Main extends Web {

  implicit val actorSystem = ActorSystem("scala-akka-boilerplate")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = actorSystem.dispatcher

  val log = Logging(actorSystem, getClass.getName)

  // TODO use startServer in HttpApp
  private def start = for {
    serverBinding@ServerBinding(localAddress) <- bindAndHandleHttp()
  } yield {
    val (host, port) = (localAddress.getHostName, localAddress.getPort)
    log.info("successfully bound to {}:{}", host, port)
    sys.addShutdownHook {
      shutDown(serverBinding)
    }
  }

  // TODO override waitForShutdownSignal in HttpApp
  private def shutDown(serverBinding: ServerBinding): Unit = {
    for {
      _ <- serverBinding.unbind()
      _ <- Future.successful(materializer.shutdown())
      _ <- actorSystem.terminate()
    } yield {
      log.error("shutting down")
      sys.exit()
    }
  }

  def main(args: Array[String]): Unit = start

}
