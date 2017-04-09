/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.brightwindanalysis.logback

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.{AppenderBase, Layout}

import scala.beans.BeanProperty

class SlackAppender extends AppenderBase[ILoggingEvent] {

  @BeanProperty var webhookUrl: String = _
  @BeanProperty var layout: Layout[ILoggingEvent] = _

  private[this] var isValidWebhookUrl: Boolean = true

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
      // TODO
      addError(s"TODO ${event.getFormattedMessage}")
    }
  }

}
