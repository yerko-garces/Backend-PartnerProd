package org.partnerprod.partnerprod.repositorio;

import org.partnerprod.partnerprod.modelo.BloqueEscena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloqueEscenaRepositorio extends JpaRepository<BloqueEscena, Long> {
    List<BloqueEscena> findByBloqueId(Long bloqueId);
    void deleteByBloqueId(Long bloqueId);
}