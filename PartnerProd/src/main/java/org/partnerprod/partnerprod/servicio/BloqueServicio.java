package org.partnerprod.partnerprod.servicio;

import jakarta.transaction.Transactional;
import org.partnerprod.partnerprod.modelo.*;
import org.partnerprod.partnerprod.repositorio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BloqueServicio {
    @Autowired
    private BloqueRepositorio bloqueRepositorio;

    @Autowired
    private BloqueEscenaRepositorio bloqueEscenaRepositorio;


    @Autowired
    private BloqueItemRepositorio bloqueItemRepositorio;

    @Autowired
    private EscenaRepositorio escenaRepositorio;

    @Autowired
    private ItemRepositorio itemRepositorio;

    @Autowired
    private PlanDeRodajeRepositorio planDeRodajeRepositorio;

    public Bloque crearBloque(Long planDeRodajeId, Bloque bloque) {
        PlanDeRodaje planDeRodaje = planDeRodajeRepositorio.findById(planDeRodajeId)
                .orElseThrow(() -> new RuntimeException("Plan de rodaje no encontrado"));

        bloque.setPlanDeRodaje(planDeRodaje);
        return bloqueRepositorio.save(bloque);
    }

    public Bloque obtenerBloquePorId(Long id) {
        return bloqueRepositorio.findById(id).orElse(null);
    }

    public List<Bloque> obtenerBloquesPorPlanDeRodaje(Long planDeRodajeId) {
        PlanDeRodaje planDeRodaje = planDeRodajeRepositorio.findById(planDeRodajeId)
                .orElseThrow(() -> new RuntimeException("Plan de rodaje no encontrado"));

        return planDeRodaje.getBloques();
    }

    @Transactional
    public Bloque actualizarBloque(Long id, Bloque bloque) {
        Bloque bloqueExistente = bloqueRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Bloque no encontrado"));

        bloqueExistente.setFecha(bloque.getFecha());
        bloqueExistente.setDescripcion(bloque.getDescripcion());

        // Crear un mapa de las escenas existentes para facilitar la búsqueda
        Map<Long, BloqueEscena> escenasExistentesMap = bloqueExistente.getEscenasAsociadas().stream()
                .collect(Collectors.toMap(be -> be.getEscena().getId(), Function.identity()));

        // Actualizar o crear nuevas escenas asociadas
        List<BloqueEscena> escenasActualizadas = new ArrayList<>();
        for (BloqueEscena escenaActualizada : bloque.getEscenasAsociadas()) {
            BloqueEscena escenaExistente = escenasExistentesMap.get(escenaActualizada.getEscena().getId());
            if (escenaExistente != null) {
                // Actualizar escena existente
                escenaExistente.setHora(escenaActualizada.getHora());
                escenaExistente.setPosicion(escenaActualizada.getPosicion());
                escenasActualizadas.add(escenaExistente);
                escenasExistentesMap.remove(escenaActualizada.getEscena().getId());
            } else {
                // Crear nueva escena asociada
                BloqueEscena nuevaEscena = new BloqueEscena();
                nuevaEscena.setEscena(escenaActualizada.getEscena());
                nuevaEscena.setHora(escenaActualizada.getHora());
                nuevaEscena.setPosicion(escenaActualizada.getPosicion());
                bloqueExistente.addBloqueEscena(nuevaEscena);
                escenasActualizadas.add(nuevaEscena);
            }
        }

        // Eliminar escenas que ya no están presentes
        for (BloqueEscena escenaAEliminar : escenasExistentesMap.values()) {
            bloqueExistente.removeBloqueEscena(escenaAEliminar);
            bloqueEscenaRepositorio.delete(escenaAEliminar);
        }

        bloqueExistente.actualizarEscenasAsociadas(escenasActualizadas);

        return bloqueRepositorio.save(bloqueExistente);
    }

    public void eliminarBloque(Long id) {
        bloqueRepositorio.deleteById(id);
    }

    public void asociarEscenaABloque(Long bloqueId, Long escenaId, LocalTime hora, Integer posicion) {
        Bloque bloque = bloqueRepositorio.findById(bloqueId)
                .orElseThrow(() -> new RuntimeException("Bloque no encontrado"));
        Escena escena = escenaRepositorio.findById(escenaId)
                .orElseThrow(() -> new RuntimeException("Escena no encontrada"));

        BloqueEscena bloqueEscena = new BloqueEscena();
        bloqueEscena.setBloque(bloque);
        bloqueEscena.setEscena(escena);
        bloqueEscena.setHora(hora);
        bloqueEscena.setPosicion(posicion);

        bloqueEscenaRepositorio.save(bloqueEscena);
    }

    public void actualizarEscenaEnBloque(Long bloqueEscenaId, LocalTime hora, Integer posicion) {
        BloqueEscena bloqueEscena = bloqueEscenaRepositorio.findById(bloqueEscenaId)
                .orElseThrow(() -> new RuntimeException("BloqueEscena no encontrado"));

        bloqueEscena.setHora(hora);
        bloqueEscena.setPosicion(posicion);

        bloqueEscenaRepositorio.save(bloqueEscena);
    }

    public void eliminarEscenaDeBloque(Long bloqueEscenaId) {
        bloqueEscenaRepositorio.deleteById(bloqueEscenaId);
    }

    public void asociarItemABloque(Long bloqueId, Long itemId, int cantidad) {
        Bloque bloque = bloqueRepositorio.findById(bloqueId)
                .orElseThrow(() -> new RuntimeException("Bloque no encontrado"));
        Item item = itemRepositorio.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        BloqueItem bloqueItem = new BloqueItem();
        bloqueItem.setBloque(bloque);
        bloqueItem.setItem(item);
        bloqueItem.setCantidad(cantidad);

        bloque.getItems().add(bloqueItem);
        bloqueRepositorio.save(bloque);
    }

    public void actualizarItemEnBloque(Long bloqueItemId, int cantidad) {
        BloqueItem bloqueItem = bloqueItemRepositorio.findById(bloqueItemId)
                .orElseThrow(() -> new RuntimeException("BloqueItem no encontrado"));

        bloqueItem.setCantidad(cantidad);

        bloqueItemRepositorio.save(bloqueItem);
    }

    public void eliminarItemDeBloque(Long bloqueItemId) {
        bloqueItemRepositorio.deleteById(bloqueItemId);
    }
}