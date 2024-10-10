package com.example.tp4_pa2_grupo12;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tp4_pa2_grupo12.entidades.Articulo;

import java.util.List;

public class ArticuloAdapter extends RecyclerView.Adapter<ArticuloAdapter.ArticuloViewHolder> {
    private List<Articulo> articulos;

    public ArticuloAdapter(List<Articulo> articulos) {
        this.articulos = articulos;
    }

    @NonNull
    @Override
    public ArticuloViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_articulo, parent, false);
        return new ArticuloViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticuloViewHolder holder, int position) {
        Articulo articulo = articulos.get(position);
        holder.nombreTextView.setText(articulo.getNombre());
        holder.stockTextView.setText(String.valueOf(articulo.getStock()));
    }

    @Override
    public int getItemCount() {
        return articulos.size();
    }

    static class ArticuloViewHolder extends RecyclerView.ViewHolder {
        TextView nombreTextView;
        TextView stockTextView;

        public ArticuloViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.nombre_articulo);
            stockTextView = itemView.findViewById(R.id.stock_articulo);
        }
    }
}
