package org.partnerprod.partnerprod.repositorio;

import org.partnerprod.partnerprod.modelo.BodegaVirtual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface BodegaVirtualRepositorio extends JpaRepository<BodegaVirtual, Long> {
    Optional<BodegaVirtual> findByUsuarioId(Long usuarioId);
}
