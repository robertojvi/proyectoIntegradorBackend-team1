package com.petcare.backend.proyectoIntegrador.controller;

import com.petcare.backend.proyectoIntegrador.entity.Reserva;
import com.petcare.backend.proyectoIntegrador.service.IReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reservas")
@CrossOrigin(origins = "*")
public class ReservaController {

    @Autowired
    private IReservaService reservaService;

    @PostMapping
    public ResponseEntity<Reserva> crear(@RequestBody Reserva reserva) {
        return new ResponseEntity<>(reservaService.crear(reserva), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerPorId(@PathVariable Short id) {
        return reservaService.obtenerPorId(id)
                .map(reserva -> new ResponseEntity<>(reserva, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Reserva>> listarTodos() {
        return new ResponseEntity<>(reservaService.listarTodos(), HttpStatus.OK);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Reserva>> listarPorUsuario(@PathVariable Short usuarioId) {
        return new ResponseEntity<>(reservaService.listarPorUsuario(usuarioId), HttpStatus.OK);
    }

    @GetMapping("/mascota/{mascotaId}")
    public ResponseEntity<List<Reserva>> listarPorMascota(@PathVariable Short mascotaId) {
        return new ResponseEntity<>(reservaService.listarPorMascota(mascotaId), HttpStatus.OK);
    }

    @GetMapping("/establecimiento/{establecimientoId}")
    public ResponseEntity<List<Reserva>> listarPorEstablecimiento(@PathVariable Short establecimientoId) {
        return new ResponseEntity<>(reservaService.listarPorEstablecimiento(establecimientoId), HttpStatus.OK);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Reserva>> listarPorEstado(@PathVariable String estado) {
        return new ResponseEntity<>(reservaService.listarPorEstado(estado), HttpStatus.OK);
    }

    @GetMapping("/fecha")
    public ResponseEntity<List<Reserva>> buscarPorRangoFechas(
            @RequestParam LocalDateTime fechaInicio,
            @RequestParam LocalDateTime fechaFin) {
        return new ResponseEntity<>(
            reservaService.buscarPorRangoFechas(fechaInicio, fechaFin),
            HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reserva> actualizar(@PathVariable Short id, @RequestBody Reserva reserva) {
        return reservaService.obtenerPorId(id)
                .map(reservaExistente -> {
                    reserva.setIdReserva(id);
                    return new ResponseEntity<>(reservaService.actualizar(reserva), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Short id) {
        if (reservaService.obtenerPorId(id).isPresent()) {
            reservaService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
} 