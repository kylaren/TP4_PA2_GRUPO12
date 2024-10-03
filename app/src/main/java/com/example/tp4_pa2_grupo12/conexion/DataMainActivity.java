package com.example.tp4_pa2_grupo12.conexion;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.tp4_pa2_grupo12.conexion.DataDB;
import com.example.tp4_pa2_grupo12.entidades.Articulo;
import com.example.tp4_pa2_grupo12.entidades.Categoria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataMainActivity {

    private Context context;
    private Spinner spnCategoria;

    public DataMainActivity(Context context, Spinner spnCategoria) {
        this.context = context;
        this.spnCategoria = spnCategoria;
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
}
