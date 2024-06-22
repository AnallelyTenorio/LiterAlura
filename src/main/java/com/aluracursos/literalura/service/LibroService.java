package com.aluracursos.literalura.service;

import com.aluracursos.literalura.dto.LibroDTO;
import com.aluracursos.literalura.repository.LibroRepository;
import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroService {
    @Autowired
    private LibroRepository repository;
}
