package com.example.proyectointerf;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Productos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        //Esconder barra superior
        getSupportActionBar().hide();
    }
    public void ir (View view){
        Intent intent = new Intent(this, Chat.class);
        startActivity(intent);
    }

}
