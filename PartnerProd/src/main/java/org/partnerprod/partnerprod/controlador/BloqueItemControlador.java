package org.partnerprod.partnerprod.controlador;

import org.partnerprod.partnerprod.modelo.AsignarItemsFechaRequest;
import org.partnerprod.partnerprod.modelo.BloqueItem;
import org.partnerprod.partnerprod.servicio.BloqueItemServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bloque-items")
public class BloqueItemControlador {

    @Autowired
    private BloqueItemServicio bloqueItemServicio;

    @PostMapping
    public ResponseEntity<BloqueItem> asignarItemABloque(
            @RequestParam Long bloqueId,
            @RequestParam Long itemId,
            @RequestParam int cantidad) {
        BloqueItem bloqueItem = bloqueItemServicio.asignarItemABloque(bloqueId, itemId, cantidad);
        return ResponseEntity.ok(bloqueItem);
    }

    @GetMapping("/por-fecha")
    public ResponseEntity<List<BloqueItem>> obtenerItemsPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<BloqueItem> bloqueItems = bloqueItemServicio.obtenerItemsPorFecha(fecha);
        return ResponseEntity.ok(bloqueItems);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarBloqueItem(@PathVariable Long id) {
        bloqueItemServicio.eliminarBloqueItem(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/asignar-por-fecha")
    public ResponseEntity<Void> asignarItemsPorFecha(@RequestBody AsignarItemsFechaRequest request) {
        bloqueItemServicio.asignarItemsPorFecha(request.getFecha(), request.getItems());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/por-fecha")
    public ResponseEntity<Void> eliminarItemsPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        bloqueItemServicio.eliminarItemsPorFecha(fecha);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/asignados-por-fecha")
    public ResponseEntity<List<BloqueItem>> obtenerItemsAsignadosPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<BloqueItem> bloqueItems = bloqueItemServicio.obtenerItemsPorFecha(fecha);
        return ResponseEntity.ok(bloqueItems);
    }

    @PostMapping("/recuperar-por-fecha")
    public ResponseEntity<Void> recuperarItemsPorFecha(@RequestBody AsignarItemsFechaRequest request) {
        bloqueItemServicio.recuperarItemsPorFecha(request.getFecha(), request.getItems());
        return ResponseEntity.ok().build();
    }
}