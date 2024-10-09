package com.example.tp4_pa2_grupo12;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tp4_pa2_grupo12.conexion.DataMainActivity;

public class Listado extends AppCompatActivity {

    private Button btnAlta, btnModificacion, btnListado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listado);

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
}