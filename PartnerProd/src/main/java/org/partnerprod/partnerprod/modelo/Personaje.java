package org.partnerprod.partnerprod.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "personajes")
@JsonIgnoreProperties({"proyecto"})
@Getter
@Setter
public class Personaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    private String nombre;

    @Getter
    private String descripcion;

    @Getter
    @ManyToOne
    @JoinColumn(name = "proyecto_id")
    private Proyecto proyecto;

}