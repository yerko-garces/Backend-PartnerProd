package com.example.demo.repositorio;

import com.example.demo.modelo.Capitulo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CapituloRepositorio extends JpaRepository<Capitulo, Long> {
    List<Capitulo> findAllByProyectoId(Long proyectoId);
}
