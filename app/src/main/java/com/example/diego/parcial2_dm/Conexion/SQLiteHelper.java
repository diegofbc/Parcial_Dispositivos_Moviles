package com.example.diego.parcial2_dm.Conexion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.diego.parcial2_dm.BaseDatos.ConstantesBD;

public class SQLiteHelper extends SQLiteOpenHelper {

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ConstantesBD.CREAR_TABLA_CLIENTE);
        db.execSQL(ConstantesBD.CREAR_TABLA_PRODUCTO);
        db.execSQL(ConstantesBD.CREAR_TABLA_VENTA);
        db.execSQL(ConstantesBD.CREAR_TABLA_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ConstantesBD.ELIMINAR_TABLA_USUARIO);
        db.execSQL(ConstantesBD.ELIMINAR_TABLA_PRODUCTO);
        db.execSQL(ConstantesBD.ELIMINAR_TABLA_VENTA);
        db.execSQL(ConstantesBD.ELIMINAR_TABLA_USUARIO);
    }
}
