package com.mycompany.practicaficherosnio;

public class PracticaFicherosNIO {

    public static void main(String[] args) {
        
        //Ejercicio1.ejercicio1();
        //Ejercicio2.ejercicio2();
        //Ejercicio3.ejercicio03();
        //Ejercicio4.ejercicio4();
        GestorPedidosCSV.inicializarFichero();
        //GestorPedidosCSV.altaPedido("2;Marta;15.50;PENDIENTE");
        GestorPedidosCSV.mostrarpedido();
        //GestorPedidosCSV.modificarEstado("4","5;Angelo;20.50;Finalizado");
        GestorPedidosCSV.borrar("3");
        GestorPedidosCSV.mostrarpedido();
        
    }
}
