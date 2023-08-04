package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
public class Tareas extends AppCompatActivity {

    public static RecyclerView recyclerView;
    public static List<ListItem> itemList;
    public static itemAdapter itemadapter;

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private Button btnAgregar;
    ImageButton btnrecargar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);

        databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getWritableDatabase();

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        itemList = new ArrayList<>();

        itemadapter = new itemAdapter(itemList);
        recyclerView.setAdapter(itemadapter);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(500);
        itemAnimator.setRemoveDuration(500);
        itemAnimator.setChangeDuration(500);
        itemAnimator.setMoveDuration(500);
        recyclerView.setItemAnimator(itemAnimator);

        itemadapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje=itemList.get(recyclerView.getChildAdapterPosition(v)).getId();
                Intent intent=new Intent(Tareas.this, contenido.class);
                intent.putExtra("Message_key",mensaje);
                startActivity(intent);

            }
        });

        btnAgregar=findViewById(R.id.btnAgregar);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Tareas.this,"boton agregar",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Tareas.this, contenido.class);
                startActivity(intent);
            }
        });


    }
    protected void onStart () {
        super.onStart();
        readTask();
    }

    private void readTask() {
        itemadapter.notifyItemRangeRemoved(itemList.size() - 1, itemList.size() + 1);
        itemList.clear();
        try {
            String[] columnas = { DatabaseHelper.COLUMN_TITLE,DatabaseHelper.COLUMN_ID,DatabaseHelper.COLUMN_DESCRIPCION};

            Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columnas, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    String Titulo = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TITLE));
                    String idTarea = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                    String Descripcion = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPCION));
                    String a= Descripcion.replaceFirst("^(.{35}).*", "$1");
                    ListItem newItem = new ListItem(Titulo, idTarea, a+"...");
                    itemList.add(newItem);
                    itemadapter.notifyItemInserted(itemList.size() - 1);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(Tareas.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }
}