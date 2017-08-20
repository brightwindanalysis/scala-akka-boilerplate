/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.brightwindanalysis
package http

import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.brightwindanalysis.Main.routes
import com.brightwindanalysis.http.route.Status
import org.scalatest.{Matchers, WordSpec}

// scalastyle:off underscore.import
import io.circe.generic.auto._
import io.circe.syntax._
// scalastyle:on underscore.import

class RoutesSpec extends WordSpec with Matchers with ScalatestRouteTest {

  "routes /status" should {
    "return OK for GET requests to the root path" in {
      Get("/status") ~> routes ~> check {
        responseAs[String] shouldEqual Status("OK").asJson.noSpaces
      }
    }
  }

}
