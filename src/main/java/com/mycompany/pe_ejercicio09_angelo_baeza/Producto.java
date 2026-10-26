
package com.mycompany.pe_ejercicio09_angelo_baeza;

// clase prodoicto con sius atributos y gtters
public class Producto {
    
    private int id;
    private String nombre;
    private double precio;

    public Producto(int id, String nombre, double precio) {
        
        if(id<=0){
            throw new IllegalArgumentException("EL ID debe ser mayor que 0");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return "Producto{" + "id=" + id + ", nombre=" + nombre + ", precio=" + precio + '}';
    }
    
    
    
    
}
