package org.partnerprod.partnerprod.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@Entity
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public Proyecto() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Proyecto proyecto)) return false;
        return Objects.equals(id, proyecto.id) &&
                Objects.equals(titulo, proyecto.titulo) &&
                Objects.equals(usuario, proyecto.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, usuario);
    }
}

