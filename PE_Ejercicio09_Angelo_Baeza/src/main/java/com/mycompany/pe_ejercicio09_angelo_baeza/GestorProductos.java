package com.mycompany.pe_ejercicio09_angelo_baeza;

import java.io.*;
import java.util.*;

public class GestorProductos {

// ── 1. LEER TODOS LOS PRODUCTOS ──────────────────────────────────────────
//
// BufferedReader lee línea a línea de forma eficiente (usa buffer interno).
// try-with-resources garantiza que el fichero se cierra aunque haya error.
    static void listarProductos() throws IOException {

        try (BufferedReader br = new BufferedReader(
                new FileReader("productos.csv"))) {

            String linea;

            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(";", -1);

                int id = Integer.parseInt(campos[0]);
                String nombre = campos[1];
                double precio = Double.parseDouble(campos[2]);

                System.out.printf("ID: %d | Nombre : %-20s | precio : %.2f€%n", id, nombre, precio);
            }

        }

    }

// ── 2. AÑADIR UN PRODUCTO ─────────────────────────────────────────────────
//
// FileWriter("productos.csv", true) → modo APPEND: no borra el contenido.
// FileWriter("productos.csv") → modo normal: sobreescribe desde cero.
    static void añadirProducto(int id, String nombre, double precio)
            throws IOException {
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter("productos.csv", true))) {
            // Construimos la línea CSV con el mismo separador del fichero
            String linea = id + ";" + nombre + ";" + precio;
            bw.write(linea);
            bw.newLine(); // escribe el salto de línea adecuado al SO
        }
        // Al salir del try, BufferedWriter y FileWriter se cierran solos
    }

// ── 3. BUSCAR PRODUCTO POR ID ─────────────────────────────────────────────
//
// Leemos línea a línea y comparamos el primer campo (índice 0) con el id.
// Si no existe el id, el método devuelve null.
    static String buscarProducto(int idBuscado) throws IOException {
        try (BufferedReader br = new BufferedReader(
                new FileReader("productos.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(";", -1);
// Comparamos como entero para evitar problemas con ceros iniciales
                int idLinea = Integer.parseInt(campos[0]);
                if (idLinea == idBuscado) {
                    return linea; // encontrado: devolvemos la línea completa
                }
            }
        }
        return null; //no encontrado
    }

// ── 4. MODIFICAR PRODUCTO ─────────────────────────────────────────────────
//
// NO se puede editar una línea en medio de un fichero de texto porque las
// líneas tienen longitud variable. Si una línea crece, empujaría a las
// demás. Si decrece, quedaría basura o habría que desplazar bytes.
//
// Estrategia: cargar todo en List, modificar, reescribir desde cero.
    static void modificarProducto(int idObjetivo, String nuevoNombre,
            double nuevoPrecio) throws IOException {
// PASO 1: leer todas las líneas en memoria
        List lineas = new ArrayList();
        try (BufferedReader br = new BufferedReader(
                new FileReader("productos.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
        }
// PASO 2: recorrer la lista y sustituir la línea que corresponde
        for (int i = 0; i < lineas.size(); i++) {
            String[] campos = ((String) lineas.get(i)).split(";", -1);
            if (Integer.parseInt(campos[0]) == idObjetivo) {
// Construimos la línea nueva con los datos actualizados
                lineas.set(i, idObjetivo + ";" + nuevoNombre + ";" + nuevoPrecio);
                break; // suponemos IDs únicos, podemos parar
            }
        }
// PASO 3: reescribir el fichero sin modo append (sobreescribe)
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter("productos.csv"))) { // SIN true → trunca el fichero
            for (int i = 0; i < lineas.size(); i++) {
                bw.write((String) lineas.get(i));
                bw.newLine();
            }
        }
    }

// ── 5. ELIMINAR PRODUCTO ─────────────────────────────────────────────────
//
// Misma estrategia que MODIFICAR: leer todo, filtrar, reescribir.
// No añadimos la línea cuyo id coincide con el que queremos borrar.
    
    static void eliminarProducto(int idEliminar) throws IOException {
// PASO 1: leer todas las líneas en memoria
        List lineas = new ArrayList();
        try (BufferedReader br = new BufferedReader(
                new FileReader("productos.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
        }
// PASO 2: reescribir omitiendo la línea del producto eliminado
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter("productos.csv"))) { // trunca el fichero original
            for (int i = 0; i < lineas.size(); i++) {
                String lineaActual = (String) lineas.get(i);
                String[] campos = lineaActual.split(";", -1);
// Solo escribimos las líneas que NO corresponden al id buscado
                if (Integer.parseInt(campos[0]) != idEliminar) {
                    bw.write(lineaActual);
                    bw.newLine();
                }
            }
        }
    }

}
