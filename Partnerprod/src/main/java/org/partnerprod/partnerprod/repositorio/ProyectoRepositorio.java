package org.partnerprod.partnerprod.repositorio;


import org.partnerprod.partnerprod.modelo.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProyectoRepositorio extends JpaRepository<Proyecto, Long> {
    List<Proyecto> findByUsuarioId(Long usuarioId);


}

