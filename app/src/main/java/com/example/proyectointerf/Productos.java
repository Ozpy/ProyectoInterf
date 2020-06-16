package com.example.proyectointerf;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Productos extends AppCompatActivity {
    ArrayList<Producto> listaProductos;
    RecyclerView recyclerProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        //Esconder barra superior
        getSupportActionBar().hide();

        listaProductos = new ArrayList<>();
        recyclerProductos= (RecyclerView)findViewById(R.id.recyclerView2);
        recyclerProductos.setLayoutManager(new LinearLayoutManager(this));

        llenarProductos();

        AdapterProductos adapterProductos=new AdapterProductos(listaProductos);
        recyclerProductos.setAdapter(adapterProductos);

    }
    private void llenarProductos() {

        listaProductos.add(new Producto("Lapiz", "Lapiz 2B de madera mirado",R.mipmap.lapiz));
        listaProductos.add(new Producto("Pluma", "Pluma negra",R.mipmap.lapiz));
        listaProductos.add(new Producto("Cuaderno", "Cuaderno profesional cuadro chico",R.mipmap.lapiz));
        listaProductos.add(new Producto("Borrador", "Borrador de migajon",R.mipmap.lapiz));

    }
    public void ir (View view){
        Intent intent = new Intent(this, Chat.class);
        startActivity(intent);
    }

}
