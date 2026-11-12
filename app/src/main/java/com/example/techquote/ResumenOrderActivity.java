package com.example.techquote;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ResumenOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen_order);

        TextView txtTicket = findViewById(R.id.txtTicketDatos);
        Button btnFinalizar = findViewById(R.id.btnFinalizar);

        Intent intent = getIntent();
        String servicio = intent.getStringExtra("RESUMEN_TITULO");
        int precio = intent.getIntExtra("RESUMEN_PRECIO", 0);
        String nombre = intent.getStringExtra("RESUMEN_NOMBRE");
        String direccion = intent.getStringExtra("RESUMEN_DIRECCION");
        String metodoPago = intent.getStringExtra("RESUMEN_PAGO");
        String fecha = intent.getStringExtra("RESUMEN_FECHA");
        String hora = intent.getStringExtra("RESUMEN_HORA");

        double total = precio;
        String detallePago = metodoPago;
        if ("Tarjeta".equals(metodoPago)) {
            total = precio * 1.15;
            detallePago += " (+15% recargo)";
        }

        int totalRedondeado = (int) Math.round(total);

        String ticket = getString(R.string.ticket_resumen, nombre, direccion, detallePago, servicio, String.valueOf(totalRedondeado), fecha, hora);
        txtTicket.setText(ticket);

        btnFinalizar.setOnClickListener(v -> {
            Toast.makeText(this, "¡Turno Confirmado!", Toast.LENGTH_LONG).show();
            Intent intentHome = new Intent(this, MainActivity.class);
            intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentHome);
            finish();
        });
    }
}