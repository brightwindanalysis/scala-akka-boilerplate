/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.brightwindanalysis

import java.util.UUID

import org.joda.time.DateTime

final case class ExampleSkeletonModel(id: UUID,
                                      exampleDate: DateTime,
                                      exampleDouble: Double,
                                      createdAt: Option[DateTime] = None,
                                      updatedAt: Option[DateTime] = None)
