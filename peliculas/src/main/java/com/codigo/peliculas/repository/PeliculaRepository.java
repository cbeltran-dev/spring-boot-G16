package com.codigo.peliculas.repository;

import com.codigo.peliculas.model.Pelicula;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PeliculaRepository {

    private final List<Pelicula> peliculas = new ArrayList<>();
    private Integer contadorId = 1;

    public PeliculaRepository(){
        peliculas.add(new Pelicula(contadorId++, "El padrino", 1972, 150));
        peliculas.add(new Pelicula(contadorId++, "El caballero oscuro", 2008, 150));
        peliculas.add(new Pelicula(contadorId++, "La lista de Schindler",1993, 150));
    }

    public List<Pelicula> listar(){
        return peliculas;
    }

    public Pelicula buscarPorId(Integer id){
        for(Pelicula p : peliculas){
            if (p.getId().equals(id)){
                return p;
            }
        }
        return null;
    }

    public Pelicula guardar(Pelicula pelicula){
        pelicula.setId(contadorId++);
        peliculas.add(pelicula);
        return pelicula;
    }

    public Pelicula actualizar(Pelicula pelicula){
        for (Pelicula p : peliculas){
            if (p.getId().equals(pelicula.getId())){
                p.setTitulo(pelicula.getTitulo());
                p.setAnio(pelicula.getAnio());
                p.setDuracion(pelicula.getDuracion());
                return p;
            }

        }
        return null;
    }

    public void eliminar(Integer id){
        Pelicula pelicula = buscarPorId(id);
        if (pelicula != null){
            peliculas.remove(pelicula);
        }
    }
}
