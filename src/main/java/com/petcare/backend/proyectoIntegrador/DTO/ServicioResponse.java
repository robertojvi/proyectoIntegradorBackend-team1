package com.petcare.backend.proyectoIntegrador.DTO;

import com.petcare.backend.proyectoIntegrador.entity.Servicio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServicioResponse {
    private short idServicio;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;

    public ServicioResponse(Servicio servicio) {
        this.idServicio = servicio.getIdServicio();
        this.nombre = servicio.getNombre();
        this.descripcion = servicio.getDescripcion();
        this.precio = servicio.getPrecio();
    }
}
