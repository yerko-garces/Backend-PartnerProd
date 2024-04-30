package org.partnerprod.partnerprod.repositorio;

import org.partnerprod.partnerprod.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNombre(String nombre);
}
