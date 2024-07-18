package org.partnerprod.partnerprod.repositorio;

import org.partnerprod.partnerprod.modelo.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepositorio extends JpaRepository<Plan, Long> {
}