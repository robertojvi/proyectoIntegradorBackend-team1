package com.petcare.backend.proyectoIntegrador.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petcare.backend.proyectoIntegrador.DTO.ServicioResponse;
import com.petcare.backend.proyectoIntegrador.DTO.ServicioSinCategoriaResponse;
import com.petcare.backend.proyectoIntegrador.entity.Servicio;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class DtoConverter {

    public static ServicioResponse convertirARespuesta(Servicio servicio) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> imagenUrls;
        try {
            if (servicio.getImagenUrl() != null) {
                imagenUrls = objectMapper.readValue(servicio.getImagenUrl(), new TypeReference<List<String>>() {});
            } else {
                imagenUrls = Collections.emptyList();
            }
        } catch (IOException e) {
            imagenUrls = Collections.emptyList();
        }
        return new ServicioResponse(
                servicio.getIdServicio(),
                servicio.getNombre(),
                servicio.getDescripcion(),
                servicio.getPrecio(),
                imagenUrls,
                servicio.getDisponibilidad(),
                servicio.getFechaRegistro(),
                servicio.getFechaActualizacion(),
                servicio.isEsBorrado(),
                servicio.getFechaBorrado(),
                servicio.getCategoria()
        );
    }

    public static ServicioSinCategoriaResponse convertirARespuestaSinCategoria(Servicio servicio) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> imagenUrls;
        try {
            if (servicio.getImagenUrl() != null) {
                imagenUrls = objectMapper.readValue(servicio.getImagenUrl(), new TypeReference<List<String>>() {});
            } else {
                imagenUrls = Collections.emptyList();
            }
        } catch (IOException e) {
            imagenUrls = Collections.emptyList();
        }
        return new ServicioSinCategoriaResponse(
                servicio.getIdServicio(),
                servicio.getNombre(),
                servicio.getDescripcion(),
                servicio.getPrecio(),
                imagenUrls,
                servicio.getDisponibilidad(),
                servicio.getFechaRegistro(),
                servicio.getFechaActualizacion(),
                servicio.isEsBorrado(),
                servicio.getFechaBorrado()
        );
    }

}
