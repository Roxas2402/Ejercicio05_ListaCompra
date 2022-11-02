package com.example.ejercicio05_listacompra.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ejercicio05_listacompra.R;
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

    //TODO: 7: CREAMOS RESOURCE FILE DENTRO DE LA CARPETA LAYOUTS (PRODUCTO_VIEW_HOLDER) Y CONFIGURAMOS LA VISTA

    //5: Mírate el vídeo para entenderlo crack
    @NonNull
    @Override
    public ProductoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //10: Ha de devolver
        View productoView = LayoutInflater.from(context).inflate(fila, null);
        productoView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        return new ProductoVH(productoView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoVH holder, int position) {

        //11:
        Producto producto = objects.get(holder.getAdapterPosition());
        holder.lblNombre.setText(producto.getNombre());
        holder.txtCantidad.setText(String.valueOf(producto.getCantidad()));

        //12: Configuramos el boton eliminar
        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                objects.remove(producto);
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        //9: Esto ha de retornar la cantidad de objetos creada
        return objects.size();
    }

    public static class ProductoVH extends RecyclerView.ViewHolder {

        //8: Creamos e instanciamos los botones
        TextView lblNombre;
        EditText txtCantidad;
        ImageButton btnEliminar;

        public ProductoVH(@NonNull View itemView) {
            super(itemView);
            lblNombre = itemView.findViewById(R.id.lblNombreProductoViewHolder);
            txtCantidad = itemView.findViewById(R.id.txtCantidadProductoViewHolder);
            btnEliminar = itemView.findViewById(R.id.btnEliminarProductoViewHolder);
        }
    }
}
