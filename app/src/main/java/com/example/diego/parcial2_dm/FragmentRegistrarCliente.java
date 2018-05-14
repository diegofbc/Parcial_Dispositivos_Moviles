package com.example.diego.parcial2_dm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.diego.parcial2_dm.BaseDatos.ConstantesBD;
import com.example.diego.parcial2_dm.Conexion.SQLiteHelper;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentRegistrarCliente.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentRegistrarCliente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRegistrarCliente extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText nombre, cedula, correo, telefono;
    Button registrarcliente;
    RadioGroup RG;
    RadioButton RBH,RBM;
    String genero;
    View vista;
    SQLiteHelper con;


    private OnFragmentInteractionListener mListener;

    public FragmentRegistrarCliente() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentRegistrarCliente.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRegistrarCliente newInstance(String param1, String param2) {
        FragmentRegistrarCliente fragment = new FragmentRegistrarCliente();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        con = new SQLiteHelper(getContext(), ConstantesBD.NOMBRE_BD,null,1);
        vista  = inflater.inflate(R.layout.fragment_fragment_registrar_cliente, container, false);
        registrarcliente = (Button) vista.findViewById(R.id.button_RegistrarCliente);
        nombre = (EditText) vista.findViewById(R.id.editText_nombrecliente);
        cedula = (EditText) vista.findViewById(R.id.editText_cedulacliente);
        correo = (EditText) vista.findViewById(R.id.editText_correocliente);
        telefono = (EditText) vista.findViewById(R.id.editText_telefonocliente);
        RG = (RadioGroup) vista.findViewById(R.id.RadioGr);
        RBH = (RadioButton) vista.findViewById(R.id.H);
        RBM = (RadioButton) vista.findViewById(R.id.M);


        RBM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioID = RG.getCheckedRadioButtonId();
                RBM = vista.findViewById(radioID);
                genero = RBM.getText().toString();
                Toast.makeText(getContext(),"Radio: "+ RBM.getText().toString(),Toast.LENGTH_LONG).show();
            }
        });
        RBH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioID = RG.getCheckedRadioButtonId();
                RBH = vista.findViewById(radioID);
                genero = RBH.getText().toString();
                Toast.makeText(getContext(),"Radio: "+ RBH.getText().toString(),Toast.LENGTH_LONG).show();
            }
        });
        registrarcliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nombre.getText().toString().equals("")||cedula.getText().toString().equals("")||telefono.getText().toString().equals("")|| correo.getText().toString().equals("")){
                    Toast.makeText(getContext(),"Algunos campos de encuentran vacios.",Toast.LENGTH_SHORT).show();
                }else{
                    if(correo.getText().toString().matches("[a-z]+@[a-z]+.[a-z]+")){
                        SQLiteDatabase db = con.getReadableDatabase();
                        String[] parametros={nombre.getText().toString(),cedula.getText().toString(),correo.getText().toString(),telefono.getText().toString(),genero};
                        String[] campos={ConstantesBD.CAMPO_IDCLIENTE};
                        try {
                            Cursor cursor = db.query(ConstantesBD.TABLA_CLIENTE,campos,ConstantesBD.CAMPO_NOMOMBRECLIENTE+"=? AND "+ConstantesBD.CAMPO_CEDULACLIENTE+"=? AND "+ConstantesBD.CAMPO_CORREOCLIENTE+"=? AND "+ConstantesBD.CAMPO_TELEFONOCLIENTE+"=? AND "+ConstantesBD.CAMPO_GENEROCLIENTE+"=?",parametros,null,null,null);
                            boolean band =cursor.moveToFirst();
                            if(band == true){
                                cursor.close();
                                db.close();
                                Toast.makeText(getContext(),"El cliente no se puede registrar porque existe en la BD.",Toast.LENGTH_SHORT).show();
                            }else{
                                cursor.close();
                                db.close();
                                registrar();
                                Toast.makeText(getContext(),"El cliente se registro satisfactoriamente.",Toast.LENGTH_LONG).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(getContext(),"El cliente no se encuentra registrado",Toast.LENGTH_LONG).show();
                        }
                        nombre.setText("");
                        cedula.setText("");
                        correo.setText("");
                        telefono.setText("");
                        RBH.toggle();
                        RBM.toggle();
                    }else{
                        Toast.makeText(getContext(),"En el campo correo, digite un correo valido.",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        return vista;
    }

    public void registrar(){
        SQLiteHelper con = new SQLiteHelper(getContext(), ConstantesBD.NOMBRE_BD,null,1);
        SQLiteDatabase db = con.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ConstantesBD.CAMPO_NOMOMBRECLIENTE, nombre.getText().toString());
        values.put(ConstantesBD.CAMPO_CEDULACLIENTE, Integer.parseInt(cedula.getText().toString()));
        values.put(ConstantesBD.CAMPO_CORREOCLIENTE,correo.getText().toString());
        values.put(ConstantesBD.CAMPO_TELEFONOCLIENTE,telefono.getText().toString());
        values.put(ConstantesBD.CAMPO_GENEROCLIENTE,genero);
        long idResultante = db.insert(ConstantesBD.TABLA_CLIENTE,ConstantesBD.CAMPO_IDCLIENTE,values);
        if(idResultante == -1){
            Toast.makeText(getContext(), "Error, el Cliente no se pudo realizar.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(), "ID Cliente: " + idResultante, Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void RegistrarCliente(View i){

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
