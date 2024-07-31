package org.partnerprod.partnerprod.repositorio;

import org.partnerprod.partnerprod.modelo.PlanItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanItemRepositorio extends JpaRepository<PlanItem, Long> {
    List<PlanItem> findByPlanId(Long planId);
}