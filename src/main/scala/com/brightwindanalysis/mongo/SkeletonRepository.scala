/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.brightwindanalysis
package mongo

import java.util.UUID

import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.{Completed, MongoCollection, SingleObservable}

protected[mongo] class SkeletonRepository(connector: Connector) extends Repository[SkeletonModel](connector) {

  private[this] lazy val skeletonCollection: MongoCollection[SkeletonModel] = getCollection("skeleton")

  override def insert(model: SkeletonModel): SingleObservable[Completed] =
    skeletonCollection.insertOne(model)

  override def find(_id: UUID): SingleObservable[SkeletonModel] =
    skeletonCollection.find(equal("_id", _id)).first()
}

object SkeletonRepository extends SkeletonRepository(MongoConnector)
