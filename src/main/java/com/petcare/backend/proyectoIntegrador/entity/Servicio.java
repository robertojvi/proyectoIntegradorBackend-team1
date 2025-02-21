package com.petcare.backend.proyectoIntegrador.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidad que representa un servicio ofrecido en el sistema.
 */
@Data
@Entity
@Table(name = "servicio")
public class Servicio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column(name = "id_servicio")
    private short idServicio; // Preguntar por el tipo de dato, es mejor short?
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "precio")
    private BigDecimal precio;
    
    @Column(name = "imagen_url")
    private String imagenUrl;
    
    @Column(name = "disponibilidad")
    private String disponibilidad; // TODO: Convertir a enum (DISPONIBLE, NO_DISPONIBLE)
    
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    @Column(name = "es_borrado")
    private boolean esBorrado;
    
    @Column(name = "fecha_borrado")
    private LocalDateTime fechaBorrado;
    
    @OneToMany(mappedBy = "servicio")
    private List<DetallePedido> detallePedidos;
    
    @ManyToOne
    @JoinColumn(name = "id_establecimiento")
    private Establecimiento establecimiento;
} 
