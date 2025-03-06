package com.petcare.backend.proyectoIntegrador.service.impl;

import com.petcare.backend.proyectoIntegrador.entity.Categoria;
import com.petcare.backend.proyectoIntegrador.entity.Servicio;
import com.petcare.backend.proyectoIntegrador.repository.ICategoriaRepository;
import com.petcare.backend.proyectoIntegrador.repository.IServicioRepository;
import com.petcare.backend.proyectoIntegrador.service.IServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioServiceImpl implements IServicioService {

    private final IServicioRepository servicioRepository;
    private final ICategoriaRepository categoriaRepository;

    ServicioServiceImpl(IServicioRepository servicioRepository, ICategoriaRepository categoriaRepository) {
        this.servicioRepository = servicioRepository;
        this.categoriaRepository = categoriaRepository;
    }
    
    @Override
    public Servicio crear(Servicio servicio) {
        servicio.setFechaRegistro(LocalDateTime.now());
        servicio.setFechaActualizacion(LocalDateTime.now());
        servicio.setEsBorrado(false);
        servicio.setDisponibilidad("DISPONIBLE");
        return servicioRepository.save(servicio);
    }
    
    @Override
    public Optional<Servicio> obtenerPorId(Short id) {
        return servicioRepository.findById(id);
    }
    
    @Override
    public List<Servicio> listarTodos() {
        return servicioRepository.findActivos();
    }

    public Servicio asignarCategoria(short idServicio, Long categoriaId) {
        Servicio servicio = servicioRepository.findById(idServicio).orElseThrow();
        Categoria categoria = categoriaRepository.findById(categoriaId).orElseThrow();
        servicio.setCategoria(categoria);
        return servicioRepository.save(servicio);
    }

    @Override
    public List<Servicio> filtrarServicios(String categoria) {
        return servicioRepository.findByCategoria(categoria);
    }

    @Override
    public void calificarServicio(short id, int calificacion) {
        // Validar que la calificación esté en el rango permitido (1 a 5)
        if (calificacion < 1 || calificacion > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5");
        }

        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

        // Calcular el nuevo rating
        double nuevoRating;
        if (servicio.getTotalValoraciones() == 0) nuevoRating = calificacion;
        else
            nuevoRating = (servicio.getRating() * servicio.getTotalValoraciones() + calificacion) / (servicio.getTotalValoraciones() + 1);

        servicio.setTotalValoraciones(servicio.getTotalValoraciones() + 1);
        servicio.setRating(nuevoRating);

        servicioRepository.save(servicio);
    }



        @Override
    public List<Servicio> buscarPorNombre(String nombre) {
        return servicioRepository.findByNombre(nombre);
    }
    
    @Override
    public List<Servicio> buscarPorDisponibilidad(String disponibilidad) {
        return servicioRepository.findByDisponibilidad(disponibilidad);
    }
    
    @Override
    public List<Servicio> buscarPorPrecioMenorIgual(BigDecimal precioMaximo) {
        return servicioRepository.findByPrecioLessThanEqual(precioMaximo);
    }
    
    @Override
    public Servicio actualizar(Servicio servicio) {
        servicio.setFechaActualizacion(LocalDateTime.now());
        return servicioRepository.save(servicio);
    }
    
    @Override
    public void eliminar(Short id) {
        Optional<Servicio> servicio = servicioRepository.findById(id);
        if (servicio.isPresent()) {
            Servicio s = servicio.get();
            s.setEsBorrado(true);
            s.setFechaBorrado(LocalDateTime.now());
            servicioRepository.save(s);
        }
    }
} 