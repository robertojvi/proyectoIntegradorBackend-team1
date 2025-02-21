package com.petcare.backend.proyectoIntegrador.repository;

import com.petcare.backend.proyectoIntegrador.entity.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IDetallePedidoRepository extends JpaRepository<DetallePedido, Short> {
    @Query("SELECT dp FROM DetallePedido dp WHERE dp.pedido.idPedido = :pedidoId")
    List<DetallePedido> findByPedidoId(Short pedidoId);
    
    @Query("SELECT dp FROM DetallePedido dp WHERE dp.servicio.idServicio = :servicioId")
    List<DetallePedido> findByServicioId(Short servicioId);
    
    @Query("SELECT dp FROM DetallePedido dp WHERE dp.esBorrado = false")
    List<DetallePedido> findActivos();
} 