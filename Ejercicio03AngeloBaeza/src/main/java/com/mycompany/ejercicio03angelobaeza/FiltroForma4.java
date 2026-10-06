
package com.mycompany.ejercicio03angelobaeza;

/**
 *
 * @author angelo.baeola
 */


import java.io.File;
import java.io.FileFilter;
import java.util.Scanner;

public class FiltroForma4 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduce la ruta del directorio a examinar: ");
        String rutaDirectorio = scanner.nextLine();

        File directorio = new File(rutaDirectorio);

        // Validación de seguridad para la carpeta principal
        if (!directorio.exists() || !directorio.isDirectory()) {
            System.err.println("Error: El directorio especificado no existe o no es válido.");
            scanner.close();
            return;
        }

        System.out.println("\n--- FORMA 4: Filtrar solo directorios con FileFilter ---");

        // --- FORMA 4: Filtrar solo directorios con FileFilter ---
   
        FileFilter filtroDirectorios = File::isDirectory;

        // listFiles ahora aplica el FileFilter en lugar del FilenameFilter
        File[] subdirectorios = directorio.listFiles(filtroDirectorios);

        System.out.println("Subdirectorios encontrados:");
        
        // Comprobamos que el array sea válido antes de iterar para evitar excepciones
        if (subdirectorios != null && subdirectorios.length > 0) {
            for (File dir : subdirectorios) {
                // Se muestra el prefijo [DIR] antes del nombre exacto de la carpeta
                System.out.println("  [DIR] " + dir.getName());
            }
        } else {
            System.out.println("  No se encontraron subdirectorios en esta ruta.");
        }

        scanner.close();
    }
}

