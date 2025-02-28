package com.petcare.backend.proyectoIntegrador.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petcare.backend.proyectoIntegrador.DTO.ServicioRequest;
import com.petcare.backend.proyectoIntegrador.DTO.ServicioResponse;
import com.petcare.backend.proyectoIntegrador.config.S3Service;
import com.petcare.backend.proyectoIntegrador.entity.Servicio;
import com.petcare.backend.proyectoIntegrador.service.IServicioService;
import com.petcare.backend.proyectoIntegrador.service.impl.ServicioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/servicios")
@CrossOrigin(origins = "*")
public class ServicioController {
    private static final String UPLOAD_DIR = "uploads/";
    private final IServicioService servicioService;
    private final S3Service s3Service;

    ServicioController(ServicioServiceImpl servicioService, S3Service s3Service) {
        this.servicioService = servicioService;
        this.s3Service = s3Service;
    }

    @PostMapping(value = "/servicio", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String crearServicio(
            @RequestPart("datos") String datosJson,
            @RequestPart("imagenes") List<MultipartFile> imagenes) {

        ObjectMapper objectMapper = new ObjectMapper();
        ServicioRequest servicioDTO;

        try {
            servicioDTO = objectMapper.readValue(datosJson, ServicioRequest.class);
        } catch (IOException e) {
            return "Error al deserializar el JSON: " + e.getMessage();
        }

        List<String> imageUrls = new ArrayList<>();

        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));

            // Guardar cada imagen y obtener su URL
            for (MultipartFile file : imagenes) {
                byte[] fileBytes = file.getBytes();
                String imageUrl = s3Service.uploadFile(fileBytes, file.getOriginalFilename());
                imageUrls.add(imageUrl);
            }

            String imagenUrlJson = objectMapper.writeValueAsString(imageUrls);

            Servicio servicio = new Servicio();
            servicio.setNombre(servicioDTO.getNombre());
            servicio.setDescripcion(servicioDTO.getDescripcion());
            servicio.setPrecio(servicioDTO.getPrecio());
            servicio.setImagenUrl(imagenUrlJson);

            servicioService.crear(servicio);

            return "Servicio creado con éxito con imágenes en: " + imageUrls;
        } catch (IOException e) {
            return "Error al subir las imágenes: " + e.getMessage();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servicio> obtenerPorId(@PathVariable Short id) {
        return servicioService.obtenerPorId(id)
                .map(servicio -> new ResponseEntity<>(servicio, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
//
//    @GetMapping
//    public ResponseEntity<List<Servicio>> listarTodos() {
//        return new ResponseEntity<>(servicioService.listarTodos(), HttpStatus.OK);
//    }

    @GetMapping
    public List<ServicioResponse> obtenerServicios() {
        List<Servicio> servicios = servicioService.listarTodos();
        return servicios.stream()
                .map(this::convertirARespuesta)
                .collect(Collectors.toList());
    }

    private ServicioResponse convertirARespuesta(Servicio servicio) {
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
                servicio.getFechaBorrado()
        );
    }


        @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Servicio>> buscarPorNombre(@PathVariable String nombre) {
        return new ResponseEntity<>(servicioService.buscarPorNombre(nombre), HttpStatus.OK);
    }

    @GetMapping("/disponibilidad/{disponibilidad}")
    public ResponseEntity<List<Servicio>> buscarPorDisponibilidad(@PathVariable String disponibilidad) {
        return new ResponseEntity<>(servicioService.buscarPorDisponibilidad(disponibilidad), HttpStatus.OK);
    }

    @GetMapping("/precio/{precioMaximo}")
    public ResponseEntity<List<Servicio>> buscarPorPrecioMenorIgual(@PathVariable BigDecimal precioMaximo) {
        return new ResponseEntity<>(servicioService.buscarPorPrecioMenorIgual(precioMaximo), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servicio> actualizar(@PathVariable Short id, @RequestBody Servicio servicio) {
        return servicioService.obtenerPorId(id)
                .map(servicioExistente -> {
                    servicio.setIdServicio(id);
                    return new ResponseEntity<>(servicioService.actualizar(servicio), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Short id) {
        if (servicioService.obtenerPorId(id).isPresent()) {
            servicioService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
} 