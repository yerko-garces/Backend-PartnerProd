package org.partnerprod.partnerprod;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.partnerprod.partnerprod.modelo.BodegaVirtual;
import org.partnerprod.partnerprod.modelo.Categoria;
import org.partnerprod.partnerprod.modelo.Item;
import org.partnerprod.partnerprod.repositorio.BodegaVirtualRepositorio;
import org.partnerprod.partnerprod.repositorio.ItemRepositorio;
import org.partnerprod.partnerprod.servicio.ItemServicio;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ItemServicioTest {

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
    void testGuardarItemNuevo() {
        Item item = new Item();
        item.setCantidad(10);
        BodegaVirtual bodega = new BodegaVirtual();
        bodega.setId(1L);
        item.setBodegaVirtual(bodega);

        when(bodegaVirtualRepositorio.findById(1L)).thenReturn(Optional.of(bodega));
        when(itemRepositorio.save(any(Item.class))).thenReturn(item);

        Item result = itemServicio.guardarItem(item);
        assertNotNull(result);
        verify(itemRepositorio, times(1)).save(item);
    }

    @Test
    void testGuardarItemExistente() {
        Long itemId = 1L;
        Item itemExistente = new Item();
        itemExistente.setId(itemId);
        itemExistente.setCantidad(10);
        BodegaVirtual bodega = new BodegaVirtual();
        bodega.setId(1L);
        itemExistente.setBodegaVirtual(bodega);

        when(itemRepositorio.findById(itemId)).thenReturn(Optional.of(itemExistente));
        when(bodegaVirtualRepositorio.findById(1L)).thenReturn(Optional.of(bodega));
        when(itemRepositorio.save(any(Item.class))).thenReturn(itemExistente);

        Item result = itemServicio.guardarItem(itemExistente);
        assertNotNull(result);
        verify(itemRepositorio, times(1)).save(itemExistente);
    }

    @Test
    void testGuardarItemConCantidadNegativa() {
        Item item = new Item();
        item.setCantidad(-1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            itemServicio.guardarItem(item);
        });

        assertEquals("La cantidad no puede ser negativa", exception.getMessage());
        verify(itemRepositorio, never()).save(any(Item.class));
    }

    @Test
    void testGuardarItemSinBodega() {
        Item item = new Item();
        item.setCantidad(10);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            itemServicio.guardarItem(item);
        });

        assertEquals("El ítem debe estar asociado a una bodega válida.", exception.getMessage());
        verify(itemRepositorio, never()).save(any(Item.class));
    }
    @Test
    void testObtenerItemPorId() {
        Long itemId = 1L;
        Item item = new Item();
        item.setId(itemId);

        when(itemRepositorio.findById(itemId)).thenReturn(Optional.of(item));

        Optional<Item> result = itemServicio.obtenerItemPorId(itemId);
        assertTrue(result.isPresent());
        assertEquals(itemId, result.get().getId());
        verify(itemRepositorio, times(1)).findById(itemId);
    }

    @Test
    void testObtenerItemPorIdNoEncontrado() {
        Long itemId = 1L;

        when(itemRepositorio.findById(itemId)).thenReturn(Optional.empty());

        Optional<Item> result = itemServicio.obtenerItemPorId(itemId);
        assertFalse(result.isPresent());
        verify(itemRepositorio, times(1)).findById(itemId);
    }
    @Test
    void testObtenerItemsPorBodega() {
        Long bodegaId = 1L;
        Item item1 = new Item();
        Item item2 = new Item();

        when(itemRepositorio.findByBodegaVirtualId(bodegaId)).thenReturn(Arrays.asList(item1, item2));

        List<Item> result = itemServicio.obtenerItemsPorBodega(bodegaId);
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(itemRepositorio, times(1)).findByBodegaVirtualId(bodegaId);
    }
    @Test
    void testObtenerItemsPorCategoria() {
        Categoria categoria = Categoria.GRIPERIA;
        Item item1 = new Item();
        Item item2 = new Item();

        when(itemRepositorio.findByCategoria(categoria)).thenReturn(Arrays.asList(item1, item2));

        List<Item> result = itemServicio.obtenerItemsPorCategoria(categoria);
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(itemRepositorio, times(1)).findByCategoria(categoria);
    }
    @Test
    void testEliminarItem() {
        Long itemId = 1L;

        doNothing().when(itemRepositorio).deleteById(itemId);

        itemServicio.eliminarItem(itemId);
        verify(itemRepositorio, times(1)).deleteById(itemId);
    }
}
