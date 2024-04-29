package org.partnerprod.partnerprod.modelo;

import jakarta.persistence.*;
import java.util.Objects;

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

    public Proyecto(String titulo, Usuario usuario) {
        this.titulo = titulo;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Proyecto)) return false;
        Proyecto proyecto = (Proyecto) o;
        return Objects.equals(id, proyecto.id) &&
                Objects.equals(titulo, proyecto.titulo) &&
                Objects.equals(usuario, proyecto.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, usuario);
    }
}

