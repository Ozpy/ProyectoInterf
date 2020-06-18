package com.example.proyectointerf;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AgregarProducto extends AppCompatActivity {
    private GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount signInAccount;

    private DatabaseReference mRootReference;    //Agrgar para la base de datos

    Spinner spn1;
    EditText etID, etNombre, etDescripcion;
    RadioButton rbLocal, rbImportado;

    String nomp ;
    String desc ;
    String nacion;
    String categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);
        getSupportActionBar().hide();
        spn1=(Spinner) findViewById(R.id.spinner_PN);
        String [] op1 ={"Arte y Manualidades","Escolares y Oficina","Computo y Electrónica","Papeles y Cartulina"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,op1);
        spn1.setAdapter(adapter1);

        etNombre=(EditText)findViewById(R.id.et_nombre_PN);
        etDescripcion=(EditText)findViewById(R.id.et_desc_PN);
        rbLocal=(RadioButton)findViewById(R.id.rb_local);
        rbImportado=(RadioButton)findViewById(R.id.rb_import);
        mRootReference = FirebaseDatabase.getInstance().getReference(); //Hace referencia a la base de datos en el nodo principal

    }

    public void registrar (View view) {

        LlenarDatos();

        if (!nomp.isEmpty() && !desc.isEmpty() && !nacion.isEmpty()) {
            LlenarDatosFirebase();

            Toast.makeText(this, "Registrado correctamente", Toast.LENGTH_SHORT).show();

            LimpiarCampos();

            //Confirmar la operación realizada
            Toast.makeText(this,"Producto registrado",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"Debes registrar primero los datos",Toast.LENGTH_SHORT).show();
        }
    }

    private void LimpiarCampos() {
        //Limpiar los campos de texto
        etNombre.setText("");
        etDescripcion.setText("");
    }

    private void LlenarDatosFirebase() {            //En firebase
        Map<String, Object> datosUsuario = new HashMap<>();

        datosUsuario.put("nombre", nomp);
        datosUsuario.put("descripcion", desc);
        datosUsuario.put("nacionalidad", nacion);
        datosUsuario.put("categoria", categoria);

        mRootReference.child("Producto").push().setValue(datosUsuario);
        Toast.makeText(this, "Registrado correctamente", Toast.LENGTH_SHORT).show();
    }

    private void LlenarDatos() {
        nomp = etNombre.getText().toString();
        desc = etDescripcion.getText().toString();
        nacion="";

        if (rbImportado.isChecked())
            nacion = "Importado";
        if (rbLocal.isChecked())
            nacion = "Nacional";
        categoria= spn1.getSelectedItem().toString();
    }

    public void cancelar(View view){
        Intent i = new Intent(this,Productos.class);
        startActivity(i);
    }
}
