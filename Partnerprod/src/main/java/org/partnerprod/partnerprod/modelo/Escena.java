package org.partnerprod.partnerprod.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "escenas")
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
    private Capitulo capitulo;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    @Column
    private String numeroEscena;
    @Column
    private String interior_exterior;
    @Column
    private String dia_noche;
    @Column
    private String resumen;
}
