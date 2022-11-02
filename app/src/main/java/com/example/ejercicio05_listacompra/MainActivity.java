package com.example.ejercicio05_listacompra;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.ejercicio05_listacompra.modelos.Producto;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        productosList = new ArrayList<>();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createProducto().show();
            }
        });
    }

    //1: Crear clase y xml con IDs y eso, también el ArrayList<Producto> y lo instanciamos

    //2: Crear alerta
    private AlertDialog createProducto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Agregar producto a la cesta");
        //Para que al presionar fuera no se cierre la ventana
        builder.setCancelable(false);

        //Para que sea un elemento independiente ponemos el null
        View productoViewModel = LayoutInflater.from(this).inflate(R.layout.producto_view_model, null);
        TextView lblTotal = productoViewModel.findViewById(R.id.lblTotalProductoViewModel);
        EditText txtNombre = productoViewModel.findViewById(R.id.txtNombreProductoViewModel);
        EditText txtCantidad = productoViewModel.findViewById(R.id.txtCantidadProductoViewModel);
        EditText txtPrecio = productoViewModel.findViewById(R.id.txtPrecioProductoViewModel);

        //2.01: Creamos el botón de cancelar
        builder.setView(productoViewModel);

        //3: He creado la variable TextWatcher. Esto es lo que pasa Antes, Durante y (el que nos importa) Después de escribir el texto
        //Los logs son para ver qué está pasando, no es relevante en la App
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("EVENTO-TEXYO", "BEFORE" + charSequence);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("EVENTO-TEXYO", "BEFORE" + charSequence);
            }

            //TODO 4: CREAMOS LO DEL CONTENT MAIN, AÑADIMOS EL RECYCLERVIEW Y LO RENOMBRAMOS A CONTENEDOR, EN EL ACTIVITY MAIN IGUAL Y LO LLAMAMOS CONTENTMAIN
            @Override
            public void afterTextChanged(Editable editable) {

                Log.d("EVENTO-TEXYO", "BEFORE" + editable.toString());
                //Esto explotará en algún momento, por lo que lo metemos en un try - catch
                try {
                    int cantidad = Integer.parseInt(txtCantidad.getText().toString());
                    float precio = Float.parseFloat(txtPrecio.getText().toString());

                    float total = cantidad * precio;

                    //El numberFormat convierte el número al tipo de moneda que el teléfono tiene configurada (5 -> 5€ || 5$)
                    NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
                    lblTotal.setText(numberFormat.format(total));
                } catch (NumberFormatException nfe) {

                }
            }
        };

        //3.01: Agrego la variable al cambio de datos
        txtCantidad.addTextChangedListener(textWatcher);
        txtPrecio.addTextChangedListener(textWatcher);



        builder.setNegativeButton("CANCELAR", null);

        //2.02: Botón agregar
        //2.03: Si los datos no están vacíos, creamos el producto
        builder.setPositiveButton("AGREGAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!txtNombre.getText().toString().isEmpty() && !txtCantidad.getText().toString().isEmpty() && !txtPrecio.getText().toString().isEmpty()) {
                    Producto producto = new Producto(txtNombre.getText().toString(),
                                                    Integer.parseInt(txtCantidad.getText().toString()),
                                                    Float.parseFloat(txtPrecio.getText().toString())
                    );
                    //2.04: Añadimos el producto
                    productosList.add(producto);
                } else {
                    Toast.makeText(MainActivity.this, "FALTAN DATOS", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return builder.create();
    }
}