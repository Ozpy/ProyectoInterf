package com.example.proyectointerf;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
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

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends AppCompatActivity {

    CircleImageView fotoPerfil;
    TextView nombre;
    RecyclerView rvMensajes;
    EditText txtMensaje;
    ImageButton btnEnviar,btnEnviarFoto;

    AdapterMensajes adapter;

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    FirebaseStorage storage;
    StorageReference storageReference;
    static final int PHOTO_SEND = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //Esconder barra superior
        getSupportActionBar().hide();

        fotoPerfil = (CircleImageView) findViewById(R.id.imagen_perfil);
        nombre = (TextView) findViewById(R.id.textView2);
        rvMensajes=(RecyclerView)findViewById(R.id.rvMensajes);
        txtMensaje=(EditText)findViewById(R.id.etMensaje);
        btnEnviar=(ImageButton)findViewById(R.id.ibEnviar);
        btnEnviarFoto=(ImageButton)findViewById(R.id.ibEnviarFoto);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("chat"); // Sala de Chat (Nombre)
        storage = FirebaseStorage.getInstance();


        adapter = new AdapterMensajes(this);
        LinearLayoutManager l = new LinearLayoutManager(this);
        rvMensajes.setLayoutManager(l);
        rvMensajes.setAdapter(adapter);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar c = Calendar.getInstance();

                String datetime = DateFormat.getDateInstance().format(new Date());
                databaseReference.push().setValue(new Mensaje(txtMensaje.getText().toString(),nombre.getText().toString(),"","1",datetime+"   "+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)));
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
    }

    public void setScrollbar(){
        rvMensajes.scrollToPosition(adapter.getItemCount()-1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_SEND && resultCode == RESULT_OK){
            Uri u = data.getData();
            storageReference = storage.getReference("imagenes_chat");//Imágenes de chat
            final StorageReference fotoReferencia = storageReference.child(u.getLastPathSegment());
          //  fotoReferencia.putFile(u).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {

            fotoReferencia.putFile(u).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                 @Override
                 public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                     if(!task.isSuccessful()){
                         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                             throw Objects.requireNonNull(task.getException());
                         }
                     }
                     return fotoReferencia.getDownloadUrl();
                 }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Calendar c = Calendar.getInstance();
                        String datetime = DateFormat.getDateInstance().format(new Date());
                        Uri downloadUrl = task.getResult();
                        Mensaje m = new Mensaje("Kevin te ha enviado una foto",downloadUrl.toString(),nombre.getText().toString(),"","2",datetime+"   "+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE));
                        databaseReference.push().setValue(m);
                        Glide.with(Chat.this).load(downloadUrl.toString()).into(fotoPerfil);
                        Toast.makeText(Chat.this, "Subida exitosamente", Toast.LENGTH_SHORT).show();
                    }
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
        }
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