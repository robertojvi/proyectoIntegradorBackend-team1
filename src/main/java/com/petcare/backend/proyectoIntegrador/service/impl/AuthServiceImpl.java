package com.petcare.backend.proyectoIntegrador.service.impl;

import com.petcare.backend.proyectoIntegrador.DTO.AuthResponse;
import com.petcare.backend.proyectoIntegrador.DTO.LoginRequest;
import com.petcare.backend.proyectoIntegrador.DTO.RegisterRequest;
import com.petcare.backend.proyectoIntegrador.entity.ERole;
import com.petcare.backend.proyectoIntegrador.entity.Usuario;
import com.petcare.backend.proyectoIntegrador.repository.IUsuarioRepository;
import com.petcare.backend.proyectoIntegrador.service.IAuthService;
import com.petcare.backend.proyectoIntegrador.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthServiceImpl implements IAuthService {

    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(IUsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            return new AuthResponse(null, null, null, null, "El correo ya está registrado.");
        }
        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .contrasenia(passwordEncoder.encode(request.getContrasenia()))
                .rol(request.getRol() != null ? request.getRol() : ERole.CLIENTE)
                .telefono(request.getTelefono())
                .fechaRegistro(LocalDateTime.now())
                .build();

        usuarioRepository.save(usuario);
        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol().toString());

        return new AuthResponse(token, usuario.getRol(), usuario.getNombre(), "Usuario creado correctamente", null);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Optional<Usuario> usuario = usuarioRepository. findByEmail(request.getEmail());
        if (usuario.isEmpty()) {
            return new AuthResponse(null, null, null, null, "Usuario no encontrado");
        }


        if (!passwordEncoder.matches(request.getContrasenia(), usuario.get().getContrasenia())) {
            return new AuthResponse(null, null, null, null, "Credenciales inválidas");
        }

        String token = jwtUtil.generateToken(usuario.get().getEmail(), usuario.get().getRol().toString());
        return new AuthResponse(token, usuario.get().getRol(), usuario.get().getNombre(), "Usuario autenticado correctamente", null);
    }
}
