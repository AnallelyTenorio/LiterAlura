package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.*;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.AutorService;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "http://gutendex.com/books/";
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final AutorService autorService;

    @Autowired
    public Principal (LibroRepository libroRepository, AutorRepository autorRepository,AutorService autorService){
        this.libroRepository= libroRepository;
        this.autorRepository = autorRepository;
        this.autorService = autorService;
    }

    public void muestraMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ******************************************************
                    Elige la opción deseada
                                        
                    1- Buscar Libro
                    
                    2- Mostrar libros guardados
                    
                    3- Mostrar lista de autores
                    
                    4- Mostrar autores vivos en un año determinado
                    
                    5- Mostrar libros por idioma
                                       
                    0- Salir
                    ******************************************************
                    """;
            System.out.println(menu);
            String input = teclado.nextLine();
            try {
                opcion = Integer.parseInt(input);
                switch (opcion) {
                    case 1:
                        buscarLibroPorNombre();
                        break;
                    case 2:
                        mostrarLibrosGuardados();
                        break;
                    case 3:
                        mostrarAutoresGuardados();
                        break;
                    case 4:
                        mostarAutoresVivos();
                        break;
                    case 5:
                        mostrarLibrosPorIdioma();
                        break;
                    case 0:
                        System.out.println("Cerrando la aplicación...");
                        break;
                    default:
                        System.out.println("opción no valida");
                }
            }
            catch(NumberFormatException e){
                    System.out.println("Entrada inválida. Por favor, ingresa un número.");
                }
            }
        }


    private void buscarLibroPorNombre() {
        System.out.println("*****************************************************");
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombreLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        RepuestaApi repuestaApi = convierteDatos.obtenerDatos(json, RepuestaApi.class);
        var respuesta = repuestaApi.results();

        if (respuesta.isEmpty()) {
            System.out.println("No se encontraron libros con el nombre: " + nombreLibro);
        } else {

            DatosLibro datosLibro = respuesta.get(0);
            System.out.println("*****************************************************");
            System.out.println("Titulo: " + datosLibro.titulo());
            for (DatosAutor autor : datosLibro.autores()) {
                System.out.println("Autor: " + autor.nombre());
            }
            System.out.println("Idiomas: " + datosLibro.idiomas());
            System.out.println("Numero de descargas: " + datosLibro.numeroDeDescargas());
            System.out.println("*****************************************************");

            // Verificar si el libro ya existe en la base de datos por título
            Optional<Libro> libroEncontrado = libroRepository.findByTitulo(datosLibro.titulo());

            if (!libroEncontrado.isPresent()) {

                // El libro no existe, crear uno nuevo
                Libro libroNuevo = new Libro();
                libroNuevo.setTitulo(datosLibro.titulo());
                libroNuevo.setIdiomas(datosLibro.idiomas());
                libroNuevo.setNumeroDeDescargas(datosLibro.numeroDeDescargas());

                DatosAutor datosAutor = datosLibro.autores().get(0);
                List<Autor> autorEncontrados = autorRepository.findByNombre(datosAutor.nombre());

                Autor autor;
                if (!autorEncontrados.isEmpty()) {
                    autor = autorEncontrados.get(0);
                    System.out.println("El autor " + datosAutor.nombre() + " ya existe en la base de datos");
                } else {
                    autor = new Autor();
                    autor.setNombre(datosAutor.nombre());
                    autor.setNacimiento(datosAutor.nacimiento());
                    autor.setMuerte(datosAutor.muerte());
                }
                autor.getLibros().add(libroNuevo);
                libroNuevo.getAutores().add(autor);

                autorRepository.save(autor);
                libroRepository.save(libroNuevo);

            }
        }
        System.out.println("Presione enter para continar");
        teclado.nextLine();
    }

    private void mostrarLibrosGuardados() {
        List<Libro> libros = libroRepository.findAll();
        libros.forEach(l->
                System.out.println("Titulo: "+l.getTitulo()+"\n"+
                        "Autor: "+ l.getAutores().stream().map(Autor::getNombre).collect(Collectors.joining(","))+"\n"+
                        "Idioma: "+ l.getIdiomas()+"\n"+
                        "Numero de descargas: "+l.getNumeroDeDescargas()+"\n"+
                        "*****************************************************"));
        System.out.println("Presione enter para continar");
        teclado.nextLine();
    }
    private void mostrarAutoresGuardados() {
        List<Autor> autores = autorService.obtenerTodosLosAutores();
        if (autores.isEmpty()){
            System.out.println("No hay autores guardados en la base de datos");
        }else {
            for (Autor autor : autores){
                System.out.println("*****************************************************");
                System.out.println("Nombre: " + autor.getNombre());
                System.out.println( autor.getNacimiento() != null ? "Año de nacimiento: "+autor.getNacimiento() : "Desconocido");
                System.out.println( autor.getMuerte() != null ? "Año de muerte: "+ autor.getMuerte() : "Desconocido");

                List<Libro> libros = autor.getLibros().stream().toList();
                if (libros == null || libros.isEmpty()){
                    System.out.println("No hay libros guardados para este autor");
                }else {
                    System.out.println("Libros guardados de este autor:");
                    for (Libro libro : libros) {
                        System.out.println(" - Título: " + libro.getTitulo());
                    }
                }
            }
            System.out.println("Presione enter para continar");
            teclado.nextLine();
        }

    }
    private void mostarAutoresVivos() {
        Scanner teclado = new  Scanner(System.in);
        System.out.println("*****************************************************");
        System.out.println("Ingresa el año en donde quieres consultar los autores vivos");
        int anoIngresado = 0;
        boolean entradaValida = false;
        while (!entradaValida) {
            try {
                System.out.print("Año: ");
                anoIngresado = teclado.nextInt();
                teclado.nextLine(); // Limpiar el buffer
                entradaValida = true; // Salir del bucle si la entrada es válida
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número entero.");
                teclado.nextLine(); // Limpiar el buffer
            }
        }
        List<Autor> autoresFiltrados = autorRepository.autoresVivosEnAnoDeterminado(anoIngresado);
        if (autoresFiltrados.isEmpty()){
            System.out.println("No existen autores guardados vivos en el año "+anoIngresado);
        }else {
            System.out.println("*****************************************************");
            System.out.println("*******" + "Autores vivos en el año " + anoIngresado + "*******");
            autoresFiltrados.forEach(a -> System.out.println("Nombre: " + a.getNombre() + "\n" +
                    "Año de nacimiento: " + a.getNacimiento() + "\n" +
                    "Año de muerte: " + a.getMuerte() + "\n" +
                    "*****************************************************"));
        }
        System.out.println("*****************************************************");
        System.out.println("Presione enter para continar");
        teclado.nextLine();
    }

    private void mostrarLibrosPorIdioma() {
        var menuIdiomas = """
                Ingresa la clave para el idioma en el que deseas buscar libros
                
                en- Ingles
                
                es- Español
                
                fr- Frances
                """;
        System.out.println(menuIdiomas);
        String idiomaIngresado = teclado.nextLine();
        while (!idiomaIngresado.equals("en") && !idiomaIngresado.equals("es") && !idiomaIngresado.equals("fr")) {
            System.out.println("Clave inválida. Por favor, ingresa una de las siguientes claves: en, es, fr.");
            //System.out.println(menuIdiomas);
            idiomaIngresado = teclado.nextLine();
        }
        List<Libro> librosFiltrados = libroRepository.librosPorDeterminadoIdioma(idiomaIngresado);
        if (librosFiltrados.isEmpty()){
            System.out.println("No existen libros guardados para el idioma: "+idiomaIngresado);
        }else {
            System.out.println("*****************************************************");
            librosFiltrados.forEach(l-> System.out.println("Titulo: "+ l.getTitulo()+"\n"+
                    "Autor: "+ l.getAutores().stream().map(Autor::getNombre).collect(Collectors.joining(","))+"\n"+
                    "Idioma: "+l.getIdiomas()+"\n"+
                    "Numero de descargas: "+l.getNumeroDeDescargas()+"\n"+
                    "*****************************************************"));
        }
        System.out.println("Presione enter para continar");
        teclado.nextLine();
    }


    public DatosLibro getDatosLibros(){
        var json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println(json);
        DatosLibro datosLibros = convierteDatos.obtenerDatos(json, DatosLibro.class);
        return datosLibros;
    }

}
