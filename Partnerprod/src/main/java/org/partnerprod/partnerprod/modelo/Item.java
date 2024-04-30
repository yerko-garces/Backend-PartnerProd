package org.partnerprod.partnerprod.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private int cantidad;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "bodega_virtual_id", nullable = false)
    private BodegaVirtual bodegaVirtual;

    public Item() {
    }

    public Item(String nombre, int cantidad, Categoria categoria, BodegaVirtual bodegaVirtual) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.categoria = categoria;
        this.bodegaVirtual = bodegaVirtual;
    }

}
