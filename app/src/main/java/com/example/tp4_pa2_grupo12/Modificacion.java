package com.example.tp4_pa2_grupo12;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tp4_pa2_grupo12.conexion.DataMainActivity;

public class Modificacion extends AppCompatActivity {

    private EditText txtIdArticulo, txtNombreProducto, txtStock;
    private Button btnBuscar;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_modificacion);

         txtIdArticulo = findViewById(R.id.txt_id);
         txtNombreProducto = findViewById(R.id.txt_nombreProducto);
         txtStock = findViewById(R.id.txt_stock);
         btnBuscar = findViewById(R.id.btnBuscar);

         DataMainActivity dataMainActivity = new DataMainActivity(this, txtNombreProducto, txtStock, btnBuscar);
         dataMainActivity.buscarArticulo(txtIdArticulo);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}