package com.example.aluracursos.literAlura.repository;

import com.example.aluracursos.literAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor,Long> {
    @Query("SELECT a FROM Author a WHERE a.nacimiento >= :anoBusqueda ORDER BY a.nacimiento ASC ")
    List<Autor> autorPorFecha(int anoBusqueda);
}
