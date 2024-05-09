package org.partnerprod.partnerprod.modelo;

import com.fasterxml.jackson.annotation.*;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @JsonBackReference
    private Capitulo capitulo;

    @ManyToMany
    @JoinTable(name = "escena_item",
            joinColumns = @JoinColumn(name = "escena_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items;

    @Column
    private String numeroEscena;

    @Column
    private String resumen;

    @Enumerated(EnumType.STRING)
    private interior_exterior interior_exterior;

    @Enumerated(EnumType.STRING)
    private dia_noche dia_noche;

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

    @PostConstruct
    private void init() {
        items = new ArrayList<>();
        personajes = new ArrayList<>();
    }
}