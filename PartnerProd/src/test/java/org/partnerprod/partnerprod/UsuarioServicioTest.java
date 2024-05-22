package org.partnerprod.partnerprod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.partnerprod.partnerprod.modelo.BodegaVirtual;
import org.partnerprod.partnerprod.modelo.Usuario;
import org.partnerprod.partnerprod.repositorio.BodegaVirtualRepositorio;
import org.partnerprod.partnerprod.repositorio.UsuarioRepositorio;
import org.partnerprod.partnerprod.servicio.UsuarioServicio;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UsuarioServicioTest {

    @Mock
    private UsuarioRepositorio usuarioRepositorioMock;

    @Mock
    private BodegaVirtualRepositorio bodegaVirtualRepositorioMock;

    @Mock
    private PasswordEncoder passwordEncoderMock;

    @InjectMocks
    private UsuarioServicio usuarioServicio;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testObtenerUsuarioPorNombre_UsuarioExistente() {
        // Arrange
        String nombreUsuario = "usuarioExistente";
        Usuario usuario = new Usuario();
        usuario.setNombre(nombreUsuario);
        when(usuarioRepositorioMock.findByNombre(nombreUsuario)).thenReturn(Optional.of(usuario));

        // Act
        Usuario resultado = usuarioServicio.obtenerUsuarioPorNombre(nombreUsuario);

        // Assert
        assertEquals(nombreUsuario, resultado.getNombre());
    }

    @Test
    public void testObtenerUsuarioPorNombre_UsuarioNoExistente() {
        // Arrange
        String nombreUsuario = "usuarioNoExistente";
        when(usuarioRepositorioMock.findByNombre(nombreUsuario)).thenReturn(Optional.empty());

        // Act & Assert
        // Verifica que se lance una excepción cuando el usuario no existe
        assertThrows(RuntimeException.class, () -> usuarioServicio.obtenerUsuarioPorNombre(nombreUsuario));
    }

    @Test
    public void testRegistrarUsuario() {
        // Arrange
        String nombre = "usuarioNuevo";
        String contraseña = "contraseña";
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setContrasena(contraseña);

        BodegaVirtual bodegaVirtual = new BodegaVirtual();
        bodegaVirtual.setUsuario(usuario);

        when(usuarioRepositorioMock.save(any(Usuario.class))).thenReturn(usuario);
        when(bodegaVirtualRepositorioMock.save(any(BodegaVirtual.class))).thenReturn(bodegaVirtual);

        // Act
        Usuario resultado = usuarioServicio.registrarUsuario(nombre, contraseña);

        // Assert
        assertEquals(nombre, resultado.getNombre());
        assertEquals(bodegaVirtual, resultado.getBodegaVirtual());
    }
}
