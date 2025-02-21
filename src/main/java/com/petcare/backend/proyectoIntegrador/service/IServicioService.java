package com.petcare.backend.proyectoIntegrador.service;

import com.petcare.backend.proyectoIntegrador.entity.Servicio;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IServicioService {
    Servicio crear(Servicio servicio);
    Optional<Servicio> obtenerPorId(Short id);
    List<Servicio> listarTodos();
    List<Servicio> buscarPorNombre(String nombre);
    List<Servicio> buscarPorDisponibilidad(String disponibilidad);
    List<Servicio> buscarPorPrecioMenorIgual(BigDecimal precioMaximo);
    Servicio actualizar(Servicio servicio);
    void eliminar(Short id);
} 