package org.partnerprod.partnerprod;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.partnerprod.partnerprod.modelo.Proyecto;
import org.partnerprod.partnerprod.repositorio.ProyectoRepositorio;
import org.partnerprod.partnerprod.servicio.ProyectoServicio;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProyectoServicioTest {

    @InjectMocks
    private ProyectoServicio proyectoServicio;

    @Mock
    private ProyectoRepositorio proyectoRepositorio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarProyecto() {
        Proyecto proyecto = new Proyecto();

        when(proyectoRepositorio.save(proyecto)).thenReturn(proyecto);

        Proyecto result = proyectoServicio.guardarProyecto(proyecto);
        assertNotNull(result);
        verify(proyectoRepositorio, times(1)).save(proyecto);
    }
    @Test
    void testObtenerProyectoPorId() {
        Long proyectoId = 1L;
        Proyecto proyecto = new Proyecto();
        proyecto.setId(proyectoId);

        when(proyectoRepositorio.findById(proyectoId)).thenReturn(Optional.of(proyecto));

        Proyecto result = proyectoServicio.obtenerProyectoPorId(proyectoId);
        assertNotNull(result);
        assertEquals(proyectoId, result.getId());
        verify(proyectoRepositorio, times(1)).findById(proyectoId);
    }

    @Test
    void testObtenerProyectoPorIdNoEncontrado() {
        Long proyectoId = 1L;

        when(proyectoRepositorio.findById(proyectoId)).thenReturn(Optional.empty());

        Proyecto result = proyectoServicio.obtenerProyectoPorId(proyectoId);
        assertNull(result);
        verify(proyectoRepositorio, times(1)).findById(proyectoId);
    }
    @Test
    void testObtenerProyectosPorUsuarioId() {
        Long usuarioId = 1L;
        Proyecto proyecto1 = new Proyecto();
        Proyecto proyecto2 = new Proyecto();

        when(proyectoRepositorio.findByUsuarioId(usuarioId)).thenReturn(Arrays.asList(proyecto1, proyecto2));

        List<Proyecto> result = proyectoServicio.obtenerProyectosPorUsuarioId(usuarioId);
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(proyectoRepositorio, times(1)).findByUsuarioId(usuarioId);
    }
    @Test
    void testEliminarProyecto() {
        Long proyectoId = 1L;

        doNothing().when(proyectoRepositorio).deleteById(proyectoId);

        proyectoServicio.eliminarProyecto(proyectoId);
        verify(proyectoRepositorio, times(1)).deleteById(proyectoId);
    }
}
