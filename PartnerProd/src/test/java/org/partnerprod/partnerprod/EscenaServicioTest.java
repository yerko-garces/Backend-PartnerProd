package org.partnerprod.partnerprod;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.partnerprod.partnerprod.modelo.Escena;
import org.partnerprod.partnerprod.repositorio.*;
import org.partnerprod.partnerprod.servicio.EscenaServicio;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EscenaServicioTest {

    @InjectMocks
    private EscenaServicio escenaServicio;

    @Mock
    private EscenaRepositorio escenaRepositorio;

    @Mock
    private ItemRepositorio itemRepositorio;

    @Mock
    private PersonajeRepositorio personajeRepositorio;

    @Mock
    private LocacionRepositorio locacionRepositorio;

    @Mock
    private CapituloRepositorio capituloRepositorio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarEscenaNueva() {
        Escena nuevaEscena = new Escena();
        nuevaEscena.setId(null); // Nueva escena, sin ID
        // Configurar mocks según sea necesario

        when(escenaRepositorio.save(any(Escena.class))).thenReturn(nuevaEscena);

        Escena result = escenaServicio.guardarEscena(nuevaEscena);
        assertNotNull(result);
        verify(escenaRepositorio, times(1)).save(nuevaEscena);
    }

    @Test
    void testGuardarEscenaExistenteConItems() {
        Long escenaId = 1L;
        Escena escenaExistente = new Escena();
        escenaExistente.setId(escenaId);
        // Configurar items y otros campos según sea necesario

        when(escenaRepositorio.findById(escenaId)).thenReturn(Optional.of(escenaExistente));
        when(escenaRepositorio.save(any(Escena.class))).thenReturn(escenaExistente);

        Escena result = escenaServicio.guardarEscena(escenaExistente);
        assertNotNull(result);
        verify(escenaRepositorio, times(1)).save(escenaExistente);
    }

    // Agregar más tests para cubrir casos específicos del método guardarEscena
    @Test
    void testObtenerEscenasPorCapituloId() {
        Long capituloId = 1L;
        Escena escena1 = new Escena();
        Escena escena2 = new Escena();

        when(escenaRepositorio.findAllByCapituloId(capituloId)).thenReturn(Arrays.asList(escena1, escena2));

        List<Escena> result = escenaServicio.obtenerEscenasPorCapituloId(capituloId);
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(escenaRepositorio, times(1)).findAllByCapituloId(capituloId);
    }
    @Test
    void testObtenerEscenaPorId() {
        Long escenaId = 1L;
        Escena escena = new Escena();
        escena.setId(escenaId);

        when(escenaRepositorio.findById(escenaId)).thenReturn(Optional.of(escena));

        Escena result = escenaServicio.obtenerEscenaPorId(escenaId);
        assertNotNull(result);
        assertEquals(escenaId, result.getId());
        verify(escenaRepositorio, times(1)).findById(escenaId);
    }

    @Test
    void testObtenerEscenaPorIdNoEncontrada() {
        Long escenaId = 1L;

        when(escenaRepositorio.findById(escenaId)).thenReturn(Optional.empty());

        Escena result = escenaServicio.obtenerEscenaPorId(escenaId);
        assertNull(result);
        verify(escenaRepositorio, times(1)).findById(escenaId);
    }
    @Test
    void testEliminarEscena() {
        Long escenaId = 1L;
        Escena escenaExistente = new Escena();
        escenaExistente.setId(escenaId);
        // Configurar items según sea necesario

        when(escenaRepositorio.findById(escenaId)).thenReturn(Optional.of(escenaExistente));

        escenaServicio.eliminarEscena(escenaId);
        verify(escenaRepositorio, times(1)).deleteById(escenaId);
    }

    @Test
    void testEliminarEscenaNoEncontrada() {
        Long escenaId = 1L;

        when(escenaRepositorio.findById(escenaId)).thenReturn(Optional.empty());

        escenaServicio.eliminarEscena(escenaId);
        verify(escenaRepositorio, times(1)).findById(escenaId);
        verify(escenaRepositorio, never()).deleteById(escenaId);
    }
}


