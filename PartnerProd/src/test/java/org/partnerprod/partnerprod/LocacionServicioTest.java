package org.partnerprod.partnerprod;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.partnerprod.partnerprod.modelo.Locacion;
import org.partnerprod.partnerprod.repositorio.LocacionRepositorio;
import org.partnerprod.partnerprod.servicio.LocacionServicio;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class LocacionServicioTest {

    @InjectMocks
    private LocacionServicio locacionServicio;

    @Mock
    private LocacionRepositorio locacionRepositorio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarLocacion() {
        Locacion locacion = new Locacion();

        when(locacionRepositorio.save(locacion)).thenReturn(locacion);

        Locacion result = locacionServicio.guardarLocacion(locacion);
        assertNotNull(result);
        verify(locacionRepositorio, times(1)).save(locacion);
    }
    @Test
    void testObtenerLocacionPorId() {
        Long locacionId = 1L;
        Locacion locacion = new Locacion();
        locacion.setId(locacionId);

        when(locacionRepositorio.findById(locacionId)).thenReturn(Optional.of(locacion));

        Locacion result = locacionServicio.obtenerLocacionPorId(locacionId);
        assertNotNull(result);
        assertEquals(locacionId, result.getId());
        verify(locacionRepositorio, times(1)).findById(locacionId);
    }

    @Test
    void testObtenerLocacionPorIdNoEncontrado() {
        Long locacionId = 1L;

        when(locacionRepositorio.findById(locacionId)).thenReturn(Optional.empty());

        Locacion result = locacionServicio.obtenerLocacionPorId(locacionId);
        assertNull(result);
        verify(locacionRepositorio, times(1)).findById(locacionId);
    }
    @Test
    void testObtenerTodasLasLocaciones() {
        Locacion locacion1 = new Locacion();
        Locacion locacion2 = new Locacion();

        when(locacionRepositorio.findAll()).thenReturn(Arrays.asList(locacion1, locacion2));

        List<Locacion> result = locacionServicio.obtenerTodasLasLocaciones();
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(locacionRepositorio, times(1)).findAll();
    }
    @Test
    void testEliminarLocacion() {
        Long locacionId = 1L;

        doNothing().when(locacionRepositorio).deleteById(locacionId);

        locacionServicio.eliminarLocacion(locacionId);
        verify(locacionRepositorio, times(1)).deleteById(locacionId);
    }
    @Test
    void testObtenerLocacionesPorProyecto() {
        Long proyectoId = 1L;
        Locacion locacion1 = new Locacion();
        Locacion locacion2 = new Locacion();

        when(locacionRepositorio.findByProyectoId(proyectoId)).thenReturn(Arrays.asList(locacion1, locacion2));

        List<Locacion> result = locacionServicio.obtenerLocacionesPorProyecto(proyectoId);
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(locacionRepositorio, times(1)).findByProyectoId(proyectoId);
    }
}
