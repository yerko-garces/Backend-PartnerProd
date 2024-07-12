package org.partnerprod.partnerprod.modelo;


import com.fasterxml.jackson.annotation.JsonValue;

public enum dia_noche {
    DIA, NOCHE;

    @JsonValue
    public String getValue() {
        return this.name();
    }
}