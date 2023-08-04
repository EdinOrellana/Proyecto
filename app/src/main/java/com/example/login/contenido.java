package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class contenido extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
     ImageButton btnsave, btnDelete,btnAtras;
     TextView tvContenidoTitulo,tvContenidoDescripcion;
     String id = null;
     Boolean BanderaGuardar_Actualizar = true;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenido);
        tvContenidoTitulo=findViewById(R.id.tvContenidoTitulo);
        tvContenidoDescripcion=findViewById(R.id.tvContenidoDescripcion);
        databaseHelper = new DatabaseHelper(this);

        //obtiene el id de la tarea
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            database = databaseHelper.getWritableDatabase();
            id = bundle.getString("Message_key");
            String[] columnas = {DatabaseHelper.COLUMN_TITLE, DatabaseHelper.COLUMN_DESCRIPCION};
            String[] parametros={id};
            BanderaGuardar_Actualizar=false;
            try {
                Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columnas, DatabaseHelper.COLUMN_ID + "=?", parametros, null, null, null);
                cursor.moveToFirst();
               tvContenidoTitulo.setText(cursor.getString(0));
                tvContenidoDescripcion.setText(cursor.getString(1));
                cursor.close();
                database.close();
            }catch (Exception e){
                Toast.makeText(contenido.this,id+e,Toast.LENGTH_SHORT).show();
            }
        }
        btnsave = findViewById(R.id.btnContenidoSave);
        btnsave.setOnClickListener(v -> {
            try {
                if (!BanderaGuardar_Actualizar) {
                    database = databaseHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(DatabaseHelper.COLUMN_TITLE, tvContenidoTitulo.getText().toString());
                    values.put(DatabaseHelper.COLUMN_DESCRIPCION, tvContenidoDescripcion.getText().toString());
                    String[] parametros = {id};
                    database.update(DatabaseHelper.TABLE_NAME, values, DatabaseHelper.COLUMN_ID + "=?", parametros);
                    Toast.makeText(contenido.this, "Acttualizado", Toast.LENGTH_SHORT).show();
                    database.close();
                } else {
                    database = databaseHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(DatabaseHelper.COLUMN_TITLE, tvContenidoTitulo.getText().toString());
                    values.put(DatabaseHelper.COLUMN_DESCRIPCION, tvContenidoDescripcion.getText().toString());
                    database.insert(DatabaseHelper.TABLE_NAME, null, values);
                    Toast.makeText(contenido.this, "Guardado", Toast.LENGTH_SHORT).show();
                    database.close();
                }
            } catch (Exception e) {
                Toast.makeText(contenido.this, "Error", Toast.LENGTH_SHORT).show();
            }
            finish();
        });

btnDelete=findViewById(R.id.btnEliminar);
btnDelete.setOnClickListener(v -> {
    try {
        database = databaseHelper.getWritableDatabase();
        String[] parametros = {id};
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.COLUMN_ID + "=?", parametros);
        Toast.makeText(contenido.this, "Eliminado", Toast.LENGTH_SHORT).show();
        database.close();
        finish();
    }catch (Exception e){
        Toast.makeText(contenido.this, "Error al elimniar", Toast.LENGTH_SHORT).show();
    }

});
        btnAtras=findViewById(R.id.btnContenidoAtras);
        btnAtras.setOnClickListener(v -> finish());
    }

}