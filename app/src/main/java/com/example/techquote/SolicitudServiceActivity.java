package com.example.techquote;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SolicitudServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud);

        TextView txtServicioNombre = findViewById(R.id.txtServicioNombre);
        TextView txtServicioPrecio = findViewById(R.id.txtServicioPrecio);
        EditText etNombre = findViewById(R.id.etNombreCliente);
        EditText etDireccion = findViewById(R.id.etDireccionCliente);
        Button btnVolver = findViewById(R.id.btnVolver);
        Button btnContinuar = findViewById(R.id.btnContinuarResumen);

        // 1. Recibir datos del catálogo
        Intent intentRecibido = getIntent();
        final String servicio = intentRecibido.getStringExtra("EXTRA_SERVICIO_TITULO");
        final int precio = intentRecibido.getIntExtra("EXTRA_SERVICIO_PRECIO", 0);

        if (servicio != null) {
            // Clean Code: Inyección de variables con comodines
            txtServicioNombre.setText(getString(R.string.txt_servicio_seleccionado, servicio));
            txtServicioPrecio.setText(getString(R.string.txt_precio_base, precio));
        }

        // 2. Botón Volver
        if (btnVolver != null) {
            btnVolver.setOnClickListener(v -> finish());
        }

        // 3. Continuar al Resumen
        btnContinuar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String direccion = etDireccion.getText().toString().trim();

            if (nombre.isEmpty() || direccion.isEmpty()) {
                Toast.makeText(this, getString(R.string.toast_completar_datos), Toast.LENGTH_SHORT).show();
                return;
            }

            String metodoPago = "Efectivo";

            // ENCOMIENDA PARA LA TERCERA PANTALLA
            Intent intentResumen = new Intent(SolicitudServiceActivity.this, ResumenOrderActivity.class);
            intentResumen.putExtra("RESUMEN_TITULO", servicio);
            intentResumen.putExtra("RESUMEN_PRECIO", precio);
            intentResumen.putExtra("RESUMEN_NOMBRE", nombre);
            intentResumen.putExtra("RESUMEN_DIRECCION", direccion);
            intentResumen.putExtra("RESUMEN_PAGO", metodoPago);

            startActivity(intentResumen);
        });
    }
}