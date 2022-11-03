package com.example.ejercicio05_listacompra.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

        //12: Configuramos el boton eliminar (ACTUALIZACIÓN: sustituimos lo comentado por lo que hay en la función confirmDelete(producto).show());
        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //objects.remove(producto);
                //notifyItemRemoved(holder.getAdapterPosition());
                //notifyDataSetChanged();
                //15: Hacemos que se muestre el cuadro de eliminar
                confirmDelete(producto).show();
            }
        });

        //16: Con esto cambiamos el modelo de datos
        //17: La mierda del cursor
        holder.txtCantidad.addTextChangedListener(new TextWatcher() {
            boolean cero = false;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //17.01
                if (charSequence.toString().length() > 0 && charSequence.charAt(0) == '0')
                    cero = true;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                try {
                    //17.02
                    if (cero && editable.toString().length() > 1) {
                        holder.txtCantidad.setText(editable.toString().substring(0, 1));
                        holder.txtCantidad.setSelection(1);
                        cero = false;
                    }

                    int cantidad = Integer.parseInt(editable.toString());
                    producto.setCantidad(cantidad);

                    //Con esto comunicamos al modelo de datos que ha habido un cambio, pero cuando controlamos la excepción ya se notifica automáticamente
                    //Por lo que no hay que notificar nada porque la excepción lo notifica automáticamente
                    //notifyItemChanged(holder.getAdapterPosition());
                } catch (NumberFormatException numberFormatException) {
                    //Si salta la excepción, la cantidad será 0
                    holder.txtCantidad.setText("0");
                }
            }
        });

        //16.02: Al clickar en la pantalla en cualquier sitio que no sea un producto, saldrá este toast
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "HOLA", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        //9: Esto ha de retornar la cantidad de objetos creada
        return objects.size();
    }

    //15.01: Hacemos el cuadro de confirmación de eliminar un producto
    private AlertDialog confirmDelete(Producto producto) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("¿¿¿¿Seguroooo????");
        builder.setCancelable(false);
        TextView textView = new TextView(context);
        textView.setText("Esta acción es irreversible");
        textView.setTextColor(Color.RED);
        textView.setTextSize(16);
        builder.setView(textView);
        textView.setPadding(70, 20, 20, 20);
        builder.setNegativeButton("Me he cagao", null);

        //15.02: Botón de confirmar eliminación
        builder.setPositiveButton("Con dos cojones", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Para saber qué producto se elimina, lo ponemos en una variable
                int posicion = objects.indexOf(producto);
                objects.remove(producto);
                notifyItemRemoved(posicion);
            }
        });
        return builder.create();
    }

    public class ProductoVH extends RecyclerView.ViewHolder {

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
