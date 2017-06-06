/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.brightwindanalysis
package cassandra

import java.util.UUID

import com.datastax.driver.core.ResultSet
import org.joda.time.{DateTime, DateTimeZone}

// scalastyle:off underscore.import
import com.outworkers.phantom.dsl._
// scalastyle:on underscore.import

import scala.concurrent.Future

private[cassandra] abstract class SkeletonModel
  extends CassandraTable[AbstractSkeletonModel, ExampleSkeletonModel] {

  override def tableName: String = "skeleton"

  object Id extends UUIDColumn(this) with PartitionKey

  object ExampleDate extends DateTimeColumn(this) with ClusteringOrder {
    override lazy val name = "example_date"
  }

  object ExampleDouble extends DoubleColumn(this) {
    override lazy val name = "example_double"
  }

  object CreatedAt extends OptionalDateTimeColumn(this) {
    override lazy val name = "created_at"
  }

  object UpdatedAt extends OptionalDateTimeColumn(this) {
    override lazy val name = "updated_at"
  }

}

private[cassandra] abstract class AbstractSkeletonModel extends SkeletonModel with RootConnector {

  def init: Future[ResultSet] =
    create.ifNotExists().future()

  def store(example: ExampleSkeletonModel): Future[ResultSet] = {
    val now = DateTime.now(DateTimeZone.UTC)

    insert
      .value(_.Id, example.id)
      .value(_.ExampleDate, example.exampleDate)
      .value(_.ExampleDouble, example.exampleDouble)
      .value(_.CreatedAt, example.createdAt match {
        case None => Some(now)
        case dateTime@Some(_) => dateTime
      })
      .value(_.UpdatedAt, Some(now))
      .future()
  }

  def retrieve(id: UUID): Future[Option[ExampleSkeletonModel]] =
    select
      .where(_.Id eqs id)
      .one()

  def retrieveAll: Future[List[ExampleSkeletonModel]] =
    select
      .fetch()

}
