package org.partnerprod.partnerprod.controlador;

import org.partnerprod.partnerprod.modelo.Item;
import org.partnerprod.partnerprod.modelo.Usuario;
import org.partnerprod.partnerprod.servicio.ItemServicio;
import org.partnerprod.partnerprod.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "http://localhost:3000")
public class ItemControlador {

    @Autowired
    private ItemServicio itemServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @PostMapping("/")
    public ResponseEntity<Item> crearItem(@RequestBody Item item) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();
        Usuario usuario = usuarioServicio.obtenerUsuarioPorNombre(nombreUsuario);

        item.setBodegaVirtual(usuario.getBodegaVirtual());
        Item creado = itemServicio.guardarItem(item);
        return ResponseEntity.ok(creado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> obtenerItem(@PathVariable Long id) {
        return itemServicio.obtenerItemPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/bodega/{bodegaId}")
    public ResponseEntity<List<Item>> listarItemsPorBodega(@PathVariable Long bodegaId) {
        List<Item> items = itemServicio.obtenerItemsPorBodega(bodegaId);
        return ResponseEntity.ok(items);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> actualizarItem(@PathVariable Long id, @RequestBody Item item) {
        item.setId(id);
        Item actualizado = itemServicio.guardarItem(item);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarItem(@PathVariable Long id) {
        itemServicio.eliminarItem(id);
        return ResponseEntity.ok().build();
    }
}
