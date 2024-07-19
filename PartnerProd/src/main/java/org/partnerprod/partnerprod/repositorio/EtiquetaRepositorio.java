package org.partnerprod.partnerprod.repositorio;

import org.partnerprod.partnerprod.modelo.Etiqueta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtiquetaRepositorio extends JpaRepository<Etiqueta, Long> {
    Etiqueta findByNombre(String nombre);
}