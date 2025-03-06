package com.petcare.backend.proyectoIntegrador.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnviarConfirmacionRequest {
    @Email
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    private String url;
}
