package org.partnerprod.partnerprod;

import org.partnerprod.partnerprod.modelo.*;
import org.partnerprod.partnerprod.repositorio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.partnerprod.partnerprod.servicio.EscenaServicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EscenaServicioTest {

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
    void guardarEscena_nuevaEscena_deberiaGuardarEscena() {
        // Given
        Escena escena = new Escena();
        escena.setTitulo_escena("Título de la escena");
        when(escenaRepositorio.save(escena)).thenReturn(escena);

        // When
        Escena escenaGuardada = escenaServicio.guardarEscena(escena);

        // Then
        assertNotNull(escenaGuardada);
        assertEquals("Título de la escena", escenaGuardada.getTitulo_escena());
        verify(escenaRepositorio, times(1)).save(escena);
    }

    @Test
    void guardarEscena_escenaExistenteConItems_deberiaActualizarCantidadDeItems() {
        // Given
        Escena escenaExistente = new Escena();
        escenaExistente.setId(1L);
        Item item1 = new Item();
        item1.setId(1L);
        item1.setCantidad(5);
        Item item2 = new Item();
        item2.setId(2L);
        item2.setCantidad(3);
        escenaExistente.setItems(List.of(item1, item2));
        when(escenaRepositorio.findById(1L)).thenReturn(Optional.of(escenaExistente));
        when(itemRepositorio.findById(1L)).thenReturn(Optional.of(item1));
        when(itemRepositorio.findById(2L)).thenReturn(Optional.of(item2));

        Escena escenaActualizada = new Escena();
        escenaActualizada.setId(1L);
        escenaActualizada.setItems(List.of(item2));
        when(escenaRepositorio.save(escenaActualizada)).thenReturn(escenaActualizada);

        // When
        Escena escenaGuardada = escenaServicio.guardarEscena(escenaActualizada);

        // Then
        assertNotNull(escenaGuardada);
        assertEquals(1, escenaGuardada.getItems().size());
        verify(itemRepositorio, times(1)).save(item1);
        verify(itemRepositorio, times(1)).save(item2);
        verify(escenaRepositorio, times(1)).save(escenaActualizada);
    }

    @Test
    void guardarEscena_escenaConPersonajesYLocacion_deberiaGuardarEscenaConPersonajesYLocacionValidos() {
        // Given
        Escena escena = new Escena();
        escena.setTitulo_escena("Título de la escena");
        Personaje personaje1 = new Personaje();
        personaje1.setId(1L);
        Proyecto proyecto1 = new Proyecto();
        personaje1.setProyecto(proyecto1);
        Personaje personaje2 = new Personaje();
        personaje2.setId(2L);
        personaje2.setProyecto(proyecto1);
        escena.setPersonajes(List.of(personaje1, personaje2));
        Locacion locacion = new Locacion();
        locacion.setId(1L);
        locacion.setProyecto(proyecto1);
        escena.setLocacion(locacion);

        Capitulo capitulo = new Capitulo();
        capitulo.setId(1L);
        capitulo.setProyecto(proyecto1);
        escena.setCapitulo(capitulo);

        when(capituloRepositorio.findById(1L)).thenReturn(Optional.of(capitulo));
        when(personajeRepositorio.findById(1L)).thenReturn(Optional.of(personaje1));
        when(personajeRepositorio.findById(2L)).thenReturn(Optional.of(personaje2));
        when(locacionRepositorio.findById(1L)).thenReturn(Optional.of(locacion));
        when(escenaRepositorio.save(escena)).thenReturn(escena);

        // When
        Escena escenaGuardada = escenaServicio.guardarEscena(escena);

        // Then
        assertNotNull(escenaGuardada);
        assertEquals(2, escenaGuardada.getPersonajes().size());
        assertNotNull(escenaGuardada.getLocacion());
        verify(escenaRepositorio, times(1)).save(escena);
    }

    @Test
    void obtenerEscenasPorCapituloId_deberiaRetornarListaDeEscenas() {
        // Given
        Long capituloId = 1L;
        List<Escena> escenas = new ArrayList<>();
        escenas.add(new Escena());
        escenas.add(new Escena());
        when(escenaRepositorio.findAllByCapituloId(capituloId)).thenReturn(escenas);

        // When
        List<Escena> escenasObtenidas = escenaServicio.obtenerEscenasPorCapituloId(capituloId);

        // Then
        assertNotNull(escenasObtenidas);
        assertEquals(2, escenasObtenidas.size());
        verify(escenaRepositorio, times(1)).findAllByCapituloId(capituloId);
    }

    @Test
    void obtenerEscenaPorId_escenaExistente_deberiaRetornarEscena() {
        // Given
        Long escenaId = 1L;
        Escena escena = new Escena();
        when(escenaRepositorio.findById(escenaId)).thenReturn(Optional.of(escena));

        // When
        Escena escenaObtenida = escenaServicio.obtenerEscenaPorId(escenaId);

        // Then
        assertNotNull(escenaObtenida);
        verify(escenaRepositorio, times(1)).findById(escenaId);
    }

    @Test
    void obtenerEscenaPorId_escenaNoExistente_deberiaRetornarNull() {
        // Given
        Long escenaId = 1L;
        when(escenaRepositorio.findById(escenaId)).thenReturn(Optional.empty());

        // When
        Escena escenaObtenida = escenaServicio.obtenerEscenaPorId(escenaId);

        // Then
        assertNull(escenaObtenida);
        verify(escenaRepositorio, times(1)).findById(escenaId);
    }

    @Test
    void eliminarEscena_escenaExistenteConItems_deberiaEliminarEscenaYActualizarCantidadDeItems() {
        // Given
        Long escenaId = 1L;
        Escena escena = new Escena();
        Item item1 = new Item();
        item1.setId(1L);
        item1.setCantidad(5);
        Item item2 = new Item();
        item2.setId(2L);
        item2.setCantidad(3);
        escena.setItems(List.of(item1, item2));
        when(escenaRepositorio.findById(escenaId)).thenReturn(Optional.of(escena));
        when(itemRepositorio.findById(1L)).thenReturn(Optional.of(item1));
        when(itemRepositorio.findById(2L)).thenReturn(Optional.of(item2));

        // When
        escenaServicio.eliminarEscena(escenaId);

        // Then
        verify(itemRepositorio, times(1)).save(item1);
        verify(itemRepositorio, times(1)).save(item2);
        verify(escenaRepositorio, times(1)).deleteById(escenaId);
    }

    @Test
    void eliminarEscena_escenaNoExistente_noDeberiaRealizarCambios() {
        // Given
        Long escenaId = 1L;
        when(escenaRepositorio.findById(escenaId)).thenReturn(Optional.empty());

        // When
        escenaServicio.eliminarEscena(escenaId);

        // Then
        verify(escenaRepositorio, times(1)).findById(escenaId);
        verify(escenaRepositorio, never()).deleteById(anyLong());
    }
}