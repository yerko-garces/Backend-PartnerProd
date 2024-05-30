package org.partnerprod.partnerprod.servicio;

import org.partnerprod.partnerprod.modelo.Bloque;
import org.partnerprod.partnerprod.modelo.BloqueItem;
import org.partnerprod.partnerprod.modelo.Item;
import org.partnerprod.partnerprod.modelo.PlanDeRodaje;
import org.partnerprod.partnerprod.repositorio.BloqueRepositorio;
import org.partnerprod.partnerprod.repositorio.ItemRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BloqueServicio {
    @Autowired
    private BloqueRepositorio bloqueRepositorio;

    @Autowired
    private ItemRepositorio itemRepositorio;

    public Bloque crearBloque(Bloque bloque) {
        if (bloque.getPosicion() == null) {
            PlanDeRodaje planDeRodaje = bloque.getPlanDeRodaje();
            List<Bloque> bloques = planDeRodaje.getBloques();
            int ultimaPosicion = bloques.isEmpty() ? 0 : bloques.get(bloques.size() - 1).getPosicion();
            bloque.setPosicion(ultimaPosicion + 1);
        }
        return bloqueRepositorio.save(bloque);
    }

    public Bloque obtenerBloquePorId(Long id) {
        return bloqueRepositorio.findById(id).orElse(null);
    }

    public Bloque actualizarBloque(Long id, Bloque bloque) {
        Bloque bloqueExistente = bloqueRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Bloque no encontrado"));

        bloqueExistente.setTitulo(bloque.getTitulo());
        bloqueExistente.setEscena(bloque.getEscena());
        bloqueExistente.setFecha(bloque.getFecha());
        bloqueExistente.setHora(bloque.getHora());
        bloqueExistente.setPosicion(bloque.getPosicion());

        if (bloque.getEscena() != null) {
            List<BloqueItem> bloqueItems = bloque.getItems();
            for (BloqueItem bloqueItem : bloqueItems) {
                Item item = bloqueItem.getItem();
                int cantidadDisponible = item.getCantidad();
                int cantidadRequerida = bloqueItem.getCantidad();
                if (cantidadDisponible >= cantidadRequerida) {
                    item.setCantidad(cantidadDisponible - cantidadRequerida);
                    itemRepositorio.save(item);
                } else {
                    throw new RuntimeException("No hay suficiente stock del item " + item.getNombre());
                }
            }
        }

        return bloqueRepositorio.save(bloqueExistente);
    }

    public void eliminarBloque(Long id) {
        Bloque bloque = bloqueRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Bloque no encontrado"));

        if (bloque.getEscena() != null) {
            List<BloqueItem> bloqueItems = bloque.getItems();
            for (BloqueItem bloqueItem : bloqueItems) {
                Item item = bloqueItem.getItem();
                int cantidadDisponible = item.getCantidad();
                int cantidadRequerida = bloqueItem.getCantidad();
                item.setCantidad(cantidadDisponible + cantidadRequerida);
                itemRepositorio.save(item);
            }
        }

        bloqueRepositorio.deleteById(id);
    }
}