package com.example.proyectointerf;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Chat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        //Esconder barra superior
        getSupportActionBar().hide();
    }
    public void ir(View view){
        Intent i = new Intent(this,Contactos.class);
        startActivity(i);
    }
    public void regresar(View view){
        Intent i = new Intent(this,Perfil.class);
        startActivity(i);
    }
}
