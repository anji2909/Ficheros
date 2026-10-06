package com.mycompany.ejercicio03angelobaeza;
/**
 *
 * @author angelo.baeola
 */

import java.io.File;
import java.io.FilenameFilter;
import java.util.Scanner;

public class FiltroForma3 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduce la ruta del directorio a examinar: ");
        String rutaDirectorio = scanner.nextLine();

        File directorio = new File(rutaDirectorio);

        // Validación para asegurar que el directorio existe
        if (!directorio.exists() || !directorio.isDirectory()) {
            System.err.println("Error: El directorio especificado no existe o no es válido.");
            scanner.close();
            return;
        }

        System.out.println("\n--- FORMA 3: Filtro combinado (empieza por 'alumno' y termina en '.csv') ---");

        // --- FORMA 3: Filtro por prefijo y extensión combinados ---

        File[] alumnosCSV = directorio.listFiles(
            (dir, nombre) -> nombre.toLowerCase().startsWith("alumno") && nombre.toLowerCase().endsWith(".csv")
        );

        System.out.println("CSVs de alumnos:");
        
        // Verifica si el resultado es válido para mostrar rutas absolutas o un mensaje alternativo
        if (alumnosCSV != null && alumnosCSV.length > 0) {
            for (File f : alumnosCSV) {
                // Se utiliza getAbsolutePath() como solicita la rúbrica
                System.out.println("  " + f.getAbsolutePath()); 
            }
        } else {
            // Mensaje que se muestra si no se encuentra ningún archivo válido
            System.out.println("  No se ha encontrado ningún archivo que cumpla con el patrón combinado.");
        }

        scanner.close();
    }
}