package com.threadpilot.insurance.config;

import org.togglz.core.Feature;
import org.togglz.core.annotation.EnabledByDefault;
import org.togglz.core.annotation.Label;

public enum Features implements Feature {

    @Label("Enable insurance discount feature")
    INSURANCE_DISCOUNT,

    @Label("Enable stockholm insurance discount feature")
    STOCKHOLM_INSURANCE_DISCOUNT,

    @Label("Enable Goteborg insurance discount feature")
    GOTEBORG_INSURANCE_DISCOUNT,

    @Label("Enable Black Friday promotion feature")
    BLACK_FRIDAY_PROMOTION;

}