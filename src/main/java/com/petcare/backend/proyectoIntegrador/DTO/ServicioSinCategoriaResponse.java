package com.petcare.backend.proyectoIntegrador.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ServicioSinCategoriaResponse {
    private short idServicio;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private List<String> imagenUrls;
    private String disponibilidad;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaActualizacion;
    private boolean esBorrado;
    private LocalDateTime fechaBorrado;
    private double rating;

    public ServicioSinCategoriaResponse(short idServicio, String nombre, String descripcion, BigDecimal precio, List<String> imagenUrls, String disponibilidad, LocalDateTime fechaRegistro, LocalDateTime fechaActualizacion, boolean esBorrado, LocalDateTime fechaBorrado, Double rating) {
        this.idServicio = idServicio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagenUrls = imagenUrls;
        this.disponibilidad = disponibilidad;
        this.fechaRegistro = fechaRegistro;
        this.fechaActualizacion = fechaActualizacion;
        this.esBorrado = esBorrado;
        this.fechaBorrado = fechaBorrado;
        this.rating = rating;
    }
}
