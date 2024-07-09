package org.partnerprod.partnerprod.repositorio;

import org.partnerprod.partnerprod.modelo.Bloque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BloqueRepositorio extends JpaRepository<Bloque, Long> {
    List<Bloque> findByFecha(LocalDate fecha);

    @Query("SELECT DISTINCT b FROM Bloque b LEFT JOIN FETCH b.items WHERE b.fecha = :fecha")
    List<Bloque> findByFechaWithItems(@Param("fecha") LocalDate fecha);
}