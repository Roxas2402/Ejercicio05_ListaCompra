package com.example.ejercicio05_listacompra.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ejercicio05_listacompra.modelos.Producto;

import java.util.List;


public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductoVH> {

    //6: Creamos las variables
    private List<Producto> objects;
    int fila;
    private Context context;

    //6.01: Creamos el constructor
    public ProductosAdapter(List<Producto> objects, int fila, Context context) {
        this.objects = objects;
        this.fila = fila;
        this.context = context;
    }

    //5: Mírate el vídeo para entenderlo crack
    @NonNull
    @Override
    public ProductoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ProductoVH extends RecyclerView.ViewHolder {
        public ProductoVH(@NonNull View itemView) {
            super(itemView);
        }
    }
}
