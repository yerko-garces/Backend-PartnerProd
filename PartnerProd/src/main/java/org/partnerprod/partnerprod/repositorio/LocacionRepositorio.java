package org.partnerprod.partnerprod.repositorio;

import org.partnerprod.partnerprod.modelo.Locacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocacionRepositorio extends JpaRepository<Locacion, Long> {
    List<Locacion> findByProyectoId(Long proyectoId);
}
