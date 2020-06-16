package com.example.proyectointerf;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.proyectointerf.BD.AdminSQLiteOpenHelper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class Perfil extends AppCompatActivity  {

    private GoogleSignInClient mGoogleSignInClient;
    TextView name, mail;
    EditText et_calle,et_colonia,et_codigopost;
    String id_local,id_fire,nombre,correo,calle,colonia,codigopost;
    Button logout;
    ImageView perfilImagen;
    TextView tv;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //Esconder barra superior
        getSupportActionBar().hide();

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
            Glide.with(this).load(String.valueOf(personPhoto)).into(perfilImagen);
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

    public void ir(View view){
        Intent i = new Intent(this,Productos.class);
        startActivity(i);
    }

    private void ReiniciarTabla(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);//NOMBRE DE ADMINISTRADOR
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        BaseDeDatos.execSQL("DROP TABLE IF EXISTS usuario");
        BaseDeDatos.execSQL("create table usuario(id_firebase text primary key ,nombre text,correo text, calle text,colonia text, codigopostal text)");
        BaseDeDatos.close();
    }
}

