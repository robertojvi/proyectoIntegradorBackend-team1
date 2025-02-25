package com.petcare.backend.proyectoIntegrador.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.time.LocalDateTime;

/**
 * Entidad que representa un establecimiento veterinario.
 * Lugar donde se brindan los servicios a las mascotas.
 */
@Data
@Entity
@Table(name = "establecimiento")
@Getter
@Setter
public class Establecimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_establecimiento")
    private short idEstablecimiento;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "horario")

  private String horario;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(name = "es_borrado")
    private boolean esBorrado;

    @Column(name = "fecha_borrado")
    private LocalDateTime fechaBorrado;

    /**
     * Relación con la entidad Usuario (Cada establecimiento pertenece a un usuario).
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id_usuario")
    private Usuario usuario;

    
    @Column(name = "estado")
    private String estado;
    
    /**
     * Usuario administrador del establecimiento
     */
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    
    /**
     * Lista de reservas programadas en el establecimiento.
     */
    @OneToMany(mappedBy = "establecimiento")
    private List<Reserva> reservas;
}
