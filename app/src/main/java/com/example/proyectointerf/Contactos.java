package com.example.proyectointerf;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectointerf.BD.AdminSQLiteOpenHelper;

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


        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);//NOMBRE DE ADMINISTRADOR
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();


        Cursor fila = BaseDeDatos.rawQuery("select * from usuario", null);
        String guardado;
        if(fila.moveToFirst()){
            do {
                listaContactos.add(new ContactoVo("HOALALSOASSAD","asasadda@gmail.com",R.mipmap.usuario));
            }while (fila.moveToNext());
            BaseDeDatos.close();
        }
    }
    public void ir(View view){
        Intent i = new Intent(this,AgregarProducto.class);
        startActivity(i);
    }

}
