package org.partnerprod.partnerprod.repositorio;

import org.partnerprod.partnerprod.modelo.Capitulo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CapituloRepositorio extends JpaRepository<Capitulo, Long> {
    List<Capitulo> findAllByProyectoId(Long proyectoId);
}
