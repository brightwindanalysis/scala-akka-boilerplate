/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.brightwindanalysis.mongo

import org.mongodb.scala.{Completed, Document, Observer}

import scala.concurrent.{Future, Promise}

protected[mongo] class SkeletonService(repository: Repository) {

  def insertOne(document: Document): Future[String] = {
    val promise = Promise[String]
    repository.insertOne(document).subscribe(new Observer[Completed] {

      override def onNext(result: Completed): Unit = promise.success(s"$result")

      override def onError(e: Throwable): Unit = promise.failure(e)

      override def onComplete(): Unit = ()
    })
    promise.future
  }

}

object SkeletonService extends SkeletonService(SkeletonRepository)
