package com.example.proyectointerf;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.proyectointerf.BD.AdminSQLiteOpenHelper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Perfil extends AppCompatActivity  {

    private GoogleSignInClient mGoogleSignInClient;

    private DatabaseReference mRootReference;    //Agrgar para la base de datos


    TextView name, mail;
    EditText et_calle,et_colonia,et_codigopost;
    String id_fire,nombre,correo,calle,colonia,foto,codigopost;
    Button logout;
    ImageView perfilImagen;
    TextView tv;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);


        //getSupportActionBar().hide();    //Esconder barra superior


        mRootReference= FirebaseDatabase.getInstance().getReference(); //Hace referencia a la base de datos en el nodo principal

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);

        logout = findViewById(R.id.logout);
        name = findViewById(R.id.name);
        mail = findViewById(R.id.mail);
        perfilImagen = findViewById(R.id.imagen_perfil);
        et_calle=findViewById(R.id.edt_calle);
        et_codigopost=findViewById(R.id.edt_pc);
        et_colonia=findViewById(R.id.edt_col);

        if(signInAccount != null){
            Uri personPhoto = signInAccount.getPhotoUrl();

            name.setText(signInAccount.getDisplayName());
            mail.setText(signInAccount.getEmail());
            foto=String.valueOf(personPhoto);
            Glide.with(this).load(foto).into(perfilImagen);      //MANDAR FOTO
        }


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleSignInClient.signOut();
                FirebaseAuth.getInstance().signOut();

                Toast.makeText(Perfil.this, "You are logged out", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Perfil.this,MainActivity.class);
                startActivity(i);
            }
        });

    }

    public void registrar (View view) {
        leer();
        Toast.makeText(this, "registrar", Toast.LENGTH_SHORT).show();
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);//NOMBRE DE ADMINISTRADOR
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();

        registro.put("id_firebase",id_fire);
        registro.put("nombre",nombre);
        registro.put("correo",correo);
        registro.put("calle",calle);
        registro.put("colonia",colonia);
        registro.put("codigopostal",codigopost);

        BaseDeDatos.insert("usuario",null,registro); //NOMBRE DE BASE DE DATOS
        BaseDeDatos.close();
        Toast.makeText(this, "Registrado Correctamente", Toast.LENGTH_SHORT).show();


        //EN FIREBASE
        LlenarDatosFirebase();

        ir();

    }

    private void LlenarDatosFirebase() {            //En firebase
        Map<String,Object> datosUsuario = new HashMap<>();

        datosUsuario.put("nombre",nombre);
        datosUsuario.put("correo",correo);
        datosUsuario.put("calle",calle);
        datosUsuario.put("colonia",colonia);
        datosUsuario.put("codigopostal",codigopost);
        datosUsuario.put("tipo","Cliente"); //Cliente o Empleado
        datosUsuario.put("foto",foto);
        mRootReference.child("Usuario").push().setValue(datosUsuario);
    }

    private void leer(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);

        id_fire=signInAccount.getId();
        nombre=signInAccount.getDisplayName();
        correo=signInAccount.getEmail();
        calle=et_calle.getText().toString();
        colonia=et_colonia.getText().toString();
        codigopost=et_codigopost.getText().toString();

    }
    public void mostrarDatos(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);//NOMBRE DE ADMINISTRADOR
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        tv.setText("");
        Cursor fila = BaseDeDatos.rawQuery("select * from usuario", null);
        String guardado;
        if(fila.moveToFirst()){
            do {
                guardado= tv.getText().toString();
                tv.setText(guardado+fila.getString(0)+"   "+fila.getString(1)+"   "+fila.getString(2)+"   "+fila.getString(3)+"   "+fila.getString(4)+"   "+fila.getString(5)+"\n");
            }while (fila.moveToNext());
            BaseDeDatos.close();
        }
    }

    public void ir(){
        Intent i = new Intent(this,Contactos.class);
        startActivity(i);
        this.finish();
    }

    private void ReiniciarTabla(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);//NOMBRE DE ADMINISTRADOR
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        BaseDeDatos.execSQL("DROP TABLE IF EXISTS usuario");
        BaseDeDatos.execSQL("create table usuario(id_firebase text primary key ,nombre text,correo text, calle text,colonia text, codigopostal text)");
        BaseDeDatos.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_perfil, menu);
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
            case R.id.item_contactos:
                intent = new Intent(this,Contactos.class);
                startActivity(intent);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void solicitarDatosFirebase() {
        mRootReference.child("Usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(final DataSnapshot snapshot : dataSnapshot.getChildren()){

                    mRootReference.child("Usuario").child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            UserFirebase user = snapshot.getValue(UserFirebase.class);
                            String nombre = user.getNombre();
                            String apellido = user.getCorreo();
                            String telefono = user.getCalle();
                            String direccion = user.getCodigopost();

                            Log.e("NombreUsuario:",""+nombre);
                            Log.e("ApellidoUsuario:",""+apellido);
                            Log.e("TelefonoUsuario:",""+telefono);
                            Log.e("DireccionUsuario:",""+direccion);
                            Log.e("Datos:",""+snapshot.getValue());
                            Toast.makeText(Perfil.this, ""+nombre, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });



                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}