package com.petcare.backend.proyectoIntegrador.service;

import com.petcare.backend.proyectoIntegrador.entity.DetallePedido;
import java.util.List;
import java.util.Optional;

public interface IDetallePedidoService {
    DetallePedido crear(DetallePedido detallePedido);
    Optional<DetallePedido> obtenerPorId(Short id);
    List<DetallePedido> listarTodos();
    List<DetallePedido> listarPorPedido(Short pedidoId);
    List<DetallePedido> listarPorServicio(Short servicioId);
    DetallePedido actualizar(DetallePedido detallePedido);
    void eliminar(Short id);
} 