package com.petcare.backend.proyectoIntegrador.controller;

import com.petcare.backend.proyectoIntegrador.entity.Establecimiento;
import com.petcare.backend.proyectoIntegrador.service.IEstablecimientoService;
import com.petcare.backend.proyectoIntegrador.service.impl.EstablecimientoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/establecimientos")
@CrossOrigin(origins = "*")
public class EstablecimientoController {

    private final IEstablecimientoService establecimientoService;

    EstablecimientoController() {
        this.establecimientoService = new EstablecimientoServiceImpl();
    }

    @PostMapping
    public ResponseEntity<Establecimiento> crear(@RequestBody Establecimiento establecimiento) {
        return new ResponseEntity<>(establecimientoService.crear(establecimiento), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Establecimiento> obtenerPorId(@PathVariable Short id) {
        return establecimientoService.obtenerPorId(id)
                .map(establecimiento -> new ResponseEntity<>(establecimiento, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Establecimiento>> listarTodos() {
        return new ResponseEntity<>(establecimientoService.listarTodos(), HttpStatus.OK);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Establecimiento>> buscarPorNombre(@PathVariable String nombre) {
        return new ResponseEntity<>(establecimientoService.buscarPorNombre(nombre), HttpStatus.OK);
    }

    @GetMapping("/direccion/{direccion}")
    public ResponseEntity<List<Establecimiento>> buscarPorDireccion(@PathVariable String direccion) {
        return new ResponseEntity<>(establecimientoService.buscarPorDireccion(direccion), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Establecimiento> actualizar(@PathVariable Short id, @RequestBody Establecimiento establecimiento) {
        return establecimientoService.obtenerPorId(id)
                .map(establecimientoExistente -> {
                    establecimiento.setIdEstablecimiento(id);
                    establecimiento.setFechaRegistro(establecimientoExistente.getFechaRegistro());
                    establecimiento.setEsBorrado(establecimientoExistente.isEsBorrado());
                    establecimiento.setFechaBorrado(establecimientoExistente.getFechaBorrado());
                    return new ResponseEntity<>(establecimientoService.actualizar(establecimiento), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Short id) {
        if (establecimientoService.obtenerPorId(id).isPresent()) {
            establecimientoService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
} 