package com.example.diego.parcial2_dm;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class HomeActivity extends AppCompatActivity implements FragmentConsultarVenta.OnFragmentInteractionListener,
FragmentRegistrarCliente.OnFragmentInteractionListener, FragmentRegistrarProducto.OnFragmentInteractionListener,
FragmentRegistrarVenta.OnFragmentInteractionListener, DefaultFragment.OnFragmentInteractionListener{

    FragmentConsultarVenta fragmentConsultarVenta;
    FragmentRegistrarCliente fragmentRegistrarCliente;
    FragmentRegistrarProducto fragmentRegistrarProducto;
    FragmentRegistrarVenta fragmentRegistrarVenta;
    DefaultFragment defaultFragment;
    Button btnConsultarVenta, btnRegistrarCliente, btnRegistrarProducto, btnRegistrarVenta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        fragmentConsultarVenta = new FragmentConsultarVenta();
        fragmentRegistrarCliente = new FragmentRegistrarCliente();
        fragmentRegistrarProducto = new FragmentRegistrarProducto();
        fragmentRegistrarVenta = new FragmentRegistrarVenta();
        defaultFragment = new DefaultFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.Contenedor, defaultFragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void onClick(View view) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            switch (view.getId()){

                case R.id.btn_Registrar_Usuario:
                    transaction.replace(R.id.Contenedor, fragmentRegistrarCliente);
                    break;
                case R.id.btn_Registrar_Producto:
                    transaction.replace(R.id.Contenedor, fragmentRegistrarProducto);
                    break;
                case R.id.btn_Registrar_Venta:
                    transaction.replace(R.id.Contenedor, fragmentRegistrarVenta);
                    break;
                case R.id.btn_Consultar_Venta:
                    transaction.replace(R.id.Contenedor, fragmentConsultarVenta);
                    break;
            }
        transaction.commit();

    }
}
