
package com.mycompany.ejercicio03angelobaeza;

/**
 *
 * @author angelo.baeola
 */
import java.io.File;
import java.io.FilenameFilter;
import java.util.Scanner;

public class FiltroForma1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String extension = ".txt"; // Valor por defecto

        System.out.print("Introduce la ruta del directorio a examinar: ");
        String rutaDirectorio = scanner.nextLine();

        // Si se pasa un argumento al ejecutar, se usa como extensión
        if (args.length > 0) {
            extension = args[0];
            System.out.println("Filtro por consola detectado. Extensión: " + extension);
        } else {
            System.out.print("Introduce la extension para filtrar (ej .pdf .txt .jpg): ");
            String inputExt = scanner.nextLine();
            if (!inputExt.trim().isEmpty()) {
                extension = inputExt;
            }
        }

        File directorio = new File(rutaDirectorio);

        // Validación de robustez
        if (!directorio.exists() || !directorio.isDirectory()) {
            System.err.println("Error: El directorio especificado no existe o no es válido.");
            scanner.close();
            return;
        }

        System.out.println("\n--- FORMA 1: Filtrando ficheros '" + extension + "' con Clase Anonima ---");

        // Declaración de la variable final necesaria para usarla dentro de la clase anónima
        final String extFiltro = extension; 

        // Creación de la clase anónima que implementa FilenameFilter
        FilenameFilter filtroAnonimo = new FilenameFilter() {
            
            @Override
            public boolean accept(File dir, String name) {
                // Se evalúa si el nombre del archivo termina con la extensión solicitada
                return name.toLowerCase().endsWith(extFiltro.toLowerCase());
            }
        };

        // listFiles aplica internamente el método accept() a cada archivo del directorio
        File[] ficheros = directorio.listFiles(filtroAnonimo);

        // Mostrar resultados, comprobando que el array no sea nulo
        if (ficheros != null && ficheros.length > 0) {
            for (File f : ficheros) {
                System.out.println("  Nombre: " + f.getName() + " | Tamanio: " + f.length() + " bytes");
            }
        } else {
            System.out.println("  No se encontraron ficheros con esa extension.");
        }

        scanner.close();
    }
}