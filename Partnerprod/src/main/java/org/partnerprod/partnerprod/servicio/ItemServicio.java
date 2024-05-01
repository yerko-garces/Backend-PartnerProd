package org.partnerprod.partnerprod.servicio;

import org.partnerprod.partnerprod.modelo.BodegaVirtual;
import org.partnerprod.partnerprod.modelo.Categoria;
import org.partnerprod.partnerprod.repositorio.BodegaVirtualRepositorio;
import org.partnerprod.partnerprod.modelo.Item;
import org.partnerprod.partnerprod.repositorio.ItemRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServicio {

    @Autowired
    private ItemRepositorio itemRepositorio;

    @Autowired
    private BodegaVirtualRepositorio bodegaVirtualRepositorio;

    public Item guardarItem(Item item) {
        if (item.getCantidad() < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }
        if (item.getId() != null) {
            Item itemExistente = itemRepositorio.findById(item.getId()).orElse(null);
            if (itemExistente != null && item.getBodegaVirtual() == null) {
                item.setBodegaVirtual(itemExistente.getBodegaVirtual());
            }
        }
        if (item.getBodegaVirtual() != null && item.getBodegaVirtual().getId() != null) {
            BodegaVirtual bodega = bodegaVirtualRepositorio.findById(item.getBodegaVirtual().getId())
                    .orElseThrow(() -> new RuntimeException("Bodega no encontrada"));
            item.setBodegaVirtual(bodega);
        } else if (item.getBodegaVirtual() == null) {
            throw new RuntimeException("El ítem debe estar asociado a una bodega válida.");
        }

        return itemRepositorio.save(item);
    }


    public Optional<Item> obtenerItemPorId(Long id) {
        return itemRepositorio.findById(id);
    }

    public List<Item> obtenerItemsPorBodega(Long bodegaId) {
        return itemRepositorio.findByBodegaVirtualId(bodegaId);
    }

    public List<Item> obtenerItemsPorCategoria(Categoria categoria) {
        return itemRepositorio.findByCategoria(categoria);
    }


    public void eliminarItem(Long id) {
        itemRepositorio.deleteById(id);
    }
}


