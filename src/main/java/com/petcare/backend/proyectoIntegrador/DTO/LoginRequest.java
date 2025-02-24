package com.petcare.backend.proyectoIntegrador.DTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String email;
    private String contrasenia;
}
