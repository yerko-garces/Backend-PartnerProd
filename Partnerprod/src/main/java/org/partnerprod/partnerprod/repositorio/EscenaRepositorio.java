package org.partnerprod.partnerprod.repositorio;


import org.partnerprod.partnerprod.modelo.Escena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface EscenaRepositorio extends JpaRepository<Escena, Long> {
    List<Escena> findAllByCapituloId(Long capituloId);
}
