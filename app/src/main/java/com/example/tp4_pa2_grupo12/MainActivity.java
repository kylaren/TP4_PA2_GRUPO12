package com.example.tp4_pa2_grupo12;

import com.example.tp4_pa2_grupo12.entidades.Articulo;
import com.example.tp4_pa2_grupo12.entidades.Categoria;
import com.example.tp4_pa2_grupo12.conexion.DataMainActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText edtId, edtNombreProducto, edtStock;
    private Spinner spnCategoria;
    private Button btnAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // componentes ui
        edtId = findViewById(R.id.edtId);
        edtNombreProducto = findViewById(R.id.edtNombreProducto);
        edtStock = findViewById(R.id.edtStock);
        spnCategoria = findViewById(R.id.spnCategoria);
        btnAgregar = findViewById(R.id.btnAgregar);

        // cargo spinner
        DataMainActivity dataMainActivity = new DataMainActivity(this, spnCategoria);
        dataMainActivity.fetchCategorias();

        // agregar art y msj toast
        btnAgregar.setOnClickListener(view -> {
            try {

                int id = Integer.parseInt(edtId.getText().toString());
                String nombre = edtNombreProducto.getText().toString();
                int stock = Integer.parseInt(edtStock.getText().toString());
                Categoria categoria = (Categoria) spnCategoria.getSelectedItem();

                Articulo articulo = new Articulo(id, nombre, stock, categoria.getId());

                dataMainActivity.agregarArticulo(articulo);

                Toast.makeText(MainActivity.this, "Agregado exitosamente", Toast.LENGTH_SHORT).show();

                limpiarCampos();

            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "Por favor, complete todos los campos correctamente.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void limpiarCampos() {
        edtId.setText("");
        edtNombreProducto.setText("");
        edtStock.setText("");
        spnCategoria.setSelection(0);
    }
}
