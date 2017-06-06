/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.brightwindanalysis
package cassandra

import com.outworkers.phantom.connectors.CassandraConnection
import com.outworkers.phantom.database.Database

class CassandraDatabase(override val connector: CassandraConnection) extends Database[CassandraDatabase](connector) {

  object SkeletonTable extends AbstractSkeletonModel with connector.Connector

}

/**
  * Production Database
  */
private[cassandra] object ProductionDb extends CassandraDatabase(Connector.clusterConnector)

private[cassandra] trait ProductionDatabaseProvider {
  private[cassandra] def database: CassandraDatabase
}

private[cassandra] trait ProductionDatabase extends ProductionDatabaseProvider {
  private[cassandra] override val database = ProductionDb
}

/**
  * Embedded Database
  */
private[cassandra] object EmbeddedDb extends CassandraDatabase(Connector.embeddedConnector)

private[cassandra] trait EmbeddedDatabaseProvider {
  private[cassandra] def database: CassandraDatabase
}

private[cassandra] trait EmbeddedDatabase extends EmbeddedDatabaseProvider {
  private[cassandra] override val database = EmbeddedDb
}
