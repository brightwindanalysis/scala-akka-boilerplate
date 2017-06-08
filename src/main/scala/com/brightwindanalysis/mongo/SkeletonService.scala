/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.brightwindanalysis.mongo

import java.util.UUID

import org.mongodb.scala.{Completed, Observer}

import scala.concurrent.{Future, Promise}

protected[mongo] class SkeletonService(repository: Repository[SkeletonModel]) {

  def insert(model: SkeletonModel): Future[UUID] = {
    val promise = Promise[UUID]
    repository.insert(model).subscribe(new Observer[Completed] {

      override def onNext(result: Completed): Unit = promise.success(model._id)

      override def onError(e: Throwable): Unit = promise.failure(e)

      override def onComplete(): Unit = ()
    })
    promise.future
  }

  def find(_id: UUID): Future[SkeletonModel] = {
    val promise = Promise[SkeletonModel]
    repository.find(_id).subscribe(new Observer[SkeletonModel] {

      override def onNext(result: SkeletonModel): Unit = promise.success(result)

      override def onError(e: Throwable): Unit = promise.failure(e)

      override def onComplete(): Unit = ()
    })
    promise.future
  }

}

object SkeletonService extends SkeletonService(SkeletonRepository)
