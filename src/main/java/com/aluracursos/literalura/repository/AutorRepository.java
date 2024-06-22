package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findByNombre(String nombre);

@Query("SELECT a FROM Autor a WHERE a.nacimiento <= :anoIngresado AND a.muerte >= :anoIngresado")
List<Autor> autoresVivosEnAnoDeterminado (int anoIngresado);

}
