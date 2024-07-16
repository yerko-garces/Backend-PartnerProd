package org.partnerprod.partnerprod.repositorio;

import org.partnerprod.partnerprod.modelo.PlanDeRodaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanDeRodajeRepositorio extends JpaRepository<PlanDeRodaje, Long> {
    List<PlanDeRodaje> findByProyectoId(Long proyectoId);
}