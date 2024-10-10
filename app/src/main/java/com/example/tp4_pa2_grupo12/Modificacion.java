package com.example.tp4_pa2_grupo12;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tp4_pa2_grupo12.conexion.DataMainActivity;
import com.example.tp4_pa2_grupo12.entidades.Categoria;

public class Modificacion extends AppCompatActivity {

    private EditText txtIdArticulo, txtNombreProducto, txtStock;
    private Button btnBuscar, btnModificar;
    private Spinner spnCategoria;
    private Button btnAlta, btnModificacion, btnListado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificacion);

        // UI components
        txtIdArticulo = findViewById(R.id.txt_id);
        txtNombreProducto = findViewById(R.id.txt_nombreProducto);
        txtStock = findViewById(R.id.txt_stock);
        spnCategoria = findViewById(R.id.spinnerCategoria);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnModificar = findViewById(R.id.btnModificar);
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

        // Data management object
        DataMainActivity dataMainActivity = new DataMainActivity(this, txtNombreProducto, txtStock, btnBuscar, spnCategoria);
        dataMainActivity.fetchCategorias();

        // Search button click event
        btnBuscar.setOnClickListener(v -> {
            String idText = txtIdArticulo.getText().toString().trim();

            // Validar ID antes de buscar
            if (idText.isEmpty()) {
                Toast.makeText(Modificacion.this, "El ID no puede estar vacío", Toast.LENGTH_SHORT).show();
                return;
            }
            int id;
            try {
                id = Integer.parseInt(idText);
            } catch (NumberFormatException e) {
                Toast.makeText(Modificacion.this, "El ID debe ser un número entero", Toast.LENGTH_SHORT).show();
                return;
            }

            // Buscar el artículo
            dataMainActivity.buscarArticulo(txtIdArticulo);
        });

        // Modify button click event
        btnModificar.setOnClickListener(v -> {
            try {
                // Validar el ID del artículo
                String idText = txtIdArticulo.getText().toString().trim();
                if (idText.isEmpty()) {
                    Toast.makeText(Modificacion.this, "El ID no puede estar vacío", Toast.LENGTH_SHORT).show();
                    return;
                }

                int id;
                try {
                    id = Integer.parseInt(idText);
                } catch (NumberFormatException e) {
                    Toast.makeText(Modificacion.this, "El ID debe ser un número entero", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validar el nombre del producto
                String nombre = txtNombreProducto.getText().toString().trim();
                if (nombre.isEmpty()) {
                    Toast.makeText(Modificacion.this, "El nombre del producto no puede estar vacío", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (contieneNumeros(nombre)) {
                    Toast.makeText(Modificacion.this, "El nombre del producto no debe contener números", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validar el stock
                String stockText = txtStock.getText().toString().trim();
                if (stockText.isEmpty()) {
                    Toast.makeText(Modificacion.this, "El stock no puede estar vacío", Toast.LENGTH_SHORT).show();
                    return;
                }

                int stock;
                try {
                    stock = Integer.parseInt(stockText);
                    if (stock <= 0) {
                        Toast.makeText(Modificacion.this, "El stock debe ser un número entero positivo", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(Modificacion.this, "El stock debe ser un número entero positivo", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validar la categoría seleccionada
                Categoria categoria = (Categoria) spnCategoria.getSelectedItem();
                if (categoria == null) {
                    Toast.makeText(Modificacion.this, "Por favor, seleccione una categoría", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Realizar la modificación
                dataMainActivity.modificarArticulo(id, nombre, stock, categoria.getId());
                Toast.makeText(Modificacion.this, "Artículo modificado con éxito", Toast.LENGTH_SHORT).show();
                limpiarCampos();

            } catch (Exception e) {
                Toast.makeText(Modificacion.this, "Ocurrió un error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // Método para limpiar los campos de texto después de la modificación
    private void limpiarCampos() {
        txtIdArticulo.setText("");
        txtNombreProducto.setText("");
        txtStock.setText("");
        spnCategoria.setSelection(0);
        txtIdArticulo.requestFocus();
    }

    // Método para verificar si un string contiene números
    private boolean contieneNumeros(String str) {
        return str.matches(".*\\d.*");
    }
}

