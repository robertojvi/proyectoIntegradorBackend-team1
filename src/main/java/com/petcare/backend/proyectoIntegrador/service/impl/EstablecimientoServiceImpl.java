package com.petcare.backend.proyectoIntegrador.service.impl;

import com.petcare.backend.proyectoIntegrador.entity.Establecimiento;
import com.petcare.backend.proyectoIntegrador.repository.IEstablecimientoRepository;
import com.petcare.backend.proyectoIntegrador.service.IEstablecimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EstablecimientoServiceImpl implements IEstablecimientoService {

    @Autowired
    private IEstablecimientoRepository establecimientoRepository;
    
    @Override
    public Establecimiento crear(Establecimiento establecimiento) {
        establecimiento.setFechaRegistro(LocalDateTime.now());
        establecimiento.setFechaActualizacion(LocalDateTime.now());
        establecimiento.setEsBorrado(false);
        establecimiento.setEstado("ACTIVO"); // Valor por defecto al crear
        if (establecimiento.getHorario() == null) {
            establecimiento.setHorario("Lun-Vie: 09:00-18:00"); // Horario por defecto
        }
        return establecimientoRepository.save(establecimiento);
    }
    
    @Override
    public Optional<Establecimiento> obtenerPorId(Short id) {
        return establecimientoRepository.findById(id);
    }
    
    @Override
    public List<Establecimiento> listarTodos() {
        return establecimientoRepository.findActivos();
    }
    
    @Override
    public List<Establecimiento> buscarPorNombre(String nombre) {
        return establecimientoRepository.findByNombre(nombre);
    }
    
    @Override
    public List<Establecimiento> buscarPorDireccion(String direccion) {
        return establecimientoRepository.findByDireccion(direccion);
    }
    
    @Override
    public Establecimiento actualizar(Establecimiento establecimiento) {
        establecimiento.setFechaActualizacion(LocalDateTime.now());
        return establecimientoRepository.save(establecimiento);
    }
    
    @Override
    public void eliminar(Short id) {
        Optional<Establecimiento> establecimiento = establecimientoRepository.findById(id);
        if (establecimiento.isPresent()) {
            Establecimiento e = establecimiento.get();
            e.setEsBorrado(true);
            e.setFechaBorrado(LocalDateTime.now());
            establecimientoRepository.save(e);
        }
    }

    @Override
    public List<Establecimiento> listarPorUsuario(Short usuarioId) {
        return establecimientoRepository.findByUsuarioId(usuarioId);
    }
} 