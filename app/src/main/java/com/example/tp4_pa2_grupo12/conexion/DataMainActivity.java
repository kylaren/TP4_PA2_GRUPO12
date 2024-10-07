package com.example.tp4_pa2_grupo12.conexion;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tp4_pa2_grupo12.conexion.DataDB;
import com.example.tp4_pa2_grupo12.entidades.Articulo;
import com.example.tp4_pa2_grupo12.entidades.Categoria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataMainActivity {

    private Context context;
    private Spinner spnCategoria;
    private EditText txtNombreProducto, txtStock;
    private Button btnBuscar;

    // Lista para almacenar los artículos
    private List<Articulo> articulos;

    public DataMainActivity(Context context, Spinner spnCategoria) {
        this.context = context;
        this.spnCategoria = spnCategoria;
        this.articulos = new ArrayList<>();
    }

    public DataMainActivity(Context context, EditText txtNombreProducto, EditText txtStock, Button btnBuscar) {
        this.context = context;
        this.txtNombreProducto = txtNombreProducto;
        this.txtStock = txtStock;
        this.btnBuscar = btnBuscar;
        this.articulos = new ArrayList<>();
    }


    // traer categorias
    public void fetchCategorias() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            ArrayList<Categoria> listaCategorias = new ArrayList<>();
            try {
                Class.forName(DataDB.driver);
                Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM categoria");

                while (rs.next()) {
                    Categoria categoria = new Categoria();
                    categoria.setId(rs.getInt("id"));
                    categoria.setDescripcion(rs.getString("descripcion"));
                    listaCategorias.add(categoria);
                }
                rs.close();
                st.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Actualiza la UI en el hilo principal
            new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                ArrayAdapter<Categoria> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, listaCategorias);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnCategoria.setAdapter(adapter);
            });
        });
    }

    // agregar art
    public void agregarArticulo(Articulo articulo) {

        // Verifica si el articulo ya existe en la base de datos
        if (existeIdEnBD(articulo.getId())) {
            Toast.makeText(context, "El artículo ya existe en la base de datos", Toast.LENGTH_SHORT).show();
            return;
        }

        //Agrega a la lista local antes de insert en la base de datos
        articulos.add(articulo);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                Class.forName(DataDB.driver);
                Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
                String query = "INSERT INTO articulo (id, nombre, stock, idCategoria) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, articulo.getId());
                ps.setString(2, articulo.getNombre());
                ps.setInt(3, articulo.getStock());
                ps.setInt(4, articulo.getIdCategoria());
                ps.executeUpdate();
                ps.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void buscarArticuloPorId(int idArticulo) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            Articulo articulo = null;
            try {
                Class.forName(DataDB.driver);
                Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
                String query = "SELECT * FROM articulo WHERE id = ?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, idArticulo);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    articulo = new Articulo();
                    articulo.setId(rs.getInt("id"));
                    articulo.setNombre(rs.getString("nombre"));
                    articulo.setStock(rs.getInt("stock"));
                }

                rs.close();
                ps.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Actualiza la UI con los datos del artículo
            Articulo art = articulo;
            new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                if (art != null) {
                    txtNombreProducto.setText(art.getNombre());
                    txtStock.setText(String.valueOf(art.getStock()));
                } else {
                    txtNombreProducto.setText("");
                    txtStock.setText("");
                    Toast.makeText(context, "Artículo no encontrado", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    public void buscarArticulo(EditText txtIdArticulo) {
        btnBuscar.setOnClickListener(v -> {
            String idText = txtIdArticulo.getText().toString().trim();
            if (!idText.isEmpty()) {
                int idArticulo = Integer.parseInt(idText);
                buscarArticuloPorId(idArticulo);
            } else {
                Toast.makeText(context, "Ingrese un ID de artículo", Toast.LENGTH_SHORT).show();
            }
        });
    }



    // Verifica si el ID existe en la base de datos
    public boolean existeIdEnBD(int id) {
        boolean exists = false;
        try {
            Class.forName(DataDB.driver);
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            String query = "SELECT COUNT(*) FROM articulo WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exists;
    }
}

