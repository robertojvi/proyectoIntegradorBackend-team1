package com.petcare.backend.proyectoIntegrador.service;

import com.petcare.backend.proyectoIntegrador.entity.Establecimiento;
import java.util.List;
import java.util.Optional;

public interface IEstablecimientoService {
    Establecimiento crear(Establecimiento establecimiento);
    Optional<Establecimiento> obtenerPorId(Short id);
    List<Establecimiento> listarTodos();
    List<Establecimiento> buscarPorNombre(String nombre);
    List<Establecimiento> buscarPorDireccion(String direccion);
    Establecimiento actualizar(Establecimiento establecimiento);
    void eliminar(Short id);
} 