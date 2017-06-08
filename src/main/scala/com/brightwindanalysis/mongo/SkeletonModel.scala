/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.brightwindanalysis.mongo

import java.util.UUID

case class SkeletonModel(_id: UUID, name: String, exampleDouble: Double)

object SkeletonModel {
  def apply(name: String, exampleDouble: Double): SkeletonModel =
    new SkeletonModel(UUID.randomUUID(), name, exampleDouble)
}
