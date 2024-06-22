package com.aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    //@Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    //@Column(name = "idiomas", columnDefinition = "character varying[]")
    private List<String> idiomas;
    //@Column(name = "numero_de_descargas")
    private Integer numeroDeDescargas;

    //@ManyToMany(mappedBy = "libros", cascade = CascadeType.ALL, fetch = FetchType.Eager)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "libros_autores",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autores_id"))
    private Set<Autor> autores = new HashSet<>();

    public Libro(){

    }

    public Libro(DatosLibro datosLibros){
//        this.titulo = datosLibros.titulo();
//        this.autores = datosLibros.autores();
//        this.idiomas =  datosLibros.idiomas();
//        this.numeroDeDescargas = datosLibros.numeroDeDescargas();
    }

    @Override
    public String toString() {
        return "Libros{" +
                "titulo='" + titulo + '\'' +
                ", autor='" + autores + '\'' +
                ", idioma='" + idiomas + '\'' +
                ", numeroDeDescargas=" + numeroDeDescargas +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Integer numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public Set<Autor> getAutores() {
        return autores;
    }

    public void setAutores(Set<Autor> autores) {
        this.autores = autores;
    }

    //    public List<Autor> getAutores() {
//        return autores;
//    }
//
//    public void setAutores(List<Autor> autores) {
//        this.autores = autores;
//    }
}
