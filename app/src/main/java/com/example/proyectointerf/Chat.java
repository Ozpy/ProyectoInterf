package com.example.proyectointerf;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient; //CONSEGUIR DATOS PRINCIPAL
    GoogleSignInAccount signInAccount;

    CircleImageView fotoPerfil;
    TextView nombre;
    RecyclerView rvMensajes;
    EditText txtMensaje;
    ImageButton btnEnviar,btnEnviarFoto;
    int tipo;
    String nombreSala;
    String nombrePrincipal,correoPrincipal; //DATOS DEL PRINCIPAL


    //MESAJES OSCAR
    DatabaseReference mRootReference;    //Agrgar para la base de datos
    String sala="SALA";
    ImageButton btnRegresar;

    //TERMINA
    AdapterMensajes adapter;
    TextView tv_nombre;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    FirebaseStorage storage;
    StorageReference storageReference;
    static final int PHOTO_SEND = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        nombreSala=getIntent().getStringExtra("nombre");
        sala=nombreSala;
        getSupportActionBar().hide();

        mRootReference = FirebaseDatabase.getInstance().getReference(); //Hace referencia a la base de datos en el nodo principal

        //Datos de cuenta actual
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInAccount = GoogleSignIn.getLastSignedInAccount(this);

        tv_nombre=findViewById(R.id.tv_nombrePicado);

        tv_nombre.setText(sala); //Si el que entro es admin
        btnRegresar=findViewById(R.id.btn_regresar);
        fotoPerfil = (CircleImageView) findViewById(R.id.imagen_perfil);
        nombre = (TextView) findViewById(R.id.tv_nombrePicado);
        rvMensajes=(RecyclerView)findViewById(R.id.rvMensajes);
        txtMensaje=(EditText)findViewById(R.id.etMensaje);
        btnEnviar=(ImageButton)findViewById(R.id.ibEnviar);
        btnEnviarFoto=(ImageButton)findViewById(R.id.ibEnviarFoto);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ir();
            }
        });
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("chat"); // Sala de Chat (Nombre)
        storage = FirebaseStorage.getInstance();

        RecuperarDatosCuenta();


        adapter = new AdapterMensajes(this);
        LinearLayoutManager l = new LinearLayoutManager(this);
        rvMensajes.setLayoutManager(l);
        rvMensajes.setAdapter(adapter);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar c = Calendar.getInstance();

                String datetime = DateFormat.getDateInstance().format(new Date());
                databaseReference.push().setValue(new Mensaje(txtMensaje.getText().toString(),nombre.getText().toString(),"","1",datetime+"   "+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE),sala));
                txtMensaje.setText("");
            }
        });

        btnEnviarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(i,"Selecciona una foto"),PHOTO_SEND);

            }
        });
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollbar();
            }
        });
/*
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Mensaje m = dataSnapshot.getValue(Mensaje.class);
                adapter.addMensaje(m);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

*/
        adapter.borrarMensajes();
        ComprobarTipoMostrarClienteSolicitarDatosFirebase();
    }

    public void setScrollbar(){
        rvMensajes.scrollToPosition(adapter.getItemCount()-1);
    }

    @Override //AQUÍ ES EL BUENO CAROLE
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_SEND && resultCode == RESULT_OK) {
            Uri u = data.getData();

            storageReference = storage.getReference("imagenes_chat");//Imágenes de chat
            final StorageReference fotoReferencia = storageReference.child("image "+u.getLastPathSegment());
            //  fotoReferencia.putFile(u).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {

            fotoReferencia.putFile(u).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fotoReferencia.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Calendar c = Calendar.getInstance();
                    String datetime = DateFormat.getDateInstance().format(new Date());
                    Mensaje m = new Mensaje(txtMensaje.getText().toString(),nombre.getText().toString(),"","2",datetime+" "+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE),sala);
                    databaseReference.push().setValue(m);

                }
                    });

//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Calendar c = Calendar.getInstance();
//                    String datetime = DateFormat.getDateInstance().format(new Date());
//                Uri u = taskSnapshot.getDownloadUrl();
//                Mensaje m = new Mensaje("Kevin ha enviado una foto",u.toString(),nombre.getText().toString(),"","2",datetime+"   "+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE));
//                databaseReference.push().setValue(m);
//                }
            //});
                    // mensajito msj = new mensajito(nombre1.getText().toString(), "Josue ha enviado una foto", ur.getResult().toString(), "2", "", "00:00");
                    //databaseReference.push().setValue(msj);
                }
            });
        }
    }

    private void ComprobarTipoMostrarClienteSolicitarDatosFirebase() {
        //TRUE REPETIDO & FALSE NO REPETIDO
        mRootReference.child("chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.borrarMensajes();
                for(final DataSnapshot snapshot : dataSnapshot.getChildren()){

                    mRootReference.child("chat").child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Mensaje m = dataSnapshot.getValue(Mensaje.class);

                            String salita = m.getSala();

                            if(salita.equals(nombreSala)){
                                agregarCard(m);
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
    }

    private void agregarCard(Mensaje m) {
        adapter.addMensaje(m);
    }

    private void RecuperarDatosCuenta() {
        if (signInAccount != null) {
            nombrePrincipal=signInAccount.getDisplayName();
            correoPrincipal=signInAccount.getEmail();
            Toast.makeText(this, nombrePrincipal+correoPrincipal, Toast.LENGTH_SHORT).show();
        }
    }
    public void borrarData() {
        adapter.borrarMensajes();
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

    public void ir(){
        Intent i = new Intent(this,Contactos.class);
        startActivity(i);
    }
    public void regresar(View view){
        Intent i = new Intent(this,Perfil.class);
        startActivity(i);
    }

}