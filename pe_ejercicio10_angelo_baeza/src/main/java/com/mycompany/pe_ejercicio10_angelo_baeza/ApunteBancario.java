package com.mycompany.pe_ejercicio10_angelo_baeza;

import java.io.Serializable;
import java.time.LocalDate;


public class ApunteBancario implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private LocalDate fecha;
    private String concepto;
    private LocalDate fechaValor;
    private double importe;
    private double saldo;

    /**
     * Constructor vacío por defecto.
     */
    public ApunteBancario() {
    }

   
    public ApunteBancario(int id, LocalDate fecha, String concepto, LocalDate fechaValor, double importe, double saldo) {
        this.id = id;
        this.fecha = fecha;
        this.concepto = concepto;
        this.fechaValor = fechaValor;
        this.importe = importe;
        this.saldo = saldo;
    }

    // --- MÉTODOS GETTER Y SETTER ---
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public LocalDate getFechaValor() {
        return fechaValor;
    }

    public void setFechaValor(LocalDate fechaValor) {
        this.fechaValor = fechaValor;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    
     //Devuelve una cadena formateada en texto de todos los campos
  
   
    @Override
    public String toString() {
        return String.format("ID: %-2d | Fecha: %-10s | Concepto: %-25s | Valor: %-10s | Importe: %8.2f € | Saldo: %8.2f €",
                id, fecha, concepto, fechaValor, importe, saldo);
    }
}
