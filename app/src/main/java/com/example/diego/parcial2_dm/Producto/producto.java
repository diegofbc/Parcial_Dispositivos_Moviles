package com.example.diego.parcial2_dm.Producto;

public class producto {

    private int Id_producto;
    private String Nombre;
    private int Stock;
    private int Precio;

    public int getId_producto() {
        return Id_producto;
    }

    public void setId_producto(int id_producto) {
        Id_producto = id_producto;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

    public int getPrecio() {
        return Precio;
    }

    public void setPrecio(int precio) {
        Precio = precio;
    }
}
