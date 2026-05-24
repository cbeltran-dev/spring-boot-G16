package com.codigo.peliculas.mapper;

import com.codigo.peliculas.dto.PeliculaRequestDTO;
import com.codigo.peliculas.dto.PeliculaResponseDTO;
import com.codigo.peliculas.model.Pelicula;

public class PeliculaMapper {

    public static PeliculaResponseDTO toResponse (Pelicula pelicula){
        PeliculaResponseDTO response = new PeliculaResponseDTO();
        response.setId(pelicula.getId());
        response.setTitulo(pelicula.getTitulo());
        response.setAnio(pelicula.getAnio());
        response.setDuracion(pelicula.getDuracion());
        response.setMensaje("Pelicula Creada");
        return response;
    }

    public static Pelicula toModel(PeliculaRequestDTO requestDTO){
        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo(requestDTO.getTitulo());
        pelicula.setAnio(requestDTO.getAnio());
        pelicula.setDuracion(requestDTO.getDuracion());
        return pelicula;
    }
}
