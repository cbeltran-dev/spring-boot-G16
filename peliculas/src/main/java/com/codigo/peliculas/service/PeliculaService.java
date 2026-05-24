package com.codigo.peliculas.service;

import com.codigo.peliculas.dto.PeliculaRequestDTO;
import com.codigo.peliculas.dto.PeliculaResponseDTO;
import com.codigo.peliculas.mapper.PeliculaMapper;
import com.codigo.peliculas.model.Pelicula;
import com.codigo.peliculas.repository.PeliculaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeliculaService {

    private final PeliculaRepository peliculaRepository;



    public PeliculaService(PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }


    public String bienvenida(){
        String saludo = "Bienvenido a la API de Streaming desde Service";
        return saludo;

    }

    public List<Pelicula> listar(){
        List<Pelicula> respuesta = peliculaRepository.listar();
        return respuesta;
    }

    public PeliculaResponseDTO guardar(PeliculaRequestDTO dto){
        Pelicula pelicula = PeliculaMapper.toModel(dto);
        Pelicula peliGuardada = peliculaRepository.guardar(pelicula);
        return PeliculaMapper.toResponse(peliGuardada);

    }

}
