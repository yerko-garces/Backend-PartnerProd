package org.partnerprod.partnerprod.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private int cantidad;
    private String categoria;

    @ManyToOne
    @JoinColumn(name = "bodega_virtual_id")
    @JsonBackReference(value = "bodegaItems")
    private BodegaVirtual bodegaVirtual;

    // Constructor, getters, setters...
}