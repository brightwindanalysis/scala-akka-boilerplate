/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.brightwindanalysis
package mongo

import org.mongodb.scala.{Completed, Document, SingleObservable}

protected[mongo] class SkeletonRepository(connector: Connector) extends Repository(connector) {

  private[this] lazy val skeletonColletion = collection("skeleton")

  override protected[mongo] def insertOne(document: Document): SingleObservable[Completed] =
    skeletonColletion.insertOne(document)

  override protected[mongo] def findFirst: SingleObservable[Document] =
    skeletonColletion.find().first()
}

object SkeletonRepository extends SkeletonRepository(MongoConnector)
