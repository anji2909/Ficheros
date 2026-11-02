package com.mycompany.pe_ejercicio10_angelo_baeza;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class PE_Ejercicio10_Angelo_Baeza {

 
    public static void main(String[] args) {
        Path fichero = Paths.get("cuentas.dat");
        Scanner sc = new Scanner(System.in);
        int opcion = -1;

        do {
            System.out.println("\n--- GESTIÓN DE EXTRACTO BANCARIO (cuentas.dat) ---");
            System.out.println("1. Insertar los datos bancarios iniciales");
            System.out.println("2. Mostrar todos los registros y el total");
            System.out.println("3. Eliminar el registro ID 4 ('Cargo compra')");
            System.out.println("4. Modificar el registro ID 2 a 'Ingreso'");
            System.out.println("5. Mostrar el número de registros y tamaño del fichero");
            System.out.println("0. Salir y realizar comprobación final");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());

                switch (opcion) {
                    case 1:
                        insertarDatosIniciales(fichero);
                        break;
                    case 2:
                        mostrarRegistrosYTotal(fichero);
                        break;
                    case 3:
                        eliminarRegistro(fichero, 4);
                        break;
                    case 4:
                        modificarConcepto(fichero, 2, "Ingreso");
                        break;
                    case 5:
                        mostrarEstadisticasFichero(fichero);
                        break;
                    case 0:
                        System.out.println("\n--- COMPROBACIÓN FINAL ANTES DE SALIR (PUNTO 6) ---");
                        mostrarRegistrosYTotal(fichero);
                        mostrarEstadisticasFichero(fichero);
                        System.out.println("Programa finalizado correctamente.");
                        break;
                    default:
                        System.out.println("Opción no válida. Inténtelo de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe introducir un número entero válido.");
            }
        } while (opcion != 0);

        sc.close();
    }

    // Punto 1 y 2: Crea los apuntes bancarios iniciales del extracto usando
   
    private static void insertarDatosIniciales(Path fichero) {
        List<ApunteBancario> iniciales = new ArrayList<>();

        iniciales.add(new ApunteBancario(0, LocalDate.of(2026, 1, 1), "Saldo anterior", LocalDate.of(2026, 1, 1), 0.0, 3528.76));
        iniciales.add(new ApunteBancario(1, LocalDate.of(2026, 1, 5), "Pago fra. teléfono", LocalDate.of(2026, 1, 5), -91.57, 3437.19));
        iniciales.add(new ApunteBancario(2, LocalDate.of(2026, 1, 10), "Ingreso en efectivo", LocalDate.of(2026, 1, 10), 150.00, 3587.19));
        iniciales.add(new ApunteBancario(3, LocalDate.of(2026, 1, 15), "Pago de préstamo", LocalDate.of(2026, 1, 15), -392.61, 3194.58));
        iniciales.add(new ApunteBancario(4, LocalDate.of(2026, 1, 20), "Cargo compra", LocalDate.of(2026, 1, 20), -123.45, 3071.13));
        iniciales.add(new ApunteBancario(5, LocalDate.of(2026, 1, 25), "Transf. a su favor", LocalDate.of(2026, 1, 25), 721.27, 3792.40));
        iniciales.add(new ApunteBancario(6, LocalDate.of(2026, 1, 31), "Reintegro cajero autom.", LocalDate.of(2026, 1, 31), -80.00, 3712.40));

        guardarListaEnFichero(fichero, iniciales);
        System.out.println("Éxito: Los 7 apuntes bancarios iniciales se han insertado en '" + fichero + "'.");
    }

    //Punto 6: Abre el fichero binario con try-with-resources, recorre todos
    
    private static void mostrarRegistrosYTotal(Path fichero) {
        if (!Files.exists(fichero)) {
            System.out.println("El fichero '" + fichero + "' aún no existe. Ejecute la opción 1 primero.");
            return;
        }

        double saldoCalculado = 0.0;
        int contador = 0;

        System.out.println("\n==========================================================================================================");
        System.out.println("                               EXTRACTO DE CUENTA - BANCO IBÉRICO                                         ");
        System.out.println("==========================================================================================================");

        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(fichero))) {
            while (true) {
                ApunteBancario apunte = (ApunteBancario) ois.readObject();
                System.out.println(apunte);

                // Si es el registro inicial (ID 0), tomamos su saldo base; para el resto, sumamos su importe
                if (apunte.getId() == 0) {
                    saldoCalculado += apunte.getSaldo();
                } else {
                    saldoCalculado += apunte.getImporte();
                }
                contador++;
            }
        } catch (EOFException e) {
            // Fin de archivo alcanzado de forma natural
            System.out.println("==========================================================================================================");
            System.out.printf("TOTAL REGISTROS MOSTRADOS: %d | NUEVO SALDO TOTAL CALCULADO: %.2f €%n", contador, saldoCalculado);
            System.out.println("==========================================================================================================");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al leer el fichero: " + e.getMessage());
        }
    }

    // Punto 3: Elimina un registro específico buscando por su ID. 
    
    private static void eliminarRegistro(Path fichero, int idEliminar) {
        if (!Files.exists(fichero)) {
            System.out.println("El fichero no existe.");
            return;
        }

        List<ApunteBancario> lista = leerFicheroALista(fichero);
        boolean eliminado = false;

        // Recorremos la lista y eliminamos el que coincida con el ID buscado
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == idEliminar) {
                lista.remove(i);
                eliminado = true;
                break;
            }
        }

        if (eliminado) {
            guardarListaEnFichero(fichero, lista);
            System.out.println("Éxito: Se ha eliminado el registro con ID " + idEliminar + ".");
        } else {
            System.out.println("Aviso: No se encontró ningún registro con el ID " + idEliminar + ".");
        }
    }

    // Punto 4: Modifica el concepto de un registro específico buscando por su ID
   
    private static void modificarConcepto(Path fichero, int idModificar, String nuevoConcepto) {
        if (!Files.exists(fichero)) {
            System.out.println("El fichero no existe.");
            return;
        }

        List<ApunteBancario> lista = leerFicheroALista(fichero);
        boolean modificado = false;

        for (ApunteBancario apunte : lista) {
            if (apunte.getId() == idModificar) {
                apunte.setConcepto(nuevoConcepto);
                modificado = true;
                break;
            }
        }

        if (modificado) {
            guardarListaEnFichero(fichero, lista);
            System.out.println("Éxito: El concepto del registro con ID " + idModificar + " ha sido cambiado a '" + nuevoConcepto + "'.");
        } else {
            System.out.println("Aviso: No se encontró ningún registro con el ID " + idModificar + ".");
        }
    }

    //Punto 5: Muestra por consola el número total de registros almacenados
   
    private static void mostrarEstadisticasFichero(Path fichero) {
        if (!Files.exists(fichero)) {
            System.out.println("El fichero no existe.");
            return;
        }

        try {
            List<ApunteBancario> lista = leerFicheroALista(fichero);
            long tamanoBytes = Files.size(fichero);

            System.out.println("\n--- ESTADÍSTICAS DEL FICHERO ---");
            System.out.println("Número actual de registros : " + lista.size());
            System.out.println("Tamaño del fichero en disco: " + tamanoBytes + " bytes");
        } catch (IOException e) {
            System.err.println("Error al obtener el tamaño del fichero: " + e.getMessage());
        }
    }

    /**
     * Método auxiliar de lectura: Abre el archivo binario con
     * try-with-resources, lee todos los objetos hasta alcanzar el final del
     * fichero (EOFException) y los devuelve en una lista en memoria.
     */
    private static List<ApunteBancario> leerFicheroALista(Path fichero) {
        List<ApunteBancario> lista = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(fichero))) {
            while (true) {
                ApunteBancario apunte = (ApunteBancario) ois.readObject();
                lista.add(apunte);
            }
        } catch (EOFException e) {
            // Fin de la lectura alcanzado correctamente
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar datos del fichero: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Método auxiliar de escritura: Abre el archivo binario con
     * try-with-resources y escribe secuencialmente todos los objetos de la
     * lista proporcionada, cerrando el flujo automáticamente al terminar.
     */
    private static void guardarListaEnFichero(Path fichero, List<ApunteBancario> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(fichero))) {
            for (ApunteBancario apunte : lista) {
                oos.writeObject(apunte);
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en el fichero: " + e.getMessage());
        }
    }
}
