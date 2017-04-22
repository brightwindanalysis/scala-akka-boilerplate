/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.brightwindanalysis.actor

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import com.brightwindanalysis.StopSystemAfterAll
import com.brightwindanalysis.actor.SkeletonActor.{RequestMessage, ResponseMessage}
import org.scalatest.{MustMatchers, WordSpecLike}

final class SkeletonActorSpec extends TestKit(ActorSystem("test-actor-system"))
  with WordSpecLike
  with MustMatchers
  // using the ImplicitSender trait will automatically set testActor as the sender
  with ImplicitSender
  with StopSystemAfterAll {

  "skeleton actor" must {
    "send Response when receives Request" in {
      val skeleton = system.actorOf(SkeletonActor.props, "skeleton-test-1")
      skeleton ! RequestMessage
      expectMsg(ResponseMessage)
    }
  }

}
