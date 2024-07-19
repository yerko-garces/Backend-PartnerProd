package org.partnerprod.partnerprod.repositorio;


import org.partnerprod.partnerprod.modelo.PlanEscenaEtiqueta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanEscenaEtiquetaRepositorio extends JpaRepository<PlanEscenaEtiqueta, Long> {
    List<PlanEscenaEtiqueta> findByPlanId(Long planId);
}
