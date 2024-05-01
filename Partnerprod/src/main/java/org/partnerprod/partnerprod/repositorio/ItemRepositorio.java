package org.partnerprod.partnerprod.repositorio;

import org.partnerprod.partnerprod.modelo.Categoria;
import org.partnerprod.partnerprod.modelo.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ItemRepositorio extends JpaRepository<Item, Long> {
    List<Item> findByBodegaVirtualId(Long bodegaVirtualId);
    List<Item> findByCategoria(Categoria categoria);
}
