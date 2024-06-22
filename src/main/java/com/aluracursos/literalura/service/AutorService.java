package com.aluracursos.literalura.service;

import com.aluracursos.literalura.model.Autor;
import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AutorService {
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    public AutorService(AutorRepository autorRepository,LibroRepository libroRepository){
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }
//    @Transactional
//    public void addLibroToAutor(Long autorId, Long libroId) {
//        Autor autor = autorRepository.findById(autorId).orElseThrow(() -> new EntityNotFoundException("Autor no encontrado"));
//        Libro libro = libroRepository.findById(libroId).orElseThrow(() -> new EntityNotFoundException("Libro no encontrado"));
//
//        if (!autor.getLibros().contains(libro)) {
//            autor.getLibros().add(libro);
//            autorRepository.save(autor);
//        }
//    }

    @Transactional (readOnly = true)
    public List<Autor> obtenerTodosLosAutores(){
        return autorRepository.findAll();
    }

}