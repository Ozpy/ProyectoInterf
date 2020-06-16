package com.example.proyectointerf;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Chat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        //Esconder barra superior
        getSupportActionBar().hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contactos,menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i;
        switch (item.getItemId()){
            case R.id.item_lista:
                 i= new Intent(this,Productos.class);
                startActivity(i);
                break;
            case R.id.item_add:
                i= new Intent(this,AgregarProducto.class);
                startActivity(i);
                break;
            case R.id.item_perfil:
                i= new Intent(this,Perfil.class);
                startActivity(i);
                break;

        }
        return super.onOptionsItemSelected(item);
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
