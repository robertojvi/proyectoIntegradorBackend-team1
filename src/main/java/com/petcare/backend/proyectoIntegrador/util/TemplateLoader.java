package com.petcare.backend.proyectoIntegrador.util;

import org.springframework.core.io.ClassPathResource;

import java.nio.file.Files;
import java.nio.file.Paths;

public class TemplateLoader {
    public static String loadTemplate(String filename) throws Exception {
        ClassPathResource resource = new ClassPathResource("templates/" + filename);
        return new String(Files.readAllBytes(Paths.get(resource.getURI())));
    }
}
