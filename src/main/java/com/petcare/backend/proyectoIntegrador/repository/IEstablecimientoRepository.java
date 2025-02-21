package com.petcare.backend.proyectoIntegrador.repository;

import com.petcare.backend.proyectoIntegrador.entity.Establecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IEstablecimientoRepository extends JpaRepository<Establecimiento, Short> {
    @Query("SELECT e FROM Establecimiento e WHERE e.nombre LIKE %:nombre%")
    List<Establecimiento> findByNombre(String nombre);
    
    @Query("SELECT e FROM Establecimiento e WHERE e.direccion LIKE %:direccion%")
    List<Establecimiento> findByDireccion(String direccion);
    
    @Query("SELECT e FROM Establecimiento e WHERE e.esBorrado = false")
    List<Establecimiento> findActivos();

    @Query("SELECT e FROM Establecimiento e WHERE e.usuario.idUsuario = :usuarioId")
    List<Establecimiento> findByUsuarioId(Short usuarioId);
} 