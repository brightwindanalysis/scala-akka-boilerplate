/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.brightwindanalysis
package mongo

import java.util.UUID

import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros.createCodecProvider
import org.mongodb.scala.{Completed, MongoCollection, SingleObservable}

protected[mongo] abstract class Repository[M](connector: Connector) {

  // TODO SkeletonModel of M
  private[this] def codecRegistry = fromRegistries(fromProviders(classOf[SkeletonModel]), DEFAULT_CODEC_REGISTRY)

  protected[this] def getCollection(name: String): MongoCollection[SkeletonModel] = {
    val collection: MongoCollection[SkeletonModel] = connector.database.getCollection(name)
    collection.withCodecRegistry(codecRegistry)
  }

  def insert(model: M): SingleObservable[Completed]

  def find(_id: UUID): SingleObservable[M]

}
