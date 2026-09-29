package com.mycompany.gestionestructura;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;

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
        
       //Ejercicio 2 escribir con texto plano 
          
          
          try(BufferedWriter bw = new BufferedWriter(
          new OutputStreamWriter(new FileOutputStream("datos_io.txt"), StandardCharsets.UTF_8))){
              
              
         // Escribimos los 3 datos solicitados
         
            bw.write("modulo;evaluacion;nota\n");
            bw.write("Acceso a Datos; 1ª Evaluacion;  8.5\n");
            bw.write("Programacion; 2ª Evaluacion; 7.0 \n");
            bw.write("Base de Datos; 1ª Evaluacion; 9.0 \n");

       
            System.out.println("Archivo datos_io.txt generado con éxito.");
            
        } catch (IOException e) {
            
            System.err.println("Ocurrió un error: " + e.getMessage());
        
        }
        
          
        //Ejercicio 3 
        
        Path ruta = Paths.get("datos_nio.txt");
        
        // Preparamos los 3 datos en una lista
        List<String> datos = Arrays.asList(
            "mascota;dueño;codigo",
            "Charly;Fer;P001",
            "Princesa;Anji;P002",
            "Martina;Anji;P003"
        );
        
        try {
            // Files.write se encarga de abrir, escribir y cerrar el archivo automáticamente
            Files.write(ruta, datos, StandardCharsets.UTF_8);
            System.out.println("Archivo datos_nio.txt generado con éxito.");
            
        } catch (IOException e) {
            System.err.println("Ocurrió un error al escribir: " + e.getMessage());
        }
          
          
          
    }
}