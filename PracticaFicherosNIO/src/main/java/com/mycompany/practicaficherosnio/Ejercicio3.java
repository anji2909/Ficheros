package com.mycompany.practicaficherosnio;

import java.nio.file.*;
import java.io.IOException;
import java.util.*;

public class Ejercicio3 {

    public static void ejercicio03() {

        Path dirConfig = Paths.get("config_app");
        Path archivoAjustes = dirConfig.resolve("ajustes.txt");
        Path backup = dirConfig.resolve("ajustes_backup.txt");

        try {
            Files.createDirectories(dirConfig);

            //1 escribir archivo 
            List<String> datos = new ArrayList<>();
            datos.add("Primera linea del codigo");
            datos.add("Segunda linea de los ahjustes");
            Files.write(archivoAjustes, datos, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            //2 verificar que existe y leerlo
            if (Files.exists(archivoAjustes)) {
                if (Files.isRegularFile(archivoAjustes)) {
                    System.out.println("El archivo existe y su nombre es : " + archivoAjustes.getFileName() + "y su tamaño es:" + Files.size(archivoAjustes) + " bytes");

                    List<String> lectura = Files.readAllLines(archivoAjustes);
                    System.out.println("====================== el contenido del archivo es ===========");
                    lectura.forEach(System.out::println);
                    System.out.println("======================FIN===================================");

                }
            } else {
                System.out.println("El rachivo no ah sido encontrado");
            }

            try {
                Files.copy(archivoAjustes, backup, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("El archiuvo de backup exitoso");
            } catch (IOException e) {
                System.err.println("Error al crear el backup" + e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("error en el sistema d earchvos" + e.getMessage());
        }

    }

}
