package org.partnerprod.partnerprod;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.partnerprod.partnerprod.modelo.Capitulo;
import org.partnerprod.partnerprod.repositorio.CapituloRepositorio;
import org.partnerprod.partnerprod.servicio.CapituloServicio;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CapituloServicioTest {

    @InjectMocks
    private CapituloServicio capituloServicio;

    @Mock
    private CapituloRepositorio capituloRepositorio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarCapitulo() {
        Capitulo capitulo = new Capitulo();
        when(capituloRepositorio.save(capitulo)).thenReturn(capitulo);

        Capitulo resultado = capituloServicio.guardarCapitulo(capitulo);

        assertEquals(capitulo, resultado);
        verify(capituloRepositorio, times(1)).save(capitulo);
    }

    @Test
    void testObtenerCapitulosPorProyectoId() {
        Long proyectoId = 1L;
        List<Capitulo> capitulos = Arrays.asList(new Capitulo(), new Capitulo());
        when(capituloRepositorio.findAllByProyectoId(proyectoId)).thenReturn(capitulos);

        List<Capitulo> resultado = capituloServicio.obtenerCapitulosPorProyectoId(proyectoId);

        assertEquals(capitulos, resultado);
        verify(capituloRepositorio, times(1)).findAllByProyectoId(proyectoId);
    }

    @Test
    void testObtenerCapituloPorId() {
        Long id = 1L;
        Capitulo capitulo = new Capitulo();
        when(capituloRepositorio.findById(id)).thenReturn(Optional.of(capitulo));

        Capitulo resultado = capituloServicio.obtenerCapituloPorId(id);

        assertEquals(capitulo, resultado);
        verify(capituloRepositorio, times(1)).findById(id);
    }

    @Test
    void testObtenerCapituloPorIdNoExiste() {
        Long id = 1L;
        when(capituloRepositorio.findById(id)).thenReturn(Optional.empty());

        Capitulo resultado = capituloServicio.obtenerCapituloPorId(id);

        assertNull(resultado);
        verify(capituloRepositorio, times(1)).findById(id);
    }

    @Test
    void testEliminarCapitulo() {
        Long id = 1L;
        doNothing().when(capituloRepositorio).deleteById(id);

        capituloServicio.eliminarCapitulo(id);

        verify(capituloRepositorio, times(1)).deleteById(id);
    }
}
