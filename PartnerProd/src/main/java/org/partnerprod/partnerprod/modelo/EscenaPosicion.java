package org.partnerprod.partnerprod.modelo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
@Getter
@Setter

public class EscenaPosicion {
    private Long escenaId;
    private LocalTime hora;
    private Integer posicion;

    public EscenaPosicion() {
    }

    public EscenaPosicion(Long escenaId, LocalTime hora, Integer posicion) {
        this.escenaId = escenaId;
        this.hora = hora;
        this.posicion = posicion;
    }

    public Long getEscenaId() {
        return escenaId;
    }

    public void setEscenaId(Long escenaId) {
        this.escenaId = escenaId;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Integer getPosicion() {
        return posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }
}
