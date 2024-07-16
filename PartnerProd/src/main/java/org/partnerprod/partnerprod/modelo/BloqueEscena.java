package org.partnerprod.partnerprod.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "bloque_escena")
@Getter
@Setter
@NoArgsConstructor
public class BloqueEscena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bloque_id", nullable = false)
    @JsonBackReference
    private Bloque bloque;

    @ManyToOne
    @JoinColumn(name = "escena_id", nullable = false)
    private Escena escena;

    @Column(nullable = false)
    private LocalTime hora;

    @Column(nullable = false)
    private Integer posicion;
}