package org.partnerprod.partnerprod;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.partnerprod.partnerprod.modelo.Personaje;
import org.partnerprod.partnerprod.repositorio.PersonajeRepositorio;
import org.partnerprod.partnerprod.servicio.PersonajeServicio;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PersonajeServicioTest {

    @InjectMocks
    private PersonajeServicio personajeServicio;

    @Mock
    private PersonajeRepositorio personajeRepositorio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarPersonaje() {
        Personaje personaje = new Personaje();

        when(personajeRepositorio.save(personaje)).thenReturn(personaje);

        Personaje result = personajeServicio.guardarPersonaje(personaje);
        assertNotNull(result);
        verify(personajeRepositorio, times(1)).save(personaje);
    }
    @Test
    void testObtenerPersonajePorId() {
        Long personajeId = 1L;
        Personaje personaje = new Personaje();
        personaje.setId(personajeId);

        when(personajeRepositorio.findById(personajeId)).thenReturn(Optional.of(personaje));

        Personaje result = personajeServicio.obtenerPersonajePorId(personajeId);
        assertNotNull(result);
        assertEquals(personajeId, result.getId());
        verify(personajeRepositorio, times(1)).findById(personajeId);
    }

    @Test
    void testObtenerPersonajePorIdNoEncontrado() {
        Long personajeId = 1L;

        when(personajeRepositorio.findById(personajeId)).thenReturn(Optional.empty());

        Personaje result = personajeServicio.obtenerPersonajePorId(personajeId);
        assertNull(result);
        verify(personajeRepositorio, times(1)).findById(personajeId);
    }
    @Test
    void testObtenerTodosLosPersonajes() {
        Personaje personaje1 = new Personaje();
        Personaje personaje2 = new Personaje();

        when(personajeRepositorio.findAll()).thenReturn(Arrays.asList(personaje1, personaje2));

        List<Personaje> result = personajeServicio.obtenerTodosLosPersonajes();
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(personajeRepositorio, times(1)).findAll();
    }
    @Test
    void testEliminarPersonaje() {
        Long personajeId = 1L;

        doNothing().when(personajeRepositorio).deleteById(personajeId);

        personajeServicio.eliminarPersonaje(personajeId);
        verify(personajeRepositorio, times(1)).deleteById(personajeId);
    }
    @Test
    void testObtenerPersonajesPorProyecto() {
        Long proyectoId = 1L;
        Personaje personaje1 = new Personaje();
        Personaje personaje2 = new Personaje();

        when(personajeRepositorio.findByProyectoId(proyectoId)).thenReturn(Arrays.asList(personaje1, personaje2));

        List<Personaje> result = personajeServicio.obtenerPersonajesPorProyecto(proyectoId);
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(personajeRepositorio, times(1)).findByProyectoId(proyectoId);
    }
}
