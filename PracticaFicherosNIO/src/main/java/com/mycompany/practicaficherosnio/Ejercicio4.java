package com.mycompany.practicaficherosnio;

import java.nio.file.*;
import java.nio.*;
import java.io.*;
import java.util.*;

public class Ejercicio4 {

    public static void ejercicio4() {

        Path fichero = Paths.get("inventario_gelato.csv");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fichero.toFile()))) {
            String cabecera = "ID;SABOR;PRECIO;KILOS_STOCK;TESTE";
            String r1 = "1;Pistacho Italiano;3.80;12.5;Marta";
            String r2 = "2;Fresa Artesanal;3.20;8.0;Marta";
            bw.write(cabecera.trim());
            bw.newLine();
            bw.write(r1);
            bw.newLine();
            bw.write(r2);
            bw.newLine();

            System.out.println("CSV escrito correctamente");
        } catch (IOException e) {
            System.err.println("Error al escribir el archivio" + e.getMessage());
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fichero.toFile()))) {

            String linea = br.readLine();     // salto la cabecera
            while ((linea = br.readLine()) != null) {

                String[] partes = linea.split(";", -1);
                int id = Integer.parseInt(partes[0]);
                String sabor = partes[1];
                double precio = Double.parseDouble(partes[2]);
                double kilos = Double.parseDouble(partes[3]);
                String teste = partes[4];

                System.out.println("ID = " + id + "Sabor = " + sabor + "Precio: " + precio + "Kilos : " + kilos + "teste :" + teste);

            }

        } catch (IOException e) {
            System.err.println("Error al leer el archivo" + e.getMessage());
        }

    }

}
