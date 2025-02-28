package com.petcare.backend.proyectoIntegrador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petcare.backend.proyectoIntegrador.DTO.ServicioRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ServicioRequestTest {

    @Test
    public void testSerialization() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ServicioRequest servicioRequest = new ServicioRequest();
        servicioRequest.setNombre("Servicio Prueba");
        servicioRequest.setDescripcion("Descripción prueba");
        servicioRequest.setPrecio(BigDecimal.valueOf(99.99));

        String json = mapper.writeValueAsString(servicioRequest);
        assertNotNull(json);

        ServicioRequest deserialized = mapper.readValue(json, ServicioRequest.class);
        assertEquals("Servicio Prueba", deserialized.getNombre());
        assertEquals("Descripción prueba", deserialized.getDescripcion());
        // Usa un delta para comparar valores de punto flotante
        assertEquals(99.99, deserialized.getPrecio(), String.valueOf(0.001));
    }
}
