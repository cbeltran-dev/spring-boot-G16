package com.codigo.peliculas.model;

public class Pelicula {

    private Integer id;
    private String titulo;
    private Integer duracion;
    private Integer anio;

    public Pelicula(){}

    public Pelicula(Integer id, String titulo,Integer anio, Integer duracion) {
        this.id = id;
        this.duracion = duracion;
        this.titulo = titulo;
        this.anio = anio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }
}
