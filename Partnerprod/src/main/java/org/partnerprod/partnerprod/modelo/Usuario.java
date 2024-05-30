package org.partnerprod.partnerprod.modelo;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "usuarioBodegaVirtual")
    private BodegaVirtual bodegaVirtual;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private String contrasena;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "usuarioPlanesDeRodaje")
    private List<PlanDeRodaje> planesDeRodaje = new ArrayList<>();

}