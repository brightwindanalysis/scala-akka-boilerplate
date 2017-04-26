/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.brightwindanalysis
package setting

import ch.qos.logback.core.PropertyDefinerBase
import com.typesafe.config.ConfigFactory

import scala.beans.BeanProperty

final class TypesafeConfigPropertyDefiner extends PropertyDefinerBase {
  @BeanProperty var propertyName: String = _

  override def getPropertyValue: String = ConfigFactory.load.getString(propertyName)
}
