package com.example.proyectointerf;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
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
        //getSupportActionBar().hide();

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
                listaContactos.add(new ContactoVo(fila.getString(1),fila.getString(2),R.mipmap.usuario));
            }while (fila.moveToNext());
            BaseDeDatos.close();
        }
    }
    public void ir(View view){
        Intent i = new Intent(this,AgregarProducto.class);
        startActivity(i);
        this.finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contactos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.item_lista:
                intent = new Intent(this,Productos.class);
                startActivity(intent);
                this.finish();
                return true;
            case R.id.item_add:
                intent = new Intent(this,AgregarProducto.class);
                startActivity(intent);
                this.finish();
                return true;
            case R.id.item_perfil:
                intent = new Intent(this,Perfil.class);
                startActivity(intent);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
