package com.mycompany.pe_ejercicio09_angelo_baeza;

import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

public class GestorProductosRA1 {

    public static void main(String[] args) {

        //1.- Definir rutas con PATH, PATHS.GET("ruta") 
        Path dirDatos = Paths.get("datos");
        Path ficheroProductos = Paths.get("datos/productos.csv");
        Path dirBackups = Paths.get("backups");
        Path ficheroTsv = Paths.get("datos/productos.tsv");
        //Path ficheroJson = Paths.get("datos/productos.json");

        //2.- Peprar el entorno 
        try {
            //Crear direcotrio usando
            Files.createDirectories(dirDatos);
            Files.createDirectories(dirBackups);

            // comprobamos si no existe != entonces lo creamos SI EXISTE NO HACE NADA
            if (!Files.exists(ficheroProductos)) {
                String cabecera = "ID;NOMBRE;PRECIO" + System.lineSeparator();

                // Escribimos el archivo direccion, texto, formato
                Files.writeString(ficheroProductos, cabecera, StandardCharsets.UTF_8);

                System.out.println("Archivo creado con su cabecera");
            }

        } catch (IOException e) {
            System.err.println("Error Grave al preparar entorno" + e.getMessage());
            return;

        }

        //MENU
        Scanner sc = new Scanner(System.in);
        int opcion = -1;

        do {
            System.out.println("\n--- GESTOR DE PRODUCTOS CSV ---");
            System.out.println("1. Listar productos");
            System.out.println("2. Anadir producto");
            System.out.println("3. Buscar producto");
            System.out.println("4. Modificar producto");
            System.out.println("5. Eliminar producto");
            System.out.println("6. Exportar de CSV a TSV");
            System.out.println("7. Importar de TSV a CSV");
            System.out.println("8. Crear copia de seguridad");
            System.out.println("9. Listar copias de seguridad");

            System.out.println("0. Salir");
            System.out.print("Elige una opcion: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());

                switch (opcion) {
                    case 1 ->
                        listarProductos(ficheroProductos);
                    case 2 ->
                        anadirProducto(ficheroProductos, sc);
                    case 3 -> {
                        System.out.print("Introduce el ID a buscar: ");
                        int idBuscar = Integer.parseInt(sc.nextLine());
                        Producto p = buscarProductoPorId(ficheroProductos, idBuscar);
                        if (p != null) {
                            System.out.println("Encontrado: " + p);
                        } else {
                            System.out.println("No existe ningun producto con el ID " + idBuscar);
                        }
                    }
                    case 4 ->
                        modificarProducto(ficheroProductos, sc);
                    case 5 ->
                        eliminarProducto(ficheroProductos, sc);
                    case 6 ->
                        exportarATsv(ficheroProductos, ficheroTsv);
                    case 7 ->
                        importarDeTsv(ficheroTsv, ficheroProductos);
                    case 8 ->
                        hacerBackup(ficheroProductos, dirBackups);
                    case 9 ->
                        listarBackups(dirBackups);
                    case 0 ->
                        System.out.println("Saliendo del programa...");
                    default ->
                        System.out.println("Opcion incorrecta.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Introduce un numero valido.");
            }
        } while (opcion != 0);

        sc.close();
    }

    // -- METODOS CRUD --
    //1.- listar productos 
    private static void listarProductos(Path ruta) {
        System.out.println("\n ==== LISTA DE PRODUTCOS ===");

        try (BufferedReader br = new BufferedReader(new FileReader(ruta.toFile(), StandardCharsets.UTF_8))) {
            String linea = br.readLine(); //Salta la cabecera
            boolean hayProductos = false;

            while ((linea = br.readLine()) != null) {
                hayProductos = true;
                String[] partes = linea.split(";", -1);
                int id = Integer.parseInt(partes[0]);
                String nombre = partes[1];
                double precio = Double.parseDouble(partes[2]);

                Producto p = new Producto(id, nombre, precio);
                System.out.println(p); // llama al to String() de clase producto
            }
            if (!hayProductos) {
                System.out.println("El Catalogo esta Vacio");
            }

        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al listar los productos" + e.getMessage());
        }

    }

    //2.- añadir Productos
    // REQUISITO: Añadir producto (Validar ID no repetido, modo append)
    private static void anadirProducto(Path ruta, Scanner sc) {
        try {
            System.out.print("Introduce el ID: ");
            int id = Integer.parseInt(sc.nextLine());

            if (id <= 0) {
                System.out.println("El ID debe ser mayor que 0.");
                return;
            }

            // Validar que no este repetido usando el metodo buscar
            if (buscarProductoPorId(ruta, id) != null) {
                System.out.println("Error: Ya existe un producto con el ID " + id);
                return;
            }

            System.out.print("Introduce el NOMBRE: ");
            String nombre = sc.nextLine().trim();
            if (nombre.isEmpty()) {
                System.out.println("El nombre no puede estar vacio.");
                return;
            }

            System.out.print("Introduce el PRECIO: ");
            double precio = Double.parseDouble(sc.nextLine());
            if (precio < 0) {
                System.out.println("El precio no puede ser negativo.");
                return;
            }

            // Guardar en fichero (FileWriter en modo append = true)
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta.toFile(), StandardCharsets.UTF_8, true))) {
                String linea = id + ";" + nombre + ";" + precio;
                bw.write(linea);
                bw.newLine(); // Añade el salto de linea para el siguiente
                System.out.println("Producto añadido correctamente.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: Has introducido un dato numerico invalido.");
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    //3. REQUISITO: Buscar producto por ID (Devuelve Producto o null, no imprime nada)
    private static Producto buscarProductoPorId(Path ruta, int idBusqueda) {
        try (BufferedReader br = new BufferedReader(new FileReader(ruta.toFile(), StandardCharsets.UTF_8))) {
            String linea = br.readLine(); // Leemos y saltamos la cabecera

            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";", -1); // split con limite -1 segun requisito
                if (partes.length >= 3) {
                    int idActual = Integer.parseInt(partes[0]);
                    if (idActual == idBusqueda) {
                        return new Producto(idActual, partes[1], Double.parseDouble(partes[2]));
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al buscar: " + e.getMessage());
        }
        return null; // Si no lo encuentra o hay error
    }

    // REQUISITO: Modificar producto (Leer en lista, sustituir y sobrescribir)
    private static void modificarProducto(Path ruta, Scanner sc) {
        System.out.print("Introduce el ID del producto a modificar: ");
        try {
            int idBusqueda = Integer.parseInt(sc.nextLine());

            // 1. Cargamos todas las lineas en la memoria RAM
            List<String> lineas = Files.readAllLines(ruta, StandardCharsets.UTF_8);
            boolean modificado = false;

            // 2. Recorremos la lista (empezamos en 1 para saltar la cabecera)
            for (int i = 1; i < lineas.size(); i++) {
                String linea = lineas.get(i);
                String[] partes = linea.split(";", -1);
                int idActual = Integer.parseInt(partes[0]);

                if (idActual == idBusqueda) {
                    System.out.println("Producto actual: " + linea);

                    System.out.print("Nuevo NOMBRE: ");
                    String nuevoNombre = sc.nextLine().trim();
                    if (nuevoNombre.isEmpty()) {
                        System.out.println("El nombre no puede estar vacio.");
                        return;
                    }

                    System.out.print("Nuevo PRECIO: ");
                    double nuevoPrecio = Double.parseDouble(sc.nextLine());
                    if (nuevoPrecio < 0) {
                        System.out.println("El precio no puede ser negativo.");
                        return;
                    }

                    // 3. Sustituimos la linea antigua por la nueva en nuestra lista
                    String nuevaLinea = idActual + ";" + nuevoNombre + ";" + nuevoPrecio;
                    lineas.set(i, nuevaLinea);
                    modificado = true;
                    break; // Salimos del bucle porque ya lo hemos encontrado
                }
            }

            // 4. Si lo hemos cambiado, sobrescribimos todo el fichero
            if (modificado) {
                // Al usar Files.write sin StandardOpenOption.APPEND, sobrescribe por defecto
                Files.write(ruta, lineas, StandardCharsets.UTF_8);
                System.out.println("Producto modificado y archivo actualizado correctamente.");
            } else {
                System.out.println("No se encontro ningun producto con el ID " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: Has introducido un formato de numero incorrecto.");
        } catch (IOException e) {
            System.err.println("Error al modificar el archivo: " + e.getMessage());
        }
    }

    // REQUISITO: Eliminar producto (Leer en lista, filtrar y sobrescribir)
    private static void eliminarProducto(Path ruta, Scanner sc) {
        System.out.print("Introduce el ID del producto a eliminar: ");
        try {
            int idBusqueda = Integer.parseInt(sc.nextLine());

            // 1. Cargamos todas las lineas
            List<String> lineas = Files.readAllLines(ruta, StandardCharsets.UTF_8);
            boolean borrado = false;

            // 2. Buscamos y eliminamos (recorremos hacia atras para que al borrar no fallen los indices)
            for (int i = lineas.size() - 1; i >= 1; i--) { // Nos detenemos en 1 para proteger la cabecera
                String linea = lineas.get(i);
                String[] partes = linea.split(";", -1);
                int idActual = Integer.parseInt(partes[0]);

                if (idActual == idBusqueda) {
                    lineas.remove(i); // Eliminamos el elemento de la lista
                    borrado = true;
                    break;
                }
            }

            // 3. Sobrescribimos el fichero completo
            if (borrado) {
                Files.write(ruta, lineas, StandardCharsets.UTF_8);
                System.out.println("Producto eliminado correctamente.");
            } else {
                System.out.println("No se encontro ningun producto con el ID " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: Debes introducir un ID numerico.");
        } catch (IOException e) {
            System.err.println("Error al eliminar del archivo: " + e.getMessage());
        }
    }

    // ==================== FIN METODOS CRUD================================
    // --- CONVERSIoN DE FORMATOS (CSV <-> TSV) ---
    // Exportar de CSV a TSV
    private static void exportarATsv(Path rutaCsv, Path rutaTsv) {
        try {
            List<String> lineasCsv = Files.readAllLines(rutaCsv, StandardCharsets.UTF_8);
            List<String> lineasTsv = new ArrayList<>();

            for (String linea : lineasCsv) {
                // Al usar replace, cambiamos todos los ';' por tabuladores
                lineasTsv.add(linea.replace(";", "\t"));
            }

            Files.write(rutaTsv, lineasTsv, StandardCharsets.UTF_8);
            System.out.println("Exito: Archivo exportado a TSV (" + rutaTsv.getFileName() + ")");
        } catch (IOException e) {
            System.err.println("Error al exportar a TSV: " + e.getMessage());
        }
    }

    // Importar de TSV a CSV
    private static void importarDeTsv(Path rutaTsv, Path rutaCsv) {
        if (!Files.exists(rutaTsv)) {
            System.out.println("Error: No existe el archivo TSV para importar.");
            return;
        }
        try {
            List<String> lineasTsv = Files.readAllLines(rutaTsv, StandardCharsets.UTF_8);
            List<String> lineasCsv = new ArrayList<>();

            for (String linea : lineasTsv) {
                lineasCsv.add(linea.replace("\t", ";"));
            }

            Files.write(rutaCsv, lineasCsv, StandardCharsets.UTF_8);
            System.out.println("Exito: Archivo importado desde TSV (" + rutaCsv.getFileName() + ")");
        } catch (IOException e) {
            System.err.println("Error al importar desde TSV: " + e.getMessage());
        }
    }

    //1.-  BACKUP
    private static void hacerBackup(Path rutaOrigen, Path dirBackups) {
        if (!Files.exists(rutaOrigen)) {
            System.out.println("Error: No existe el archivo de productos para hacer backup.");
            return;
        }

        try {
            // Generar la marca de tiempo YYYYMMDD_HHMMSS
            LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            String marcaTiempo = ahora.format(formato);

            // Construir la ruta de destino
            String nombreBackup = "productos_" + marcaTiempo + ".csv";
            Path rutaDestino = dirBackups.resolve(nombreBackup);

            // Ejecutar la copia reemplazando si por algun motivo ya existiera en ese mismo segundo
            Files.copy(rutaOrigen, rutaDestino, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Exito: Copia de seguridad creada en " + rutaDestino);

        } catch (IOException e) {
            System.err.println("Error al crear la copia de seguridad: " + e.getMessage());
        }
    }

    //2.- Listar los ficheros de backups (Files.list)
    private static void listarBackups(Path dirBackups) {
        System.out.println("\n--- LISTADO DE COPIAS DE SEGURIDAD ---");

        if (!Files.exists(dirBackups)) {
            System.out.println("El directorio de backups no existe.");
            return;
        }

        // try-with-resources es obligatorio con Files.list para evitar fugas de memoria
        try (Stream<Path> stream = Files.list(dirBackups)) {
            long cantidad = stream
                    .filter(Files::isRegularFile) // Filtramos para que solo muestre archivos, no subcarpetas
                    .peek(path -> System.out.println(" - " + path.getFileName()))
                    .count();

            if (cantidad == 0) {
                System.out.println("La carpeta de backups esta vacia.");
            }
        } catch (IOException e) {
            System.err.println("Error al leer el directorio de backups: " + e.getMessage());
        }
    }

}
