package com.mycompany.gestionestructura;

import java.io.IOException;
import java.nio.file.*;

public class Ejercicio_01_Acesso_Datos_Angelo_Baeza {

    public static void main(String[] args) {
        
        // Rutas de carpetas y ficheros pedidos
        Path dirSrcJunit = Paths.get("src/junit");
        Path dirBin = Paths.get("bin");
        Path dirImg = Paths.get("img");

        Path fileReadme = Paths.get("readme.md");
        Path fileRun = Paths.get("src/run.java");
        Path fileImagen = Paths.get("img/imagen");

        try {
            // Crear las carpetas si no existen
            Files.createDirectories(dirSrcJunit);
            Files.createDirectories(dirBin);
            Files.createDirectories(dirImg);

            // Crear los archivos comprobando antes si ya están creados
            if (!Files.exists(fileReadme)) {
                Files.createFile(fileReadme);
            }
            if (!Files.exists(fileRun)) {
                Files.createFile(fileRun);
            }
            if (!Files.exists(fileImagen)) {
                Files.createFile(fileImagen);
            }

            // Comprobación de existencia
            System.out.println("Comprobando existencia de ficheros y carpetas:");
            System.out.println("readme.md: " + Files.exists(fileReadme));
            System.out.println("src/run.java: " + Files.exists(fileRun));
            System.out.println("img/imagen: " + Files.exists(fileImagen));
            System.out.println("src/junit: " + Files.exists(dirSrcJunit));
            System.out.println("bin: " + Files.exists(dirBin));

            // Listar el contenido del directorio del proyecto
            System.out.println("\nContenido en el directorio:");
            Path actual = Paths.get(".");
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(actual)) {
                for (Path p : stream) {
                    System.out.println(" - " + p.getFileName());
                }
            }

        } catch (IOException e) {
            System.out.println("Error al gestionar los ficheros: " + e.getMessage());
        }
    }
}