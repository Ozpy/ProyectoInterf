package com.example.proyectointerf;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectointerf.BD.AdminSQLiteOpenHelper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Contactos extends AppCompatActivity {

    ArrayList<ContactoVo> listaContactos;
    RecyclerView recyclerContactos;
    UserFirebase user;
    DatabaseReference mRootReference;    //Agrgar para la base de datos
    AdapterContactos adapterContactos;
    GoogleSignInAccount signInAccount ;
    MenuItem menuItemAdd;
    int tipo=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);

        //Esconder barra superior
        //getSupportActionBar().hide();
        signInAccount = GoogleSignIn.getLastSignedInAccount(this);//Google acount
        mRootReference = FirebaseDatabase.getInstance().getReference(); //Hace referencia a la base de datos en el nodo principal

        listaContactos = new ArrayList<>();
        recyclerContactos= (RecyclerView)findViewById(R.id.recyclerView);
        recyclerContactos.setLayoutManager(new LinearLayoutManager(this));

        adapterContactos=new AdapterContactos(listaContactos);

        adapterContactos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailC=listaContactos.get(recyclerContactos.getChildAdapterPosition(v)).getEmail();//.toString()
                ir(emailC);
            }
        });
        solicitarDatosFirebase();

        recyclerContactos.setAdapter(adapterContactos);
    }


    //FIREBASE
    private void solicitarDatosFirebase() {

        mRootReference.child("Usuario").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                borrarData();
                final int[] i = {0};
                for(final DataSnapshot snapshot : dataSnapshot.getChildren()){
                    mRootReference.child("Usuario").child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            user = snapshot.getValue(UserFirebase.class);
                            String nombre = user.getNombre();
                            String correo = user.getCorreo();
                            String estado = user.getEstado();
                            String guard;
                            i[0]++;
                            Log.e("rrrrrrrrrrrrrrrrrrrrrrr", ""+i[0]);
                                if(signInAccount.getEmail().equals(correo)){
                                }else{
                                    agregarCard(nombre,correo,estado);
                                }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                    recyclerContactos.setAdapter(adapterContactos);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    } //AGREGA CARD
    private void verDatosFirebase(){
        //DatabaseReference mRootReference;    //Agrgar para la base de datos
        mRootReference = FirebaseDatabase.getInstance().getReference();

        mRootReference.child("Usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final int[] i = {0};
                for(final DataSnapshot snapshot : dataSnapshot.getChildren()){
                    mRootReference.child("Usuario").child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            user = snapshot.getValue(UserFirebase.class);
                            String nombre = user.getNombre();
                            String correo = user.getCorreo();
                            String calle = user.getCalle();
                            String colonia = user.getColonia();
                            String codigopost = user.getCodigopost();
                            String foto = user.getFoto();
                            i[0]++;
                            Log.e("\nNombre:", nombre);
                            Log.e("\nCorreo:", correo);
                            Log.e("\nCalle:", calle);
                            Log.e("\nColonia:", colonia);
                            Log.e("\nCodigoPostal:", codigopost);
                            Log.e("\nFoto:", foto);
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }


                    });
                    recyclerContactos.setAdapter(adapterContactos);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

    }
    private void ComprobarTipoSolicitarDatosFirebase() {

        //TRUE REPETIDO & FALSE NO REPETIDO
        mRootReference.child("Usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(final DataSnapshot snapshot : dataSnapshot.getChildren()){

                    mRootReference.child("Usuario").child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            UserFirebase user = snapshot.getValue(UserFirebase.class);
                            String nombre = user.getNombre();
                            String correo = user.getCorreo();
                            String tipos= user.getTipo();

                            Log.e("NombreUsuario:",""+nombre);
                            Log.e("Correo:",""+correo);

                            Log.e("Datos:",""+snapshot.getValue());

                            if(signInAccount.getEmail().equals(correo)){
                                if(tipos.equals("Cliente")){
                                tipo=0;
                                    Toast.makeText(Contactos.this, "Eres cliente", Toast.LENGTH_SHORT).show();

                                }else{
                                    tipo=1;
                                    Toast.makeText(Contactos.this, "Eres empleado", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    } //Cambia el valor e la variable globar repetido a 1 si esta repetido el e-mail

    //SQLite
    private void llenarContactos() {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);//NOMBRE DE ADMINISTRADOR
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();


        Cursor fila = BaseDeDatos.rawQuery("select * from usuario", null);
        String guardado;

        if(fila.moveToFirst()){
            do {
       //         listaContactos.add(new ContactoVo(fila.getString(1),fila.getString(2),R.mipmap.usuario));
            }while (fila.moveToNext());
            BaseDeDatos.close();
        }
    }


    //GENERALES
    private void agregarCard(String nombre, String correo,String estado) {

        listaContactos.add(new ContactoVo(nombre,correo,R.mipmap.usuario, "ok"));
        recyclerContactos.setAdapter(adapterContactos);
    }
    public void borrarData() {
        listaContactos.clear(); //Borras la data con la que llenas el recyclerview
        adapterContactos.notifyDataSetChanged(); //le notificas al adaptador que no hay nada para llenar la vista
    }
    private void ir(String emailC) {
        Bundle parametro = new Bundle();
        parametro.putString("emailC", emailC);
        Intent i;
        i = new Intent(this, Chat.class);
        i.putExtras(parametro);
        startActivity(i);
        this.finish();
    }

    //MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contactos, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {

        //TRUE REPETIDO & FALSE NO REPETIDO
        mRootReference.child("Usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(final DataSnapshot snapshot : dataSnapshot.getChildren()){

                    mRootReference.child("Usuario").child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            UserFirebase user = snapshot.getValue(UserFirebase.class);
                            String nombre = user.getNombre();
                            String correo = user.getCorreo();
                            String tipos= user.getTipo();

                            Log.e("NombreUsuario:",""+nombre);
                            Log.e("Correo:",""+correo);

                            Log.e("Datos:",""+snapshot.getValue());

                            if(signInAccount.getEmail().equals(correo)){
                                if(tipos.equals("Cliente")){
                                    menu.getItem(1).setEnabled(false);
                                    Toast.makeText(Contactos.this, "Eres cliente", Toast.LENGTH_SHORT).show();

                                }else{
                                    menu.getItem(1).setEnabled(true);
                                    Toast.makeText(Contactos.this, "Eres empleado", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

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

