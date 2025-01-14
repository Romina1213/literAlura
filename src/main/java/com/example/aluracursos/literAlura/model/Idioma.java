package com.example.aluracursos.literAlura.model;

public enum Idioma {
    ESPAÑOL("es"),
    INGLES("en"),
    PORTUGUES("pt");

    private String idioma;
    Idioma(String idiomas){
        this.idioma = idiomas;
    }
    public String getIdioma() {
        return idioma;
    }
    public static Idioma fromString(String idioma) {
        for (Idioma idiomaEnum : Idioma.values()) {
            if (idiomaEnum.getIdioma().equals(idioma)) {
                return idiomaEnum;
            }
        }
        return null;
    }

}
