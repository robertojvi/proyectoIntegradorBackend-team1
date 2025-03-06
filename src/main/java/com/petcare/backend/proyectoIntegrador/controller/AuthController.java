package com.petcare.backend.proyectoIntegrador.controller;

import com.petcare.backend.proyectoIntegrador.DTO.AuthResponse;
import com.petcare.backend.proyectoIntegrador.DTO.EnviarConfirmacionRequest;
import com.petcare.backend.proyectoIntegrador.DTO.LoginRequest;
import com.petcare.backend.proyectoIntegrador.DTO.RegisterRequest;
import com.petcare.backend.proyectoIntegrador.service.IAuthService;
import com.petcare.backend.proyectoIntegrador.util.TemplateLoader;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final IAuthService authService;

    AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/enviar-confirmacion")
    public ResponseEntity<?> enviarConfirmacion(@RequestBody EnviarConfirmacionRequest request) throws Exception {
        String htmlContent = TemplateLoader.loadTemplate("confirmacion.html");

        // Reemplazar valores dinámicos
        htmlContent = htmlContent.replace("{{username}}", request.getUsername());
        htmlContent = htmlContent.replace("{{url}}", request.getUrl());

        authService.enviarCorreoConfirmacion(request.getEmail(), "Bienvenid@", htmlContent);

        return ResponseEntity.ok("Correo enviado");
    }

    @GetMapping("/env")
    public String getEnv() {
        return "AWS_ACCESS_KEY_ID: " + System.getenv("AWS_ACCESS_KEY_ID") + ", AWS_SECRET_ACCESS_KEY: " + System.getenv("AWS_SECRET_ACCESS_KEY");
    }
}
