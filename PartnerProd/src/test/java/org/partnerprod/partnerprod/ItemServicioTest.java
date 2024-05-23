package org.partnerprod.partnerprod;

import org.partnerprod.partnerprod.modelo.BodegaVirtual;
import org.partnerprod.partnerprod.modelo.Categoria;
import org.partnerprod.partnerprod.modelo.Item;
import org.partnerprod.partnerprod.repositorio.BodegaVirtualRepositorio;
import org.partnerprod.partnerprod.repositorio.ItemRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.partnerprod.partnerprod.servicio.ItemServicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemServicioTest {

    @InjectMocks
    private ItemServicio itemServicio;

    @Mock
    private ItemRepositorio itemRepositorio;

    @Mock
    private BodegaVirtualRepositorio bodegaVirtualRepositorio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void guardarItem_cantidadNegativa_deberiaLanzarExcepcion() {
        // Given
        Item item = new Item();
        item.setCantidad(-1);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> itemServicio.guardarItem(item));
    }

    @Test
    void guardarItem_itemExistenteConBodegaNula_deberiaAsignarBodegaExistente() {
        // Given
        Item itemExistente = new Item();
        itemExistente.setId(1L);
        BodegaVirtual bodegaExistente = new BodegaVirtual();
        itemExistente.setBodegaVirtual(bodegaExistente);
        when(itemRepositorio.findById(1L)).thenReturn(Optional.of(itemExistente));

        Item itemActualizado = new Item();
        itemActualizado.setId(1L);
        itemActualizado.setCantidad(5);
        when(itemRepositorio.save(itemActualizado)).thenReturn(itemActualizado);

        // When
        Item itemGuardado = itemServicio.guardarItem(itemActualizado);

        // Then
        assertNotNull(itemGuardado);
        assertEquals(bodegaExistente, itemGuardado.getBodegaVirtual());
        verify(itemRepositorio, times(1)).save(itemActualizado);
    }

    @Test
    void guardarItem_bodegaValida_deberiaGuardarItem() {
        // Given
        BodegaVirtual bodega = new BodegaVirtual();
        bodega.setId(1L);
        when(bodegaVirtualRepositorio.findById(1L)).thenReturn(Optional.of(bodega));

        Item item = new Item();
        item.setCantidad(5);
        item.setBodegaVirtual(bodega);
        when(itemRepositorio.save(item)).thenReturn(item);

        // When
        Item itemGuardado = itemServicio.guardarItem(item);

        // Then
        assertNotNull(itemGuardado);
        assertEquals(bodega, itemGuardado.getBodegaVirtual());
        verify(itemRepositorio, times(1)).save(item);
    }

    @Test
    void guardarItem_bodegaInvalida_deberiaLanzarExcepcion() {
        // Given
        Item item = new Item();
        item.setCantidad(5);

        // When & Then
        assertThrows(RuntimeException.class, () -> itemServicio.guardarItem(item));
    }

    @Test
    void obtenerItemPorId_itemExistente_deberiaRetornarItem() {
        // Given
        Long itemId = 1L;
        Item item = new Item();
        when(itemRepositorio.findById(itemId)).thenReturn(Optional.of(item));

        // When
        Optional<Item> itemObtenido = itemServicio.obtenerItemPorId(itemId);

        // Then
        assertTrue(itemObtenido.isPresent());
        assertEquals(item, itemObtenido.get());
        verify(itemRepositorio, times(1)).findById(itemId);
    }

    @Test
    void obtenerItemPorId_itemNoExistente_deberiaRetornarOptionalVacio() {
        // Given
        Long itemId = 1L;
        when(itemRepositorio.findById(itemId)).thenReturn(Optional.empty());

        // When
        Optional<Item> itemObtenido = itemServicio.obtenerItemPorId(itemId);

        // Then
        assertFalse(itemObtenido.isPresent());
        verify(itemRepositorio, times(1)).findById(itemId);
    }

    @Test
    void obtenerItemsPorBodega_deberiaRetornarListaDeItems() {
        // Given
        Long bodegaId = 1L;
        List<Item> items = new ArrayList<>();
        items.add(new Item());
        items.add(new Item());
        when(itemRepositorio.findByBodegaVirtualId(bodegaId)).thenReturn(items);

        // When
        List<Item> itemsObtenidos = itemServicio.obtenerItemsPorBodega(bodegaId);

        // Then
        assertNotNull(itemsObtenidos);
        assertEquals(2, itemsObtenidos.size());
        verify(itemRepositorio, times(1)).findByBodegaVirtualId(bodegaId);
    }

    @Test
    void obtenerItemsPorCategoria_deberiaRetornarListaDeItems() {
        // Given
        Categoria categoria = Categoria.AUDIO;
        List<Item> items = new ArrayList<>();
        items.add(new Item());
        items.add(new Item());
        when(itemRepositorio.findByCategoria(categoria)).thenReturn(items);

        // When
        List<Item> itemsObtenidos = itemServicio.obtenerItemsPorCategoria(categoria);

        // Then
        assertNotNull(itemsObtenidos);
        assertEquals(2, itemsObtenidos.size());
        verify(itemRepositorio, times(1)).findByCategoria(categoria);
    }

    @Test
    void eliminarItem_idValido_deberiaEliminarItem() {
        // Given
        Long itemId = 1L;

        // When
        itemServicio.eliminarItem(itemId);

        // Then
        verify(itemRepositorio, times(1)).deleteById(itemId);
    }

    @Test
    void eliminarItem_idNulo_deberiaLanzarExcepcion() {
        // Given
        Long itemId = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> itemServicio.eliminarItem(itemId));
        verify(itemRepositorio, never()).deleteById(any());
    }
}