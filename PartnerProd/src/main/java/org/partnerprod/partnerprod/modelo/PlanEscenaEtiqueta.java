package org.partnerprod.partnerprod.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "plan_escena_etiqueta")
@Getter
@Setter
@NoArgsConstructor
public class PlanEscenaEtiqueta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    @JsonIgnore
    private Plan plan;

    @ManyToOne
    @JoinColumn(name = "escena_id")
    private Escena escena;

    @ManyToOne
    @JoinColumn(name = "etiqueta_id")
    private Etiqueta etiqueta;

    @Column(nullable = true)
    private LocalTime hora;

    @Column(nullable = false)
    private Integer posicion;
}