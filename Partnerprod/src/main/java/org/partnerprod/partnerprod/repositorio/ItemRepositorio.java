package org.partnerprod.partnerprod.repositorio;

import org.partnerprod.partnerprod.modelo.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepositorio extends JpaRepository<Item, Long> {
    List<Item> findByBodegaVirtualId(Long bodegaVirtualId);
}
