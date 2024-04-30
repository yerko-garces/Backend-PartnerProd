package org.partnerprod.partnerprod.controlador;

import org.partnerprod.partnerprod.servicio.CapituloServicio;
import org.partnerprod.partnerprod.modelo.Capitulo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/capitulos")
public class CapituloControlador {
    @Autowired
    CapituloServicio capituloServicio;

    @PostMapping("/")
    public ResponseEntity<Capitulo> crearCapitulo(@RequestBody Capitulo capitulo) {
        Capitulo nuevoCapitulo = capituloServicio.guardarCapitulo(capitulo);
        return ResponseEntity.ok(nuevoCapitulo);
    }

    @GetMapping("/{proyectoId}")
    public ResponseEntity<List<Capitulo>> listarCapitulosPorProyecto(@PathVariable Long proyectoId) {
        List<Capitulo> capitulos = capituloServicio.obtenerCapitulosPorProyectoId(proyectoId);
        return ResponseEntity.ok(capitulos);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Capitulo> actualizarCapitulo(@PathVariable Long id, @RequestBody Capitulo capitulo) {
        capitulo.setId(id);
        Capitulo actualizado = capituloServicio.guardarCapitulo(capitulo);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCapitulo(@PathVariable Long id) {
        capituloServicio.eliminarCapitulo(id);
        return ResponseEntity.ok().build();
    }
}
