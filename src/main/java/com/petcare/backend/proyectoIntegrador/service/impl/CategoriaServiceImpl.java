package com.petcare.backend.proyectoIntegrador.service.impl;

import com.petcare.backend.proyectoIntegrador.entity.Categoria;
import com.petcare.backend.proyectoIntegrador.repository.ICategoriaRepository;
import com.petcare.backend.proyectoIntegrador.service.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements ICategoriaService {

    @Autowired
    private ICategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> obtenerTodasLasCategorias() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria crearCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public Categoria obtenerCategoriaPorId(Long id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }
}
