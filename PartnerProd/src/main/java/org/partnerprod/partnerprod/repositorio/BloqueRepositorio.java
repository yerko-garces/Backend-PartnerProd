package org.partnerprod.partnerprod.repositorio;

import org.partnerprod.partnerprod.modelo.Bloque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloqueRepositorio extends JpaRepository<Bloque, Long> {
}