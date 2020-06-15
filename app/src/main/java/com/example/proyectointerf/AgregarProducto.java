package com.example.proyectointerf;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class AgregarProducto extends AppCompatActivity {
    Spinner spn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);

        spn1=(Spinner) findViewById(R.id.spinner_PN);

        String [] op1 ={"Elige uno","Arte y Manualidades","Escolares y Oficina","Computo y Electrónica","Papeles y Cartulina"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,op1);
        spn1.setAdapter(adapter1);
        getSupportActionBar().hide();
    }

    public void ir(View view){
        Intent i = new Intent(this,Productos.class);
        startActivity(i);
    }
}
