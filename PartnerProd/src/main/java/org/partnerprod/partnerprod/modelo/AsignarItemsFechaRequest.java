package org.partnerprod.partnerprod.modelo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Map;

public class AsignarItemsFechaRequest {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    private Map<Long, Integer> items;

    // Constructor
    public AsignarItemsFechaRequest() {
    }

    // Getters y setters
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Map<Long, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Long, Integer> items) {
        this.items = items;
    }
}