/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.brightwindanalysis.cassandra

import java.util.UUID

import com.brightwindanalysis.ExampleSkeletonModel
import com.datastax.driver.core.ResultSet

import scala.concurrent.Future

trait SkeletonService extends ProductionDatabase {

  def initSchema: Future[ResultSet] = database.SkeletonTable.init

  def saveOrUpdate(example: ExampleSkeletonModel): Future[ResultSet] = database.SkeletonTable.store(example)

  def retrieve(id: UUID): Future[Option[ExampleSkeletonModel]] =
    database.SkeletonTable.retrieve(id)

  def retrieveAll: Future[List[ExampleSkeletonModel]] =
    database.SkeletonTable.retrieveAll

}

object SkeletonService extends SkeletonService
