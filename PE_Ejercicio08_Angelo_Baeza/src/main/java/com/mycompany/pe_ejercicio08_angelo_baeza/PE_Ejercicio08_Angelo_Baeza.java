package com.mycompany.pe_ejercicio08_angelo_baeza;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class PE_Ejercicio08_Angelo_Baeza {

    public static void main(String[] args) throws IOException {

        // Asegurarse de que la carpeta existe para que no de error
        Files.createDirectories(Paths.get("datos/"));
        Path p = Paths.get("datos/alumnos.txt");
        
        // Si el archivo no existe, lo creamos vacío al inicio
        if (!Files.exists(p)) {
            Files.createFile(p);
        }

        Scanner sc = new Scanner(System.in);
        int entrada;

        // El bucle comienza aquí, cubriendo también el menú visual
        do {
            System.out.println("""
                 
                 ===========================================
                 Ingresa una opcion 
                 1. Añadir un alumno al final del Fichero 
                 2. Mostrar toda la lista de Alumnos 
                 3. Buscar y localizar un alumno por nombre
                 4. Contar el total de registros
                 ======= Para SALIR ingresa 0 ======= 
                 ===========================================""");
                 
            entrada = sc.nextInt();
            sc.nextLine(); // Limpiar el buffer

            switch (entrada) {
                case 1:
                    System.out.println("Ingrese el nombre y el apellido del alumno para agregar a la lista:");
                    String nombre = sc.nextLine(); 
                    
                    
                    Files.writeString(p, nombre + System.lineSeparator(),
                            StandardCharsets.UTF_8,
                            StandardOpenOption.APPEND); 
                    
                    System.out.println("\n Operacion realizada con exito");
                    break;
                    
                case 2:
                    System.out.println("\n--- LISTA DE ALUMNOS ---");
                    List<String> lineas = Files.readAllLines(p, StandardCharsets.UTF_8);
                    if (lineas.isEmpty()) {
                        System.out.println("El fichero está vacío.");
                    } else {
                        lineas.forEach(System.out::println);
                    }
                    System.out.println("------------------------\n Operacion realizada con exito");
                    break;
                    
                case 3:
                    System.out.println("Ingrese el nombre o parte del nombre a buscar:");
                    String busqueda = sc.nextLine().toLowerCase(); 
                    
                    System.out.println("\n--- RESULTADOS DE BÚSQUEDA ---");
                    
                  
                    try (Stream<String> stream = Files.lines(p, StandardCharsets.UTF_8)) {
                        
                        long encontrados = stream
                            .filter(l -> l.toLowerCase().contains(busqueda)) 
                            .peek(System.out::println) 
                            .count();
                            
                        if (encontrados == 0) {
                            System.out.println(" No se encontró ningún alumno con ese nombre.");
                        } else {
                            System.out.println("Se encontraron " + encontrados + " coincidencia(s).");
                        }
                    }         
                    break;
                    
                case 4:
                    List<String> lineasConteo = Files.readAllLines(p, StandardCharsets.UTF_8);
                    System.out.println("\n El total de registros (alumnos) es: " + lineasConteo.size());
                    break;
                    
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                    
                default:
                    System.out.println("\n Opcion ingresada no valida. Ingresa otra opcion.");
                    break;
            }
            
        } while (entrada != 0);

        sc.close(); 
    }
}