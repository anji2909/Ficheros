package com.mycompany.gestionestructura;


import java.nio.file.*;
import java.io.IOException;

public class GestionEstructura {
    public static void main(String[] args) {
        // 1. Definir las rutas base utilizando Paths.get()
        Path raiz = Paths.get(".");
        Path srcJunit = raiz.resolve("src/junit");
        Path bin = raiz.resolve("bin");
        Path img = raiz.resolve("img");

        Path readme = raiz.resolve("readme.md");
        Path runJava = raiz.resolve("src/run.java");
        Path imagen = raiz.resolve("img/imagen");

        try {
            // 2. Crear los directorios
            Files.createDirectories(srcJunit); 
            Files.createDirectories(bin);      
            Files.createDirectories(img);      

            // 3. Crear los ficheros comprobando primero si existen
            if (!Files.exists(readme)) Files.createFile(readme);
            if (!Files.exists(runJava)) Files.createFile(runJava);
            if (!Files.exists(imagen)) Files.createFile(imagen);

            // 4. Comprobar la existencia por pantalla
            System.out.println("--- COMPROBACIÓN DE EXISTENCIA ---");
            System.out.println("¿Existe readme.md? " + Files.exists(readme));
            System.out.println("¿Existe src/run.java? " + Files.exists(runJava));
            System.out.println("¿Existe img/imagen? " + Files.exists(imagen));
            System.out.println("¿Existe src/junit? " + Files.exists(srcJunit));

            // 5. Recorrer el contenido y mostrarlo por pantalla
            System.out.println("\n--- CONTENIDO DEL DIRECTORIO ---");
            Files.walk(raiz).forEach(path -> {
                System.out.println("- " + path.getFileName());
            });

        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        }
    }
}