/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.brightwindanalysis
package mongo

import org.mongodb.scala.{Completed, Document, MongoCollection, SingleObservable}

protected[mongo] abstract class Repository(connector: Connector) {

  protected[this] def collection(name: String): MongoCollection[Document] = connector.database.getCollection(name)

  protected[mongo] def insertOne(document: Document): SingleObservable[Completed]

  protected[mongo] def findFirst: SingleObservable[Document]

}
