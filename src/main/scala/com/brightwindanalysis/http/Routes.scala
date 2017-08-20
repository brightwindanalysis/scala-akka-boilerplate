/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.brightwindanalysis
package http

import akka.http.scaladsl.model.StatusCodes.ServiceUnavailable
import akka.http.scaladsl.server.Directives.pathPrefix
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import com.brightwindanalysis.http.route.StatusRoute

import scala.concurrent.ExecutionContext

// scalastyle:off underscore.import
import akka.http.scaladsl.server.Directives._
// scalastyle:on underscore.import

trait Routes extends StatusRoute {

  protected[this] implicit def executionContext: ExecutionContext
  protected[this] implicit def timeout: Timeout

  /**
    * All routes.
    *
    * @return Route
    */
  def routes: Route = statusRoute ~ v1

  private[this] def v1 =
    pathPrefix("v1") {
      get {
        complete(ServiceUnavailable)
      }
    }

}
