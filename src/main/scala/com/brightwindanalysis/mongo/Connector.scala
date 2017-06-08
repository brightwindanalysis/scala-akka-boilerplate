/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.brightwindanalysis
package mongo

import com.typesafe.config.ConfigFactory
import org.mongodb.scala.{MongoClient, MongoDatabase}

trait Connector {
  def database: MongoDatabase
}

protected[mongo] object MongoConnector extends Connector {

  private[this] lazy val config = ConfigFactory.load()
  private[this] lazy val mongoConfig = config getConfig "application.mongo"
  private[this] lazy val host = mongoConfig getString "host"
  private[this] lazy val port = mongoConfig getInt "port"
  private[this] lazy val databaseName = mongoConfig getString "database"

  private[this] lazy val mongoClient: MongoClient = MongoClient(s"mongodb://$host:$port")

  lazy val database: MongoDatabase = mongoClient.getDatabase(databaseName)

}
