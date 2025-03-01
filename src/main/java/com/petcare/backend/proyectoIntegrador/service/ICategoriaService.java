package com.petcare.backend.proyectoIntegrador.service;

import com.petcare.backend.proyectoIntegrador.entity.Categoria;

import java.util.List;

public interface ICategoriaService {

    List<Categoria> obtenerTodasLasCategorias();
    Categoria crearCategoria(Categoria categoria);
    Categoria obtenerCategoriaPorId(Long id);
    void eliminarCategoria(Long id);
}
