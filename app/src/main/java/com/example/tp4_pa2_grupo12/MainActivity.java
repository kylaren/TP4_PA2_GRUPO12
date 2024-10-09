package com.example.tp4_pa2_grupo12;

import com.example.tp4_pa2_grupo12.entidades.Articulo;
import com.example.tp4_pa2_grupo12.entidades.Categoria;
import com.example.tp4_pa2_grupo12.conexion.DataMainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText edtId, edtNombreProducto, edtStock;
    private Spinner spnCategoria;
    private Button btnAgregar;
    private Button btnAlta, btnModificacion, btnListado;

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
                // Valida ID
                String idText = edtId.getText().toString().trim();
                if (idText.isEmpty()) {
                    Toast.makeText(MainActivity.this, "El ID no puede estar vacío", Toast.LENGTH_SHORT).show();
                    return;
                }

                int id;
                try {
                    id = Integer.parseInt(idText);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "El ID debe ser un número entero", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verifica si el ID ya existe en segundo plano
                new Thread(() -> {
                    // verifica el ID en un hilo secundario
                    boolean idExist = dataMainActivity.existeIdEnBD(id);

                    runOnUiThread(() -> {
                        if (idExist) {
                            Toast.makeText(MainActivity.this, "El ID ya existe. Por favor, ingrese uno nuevo.", Toast.LENGTH_SHORT).show();
                        } else {
                            // Valida que el nombre del producto sea de tipo texto y no contenga datos numéricos
                            String nombre = edtNombreProducto.getText().toString().trim();
                            if (nombre.isEmpty() || contieneNumeros(nombre)) {
                                Toast.makeText(MainActivity.this, "El nombre del producto no puede estar vacío.", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (contieneNumeros(nombre)) {
                                Toast.makeText(MainActivity.this, "El nombre del producto no debe contener números.", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            // Valida que el stock solo acepte datos numéricos enteros y positivos
                            String stockText = edtStock.getText().toString().trim();
                            if (stockText.isEmpty()) {
                                Toast.makeText(MainActivity.this, "El stock no puede estar vacío.", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            int stock;
                            try {
                                stock = Integer.parseInt(stockText);
                                if (stock <= 0) {
                                    Toast.makeText(MainActivity.this, "El stock debe ser un número entero positivo.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (NumberFormatException e) {
                                Toast.makeText(MainActivity.this, "El stock debe ser un número entero positivo.", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            // Obtengo la categoría seleccionada
                            Categoria categoria = (Categoria) spnCategoria.getSelectedItem();
                            if (categoria == null) {
                                Toast.makeText(MainActivity.this, "Por favor, seleccione una categoría.", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            // Crea el artículo y lo agrega usando el método de DataMainActivity
                            Articulo articulo = new Articulo(id, nombre, stock, categoria.getId());
                            dataMainActivity.agregarArticulo(articulo);

                            Toast.makeText(MainActivity.this, "Agregado exitosamente", Toast.LENGTH_SHORT).show();
                            limpiarCampos();
                        }
                    });
                }).start();

            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "Por favor, complete todos los campos correctamente.", Toast.LENGTH_SHORT).show();
            }
        });

        btnAlta = findViewById(R.id.btnAlta);
        btnModificacion = findViewById(R.id.btnModificacion);
        btnListado = findViewById(R.id.btnListado);

         btnAlta.setOnClickListener(view -> {
               Toast.makeText(this, "Ya estás en ALTA", Toast.LENGTH_SHORT).show();
        });

        btnModificacion.setOnClickListener(view -> {
                   Intent intent = new Intent(MainActivity.this, Modificacion.class);
            startActivity(intent);
        });

        btnListado.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Listado.class);
            startActivity(intent);
        });

    }

    private void limpiarCampos() {
        edtId.setText("");
        edtNombreProducto.setText("");
        edtStock.setText("");
        spnCategoria.setSelection(0);
        edtId.requestFocus();
    }

    // Método que verifica si un string contiene números
    private boolean contieneNumeros(String str){
        return str.matches(".*\\d.*");
    }

//    public void IraModificar(View view){
//        Intent i = new Intent(this, Modificacion.class);
//        startActivity(i);
//    }
}
