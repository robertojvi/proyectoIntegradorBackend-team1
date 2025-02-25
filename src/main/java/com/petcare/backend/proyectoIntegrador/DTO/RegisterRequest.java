package com.petcare.backend.proyectoIntegrador.DTO;

import com.petcare.backend.proyectoIntegrador.entity.ERole;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String nombre;
    private String apellido;
    private String email;
    private String contrasenia;
    private ERole rol;
    private String telefono;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaActualizacion;
    private LocalDateTime fechaBorrado;
    private boolean esBorrado;
}
