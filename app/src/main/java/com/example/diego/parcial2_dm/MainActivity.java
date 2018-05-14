package com.example.diego.parcial2_dm;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diego.parcial2_dm.BaseDatos.ConstantesBD;
import com.example.diego.parcial2_dm.Conexion.SQLiteHelper;

public class MainActivity extends AppCompatActivity {

    EditText usuario , contraseña;
    SQLiteHelper con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usuario = ( EditText) findViewById(R.id.text_usuario);
        contraseña =  (EditText) findViewById(R.id.text_password);
        con = new SQLiteHelper(getApplicationContext(), ConstantesBD.NOMBRE_BD,null,1);
    }

    public void registrarAdmin(){
        SQLiteHelper con = new SQLiteHelper(getApplicationContext(),ConstantesBD.NOMBRE_BD,null,1);
        SQLiteDatabase db = con.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ConstantesBD.CAMPO_NOMBREUSUARIO,"Gustavo");
        values.put(ConstantesBD.CAMPO_CONTRASEÑAUSUARIO,ConstantesBD.MD5("admin"));
        values.put(ConstantesBD.CAMPO_CORREOUSUARIO,"admin@gmail.com");
        long idResultante = db.insert(ConstantesBD.TABLA_USUARIO,ConstantesBD.CAMPO_NOMBREUSUARIO,values);
        if(idResultante == -1){
            Toast.makeText(getApplicationContext(), "Error, el registro no se pudo realizar.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(), "Nombre registro: " + idResultante, Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void onClick(View i){
        if(usuario.getText().toString().equals("")||contraseña.getText().toString().equals("")){
            Toast.makeText(getBaseContext(),"El Campo Usuario o contraseña esta vacio",Toast.LENGTH_LONG).show();
        }else{
            if(usuario.getText().toString().matches("[a-z]+@[a-z]+.[a-z]+")){
                SQLiteDatabase db = con.getReadableDatabase();
                String[] parametros={usuario.getText().toString(),ConstantesBD.MD5(contraseña.getText().toString())};
                String[] campos={ConstantesBD.CAMPO_NOMBREUSUARIO};
                try {
                    Cursor cursor = db.query(ConstantesBD.TABLA_USUARIO,campos,ConstantesBD.CAMPO_CORREOUSUARIO+"=? AND "+ConstantesBD.CAMPO_CONTRASEÑAUSUARIO+"=?",parametros,null,null,null);
                    boolean band =cursor.moveToFirst();
                    if(band == true){
                        cursor.close();
                        db.close();
                        Intent ir = new Intent(MainActivity.this,HomeActivity.class);
                        ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TASK | ir.FLAG_ACTIVITY_CLEAR_TOP);
                        //Pasar a otra actividad
                        startActivity(ir);
                    }else{
                        cursor.close();
                        db.close();
                        Toast.makeText(getBaseContext(),"El usuario no se encuentra registrado.",Toast.LENGTH_LONG).show();
                        registrarAdmin();
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"El usuario no se encuentra registrado",Toast.LENGTH_LONG).show();
                }
                usuario.setText("");
                contraseña.setText("");
                usuario.findFocus();
            }else{
                Toast.makeText(getApplicationContext(),"En el campo usuario, digite el correo con el que se registro.",Toast.LENGTH_LONG).show();
            }
        }
    }
}
