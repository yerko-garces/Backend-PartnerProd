package org.partnerprod.partnerprod.repositorio;

import org.partnerprod.partnerprod.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Usuario findByNombre(String nombre);
}

