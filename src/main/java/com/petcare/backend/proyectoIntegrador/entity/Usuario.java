package com.petcare.backend.proyectoIntegrador.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidad que representa a un usuario del sistema.
 * Puede ser un cliente o un administrador según su rol.
 */
@Data
@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private short idUsuario; //? Preguntar por el tipo de dato, es mejor short?
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "contrasenia")
    private String contrasenia;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private ERole rol; // TODO: Convertir a enum (ADMIN, CLIENTE)
    
    @Column(name = "telefono")
    private String telefono;
    
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    @Column(name = "es_borrado")
    private boolean esBorrado;
    
    @Column(name = "fecha_borrado")
    private LocalDateTime fechaBorrado;

    /**
     * Lista de mascotas registradas por el usuario.
     * Un usuario puede tener múltiples mascotas.
     */
    @OneToMany(mappedBy = "usuario")
    private List<Mascota> mascotas;
    
    /**
     * Lista de pedidos realizados por el usuario.
     * Un usuario puede tener múltiples pedidos.
     */
    @OneToMany(mappedBy = "usuario")
    private List<Pedido> pedidos;
}