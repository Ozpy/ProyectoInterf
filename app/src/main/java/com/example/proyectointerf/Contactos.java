package com.example.proyectointerf;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Contactos extends AppCompatActivity {
    ArrayList<ContactoVo> listaContactos;
    RecyclerView recyclerContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);

        //Esconder barra superior
        getSupportActionBar().hide();

        listaContactos = new ArrayList<>();
        recyclerContactos= (RecyclerView)findViewById(R.id.recyclerView);
        recyclerContactos.setLayoutManager(new LinearLayoutManager(this));



        llenarContactos();

        AdapterContactos adapterContactos=new AdapterContactos(listaContactos);
        recyclerContactos.setAdapter(adapterContactos);



    }

    private void llenarContactos() {
        listaContactos.add(new ContactoVo("HOALALSOASSAD","asasadda@gmail.com",R.mipmap.usuario));
        listaContactos.add(new ContactoVo("HOALALSOASSAD","asasadda@gmail.com",R.mipmap.usuario));
        listaContactos.add(new ContactoVo("HOALALSOASSAD","asasadda@gmail.com",R.mipmap.usuario));
        listaContactos.add(new ContactoVo("HOALALSOASSAD","asasadda@gmail.com",R.mipmap.usuario));
        listaContactos.add(new ContactoVo("HOALALSOASSAD","asasadda@gmail.com",R.mipmap.usuario));
        listaContactos.add(new ContactoVo("HOALALSOASSAD","asasadda@gmail.com",R.mipmap.usuario));
        listaContactos.add(new ContactoVo("HOALALSOASSAD","asasadda@gmail.com",R.mipmap.usuario));
        listaContactos.add(new ContactoVo("HOALALSOASSAD","asasadda@gmail.com",R.mipmap.usuario));
        listaContactos.add(new ContactoVo("HOALALSOASSAD","asasadda@gmail.com",R.mipmap.usuario));
        listaContactos.add(new ContactoVo("HOALALSOASSAD","asasadda@gmail.com",R.mipmap.usuario));
        listaContactos.add(new ContactoVo("HOALALSOASSAD","asasadda@gmail.com",R.mipmap.usuario));
        listaContactos.add(new ContactoVo("HOALALSOASSAD","asasadda@gmail.com",R.mipmap.usuario));
        listaContactos.add(new ContactoVo("HOALALSOASSAD","asasadda@gmail.com",R.mipmap.usuario));
        listaContactos.add(new ContactoVo("HOALALSOASSAD","asasadda@gmail.com",R.mipmap.usuario));
        listaContactos.add(new ContactoVo("HOALALSOASSAD","asasadda@gmail.com",R.mipmap.usuario));
        listaContactos.add(new ContactoVo("HOALALSOASSAD","asasadda@gmail.com",R.mipmap.usuario));
        listaContactos.add(new ContactoVo("HOALALSOASSAD","asasadda@gmail.com",R.mipmap.usuario));
        listaContactos.add(new ContactoVo("HOALALSOASSAD","asasadda@gmail.com",R.mipmap.usuario));
    }
    public void ir(View view){
        Intent i = new Intent(this,AgregarProducto.class);
        startActivity(i);
    }

}
