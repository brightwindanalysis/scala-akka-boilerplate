/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.brightwindanalysis
package cassandra

import com.outworkers.phantom.connectors.{CassandraConnection, ContactPoint, ContactPoints, KeySpaceCQLQuery}
import com.typesafe.config.ConfigFactory

// scalastyle:off underscore.import
import scala.collection.JavaConverters._
// scalastyle:on underscore.import

private[cassandra] object Connector {

  private[this] lazy val config = ConfigFactory.load()
  private[this] lazy val cassandraConfig = config getConfig "application.cassandra"
  private[this] lazy val host = cassandraConfig getString "host"
  private[this] lazy val port = cassandraConfig getInt "port"
  private[this] lazy val hosts = cassandraConfig getString "hosts" split ","
  private[this] lazy val keySpace = cassandraConfig getString "key-space"

  private[this] lazy val createKeySpaceQuery = new KeySpaceCQLQuery {
    def queryString: String = s"CREATE KEYSPACE IF NOT EXISTS $keyspace WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '2'} AND durable_writes = true;"

    def keyspace: String = keySpace
  }

  lazy val singleNodeConnector: CassandraConnection =
    ContactPoint(host, port).keySpace(keySpace)

  lazy val clusterConnector: CassandraConnection =
    ContactPoints(hosts)
      //.withClusterBuilder(_.withCredentials("USERNAME", "PASSWORD"))
      .keySpace(createKeySpaceQuery)

  lazy val embeddedConnector: CassandraConnection =
    ContactPoint.embedded.noHeartbeat().keySpace("key-space-test")

}
