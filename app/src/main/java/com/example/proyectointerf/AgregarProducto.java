package com.example.proyectointerf;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectointerf.BD.AdminSQLiteOpenHelper;

public class AgregarProducto extends AppCompatActivity {
    Spinner spn1;
    EditText etID, etNombre, etDescripcion;
    RadioButton rbLocal, rbImportado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);
        getSupportActionBar().hide();
        spn1=(Spinner) findViewById(R.id.spinner_PN);
        String [] op1 ={"Arte y Manualidades","Escolares y Oficina","Computo y Electrónica","Papeles y Cartulina"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,op1);
        spn1.setAdapter(adapter1);
        etID=(EditText)findViewById(R.id.etID);
        etNombre=(EditText)findViewById(R.id.et_nombre_PN);
        etDescripcion=(EditText)findViewById(R.id.et_desc_PN);
        rbLocal=(RadioButton)findViewById(R.id.rb_local);
        rbImportado=(RadioButton)findViewById(R.id.rb_import);

    }

    public void registrar (View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String idPr = etID.getText().toString();
        String nomp = etNombre.getText().toString();
        String desc = etDescripcion.getText().toString();
        String nacion="";
        if (rbImportado.isChecked())
            nacion = "Importado";
        if (rbLocal.isChecked())
            nacion = "Nacional";
        String seleccionSpinner = spn1.getSelectedItem().toString();

        if (!idPr.isEmpty() && !nomp.isEmpty() && !desc.isEmpty() && !nacion.isEmpty() && !seleccionSpinner.isEmpty()) {
            ContentValues registro = new ContentValues();

            registro.put("idProducto", idPr);
            registro.put("nombre", nomp);
            registro.put("descripcion", desc);
            registro.put("tipo", nacion);
            registro.put("nacionalidad", seleccionSpinner);

            if( BaseDeDatos != null){
                try {
                    BaseDeDatos.insert("producto",null,registro);
                } catch (SQLException e){
                    Log.e("Exception","Error: "+String.valueOf(e.getMessage()));
                }
                BaseDeDatos.close();
            }

            //Limpiar los campos de texto
            etID.setText("");
            etNombre.setText("");
            etDescripcion.setText("");
            etID.requestFocus();

            //Confirmar la operación realizada
            Toast.makeText(this,"Producto registrado",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"Debes registrar primero los datos",Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelar(View view){
        Intent i = new Intent(this,Productos.class);
        startActivity(i);
    }

}
