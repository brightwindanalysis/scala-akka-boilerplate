/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.brightwindanalysis.http

import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.brightwindanalysis.Main.routes
import org.scalatest.{Matchers, WordSpec}

class RoutesSpec extends WordSpec with Matchers with ScalatestRouteTest {

  "routes /status" should {
    "return OK for GET requests to the root path" in {
      Get("/status") ~> routes ~> check {
        responseAs[String] shouldEqual "OK"
      }
    }
  }

}