package com.petcare.backend.proyectoIntegrador.controller;

import com.petcare.backend.proyectoIntegrador.DTO.CategoriaResponse;
import com.petcare.backend.proyectoIntegrador.DTO.ServicioResponse;
import com.petcare.backend.proyectoIntegrador.DTO.ServicioSinCategoriaResponse;
import com.petcare.backend.proyectoIntegrador.entity.Categoria;
import com.petcare.backend.proyectoIntegrador.service.ICategoriaService;
import com.petcare.backend.proyectoIntegrador.util.DtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private ICategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> obtenerTodasLasCategorias() {
        List<Categoria> categorias = categoriaService.obtenerTodasLasCategorias();
        List<CategoriaResponse> respuestas = categorias.stream()
                .map(this::convertirCategoriaARespuesta)
                .collect(Collectors.toList());
        return new ResponseEntity<>(respuestas, HttpStatus.OK);
    }

    private CategoriaResponse convertirCategoriaARespuesta(Categoria categoria) {
        List<ServicioSinCategoriaResponse> servicios = categoria.getServicios().stream()
                .map(DtoConverter::convertirARespuestaSinCategoria)
                .collect(Collectors.toList());
        return new CategoriaResponse(categoria.getIdCategoria(), categoria.getNombre(), servicios);
    }

    @PostMapping
    public ResponseEntity<Categoria> crearCategoria(@RequestBody Categoria categoria) {
        Categoria nuevaCategoria = categoriaService.crearCategoria(categoria);
        return new ResponseEntity<>(nuevaCategoria, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> obtenerCategoriaPorId(@PathVariable Long id) {
        Categoria categoria = categoriaService.obtenerCategoriaPorId(id);
        if (categoria != null) {
            CategoriaResponse respuesta = convertirCategoriaARespuesta(categoria);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        Categoria categoriaExistente = categoriaService.obtenerCategoriaPorId(id);
        if (categoriaExistente != null) {
            categoria.setIdCategoria(id);
            Categoria categoriaActualizada = categoriaService.crearCategoria(categoria);
            return new ResponseEntity<>(categoriaActualizada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        Categoria categoria = categoriaService.obtenerCategoriaPorId(id);
        if (categoria != null) {
            categoriaService.eliminarCategoria(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
