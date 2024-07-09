package org.partnerprod.partnerprod.repositorio;

import org.partnerprod.partnerprod.modelo.Bloque;
import org.partnerprod.partnerprod.modelo.BloqueItem;
import org.partnerprod.partnerprod.modelo.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BloqueItemRepositorio extends JpaRepository<BloqueItem, Long> {
    List<BloqueItem> findByBloque_Fecha(LocalDate fecha);
    void deleteByBloque_Fecha(LocalDate fecha);
    Optional<BloqueItem> findByBloqueAndItem(Bloque bloque, Item item);
}