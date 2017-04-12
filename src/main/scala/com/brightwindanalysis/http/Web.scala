/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.brightwindanalysis.http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.stream.ActorMaterializer
import com.brightwindanalysis.setting.Settings

import scala.util.{Failure, Success}

sealed trait FooMagnet {
  def apply(): Unit
}

object FooMagnet {
  implicit def fromFoo(f: () => Unit): FooMagnet = new FooMagnet {
    override def apply(): Unit = f()
  }
}

trait Web extends Routes {

  def bindAndHandleHttp(onStart: FooMagnet)
                       (implicit system: ActorSystem, materializer: ActorMaterializer): Unit = {

    implicit val _ = system.dispatcher
    val log = system.log
    val httpConfig = Settings(system).Http

    Http().bindAndHandle(routes, httpConfig.host, httpConfig.port).onComplete {
      case Success(serverBinding@ServerBinding(localAddress)) =>
        val (host, port) = (localAddress.getHostName, localAddress.getPort)
        log.info("successfully bound to {}:{}", host, port)
        onStart()
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
            log.error("failed to shut down", error)
            shutdown(failed = true)
        }
      }
    }

    def shutdown(failed: Boolean = false): Unit = {
      log.info(s"[failed=$failed] shutting down...")
      materializer.shutdown()
      system.terminate().onComplete {
        case Success(_) if !failed => sys.exit()
        case _ => sys.exit(-1)
      }
    }
  }

}
