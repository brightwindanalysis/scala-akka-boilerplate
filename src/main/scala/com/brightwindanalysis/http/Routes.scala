/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.brightwindanalysis.http

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.{complete, get, path, pathEndOrSingleSlash}
import akka.http.scaladsl.server.Route

// scalastyle:off underscore.import
import io.circe.generic.auto._
import io.circe.syntax._
// scalastyle:on underscore.import

final case class Status(value: String)

trait Routes {

  val routes: Route = {
    path("status") {
      pathEndOrSingleSlash {
        get {
          complete(HttpEntity(ContentTypes.`application/json`, Status("OK").asJson.noSpaces))
        }
      }
    }
  }

}
