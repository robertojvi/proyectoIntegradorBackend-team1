package com.petcare.backend.proyectoIntegrador.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoriaResponse {
    private Long idCategoria;
    private String nombre;
    private List<ServicioSinCategoriaResponse> servicios;

    public CategoriaResponse(Long idCategoria, String nombre, List<ServicioSinCategoriaResponse> servicios) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.servicios = servicios;
    }
}

