package com.example.tp4_pa2_grupo12;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp4_pa2_grupo12.ArticuloAdapter;
import com.example.tp4_pa2_grupo12.conexion.DataMainActivity;
import com.example.tp4_pa2_grupo12.entidades.Articulo;

import java.util.ArrayList;

public class Listado extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArticuloAdapter adapter;
    private DataMainActivity dataMainActivity;
    private ArrayList<Articulo> articulos;
    private Button btnAlta, btnModificacion, btnListado;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        articulos = new ArrayList<>();

        adapter = new ArticuloAdapter(articulos);
        recyclerView.setAdapter(adapter);

        // Inicializar DataMainActivity
        dataMainActivity = new DataMainActivity(this);
        loadArticulos();


        btnAlta = findViewById(R.id.btnAlta);
        btnModificacion = findViewById(R.id.btnModificacion);
        btnListado = findViewById(R.id.btnListado);

        btnAlta.setOnClickListener(view -> {
            Intent intent = new Intent(Listado.this, MainActivity.class);
            startActivity(intent);
        });
        btnModificacion.setOnClickListener(view -> {
            Intent intent = new Intent(Listado.this, Modificacion.class);
            startActivity(intent);
        });
        btnListado.setOnClickListener(view -> {
            Toast.makeText(this, "Ya estás en Listado", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadArticulos() {
        dataMainActivity.getArticulos(new DataMainActivity.DataCallback<ArrayList<Articulo>>() {
            @Override
            public void onSuccess(ArrayList<Articulo> result) {
                articulos.clear(); // Limpiar la lista antes de agregar nuevos artículos
                articulos.addAll(result); // Agregar nuevos artículos
                adapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
            }

            @Override
            public void onFailure(Exception e) {
                // Manejar el error, por ejemplo, mostrando un Toast
                Toast.makeText(Listado.this, "Error al cargar los artículos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
