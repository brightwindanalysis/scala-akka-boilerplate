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
// scalastyle:on underscore.import

final case class SlackLog(text: String)

final class SlackAppender extends AppenderBase[ILoggingEvent] {

  @BeanProperty var applicationName: String = _
  @BeanProperty var webhookUrl: String = _
  @BeanProperty var layout: Layout[ILoggingEvent] = _

  private[this] var isValidWebhookUrl: Boolean = _
  private[this] val webhookUrlPrefix = "https://hooks.slack.com/services"

  override def start(): Unit = {
    isValidWebhookUrl = Option(webhookUrl) exists { url =>
      url.nonEmpty && url.startsWith(webhookUrlPrefix)
    }
    super.start()
  }

  override def append(event: ILoggingEvent): Unit = {
    if (isValidWebhookUrl) {

      val text = s"${ZonedDateTime.now}\n$applicationName\n${event.getFormattedMessage}"

      val request = url(webhookUrl)
        .POST
        .setContentType("application/json", "UTF-8") << SlackLog(text).asJson.noSpaces

      Http(request)
        .onComplete {
          case Success(_) => addInfo("slack notification succeed")
          case Failure(error) => addWarn("slack notification failed", error)
        }
    }
  }

}
