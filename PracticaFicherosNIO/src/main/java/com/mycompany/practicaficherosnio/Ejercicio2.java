package com.mycompany.practicaficherosnio;

import java.nio.file.*;
import java.io.IOException;
import java.util.*;

public class Ejercicio2 {

    public static void ejercicio2() {

        Path dirLog = Paths.get("logs_app");
        Path log = dirLog.resolve("log_hoy.txt");

        try {
            //1. borrar log  al inicio el programa para ejecutarlo varias veces
            Files.deleteIfExists(log);
            //2. crear directorio 

            Files.createDirectories(dirLog);

            //3. escribir archivo 
            List<String> contenido = new ArrayList<String>();
            contenido.add("10:00 - Sistema iniciado");
            contenido.add("10:10 - Usuario admin loggeado");

            Files.write(log, contenido, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            //4. Comprobacion y del archivo 
            if (Files.exists(log)) {
                if (Files.isRegularFile(log)) {
                    System.out.println("El archivo regular existe y su tamaño es " + Files.size(log) + " bytes");

                    //5.leer el contennido deberia ir dentro del if anterior pero esta vez como ejemplo lo pondre fguera
                    List<String> lectura = Files.readAllLines(log);
                    
                  
                    
                    System.out.println("========== el contentido del archivo es el siguiente=========");
                    
                     lectura.forEach(System.out::println);
                    //for (String linea : lectura) {

                    //    System.out.println(linea);
                    //}

                    System.out.println("==========================FIN================================");

                }
            } else {
                System.out.println("El archivo no se puede encontrar ");
            }

            //6. mover el archivo
            Path logArchivado = dirLog.resolve("log_archivado.txt");

            Files.move(log, logArchivado, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Archivo renombrado correctamente a: " + logArchivado.getFileName());

        } catch (IOException e) {
            System.err.println("Error con el archivo " + e.getMessage());
        }

    }

}
