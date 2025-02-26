package com.petcare.backend.proyectoIntegrador.DTO;

import com.petcare.backend.proyectoIntegrador.entity.Reserva;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservaResponse {
    private short idReserva;
    private String servicio;
    private LocalDateTime fecha;

    // ✅ Constructor que recibe un objeto Reserva
    public ReservaResponse(Reserva reserva) {
        this.idReserva = reserva.getIdReserva();
        this.servicio = reserva.getServicio().getNombre();
        this.fecha = reserva.getFecha();
    }


    public short getId() { return idReserva; }
    public void setId(short id) { this.idReserva = id; }

}
