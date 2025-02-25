package com.petcare.backend.proyectoIntegrador.controller;

import com.petcare.backend.proyectoIntegrador.entity.Servicio;
import com.petcare.backend.proyectoIntegrador.service.IServicioService;
import com.petcare.backend.proyectoIntegrador.service.impl.ServicioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/servicios")
@CrossOrigin(origins = "*")
public class ServicioController {

    private final IServicioService servicioService;

    ServicioController(ServicioServiceImpl servicioService) {
        this.servicioService = servicioService;
    }

    @PostMapping
    public ResponseEntity<Servicio> crear(@RequestBody Servicio servicio) {
        System.out.println("Recibiendo petición POST en /servicios");
        System.out.println("Servicio recibido: " + servicio);
        
        // Validaciones básicas
        if (servicio.getNombre() == null || servicio.getNombre().trim().isEmpty()) {
            System.out.println("Error: nombre vacío");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (servicio.getPrecio() == null || servicio.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        try {
            Servicio nuevoServicio = servicioService.crear(servicio);
            return new ResponseEntity<>(nuevoServicio, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servicio> obtenerPorId(@PathVariable Short id) {
        return servicioService.obtenerPorId(id)
                .map(servicio -> new ResponseEntity<>(servicio, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Servicio>> listarTodos() {
        return new ResponseEntity<>(servicioService.listarTodos(), HttpStatus.OK);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Servicio>> buscarPorNombre(@PathVariable String nombre) {
        return new ResponseEntity<>(servicioService.buscarPorNombre(nombre), HttpStatus.OK);
    }

    @GetMapping("/disponibilidad/{disponibilidad}")
    public ResponseEntity<List<Servicio>> buscarPorDisponibilidad(@PathVariable String disponibilidad) {
        return new ResponseEntity<>(servicioService.buscarPorDisponibilidad(disponibilidad), HttpStatus.OK);
    }

    @GetMapping("/precio/{precioMaximo}")
    public ResponseEntity<List<Servicio>> buscarPorPrecioMenorIgual(@PathVariable BigDecimal precioMaximo) {
        return new ResponseEntity<>(servicioService.buscarPorPrecioMenorIgual(precioMaximo), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servicio> actualizar(@PathVariable Short id, @RequestBody Servicio servicio) {
        return servicioService.obtenerPorId(id)
                .map(servicioExistente -> {
                    servicio.setIdServicio(id);
                    return new ResponseEntity<>(servicioService.actualizar(servicio), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Short id) {
        if (servicioService.obtenerPorId(id).isPresent()) {
            servicioService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
} 