package com.codigo.peliculas.controller;

import com.codigo.peliculas.dto.PeliculaRequestDTO;
import com.codigo.peliculas.dto.PeliculaResponseDTO;
import com.codigo.peliculas.model.Pelicula;
import com.codigo.peliculas.repository.PeliculaRepository;
import com.codigo.peliculas.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/pelicula")
public class PeliculaController {

    private final PeliculaService peliculaService;

    public PeliculaController(PeliculaService peliculaService) {
        this.peliculaService = peliculaService;
    }

    @GetMapping("/bienvenida")
    public String bienvenida(){
        return peliculaService.bienvenida();
    }

    @GetMapping("/listar")
    public List<Pelicula> listar(){
        List<Pelicula> respuesta = peliculaService.listar();
        return respuesta;
    }

    @PostMapping("/guardar")
    public ResponseEntity<PeliculaResponseDTO>  guardar(@RequestBody PeliculaRequestDTO dto){
        PeliculaResponseDTO respuesta = peliculaService.guardar(dto);
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(respuesta);
    }
}
