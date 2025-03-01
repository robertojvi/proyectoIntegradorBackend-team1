package com.petcare.backend.proyectoIntegrador.DTO;

import com.petcare.backend.proyectoIntegrador.entity.Categoria;
import lombok.*;

import java.math.BigDecimal;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ServicioRequest {
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Categoria categoria;

}