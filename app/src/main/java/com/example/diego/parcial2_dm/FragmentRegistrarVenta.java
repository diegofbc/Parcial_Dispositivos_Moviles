package com.example.diego.parcial2_dm;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diego.parcial2_dm.BaseDatos.ConstantesBD;
import com.example.diego.parcial2_dm.Cliente.cliente;
import com.example.diego.parcial2_dm.Conexion.SQLiteHelper;
import com.example.diego.parcial2_dm.Producto.producto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;

import static android.app.DatePickerDialog.*;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentRegistrarVenta.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentRegistrarVenta#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRegistrarVenta extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Spinner combocliente, comboproducto;
    ArrayList<String> ListCliente;
    ArrayList<cliente> ClienteList;
    ArrayList<String> ListProducto;
    ArrayList<producto> ProductoList;
    Button Bregistrar, Bfecha, Btotal;
    TextView fechac, totalv;
    EditText cantidad;
    View vista;
    SQLiteHelper con;
    int idcliente, idproducto, preciouni = 0,totalventa;
    int ayear, amonth,aday;
    Calendar C = null;

    private OnFragmentInteractionListener mListener;

    public FragmentRegistrarVenta() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentRegistrarVenta.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRegistrarVenta newInstance(String param1, String param2) {
        FragmentRegistrarVenta fragment = new FragmentRegistrarVenta();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        con = new SQLiteHelper(getContext(), ConstantesBD.NOMBRE_BD,null,1);
        vista  = inflater.inflate(R.layout.fragment_fragment_registrar_venta, container, false);
        C = Calendar.getInstance();
        ayear = C.get(Calendar.YEAR);
        amonth = C.get(Calendar.MONTH);
        aday = C.get(Calendar.DAY_OF_MONTH);
        Bregistrar = (Button) vista.findViewById(R.id.button_venta);
        Btotal = (Button) vista.findViewById(R.id.button_calculartotal);
        Bfecha = (Button) vista.findViewById(R.id.button_fecha);
        cantidad = (EditText) vista.findViewById(R.id.editText_cantidad);
        fechac = (TextView) vista.findViewById(R.id.textview_fecha);
        totalv = (TextView) vista.findViewById(R.id.textview_total);
        combocliente = (Spinner) vista.findViewById(R.id.spinner_Cliente);
        comboproducto = (Spinner) vista.findViewById(R.id.spinner_Producto);
        consultarListacliente();
        consultarListaproducto();
        ArrayAdapter<CharSequence> adaptador1 = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,ListCliente);
        combocliente.setAdapter(adaptador1);
        ArrayAdapter<CharSequence> adaptador2 = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,ListProducto);
        comboproducto.setAdapter(adaptador2);

        Bregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cantidad.getText().toString().equals("")||preciouni==0){
                    Toast.makeText(getContext(),"Algunos campos de encuentran vacios.",Toast.LENGTH_SHORT).show();
                }else{
                    if(cantidad.getText().length()>0 && preciouni>0){
                        SQLiteDatabase db = con.getReadableDatabase();
                        String[] parametros={String.valueOf(idcliente),String.valueOf(idproducto),fechac.getText().toString(),cantidad.getText().toString(),totalv.getText().toString()};
                        String[] campos={ConstantesBD.CAMPO_IDVENTA};
                        try {
                            Cursor cursor = db.query(ConstantesBD.TABLA_VENTA,campos,ConstantesBD.CAMPO_FORK_IDCLIENTE+"=? AND "+ConstantesBD.CAMPO_FORK_IDPRODUCTO+"=? AND "+ConstantesBD.CAMPO_FECHAVENTA+"=? AND "+ConstantesBD.CAMPO_CANTIDADVENTA+"=? AND "+ConstantesBD.CAMPO_TOTALVENTA+"=?",parametros,null,null,null);
                            boolean band =cursor.moveToFirst();
                            if(band == true){
                                cursor.close();
                                db.close();
                                Toast.makeText(getContext(),"La Venta no se puede registrar porque ya existe en la BD.",Toast.LENGTH_SHORT).show();
                            }else{
                                cursor.close();
                                db.close();
                                registrarventa();
                            }
                        }catch (Exception e){
                            Toast.makeText(getContext(),"La venta no se encuentra registrada.",Toast.LENGTH_LONG).show();
                        }
                        fechac.setText("");
                        cantidad.setText("");
                        totalv.setText("");
                    }else{
                        Toast.makeText(getContext(),"Existen algunos campos vacios.",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        Btotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int canventa = Integer.parseInt(cantidad.getText().toString());
                if(cantidad.getText().length()==0 && preciouni==0){
                    Toast.makeText(getContext(),"No ha seleccionado ningun producto y digite la cantidad a comprar.", Toast.LENGTH_SHORT).show();
                }else{
                    totalventa = canventa*preciouni;
                    colocar_total(totalventa);
                }
            }
        });
        Bfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),dateListener,ayear,amonth,aday);
                datePickerDialog.show();
            }
        });
        combocliente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0) {
                    idcliente = ClienteList.get(position - 1).getIdcliente();
                    //Toast.makeText(getContext(),"ID Cliente: "+idcliente,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(),"No ha seleccionado ningun cliente.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        comboproducto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    idproducto = ProductoList.get(position-1).getId_producto();
                    preciouni = ProductoList.get(position-1).getPrecio();
                    Toast.makeText(getContext(),"precio: "+preciouni,Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getContext(),"ID Producto: "+idproducto,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(),"No ha seleccionado ningun producto.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return vista;
    }

    public void registrarventa(){
        SQLiteHelper con = new SQLiteHelper(getContext(), ConstantesBD.NOMBRE_BD,null,1);
        SQLiteDatabase db = con.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ConstantesBD.CAMPO_FORK_IDCLIENTE, idcliente);
        values.put(ConstantesBD.CAMPO_FORK_IDPRODUCTO, idproducto);
        values.put(ConstantesBD.CAMPO_FECHAVENTA,fechac.getText().toString());
        values.put(ConstantesBD.CAMPO_CANTIDADVENTA,Integer.parseInt(cantidad.getText().toString()));
        values.put(ConstantesBD.CAMPO_TOTALVENTA,totalventa);
        long idResultante = db.insert(ConstantesBD.TABLA_VENTA,ConstantesBD.CAMPO_IDVENTA,values);
        if(idResultante == -1){
            Toast.makeText(getContext(), "Error, la Venta no se registrar.", Toast.LENGTH_SHORT).show();
        }else {
            //Toast.makeText(getContext(), "ID Producto: " +idResultante, Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(),"La Venta se registro satisfactoriamente.",Toast.LENGTH_LONG).show();
        }
        db.close();
    }

    DatePickerDialog.OnDateSetListener dateListener = new OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            colocar_fecha(year,month,dayOfMonth);
        }
    };

    private void colocar_fecha(int ayear, int amonth, int aday){
        fechac.setText(ayear+"-"+(amonth+1)+"-"+aday);
    }

    private void colocar_total(int totalventa){
        totalv.setText("$ "+totalventa);
    }

    private void consultarListaproducto() {
        SQLiteDatabase db = con.getReadableDatabase();
        producto p = null;
        ProductoList = new ArrayList<producto>();

        Cursor cursor1 = db.rawQuery("SELECT * FROM "+ConstantesBD.TABLA_PRODUCTO,null);
        while (cursor1.moveToNext()){
            p = new producto();
            p.setId_producto(cursor1.getInt(0));
            p.setNombre(cursor1.getString(1));
            p.setStock(cursor1.getInt(2));
            p.setPrecio(cursor1.getInt(3));

            Log.i("IDP",String.valueOf(p.getId_producto()));
            Log.i("NOMBREP",p.getNombre());
            Log.i("STOCKP",String.valueOf(p.getStock()));
            Log.i("PRECIOP",String.valueOf(p.getPrecio()));
            ProductoList.add(p);
        }
        cursor1.close();
        db.close();
        obtenerListaproducto();
    }

    private void obtenerListaproducto() {
        ListProducto = new ArrayList<String>();
        ListProducto.add("Seleccione");
        for (int i = 0; i <ProductoList.size() ; i++) {
            ListProducto.add(ProductoList.get(i).getNombre());
        }
    }

    private void consultarListacliente() {
        SQLiteDatabase db = con.getReadableDatabase();
        cliente c = null;
        ClienteList = new ArrayList<cliente>();

        Cursor cursor = db.rawQuery("SELECT * FROM "+ConstantesBD.TABLA_CLIENTE,null);
        while (cursor.moveToNext()){
            c = new cliente();
            c.setIdcliente(cursor.getInt(0));
            c.setCedula(cursor.getInt(1));
            c.setNombre(cursor.getString(2));
            c.setGenero(cursor.getString(3));
            c.setCorreo(cursor.getString(4));
            c.setTelefono(cursor.getString(5));

            Log.i("IDC",String.valueOf(c.getIdcliente()));
            Log.i("NOMBREC",c.getNombre().toString());
            Log.i("CEDULAC", String.valueOf(c.getCedula()));
            Log.i("GENEROC",c.getGenero().toString());
            Log.i("CORREOC",c.getCorreo().toString());
            Log.i("TELEFONOC",c.getTelefono().toString());
            ClienteList.add(c);
        }
        cursor.close();
        db.close();
        obtenerListacliente();
    }

    private void obtenerListacliente() {
        ListCliente = new ArrayList<String>();
        ListCliente.add("Seleccione");
        for (int i = 0; i < ClienteList.size() ; i++) {
            ListCliente.add(ClienteList.get(i).getNombre()+" - "+ClienteList.get(i).getCorreo());
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}