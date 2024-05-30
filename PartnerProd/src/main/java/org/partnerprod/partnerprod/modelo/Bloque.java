package org.partnerprod.partnerprod.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bloques")
@Getter
@Setter
@NoArgsConstructor
public class Bloque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "plan_de_rodaje_id", nullable = false)
    @JsonBackReference
    private PlanDeRodaje planDeRodaje;

    @Column
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "escena_id")
    private Escena escena;

    @OneToMany(mappedBy = "bloque", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<BloqueItem> items = new ArrayList<>();

    @Column
    private LocalDate fecha;

    @Column
    private LocalTime hora;

    @Column(nullable = false)
    private Integer posicion;

}