package org.partnerprod.partnerprod.modelo;

import com.fasterxml.jackson.annotation.JsonValue;

public enum interior_exterior {
    INTERIOR, EXTERIOR;

    @JsonValue
    public String getValue() {
        return this.name();
    }
}