package com.example.diego.parcial2_dm.BaseDatos;

public class ConstantesBD {

    //BASE DE DATOS
    public static String NOMBRE_BD = "bd_cabinas";

    //TABLA CLIENTE
    public static String TABLA_CLIENTE = "cliente";
    public static String CAMPO_IDCLIENTE = "idcliente";
    public static String CAMPO_CEDULACLIENTE = "cedula";
    public static String CAMPO_NOMOMBRECLIENTE = "nombre";
    public static String CAMPO_GENEROCLIENTE = "genero";
    public static String CAMPO_CORREOCLIENTE = "correo";
    public static String CAMPO_TELEFONOCLIENTE = "telefono";

    public static final  String  CREAR_TABLA_CLIENTE="CREATE TABLE "+TABLA_CLIENTE+" ("+CAMPO_IDCLIENTE+
            " INTEGER PRIMARY KEY AUTOINCREMENT,"+CAMPO_CEDULACLIENTE+" INTEGER,"+CAMPO_NOMOMBRECLIENTE+" TEXT,"+
            " "+CAMPO_GENEROCLIENTE+" TEXT,"+CAMPO_CORREOCLIENTE+" TEXT, "+CAMPO_TELEFONOCLIENTE+" TEXT)";
    public static final String ELIMINAR_TABLA_CLIENTE = "DROP TABLE IF EXISTS "+TABLA_CLIENTE;

    //TABLA PRODUCTO
    public static String TABLA_PRODUCTO = "producto";
    public static String CAMPO_IDPRODUCTO = "idproducto";
    public static String CAMPO_NOMBREPRODUCTO = "nombre";
    public static String CAMPO_STOCKPRODUCTO = "stock";
    public static String CAMPO_PRECIOPRODUCTO = "preciouni";

    public static final String CREAR_TABLA_PRODUCTO = "CREATE TABLE "+TABLA_PRODUCTO+" ("+CAMPO_IDPRODUCTO+" INTEGER PRIMARY " +
            "KEY AUTOINCREMENT, "+CAMPO_NOMBREPRODUCTO+" TEXT, "+CAMPO_STOCKPRODUCTO+" INTEGER, "+CAMPO_PRECIOPRODUCTO+" INTEGER)";
    public static final String ELIMINAR_TABLA_PRODUCTO = "DROP TABLE IF EXISTS "+TABLA_PRODUCTO;

    //TABLA VENTA
    public static String TABLA_VENTA = "venta";
    public static String CAMPO_IDVENTA = "idventa";
    public static String CAMPO_FORK_IDPRODUCTO = "idproducto";
    public static String CAMPO_FORK_IDCLIENTE = "idcliente";
    public static String CAMPO_FECHAVENTA = "fecha";
    public static String CAMPO_CANTIDADVENTA = "cantidad";
    public static String CAMPO_TOTALVENTA = "total";

    public static final String CREAR_TABLA_VENTA = "CREATE TABLE "+TABLA_VENTA+" ("+CAMPO_IDVENTA+" INTEGER PRIMARY " +
            "KEY AUTOINCREMENT, "+CAMPO_FORK_IDCLIENTE+" INTEGER, "+CAMPO_FORK_IDPRODUCTO+" INTEGER, "+CAMPO_FECHAVENTA+" TEXT," +
            ""+CAMPO_CANTIDADVENTA+" INTEGER, "+CAMPO_TOTALVENTA+" INTEGER, " +
            "FOREIGN KEY("+CAMPO_FORK_IDCLIENTE+")REFERENCES "+TABLA_CLIENTE+"("+CAMPO_IDCLIENTE+")," +
            "FOREIGN KEY ("+CAMPO_FORK_IDPRODUCTO+") REFERENCES "+TABLA_PRODUCTO+"("+CAMPO_IDPRODUCTO+"))";
    public static final String ELIMINAR_TABLA_VENTA = "DROP TABLE IF EXISTS "+TABLA_VENTA;

    //TABLA USUARIO
    public static String TABLA_USUARIO = "usuario";
    public static String CAMPO_IDUSUARIO = "idusuario";
    public static String CAMPO_NOMBREUSUARIO = "nombre";
    public static String CAMPO_CORREOUSUARIO = "correo";
    public static String CAMPO_CONTRASEÑAUSUARIO = "password";

    public static final String CREAR_TABLA_USUARIO = "CREATE TABLE "+TABLA_USUARIO+" ("+CAMPO_IDUSUARIO+" INTEGER PRIMARY "
            +"KEY AUTOINCREMENT, "+CAMPO_NOMBREUSUARIO+" TEXT, "+CAMPO_CORREOUSUARIO+" TEXT, "+CAMPO_CONTRASEÑAUSUARIO+" TEXT)";
    public static final String ELIMINAR_TABLA_USUARIO = "DROP TABLE IF EXISTS "+TABLA_USUARIO;

    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

}

