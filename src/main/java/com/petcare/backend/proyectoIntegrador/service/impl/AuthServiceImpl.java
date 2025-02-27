package com.petcare.backend.proyectoIntegrador.service.impl;

import com.petcare.backend.proyectoIntegrador.DTO.AuthResponse;
import com.petcare.backend.proyectoIntegrador.DTO.LoginRequest;
import com.petcare.backend.proyectoIntegrador.DTO.RegisterRequest;
import com.petcare.backend.proyectoIntegrador.config.JwtService;
import com.petcare.backend.proyectoIntegrador.entity.ERole;
import com.petcare.backend.proyectoIntegrador.entity.Usuario;
import com.petcare.backend.proyectoIntegrador.service.IAuthService;
import com.petcare.backend.proyectoIntegrador.service.IUsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthServiceImpl implements IAuthService {

    private final IUsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public AuthServiceImpl(IUsuarioService usuarioService, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (usuarioService.buscarPorEmail(request.getEmail()).isPresent()) {
            return new AuthResponse(null, null, null, null, null, "El correo ya está registrado.");
        }

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .contrasenia(passwordEncoder.encode(request.getContrasenia()))
                .role(request.getRole() != null ? request.getRole() : ERole.CLIENTE)
                .telefono(request.getTelefono())
                .fechaRegistro(LocalDateTime.now())
                .build();

        usuarioService.crear(usuario);
        String token = jwtService.generateToken(usuario.getEmail());

        return new AuthResponse(token, usuario.getRole(), usuario.getNombre(), usuario.getApellido(), "Usuario creado correctamente", null);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Optional<Usuario> usuario = usuarioService.buscarPorEmail(request.getEmail());
        if (usuario.isEmpty()) {
            return new AuthResponse(null, null, null, null, null, "Usuario no encontrado");
        }

        if (!passwordEncoder.matches(request.getContrasenia(), usuario.get().getContrasenia())) {
            return new AuthResponse(null, null, null, null, null, "Credenciales inválidas");
        }

        String token = jwtService.generateToken(usuario.get().getEmail());
        return new AuthResponse(token, usuario.get().getRole(), usuario.get().getNombre(), usuario.get().getApellido(), "Usuario autenticado correctamente", null);
    }
}
