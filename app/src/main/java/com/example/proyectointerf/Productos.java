package com.example.proyectointerf;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Productos extends AppCompatActivity {
    ArrayList<Producto> listaProductos;
    RecyclerView recyclerProductos;
    DatabaseReference mRootReference;
    AdapterProductos adapterProductos;
    ProductoFirebase prod;
    String nombre;
    String descripcion;
    int foto ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        //Esconder barra superior
        getSupportActionBar().hide();

        listaProductos = new ArrayList<>();
        recyclerProductos= (RecyclerView)findViewById(R.id.recyclerView2);
        mRootReference = FirebaseDatabase.getInstance().getReference(); //Hace referencia a la base de datos en el nodo principal
        recyclerProductos.setLayoutManager(new LinearLayoutManager(this));
        llenarProductos();
        adapterProductos = new AdapterProductos(listaProductos);
        recyclerProductos.setAdapter(adapterProductos);
    }

    private void llenarProductos() {

        mRootReference.child("Producto").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                borrarData();
                for(final DataSnapshot snapshot : dataSnapshot.getChildren()){
                    mRootReference.child("Producto").child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            prod = snapshot.getValue(ProductoFirebase.class);
                            nombre = prod.getNombre();
                            descripcion = prod.getDescripcion();
                                agregarCard(nombre,descripcion);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                    recyclerProductos.setAdapter(adapterProductos);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void borrarData() {
        listaProductos.clear(); //Borras la data con la que llenas el recyclerview
        adapterProductos.notifyDataSetChanged(); //le notificas al adaptador que no hay nada para llenar la vista
    }
    private void agregarCard(String nombre, String descripcion) {
        listaProductos.add(new Producto(nombre,descripcion));
        recyclerProductos.setAdapter(adapterProductos);
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
