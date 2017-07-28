/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.brightwindanalysis
package http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.stream.ActorMaterializer
import com.brightwindanalysis.setting.Settings

import scala.util.{Failure, Success}

trait Web extends Routes {

  protected[this] implicit def actorSystem: ActorSystem
  protected[this] implicit def materializer: ActorMaterializer

  def bindAndHandleHttp(onStart: => Unit): Unit = {

    implicit val _ = actorSystem.dispatcher
    val log = actorSystem.log
    val httpConfig = Settings(actorSystem).Http

    Http().bindAndHandle(routes, httpConfig.host, httpConfig.port).onComplete {
      case Success(serverBinding @ ServerBinding(localAddress)) =>
        val (host, port) = (localAddress.getHostName, localAddress.getPort)
        log.info("successfully bound to {}:{}", host, port)
        onStart
        shutdownHttp(serverBinding)
      case Failure(error) =>
        log.error("failed to bind to {}:{}", httpConfig.host, httpConfig.port, error)
        shutdown(failed = true)
    }

    def shutdownHttp(serverBinding: ServerBinding) = {
      sys.addShutdownHook {
        serverBinding.unbind().onComplete {
          case Success(_) => shutdown()
          case Failure(error) =>
            log.error(error.getCause, "failed to shut down", error)
            shutdown(failed = true)
        }
      }
    }

    def shutdown(failed: Boolean = false): Unit = {
      log.info(s"[failed=$failed] shutting down...")
      materializer.shutdown()
      actorSystem.terminate().onComplete {
        case Success(_) if !failed => sys.exit()
        case _ => sys.exit(-1)
      }
    }
  }

}
