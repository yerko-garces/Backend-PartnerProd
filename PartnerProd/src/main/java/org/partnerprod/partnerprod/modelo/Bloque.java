package org.partnerprod.partnerprod.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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
    private LocalDate fecha;

    @Column
    private String descripcion;

    @OneToMany(mappedBy = "bloque", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<BloqueItem> items = new ArrayList<>();

    @OneToMany(mappedBy = "bloque", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<BloqueEscena> escenasAsociadas = new ArrayList<>();

    // Método auxiliar para mantener la relación bidireccional
    public void addBloqueEscena(BloqueEscena bloqueEscena) {
        escenasAsociadas.add(bloqueEscena);
        bloqueEscena.setBloque(this);
    }

    // Método auxiliar para remover BloqueEscena manteniendo la relación bidireccional
    public void removeBloqueEscena(BloqueEscena bloqueEscena) {
        escenasAsociadas.remove(bloqueEscena);
        bloqueEscena.setBloque(null);
    }

    // Método para actualizar las escenas asociadas
    public void actualizarEscenasAsociadas(List<BloqueEscena> nuevasEscenasAsociadas) {
        this.escenasAsociadas.clear();
        for (BloqueEscena bloqueEscena : nuevasEscenasAsociadas) {
            addBloqueEscena(bloqueEscena);
        }
    }
}