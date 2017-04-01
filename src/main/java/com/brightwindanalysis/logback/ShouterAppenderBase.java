/*
 * Copyright (C) BrightWind - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.brightwindanalysis.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

public abstract class ShouterAppenderBase extends AppenderBase<ILoggingEvent> {

    private Slack slack;

    @Override
    public void start() {
        // TODO validation
        super.start();
    }

    @Override
    public void stop() {
        // TODO
        super.stop();
    }

    public Slack getSlack() {
        return slack;
    }

    public void setSlack(Slack slack) {
        this.slack = slack;
    }

}