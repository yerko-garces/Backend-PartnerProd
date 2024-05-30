package org.partnerprod.partnerprod.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "planes_de_rodaje")
@Getter
@Setter
@NoArgsConstructor
public class PlanDeRodaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference(value = "usuarioPlanesDeRodaje")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "proyecto_id", nullable = false)
    @JsonBackReference
    private Proyecto proyecto;

    @OneToMany(mappedBy = "planDeRodaje", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Bloque> bloques = new ArrayList<>();

}