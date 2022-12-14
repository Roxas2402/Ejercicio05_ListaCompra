package com.example.ejercicio05_listacompra;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.ejercicio05_listacompra.adapters.ProductosAdapter;
import com.example.ejercicio05_listacompra.modelos.Producto;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ejercicio05_listacompra.databinding.ActivityMainBinding;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<Producto> productosList;
    //12+1:
    private ProductosAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        productosList = new ArrayList<>();

        //12+1.01: Instanciamos el layout y el adapter y se lo pasamos al binding para que reconozca los IDs
        adapter = new ProductosAdapter(productosList, R.layout.producto_view_holder, this);
        layoutManager = new GridLayoutManager(this, 1);
        binding.contentMain.contenedor.setLayoutManager(layoutManager);
        binding.contentMain.contenedor.setAdapter(adapter);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createProducto().show();
            }
        });
    }

    //1: Crear clase y xml con IDs y eso, tambi??n el ArrayList<Producto> y lo instanciamos

    //2: Crear alerta
    private AlertDialog createProducto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alert_add_title);
        //Para que al presionar fuera no se cierre la ventana
        builder.setCancelable(false);

        //Para que sea un elemento independiente ponemos el null
        View productoViewModel = LayoutInflater.from(this).inflate(R.layout.producto_view_model, null);
        TextView lblTotal = productoViewModel.findViewById(R.id.lblTotalProductoViewModel);
        EditText txtNombre = productoViewModel.findViewById(R.id.txtNombreProductoViewModel);
        EditText txtCantidad = productoViewModel.findViewById(R.id.txtCantidadProductoViewModel);
        EditText txtPrecio = productoViewModel.findViewById(R.id.txtPrecioProductoViewModel);

        //2.01: Creamos el bot??n de cancelar
        builder.setView(productoViewModel);

        //TODO: EL SIGUIENTE PASO ES EL 15

        //3: He creado la variable TextWatcher. Esto es lo que pasa Antes, Durante y (el que nos importa) Despu??s de escribir el texto
        //Los logs son para ver qu?? est?? pasando, no es relevante en la App
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("EVENTO-TEXYO", "BEFORE" + charSequence);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("EVENTO-TEXTO", "BEFORE" + charSequence);
            }

            //TODO 4: CREAMOS LO DEL CONTENT MAIN, A??ADIMOS EL RECYCLERVIEW Y LO RENOMBRAMOS A CONTENEDOR, EN EL ACTIVITY MAIN IGUAL Y LO LLAMAMOS CONTENTMAIN
            @Override
            public void afterTextChanged(Editable editable) {

                Log.d("EVENTO-TEXTO", "BEFORE" + editable.toString());
                //Esto explotar?? en alg??n momento, por lo que lo metemos en un try - catch
                try {
                    int cantidad = Integer.parseInt(txtCantidad.getText().toString());
                    float precio = Float.parseFloat(txtPrecio.getText().toString());

                    float total = cantidad * precio;

                    //El numberFormat convierte el n??mero al tipo de moneda que el tel??fono tiene configurada (5 -> 5??? || 5$)
                    NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
                    lblTotal.setText(numberFormat.format(total));
                } catch (NumberFormatException nfe) {

                }
            }
        };

        //3.01: Agrego la variable al cambio de datos
        txtCantidad.addTextChangedListener(textWatcher);
        txtPrecio.addTextChangedListener(textWatcher);


        builder.setNegativeButton(R.string.alert_cancel_button, null);

        //2.02: Bot??n agregar
        //2.03: Si los datos no est??n vac??os, creamos el producto
        builder.setPositiveButton(R.string.alert_add_ok_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!txtNombre.getText().toString().isEmpty() && !txtCantidad.getText().toString().isEmpty() && !txtPrecio.getText().toString().isEmpty()) {
                    Producto producto = new Producto(txtNombre.getText().toString(),
                        Integer.parseInt(txtCantidad.getText().toString()),
                        Float.parseFloat(txtPrecio.getText().toString())
                    );
                    //2.04: A??adimos el producto
                    productosList.add(producto);
                    //14: Notificamos cada vez que se inserta un producto, con esto desplaza el n??mero de elementos hacia abajo cada vez que se inserta uno
                    adapter.notifyItemInserted(productosList.size() - 1);
                } else {
                    Toast.makeText(MainActivity.this, "FALTAN DATOS", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return builder.create();
    }

    //20: Girar la pantalla y que no se borren los datos
    //Al girar la pantalla se destruye y se reconstruye la actividad. Mientras giras la pantalla, los datos que t?? quieras se guardan en el bundle onState.
    //Una vez se reconstruya la actividad, el bundle onState se pasa al savedInstanceState y lo muestra
    //20.01: Hay que hacer serializable la clase Producto
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("LISTA", productosList);

    }

    @Override
    protected void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        productosList.addAll((ArrayList<Producto>) savedInstanceState.getSerializable("LISTA"));
        adapter.notifyDataSetChanged();
    }
}