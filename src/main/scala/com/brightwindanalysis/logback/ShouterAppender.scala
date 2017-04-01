/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.brightwindanalysis.logback

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import ch.qos.logback.classic.spi.ILoggingEvent

import scala.util.{Failure, Success, Try}

// scalastyle:off underscore.import
import akka.http.scaladsl.model._
// scalastyle:on underscore.import

import scala.concurrent.Future

/**
  * Slack integration:
  *   - add Incoming WebHooks app integration
  *   - select channel
  */
class ShouterAppender extends ShouterAppenderBase {

  implicit val actorSystem = ActorSystem("shouter")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = actorSystem.dispatcher

//    val message = SlackMessage(event.getFormattedMessage)
//    // message.asJson.noSpaces

  override def start(): Unit = {
    // TODO
    Try(getSlack.getWebhookUrl) map { webhookUrl =>
      val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(
        method = HttpMethods.POST,
        uri = webhookUrl,
        entity = HttpEntity(ContentTypes.`application/json`, s"""{"text":"onStart"}""")))

      responseFuture.onComplete {
        case Success(value) => addError(value.toString)
        case Failure(error) => addError(error.toString)
      }
    }
  }

  override def append(eventObject: ILoggingEvent): Unit = {}
}