package org.partnerprod.partnerprod.servicio;

import org.partnerprod.partnerprod.modelo.BodegaVirtual;
import org.partnerprod.partnerprod.modelo.Usuario;
import org.partnerprod.partnerprod.repositorio.BodegaVirtualRepositorio;
import org.partnerprod.partnerprod.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private BodegaVirtualRepositorio bodegaVirtualRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario obtenerUsuarioPorNombre(String nombreUsuario) {
        return usuarioRepositorio.findByNombre(nombreUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + nombreUsuario));
    }

    @Transactional
    public Usuario registrarUsuario(String nombre, String contraseña) {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setContrasena(contraseña);

        usuario = usuarioRepositorio.save(usuario);

        BodegaVirtual bodegaVirtual = new BodegaVirtual();
        bodegaVirtual.setUsuario(usuario);
        bodegaVirtual = bodegaVirtualRepositorio.save(bodegaVirtual);

        usuario.setBodegaVirtual(bodegaVirtual);
        return usuario;
    }
}
