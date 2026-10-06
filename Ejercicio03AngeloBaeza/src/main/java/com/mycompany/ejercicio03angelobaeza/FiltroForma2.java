package com.mycompany.ejercicio03angelobaeza;

/**
 *
 * @author angelo.baeola
 */

import java.io.File;
import java.io.FilenameFilter;
import java.util.Scanner;

public class FiltroForma2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduce la ruta del directorio a examinar: ");
        String rutaDirectorio = scanner.nextLine();

        File directorio = new File(rutaDirectorio);

        // Validacion del directorio
        if (!directorio.exists() || !directorio.isDirectory()) {
            System.err.println("Error: El directorio especificado no existe o no es valido.");
            scanner.close();
            return;
        }

        System.out.println("\n--- FORMA 2: Filtrando ficheros '.java' con Expresion Lambda ---");

        // --- FORMA 2: Expresion lambda
        
        FilenameFilter filtroLambda = (dir, nombre) -> nombre.toLowerCase().endsWith(".java");
        
        // Se aplica el filtro
        File[] ficherosJava = directorio.listFiles(filtroLambda);

        System.out.println("Ficheros .java encontrados:");
        
        // Se comprueba que el array no sea nulo antes de iterar y se imprimen los nombres
        if (ficherosJava != null && ficherosJava.length > 0) {
            for (File f : ficherosJava) {
                System.out.println("  " + f.getName());
            }
        } else {
            System.out.println("  No se encontraron ficheros .java en esta ruta.");
        }

        scanner.close();
    }
}