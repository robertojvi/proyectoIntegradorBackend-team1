package com.petcare.backend.proyectoIntegrador.service.impl;

import com.petcare.backend.proyectoIntegrador.entity.Usuario;
import com.petcare.backend.proyectoIntegrador.repository.IUsuarioRepository;
import com.petcare.backend.proyectoIntegrador.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;
    
    // ... resto del código que ya teníamos ...
} 