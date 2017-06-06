/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.brightwindanalysis
package actor

import akka.actor.{Actor, ActorLogging, Props}
import akka.event.LoggingReceive
import com.brightwindanalysis.actor.SkeletonActor.{RequestMessage, ResponseMessage}

object SkeletonActor {
  def name: String = "skeleton-actor"
  def props: Props = Props[SkeletonActor]

  sealed trait Message
  case class RequestMessage(value: String) extends Message
  case object ResponseMessage extends Message
}

final class SkeletonActor extends Actor with ActorLogging {
  override def receive: Receive = LoggingReceive {
    case RequestMessage(value) =>
      log.debug(s"message: $value")
      sender() ! ResponseMessage
    case _ =>
      log.error("invalid message")
  }
}
