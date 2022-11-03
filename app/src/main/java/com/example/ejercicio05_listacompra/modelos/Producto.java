package com.example.ejercicio05_listacompra.modelos;

import java.io.Serializable;

//20.01: Hacer serializable
public class Producto implements Serializable {
    private String nombre;
    private int cantidad;
    private float importe;
    private float total;

    public Producto(String nombre, int cantidad, float importe) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.importe = importe;
        this.total = this.cantidad * this.importe;
    }

    public void actualizaTotal() {
        this.total = this.cantidad * this.importe;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getImporte() {
        return importe;
    }

    public void setImporte(float importe) {
        this.importe = importe;
    }

    public float getTotal() {
        return total;
    }
}
