/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.brightwindanalysis.logback

import java.time.ZonedDateTime

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.{AppenderBase, Layout}
import dispatch.Defaults.executor
import dispatch.{Http, url}

import scala.beans.BeanProperty
import scala.util.{Failure, Success}

// scalastyle:off underscore.import
import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.java8.time._
// scalastyle:on underscore.import

case class EventLog(name: String, message: String, data: ZonedDateTime)

class SlackAppender extends AppenderBase[ILoggingEvent] {

  @BeanProperty var applicationName: String = _
  @BeanProperty var webhookUrl: String = _
  @BeanProperty var layout: Layout[ILoggingEvent] = _

  private[this] var isValidWebhookUrl: Boolean = _

  override def start(): Unit = {
    isValidWebhookUrl = Option(webhookUrl) match {
      case Some(url) if url.nonEmpty =>
        addInfo(s"valid webhookUrl=$url")
        true
      case _ =>
        addError("invalid webhookUrl")
        false
    }
    super.start()
  }

  override def append(event: ILoggingEvent): Unit = {
    if (isValidWebhookUrl) {

      val eventLog = EventLog(applicationName, event.getFormattedMessage, ZonedDateTime.now).asJson.noSpaces
      val request = url(webhookUrl)
        .POST
        .setContentType("application/json", "UTF-8") << eventLog

      Http(request)
        .onComplete {
          case Success(_) => addInfo("slack notification succeed")
          case Failure(error) => addError("slack notification failed", error)
        }
    }
  }

}
