package com.example.techquote;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog;
import java.util.Calendar;

public class SolicitudServiceActivity extends AppCompatActivity {

    private String horarioSeleccionado = "09:00 hs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud);

        TextView txtServicioNombre = findViewById(R.id.txtServicioNombre);
        TextView txtServicioPrecio = findViewById(R.id.txtServicioPrecio);
        EditText etNombre = findViewById(R.id.etNombreCliente);
        EditText etDireccion = findViewById(R.id.etDireccionCliente);
        RadioGroup rgMetodoPago = findViewById(R.id.rgMetodoPago);
        EditText etSelectDate = findViewById(R.id.etSelectDate); // Usando el nuevo EditText
        View btnVolver = findViewById(R.id.btnVolver);
        TextView btnAyuda = findViewById(R.id.btnAyuda);
        Button btnContinuar = findViewById(R.id.btnContinuarResumen);

        LinearLayout btnHorario1 = findViewById(R.id.btnHorario1);
        LinearLayout btnHorario2 = findViewById(R.id.btnHorario2);
        LinearLayout btnHorario3 = findViewById(R.id.btnHorario3);

        // Referencias nuevas que hizo Miguel
        LinearLayout layoutTransferencia = findViewById(R.id.layoutTransferencia);
        LinearLayout layoutTarjeta = findViewById(R.id.layoutTarjeta);

        // Lógica de visibilidad (Comportamiento dinámico para el 10)
        rgMetodoPago.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbTransferencia) {
                // Mostrar datos del banco, ocultar tarjeta
                layoutTransferencia.setVisibility(View.VISIBLE);
                layoutTarjeta.setVisibility(View.GONE);
            } else if (checkedId == R.id.rbTarjeta) {
                // Mostrar input de tarjeta, ocultar banco
                layoutTransferencia.setVisibility(View.GONE);
                layoutTarjeta.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.rbEfectivo) {
                // Ocultar todo
                layoutTransferencia.setVisibility(View.GONE);
                layoutTarjeta.setVisibility(View.GONE);
            }
        });

        Intent intentRecibido = getIntent();
        final String servicio = intentRecibido.getStringExtra("EXTRA_SERVICIO_TITULO");
        final int precio = intentRecibido.getIntExtra("EXTRA_SERVICIO_PRECIO", 0);

        if (servicio != null) {
            txtServicioNombre.setText(getString(R.string.txt_servicio_seleccionado, servicio));
            txtServicioPrecio.setText(getString(R.string.txt_precio_base, precio));
        }

        // calendario que esta mejor
        etSelectDate.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    SolicitudServiceActivity.this,
                    (view, anio, mes, dia) -> {
                        String fechaElegida = dia + "/" + (mes + 1) + "/" + anio;
                        etSelectDate.setText(fechaElegida);
                    },
                    year, month, day);

            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        });

        // Selección dinámica de horarios
        View.OnClickListener selectorHorario = v -> {
            btnHorario1.setBackgroundResource(android.R.drawable.editbox_background_normal);
            btnHorario2.setBackgroundResource(android.R.drawable.editbox_background_normal);
            btnHorario3.setBackgroundResource(android.R.drawable.editbox_background_normal);

            v.setBackgroundColor(Color.parseColor("#DBEAFE"));

            if (v.getId() == R.id.btnHorario1) horarioSeleccionado = "09:00 hs";
            else if (v.getId() == R.id.btnHorario2) horarioSeleccionado = "10:30 hs";
            else if (v.getId() == R.id.btnHorario3) horarioSeleccionado = "11:30 hs";
        };

        btnHorario1.setOnClickListener(selectorHorario);
        btnHorario2.setOnClickListener(selectorHorario);
        btnHorario3.setOnClickListener(selectorHorario);

        if (btnVolver != null) {
            btnVolver.setOnClickListener(v -> finish());
        }

        if (btnAyuda != null) {
            btnAyuda.setOnClickListener(v ->
                    Toast.makeText(this, "Soporte técnico: soporte@techquote.com.ar", Toast.LENGTH_SHORT).show()
            );
        }

        btnContinuar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String direccion = etDireccion.getText().toString().trim();
            String fechaSeleccionada = etSelectDate.getText().toString().trim();

            if (nombre.isEmpty() || direccion.isEmpty() || fechaSeleccionada.isEmpty()) {
                Toast.makeText(this, "Completá nombre, dirección y fecha", Toast.LENGTH_SHORT).show();
                return;
            }

            // Captura real del RadioButton seleccionado
            String metodoPago = "Efectivo";
            int selectedId = rgMetodoPago.getCheckedRadioButtonId();
            if (selectedId != -1) {
                RadioButton rbSeleccionado = findViewById(selectedId);
                metodoPago = rbSeleccionado.getText().toString();
            }

            Intent intentResumen = new Intent(SolicitudServiceActivity.this, ResumenOrderActivity.class);
            intentResumen.putExtra("RESUMEN_TITULO", servicio);
            intentResumen.putExtra("EXTRA_METODO_PAGO", metodoPago);
            intentResumen.putExtra("RESUMEN_PRECIO", precio);
            intentResumen.putExtra("RESUMEN_NOMBRE", nombre);
            intentResumen.putExtra("RESUMEN_DIRECCION", direccion);
            intentResumen.putExtra("RESUMEN_PAGO", metodoPago);
            intentResumen.putExtra("RESUMEN_FECHA", fechaSeleccionada);
            intentResumen.putExtra("RESUMEN_PAGO", metodoPago);
            intentResumen.putExtra("RESUMEN_HORA", horarioSeleccionado);
            startActivity(intentResumen);
        });
    }
}