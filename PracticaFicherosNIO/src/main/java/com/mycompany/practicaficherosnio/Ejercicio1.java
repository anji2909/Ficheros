/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practicaficherosnio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author usuario
 */
public class Ejercicio1 {

    public static void ejercicio1() {
        Path dirWorkspace = Paths.get("workspace");
        Path dirBackup = Paths.get("backup");
        Path archivoOrigen = dirWorkspace.resolve("tareas.txt");
        Path archivoDestino = dirBackup.resolve("tareas_copia.txt");

        try {
            //1. crear directorios
            Files.createDirectories(dirWorkspace);
            Files.createDirectories(dirBackup);
            System.out.println("Directorios creados correctamente");

            //2. Escribir fichero
            List<String> tareas = Arrays.asList("Repasar NIO", "EstudiarCSV y Jason", "Practicar Acceso Aleatorio");
            Files.write(archivoOrigen, tareas, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("Archivo creado correctamente");

            //3. leer el fichero
            if (Files.exists(archivoOrigen)) {
                System.out.println("==============Contenido de tareas.txt=================");
                List<String> lineasLeidas = Files.readAllLines(archivoOrigen);

                for (String linea : lineasLeidas) {
                    System.out.println(linea);
                }

                System.out.println("_________________________________________________________");
            } else {
                System.out.println("El archivo no se ha encontrado");
            }

            //4. copiar el fichero
            Files.copy(archivoOrigen, archivoDestino, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Copia de seguridad realizada en " + archivoDestino.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error con el archivo" + e.getMessage());
        }

    }
}
