package com.example.aluracursos.literAlura.principal;

import com.example.aluracursos.literAlura.model.*;
import com.example.aluracursos.literAlura.repository.AutorRepository;
import com.example.aluracursos.literAlura.repository.LibroRepository;
import com.example.aluracursos.literAlura.service.ConvierteDatos;
import com.example.aluracursos.literAlura.service.CosumoAPI;

import java.util.*;

public class Principal {
    private CosumoAPI consumoApi = new CosumoAPI();
    private Scanner teclado = new Scanner(System.in);
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos convertir = new ConvierteDatos();
    private LibroRepository libroRepository;
    private List<Libro> listaLibros;
    private AutorRepository autorRepository;
    private List<Autor> listaAutores;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository ) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraMenu(){
        var opcion = -1;
        while(opcion != 0) {
            String menu = """
                    Elija la opcion a travez de su numero:
                    1- buscar libro por titulo
                    2- listar libros registrados
                    3- listar autores registrados
                    4- listar autores vivos en un determinado año
                    5- listar libros por idioma
                    0- salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();
            switch (opcion){
                case 1 :
                    buscarLibroPorTitulo();
                    break;
                case 2 :
                    listarLibrosRegistrados();
                    break;
                case 3 :
                    listarAutoresRegistrados();
                    break;
                case 4 :
                    listarAutoresVivosSegunAnio();
                    break;
                case 5 :
                    listarLibrosPorIdioma();
                case 0 :
                    System.out.println("Finalizando programa...");
                    break;
                default:
                    System.out.println("La opcion ingresada no existe, vuelva a intentarlo");
            }
        }
    }

    private Datos getDatosLibro() {
        System.out.println("Ingrese el nombre del libro que desea buscar");
        var titulo = teclado.nextLine();
        var url = URL_BASE + "?search=" + titulo.replace(" ", "+");
        var json = consumoApi.obtenerDatos(url);
        Datos datos = convertir.obtenerDatos(json, Datos.class);
        return datos;
    }

    private void buscarLibroPorTitulo()
    {
        Datos datosLibro = getDatosLibro();

        try {
            Libro libro = new Libro(datosLibro.resultados().get(0));
            Autor author = new Autor(datosLibro.resultados().get(0).autor().get(0));

            System.out.println("""
                    Datos  del  libro=
                            Titulo: %s
                            Autor: %s
                            Idioma: %s
                            Descargas: %s
                    """.formatted(libro.getTitulo(),
                    libro.getAutor(),
                    libro.getIdioma(),
                    libro.getNumeroDescargas().toString()));

            libroRepository.save(libro);
            autorRepository.save(author);

        }catch (Exception e){
            System.out.println("No se encontró el libro");
        }

    }

    private void listarLibrosRegistrados() {
        listaLibros = libroRepository.findAll();
        listaLibros.stream().forEach(l -> {
            System.out.println("""
                    =======================================
                    =          Datos  del  libro          =
                    =======================================

                            Titulo: %s
                            Autor: %s
                            Idioma: %s
                            Descargas: %s
                    """.formatted(l.getTitulo(),
                    l.getAutor(),
                    l.getIdioma(),
                    l.getNumeroDescargas().toString()));
        });
    }

    private void listarAutoresRegistrados() {
        listaAutores = autorRepository.findAll();
        listaAutores.stream().forEach(a -> {
            System.out.println("""
                            Autor: %s
                            Año de Nacimiento: %s
                            Año de Muerte: %s
                        """.formatted(a.getNombre(),
                    a.getAnioNacimiento().toString(),
                    a.getAnioFallecimiento().toString()));
        });
    }

    public void listarAutoresVivosSegunAnio()
    {
        System.out.println("Ingresa el año::");
        var anio = teclado.nextInt();
        teclado.nextLine();

        List<Autor> authors = autorRepository.autorPorFecha(anio);
        authors.forEach( a -> {
            System.out.println("""
                        Nombre de autor: %s
                        Fecha de nacimiento: %s
                        Fecha de fallecimiento: %s
                        """.formatted(a.getNombre(),a.getAnioNacimiento().toString(),a.getAnioFallecimiento().toString()));
        });
    }


    private void listarLibrosPorIdioma()
    {
        System.out.println("""
                   Selecciona el Idioma de los libros que desea consultar: 
                   1 - Ingles(EN)
                   2 - Español(ES)
                   """);

        try {
            var opcion2 = teclado.nextInt();
            teclado.nextLine();

            switch (opcion2) {
                case 1:
                    listaLibros = libroRepository.findByLenguaje("en");
                    break;
                case 2:
                    listaLibros = libroRepository.findByLenguaje("es");
                    break;

                default:
                    System.out.println("Por favor ingresa una opcion valida");
            }

            listaLibros.stream().forEach(l -> {
                System.out.println("""    
                            Titulo: %s
                            Autor: %s
                            Idioma: %s
                            Numero de descargas: %s
                        """.formatted(l.getTitulo(),
                        l.getAutor(),
                        l.getIdioma(),
                        l.getNumeroDescargas().toString()));
            });

        } catch (Exception e){
            System.out.println("Por favor ingresa un valor válido");
        }
    }
}
