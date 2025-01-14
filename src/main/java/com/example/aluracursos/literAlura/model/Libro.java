package com.example.aluracursos.literAlura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "Libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true)
    private String titulo;

    @Enumerated(EnumType.STRING)
    private Idioma idioma;
    @ManyToOne
    private Autor autor;
    private String nombreAutor;
    private Double numeroDescargas;
    public Libro(){}
    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.autor = autor;
        this.idioma = Idioma.fromString(datosLibro.idioma().get(0));
        this.numeroDescargas = datosLibro.numeroDeDescargas();
        this.nombreAutor = autor.getNombre();
    }


    @Override
    public String toString() {
        return
                "Titulo=" + titulo +
                "Autor=" + autor +
                "Idioma=" + idioma +
                "NumeroDescargas=" + numeroDescargas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Double getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Double numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }
}
