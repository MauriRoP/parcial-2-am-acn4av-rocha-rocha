package com.example.techquote;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SolicitudServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud);

        // Referencias de la interfaz
        TextView txtServicioNombre = findViewById(R.id.txtServicioNombre);
        TextView txtServicioPrecio = findViewById(R.id.txtServicioPrecio);
        EditText etNombre = findViewById(R.id.etNombreCliente);
        EditText etDireccion = findViewById(R.id.etDireccionCliente);
        Spinner spinnerFecha = findViewById(R.id.spinnerFecha);
        View btnVolver = findViewById(R.id.btnVolver);
        Button btnContinuar = findViewById(R.id.btnContinuarResumen);

        String[] diasNoviembre = {
                "01 de noviembre de 2026", "02 de noviembre de 2026", "03 de noviembre de 2026",
                "04 de noviembre de 2026", "05 de noviembre de 2026", "06 de noviembre de 2026",
                "07 de noviembre de 2026", "08 de noviembre de 2026", "09 de noviembre de 2026",
                "10 de noviembre de 2026", "11 de noviembre de 2026", "12 de noviembre de 2026",
                "13 de noviembre de 2026", "14 de noviembre de 2026", "15 de noviembre de 2026",
                "16 de noviembre de 2026", "17 de noviembre de 2026", "18 de noviembre de 2026",
                "19 de noviembre de 2026", "20 de noviembre de 2026", "21 de noviembre de 2026",
                "22 de noviembre de 2026", "23 de noviembre de 2026", "24 de noviembre de 2026",
                "25 de noviembre de 2026", "26 de noviembre de 2026", "27 de noviembre de 2026",
                "28 de noviembre de 2026", "29 de noviembre de 2026", "30 de noviembre de 2026"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, diasNoviembre);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFecha.setAdapter(adapter);

        Intent intentRecibido = getIntent();
        final String servicio = intentRecibido.getStringExtra("EXTRA_SERVICIO_TITULO");
        final int precio = intentRecibido.getIntExtra("EXTRA_SERVICIO_PRECIO", 0);

        if (servicio != null) {
            txtServicioNombre.setText("Servicio: " + servicio);
            txtServicioPrecio.setText("Precio base: $" + precio + " ARS");
        }

        if (btnVolver != null) {
            btnVolver.setOnClickListener(v -> finish());
        }

        btnContinuar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String direccion = etDireccion.getText().toString().trim();
            String fechaSeleccionada = spinnerFecha.getSelectedItem().toString();

            if (nombre.isEmpty() || direccion.isEmpty()) {
                Toast.makeText(this, "Completá nombre y dirección bro", Toast.LENGTH_SHORT).show();
                return;
            }

            String metodoPago = "Efectivo";

            Intent intentResumen = new Intent(SolicitudServiceActivity.this, ResumenOrderActivity.class);
            intentResumen.putExtra("RESUMEN_TITULO", servicio);
            intentResumen.putExtra("RESUMEN_PRECIO", precio);
            intentResumen.putExtra("RESUMEN_NOMBRE", nombre);
            intentResumen.putExtra("RESUMEN_DIRECCION", direccion);
            intentResumen.putExtra("RESUMEN_PAGO", metodoPago);
            intentResumen.putExtra("RESUMEN_FECHA", fechaSeleccionada);

            startActivity(intentResumen);
        });
    }
}