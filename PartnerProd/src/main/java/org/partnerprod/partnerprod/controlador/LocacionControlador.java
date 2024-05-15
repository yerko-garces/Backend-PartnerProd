package org.partnerprod.partnerprod.controlador;

import com.google.gson.Gson;
import org.partnerprod.partnerprod.modelo.Locacion;
import org.partnerprod.partnerprod.modelo.Proyecto;
import org.partnerprod.partnerprod.servicio.LocacionServicio;
import org.partnerprod.partnerprod.servicio.ProyectoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locaciones")
public class LocacionControlador {
    @Autowired
    private LocacionServicio locacionServicio;

    @Autowired
    private ProyectoServicio proyectoServicio;

    @PostMapping("/")
    public ResponseEntity<Locacion> crearLocacion(@RequestBody Locacion locacion) {
        System.out.println("Entrando al método crearLocacion");
        System.out.println("Locación recibida: " + locacion);
        System.out.println("JSON recibido: " + new Gson().toJson(locacion));

        if (locacion.getProyecto() == null || locacion.getProyecto().getId() == null) {
            System.out.println("El id del proyecto no se ha proporcionado correctamente");
            return ResponseEntity.badRequest().body(null);
        }

        Long proyectoId = locacion.getProyecto().getId();
        System.out.println("Id del proyecto: " + proyectoId);

        Proyecto proyecto = proyectoServicio.obtenerProyectoPorId(proyectoId);

        if (proyecto == null) {
            System.out.println("El proyecto no existe");
            return ResponseEntity.badRequest().body(null);
        }

        System.out.println("Proyecto obtenido: " + proyecto);

        locacion.setProyecto(proyecto);

        Locacion nuevaLocacion = locacionServicio.guardarLocacion(locacion);

        System.out.println("Locación guardada: " + nuevaLocacion);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaLocacion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Locacion> obtenerLocacionPorId(@PathVariable Long id) {
        Locacion locacion = locacionServicio.obtenerLocacionPorId(id);
        if (locacion != null) {
            return ResponseEntity.ok(locacion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/proyecto/{proyectoId}")
    public ResponseEntity<List<Locacion>> obtenerLocacionesPorProyecto(@PathVariable Long proyectoId) {
        List<Locacion> locaciones = locacionServicio.obtenerLocacionesPorProyecto(proyectoId);
        return ResponseEntity.ok(locaciones.isEmpty() ? List.of() : locaciones);
    }

    @GetMapping("/")
    public ResponseEntity<List<Locacion>> obtenerTodasLasLocaciones() {
        List<Locacion> locaciones = locacionServicio.obtenerTodasLasLocaciones();
        return ResponseEntity.ok(locaciones);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Locacion> actualizarLocacion(@PathVariable Long id, @RequestBody Locacion locacion) {
        Locacion locacionExistente = locacionServicio.obtenerLocacionPorId(id);
        if (locacionExistente == null) {
            return ResponseEntity.notFound().build();
        }

        locacion.setId(id);

        if (locacion.getProyecto() == null) {
            locacion.setProyecto(locacionExistente.getProyecto());
        } else {
            Proyecto proyecto = proyectoServicio.obtenerProyectoPorId(locacion.getProyecto().getId());
            if (proyecto == null) {
                return ResponseEntity.badRequest().body(null);
            }
            locacion.setProyecto(proyecto);
        }

        Locacion locacionActualizada = locacionServicio.guardarLocacion(locacion);
        return ResponseEntity.ok(locacionActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLocacion(@PathVariable Long id) {
        locacionServicio.eliminarLocacion(id);
        return ResponseEntity.noContent().build();
    }
}
