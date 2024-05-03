package org.partnerprod.partnerprod.repositorio;

import org.partnerprod.partnerprod.modelo.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonajeRepositorio extends JpaRepository<Personaje, Long> {
}