package org.partnerprod.partnerprod.modelo;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Setter
@Getter
@Table(name = "proyecto")
@Entity
@JsonIgnoreProperties({"usuario"})
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String titulo;
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    @JsonManagedReference
    private List<Capitulo> capitulos;
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval =
            true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property =
            "id")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Personaje> personajes;
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.REMOVE, orphanRemoval =
            true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property =
            "id")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Locacion> locaciones;
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval =
            true)
    @JsonManagedReference
    private List<PlanDeRodaje> planesDeRodaje = new ArrayList<>();
}