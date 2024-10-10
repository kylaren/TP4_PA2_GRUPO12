package com.example.tp4_pa2_grupo12;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
