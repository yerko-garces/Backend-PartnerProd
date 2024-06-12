package org.partnerprod.partnerprod.modelo;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "escenas")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@NoArgsConstructor
public class Escena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo_escena;

    @ManyToOne
    @JoinColumn(name = "capitulo_id", nullable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)

    private Capitulo capitulo;

    @Column
    private String numeroEscena;

    @Column
    private String resumen;

    @Enumerated(EnumType.STRING)
    private interior_exterior interiorExterior;

    @Enumerated(EnumType.STRING)
    private dia_noche diaNoche;

    @Getter
    @ManyToMany
    @JoinTable(name = "escena_personaje",
            joinColumns = @JoinColumn(name = "escena_id"),
            inverseJoinColumns = @JoinColumn(name = "personaje_id"))
    private List<Personaje> personajes;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locacion_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Locacion locacion;
}