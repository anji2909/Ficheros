package com.mycompany.practicaficherosnio;

import java.nio.file.Files;
import java.nio.file.*;
import java.nio.file.Paths;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorPedidosCSV {

    // 1.incicializar el fichero
    public static void inicializarFichero() {

        Path fichero = Paths.get("pedidos_chiringuito.csv");

        if (!Files.exists(fichero)) {
            try (BufferedWriter bw = Files.newBufferedWriter(fichero)) {
                String Cabecera = "ID_PEDIDO;CLIENTE;TOTAL;ESTADO";
                //Files.writeString(fichero, Cabecera, StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);
                bw.write(Cabecera);

                System.out.println("El archivo a sido creado exitosamente con su cabecera");

            } catch (IOException e) {
                System.err.println("Error al escribir el fichero" + e.getMessage());
            }
        } else {
            System.out.println("El Archivo ya existe");
        }
    }

    public static void altaPedido(String lineaCSV) {
        Path fichero = Paths.get("pedidos_chiringuito.csv");
        try (BufferedWriter bw = Files.newBufferedWriter(fichero, StandardOpenOption.APPEND)) {
            bw.newLine();
            bw.write(lineaCSV);

        } catch (IOException e) {
            System.err.println("Error al leer el fichero" + e.getMessage());
        }
    }

    public static void mostrarpedido() {
        Path fichero = Paths.get("pedidos_chiringuito.csv");

        try (BufferedReader br = Files.newBufferedReader(fichero)) {

            String linea = br.readLine(); // salto de cabecera

            while ((linea = br.readLine()) != null) {
                
                if(linea.trim().isEmpty()){
                    continue;
                }
                String[] partes = linea.split(";", -1);
                int id = Integer.parseInt(partes[0]);
                String cliente = partes[1];
                double total = Double.parseDouble(partes[2]);
                String estado = partes[3];

                System.out.println("ID : " + id + " Cliente :" + cliente + " Total :" + total + " Estado :" + estado);
            }

        } catch (IOException e) {
            System.err.println("Error al leer el fichero" + e.getMessage());
        }

    }

    public static void modificarEstado(String idBuscado, String nuevoEstado) {
        Path fichero = Paths.get("pedidos_chiringuito.csv");

        try {
            List<String> lista = Files.readAllLines(fichero);
            for (int i = 1; i < lista.size(); i++) {
                String linea = lista.get(i);
                String[] partes = linea.split(";", -1);
                String idActual = partes[0];

                if (idActual.equals(idBuscado)) {
                    System.out.println("As econtrado el ID");
                    lista.set(i, nuevoEstado);

                }

            }

            Files.write(fichero, lista, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Eror al leer el arcivho" + e.getMessage());
        }

    }

    public static void borrar(String idBuscado) {
        Path fichero = Paths.get("pedidos_chiringuito.csv");

        try {
            List<String> lista = Files.readAllLines(fichero);
            List<String> listaFiltrada = new ArrayList<>();
            for (int i = 1; i < lista.size(); i++) {
                String linea = lista.get(i);
                String[] partes = linea.split(";", -1);
                String idActual = partes[0];

                if (idActual.equals(idBuscado)) {
                    System.out.println("As econtrado el ID a BORRAR");  

                }else{
                    listaFiltrada.add(linea);
                }

            }

            Files.write(fichero, listaFiltrada, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Eror al leer el arcivho" + e.getMessage());
        }

    }

}
