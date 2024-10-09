package com.example.tp4_pa2_grupo12;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
<<<<<<< Updated upstream
=======
import android.widget.Spinner;
>>>>>>> Stashed changes
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tp4_pa2_grupo12.conexion.DataMainActivity;
import com.example.tp4_pa2_grupo12.entidades.Categoria;

public class Modificacion extends AppCompatActivity {

    private EditText txtIdArticulo, txtNombreProducto, txtStock;
<<<<<<< Updated upstream
    private Button btnBuscar;
    private Button btnAlta, btnModificacion, btnListado;

=======
    private Button btnBuscar, btnModificar;
    private Spinner spnCategoria;
>>>>>>> Stashed changes
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_modificacion);

         txtIdArticulo = findViewById(R.id.txt_id);
         txtNombreProducto = findViewById(R.id.txt_nombreProducto);
         txtStock = findViewById(R.id.txt_stock);
         btnBuscar = findViewById(R.id.btnBuscar);
         spnCategoria = findViewById(R.id.spinnerCategoria);
         btnModificar = findViewById(R.id.btnModificar);



         DataMainActivity dataMainActivity = new DataMainActivity(this, txtNombreProducto, txtStock, btnBuscar, spnCategoria);

         dataMainActivity.fetchCategorias();
         dataMainActivity.buscarArticulo(txtIdArticulo);

<<<<<<< Updated upstream
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

         btnAlta = findViewById(R.id.btnAlta);
         btnModificacion = findViewById(R.id.btnModificacion);
         btnListado = findViewById(R.id.btnListado);

         btnAlta.setOnClickListener(view -> {

             Intent intent = new Intent(Modificacion.this, MainActivity.class);
             startActivity(intent);
         });

         btnModificacion.setOnClickListener(view -> {

             Toast.makeText(this, "Ya estás en MODIFICACION", Toast.LENGTH_SHORT).show();

         });

        btnListado.setOnClickListener(view -> {
            Intent intent = new Intent(Modificacion.this, Listado.class);
            startActivity(intent);
        });

=======
         btnModificar.setOnClickListener(v -> {
             int idArticulo = Integer.parseInt(txtIdArticulo.getText().toString());
             String nuevoNombre = txtNombreProducto.getText().toString();
             int nuevoStock = Integer.parseInt(txtStock.getText().toString());
             int idCategoria = ((Categoria) spnCategoria.getSelectedItem()).getId();

              dataMainActivity.modificarArticulo(idArticulo,nuevoNombre,nuevoStock,idCategoria);
              limpiarCampos();

         });

    }

    private void limpiarCampos() {
        txtIdArticulo.setText("");
        txtNombreProducto.setText("");
        txtStock.setText("");
        spnCategoria.setSelection(0);
        txtIdArticulo.requestFocus();
>>>>>>> Stashed changes
    }
}