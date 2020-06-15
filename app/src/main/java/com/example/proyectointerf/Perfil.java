package com.example.proyectointerf;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class Perfil extends AppCompatActivity  {

    private GoogleSignInClient mGoogleSignInClient;
    TextView name, mail;
    Button logout;
    ImageView perfilImagen;

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




        logout = findViewById(R.id.logout);
        name = findViewById(R.id.name);
        mail = findViewById(R.id.mail);
        perfilImagen = findViewById(R.id.imagen_perfil);

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
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
    public void ir(View view){
        Intent i = new Intent(this,Chat.class);
        startActivity(i);
    }
}

