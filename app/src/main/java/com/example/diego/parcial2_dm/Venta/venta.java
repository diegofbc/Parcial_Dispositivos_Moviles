package com.example.diego.parcial2_dm.Venta;

import java.sql.Date;

public class venta {

    private int id_venta;
    private int Forkey_idproducto;
    private int Forkey_idcliente;
    private Date fecha;
    private int total;
    private int cantidad;


    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public int getForkey_idproducto() {
        return Forkey_idproducto;
    }

    public void setForkey_idproducto(int forkey_idproducto) {
        Forkey_idproducto = forkey_idproducto;
    }

    public int getForkey_idcliente() {
        return Forkey_idcliente;
    }

    public void setForkey_idcliente(int forkey_idcliente) {
        Forkey_idcliente = forkey_idcliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
