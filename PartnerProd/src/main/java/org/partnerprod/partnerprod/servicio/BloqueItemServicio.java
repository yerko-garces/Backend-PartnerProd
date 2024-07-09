package org.partnerprod.partnerprod.servicio;

import jakarta.transaction.Transactional;
import org.partnerprod.partnerprod.modelo.BloqueItem;
import org.partnerprod.partnerprod.modelo.Bloque;
import org.partnerprod.partnerprod.modelo.Item;
import org.partnerprod.partnerprod.repositorio.BloqueItemRepositorio;
import org.partnerprod.partnerprod.repositorio.BloqueRepositorio;
import org.partnerprod.partnerprod.repositorio.ItemRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class BloqueItemServicio {

    @Autowired
    private BloqueItemRepositorio bloqueItemRepositorio;

    @Autowired
    private BloqueRepositorio bloqueRepositorio;

    @Autowired
    private ItemRepositorio itemRepositorio;

    public BloqueItem asignarItemABloque(Long bloqueId, Long itemId, int cantidad) {
        Bloque bloque = bloqueRepositorio.findById(bloqueId)
                .orElseThrow(() -> new RuntimeException("Bloque no encontrado"));
        Item item = itemRepositorio.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        BloqueItem bloqueItem = new BloqueItem();
        bloqueItem.setBloque(bloque);
        bloqueItem.setItem(item);
        bloqueItem.setCantidad(cantidad);

        return bloqueItemRepositorio.save(bloqueItem);
    }

    public List<BloqueItem> obtenerItemsPorFecha(LocalDate fecha) {
        return bloqueItemRepositorio.findByBloque_Fecha(fecha);
    }

    public void eliminarBloqueItem(Long bloqueItemId) {
        bloqueItemRepositorio.deleteById(bloqueItemId);
    }

    @Transactional
    public void asignarItemsPorFecha(LocalDate fecha, Map<Long, Integer> items) {
        List<Bloque> bloques = bloqueRepositorio.findByFechaWithItems(fecha);
        if (bloques.isEmpty()) {
            throw new RuntimeException("No hay bloques para la fecha especificada");
        }

        for (Map.Entry<Long, Integer> entry : items.entrySet()) {
            Long itemId = entry.getKey();
            Integer cantidadRequerida = entry.getValue();

            if (cantidadRequerida == null || cantidadRequerida <= 0) {
                throw new RuntimeException("Cantidad inválida para el item con ID: " + itemId);
            }

            Item item = itemRepositorio.findById(itemId)
                    .orElseThrow(() -> new RuntimeException("Item no encontrado con ID: " + itemId));

            if (item.getCantidad() < cantidadRequerida) {
                throw new RuntimeException("No hay suficiente cantidad del item: " + item.getNombre());
            }

            item.setCantidad(item.getCantidad() - cantidadRequerida);
            itemRepositorio.save(item);

            for (Bloque bloque : bloques) {
                BloqueItem bloqueItem = new BloqueItem();
                bloqueItem.setBloque(bloque);
                bloqueItem.setItem(item);
                bloqueItem.setCantidad(cantidadRequerida);
                bloqueItemRepositorio.save(bloqueItem);
            }
        }
    }

    @Transactional
    public void eliminarItemsPorFecha(LocalDate fecha) {
        List<BloqueItem> bloqueItems = bloqueItemRepositorio.findByBloque_Fecha(fecha);
        for (BloqueItem bloqueItem : bloqueItems) {
            Item item = bloqueItem.getItem();
            item.setCantidad(item.getCantidad() + bloqueItem.getCantidad());
            itemRepositorio.save(item);
        }
        bloqueItemRepositorio.deleteByBloque_Fecha(fecha);
    }

    @Transactional
    public void recuperarItemsPorFecha(LocalDate fecha, Map<Long, Integer> items) {
        List<BloqueItem> bloqueItems = bloqueItemRepositorio.findByBloque_Fecha(fecha);

        for (Map.Entry<Long, Integer> entry : items.entrySet()) {
            Long bloqueItemId = entry.getKey();
            Integer cantidadRecuperar = entry.getValue();

            BloqueItem bloqueItem = bloqueItems.stream()
                    .filter(bi -> bi.getId().equals(bloqueItemId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("BloqueItem no encontrado con ID: " + bloqueItemId));

            if (cantidadRecuperar > bloqueItem.getCantidad()) {
                throw new RuntimeException("No se puede recuperar más cantidad de la asignada para el item: " + bloqueItem.getItem().getNombre());
            }

            Item item = bloqueItem.getItem();
            item.setCantidad(item.getCantidad() + cantidadRecuperar);
            itemRepositorio.save(item);

            if (cantidadRecuperar == bloqueItem.getCantidad()) {
                bloqueItemRepositorio.delete(bloqueItem);
            } else {
                bloqueItem.setCantidad(bloqueItem.getCantidad() - cantidadRecuperar);
                bloqueItemRepositorio.save(bloqueItem);
            }
        }
    }
}