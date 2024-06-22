package com.aluracursos.literalura.dto;

import com.aluracursos.literalura.model.DatosAutor;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.List;

public record LibroDTO(
        Long id,
        String titulo,
        List<DatosAutor> autores,
        List<String> idiomas,
        Integer numeroDeDescargas
) {
}
