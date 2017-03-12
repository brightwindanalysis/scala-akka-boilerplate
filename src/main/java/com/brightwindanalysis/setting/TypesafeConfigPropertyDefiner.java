/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.brightwindanalysis.setting;

import ch.qos.logback.core.PropertyDefinerBase;
import com.typesafe.config.ConfigFactory;

public class TypesafeConfigPropertyDefiner extends PropertyDefinerBase {

    private String propertyName;

    @Override
    public String getPropertyValue() {
        return ConfigFactory.load().getString(propertyName);
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
}
