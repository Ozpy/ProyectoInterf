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
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);//NOMBRE DE ADMINISTRADOR
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        Cursor fila = BaseDeDatos.rawQuery("select * from producto", null);
        if (fila.moveToFirst()) {
            do {
                listaProductos.add(new Producto(fila.getString(0), fila.getString(1), R.mipmap.lapiz));
            } while (fila.moveToNext());
            BaseDeDatos.close();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_productos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.item_add:
                intent = new Intent(this,AgregarProducto.class);
                startActivity(intent);
                this.finish();
                return true;
            case R.id.item_contactos:
                intent = new Intent(this,Contactos.class);
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
    public void ir(View view){
        Intent i= new Intent(this,Chat.class);
        startActivity(i);
    }
}
