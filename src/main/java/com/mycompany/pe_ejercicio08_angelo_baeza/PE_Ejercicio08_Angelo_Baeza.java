package com.mycompany.pe_ejercicio08_angelo_baeza;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class PE_Ejercicio08_Angelo_Baeza {

    public static void main(String[] args){

        
        Path p = Paths.get("datos/alumnos.txt");
        // Asegurarse de que la carpeta existe para que no de error
        try{
        Files.createDirectories(Paths.get("datos/"));
            if (!Files.exists(p)) {
            Files.createFile(p);
            }

        }catch(IOException e){
           System.err.println("Error crítico al inicializar el sistema de archivos: " + e.getMessage());
        }
      
       
        Scanner sc = new Scanner(System.in);
        int entrada=0;

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
            try{     
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
                    mostrarAlumnos(p);
                    break;
                    
                case 3:
                    buscarAlumno(p, sc);
                    break;
                    
                case 4:
                    List<String> lineasConteo = Files.readAllLines(p, StandardCharsets.UTF_8);
                    System.out.println("\n El total de registros (alumnos) es: " + lineasConteo.size());
                    break;
                    
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
            
            }
            }catch (Exception e){
              System.out.println("\n Opcion ingresada no valida. Ingresa otra opcion.");
              sc.nextLine();

            }
            
        } while (entrada != 0);

        sc.close(); 
    }
    
    private static void mostrarAlumnos(Path p) {
        System.out.println("\n--- LISTA DE ALUMNOS ---");
        try {
            List<String> lineas = Files.readAllLines(p, StandardCharsets.UTF_8);
            if (lineas.isEmpty()) {
                System.out.println("El fichero está vacío.");
            } else {
                lineas.forEach(System.out::println);
            }
            System.out.println("------------------------\n Operación realizada con éxito.");
        } catch (NoSuchFileException e) {
            System.err.println("Error: El archivo de alumnos no fue encontrado.");
        } catch (IOException e) {
            System.err.println("Error de lectura: " + e.getMessage());
        }
    }
    
    private static void buscarAlumno(Path p, Scanner sc) {
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
        } catch (NoSuchFileException e) {
            System.err.println("Error: No se pudo buscar porque el archivo no existe.");
        } catch (IOException e) {
            System.err.println("Error de lectura durante la búsqueda: " + e.getMessage());
        }
    }
}