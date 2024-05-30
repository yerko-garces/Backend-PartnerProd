package org.partnerprod.partnerprod.repositorio;

import org.partnerprod.partnerprod.modelo.PlanDeRodaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanDeRodajeRepositorio extends JpaRepository<PlanDeRodaje, Long> {
}