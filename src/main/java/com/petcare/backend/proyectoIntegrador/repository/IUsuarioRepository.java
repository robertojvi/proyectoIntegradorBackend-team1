package com.petcare.backend.proyectoIntegrador.repository;

import com.petcare.backend.proyectoIntegrador.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Short> {
    @Query("SELECT u FROM Usuario u WHERE u.nombre LIKE %:nombre%")
    List<Usuario> findByNombre(String nombre);
    
    Optional<Usuario> findByEmail(String email);
    
    List<Usuario> findByRol(String rol);
    
    @Query("SELECT u FROM Usuario u WHERE u.esBorrado = false")
    List<Usuario> findActivos();
} 