package com.example.techquote;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;
import java.util.HashMap;
import java.util.Map;

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

        String ticket = "SERVICIO: " + servicio +
                "\n\nDATOS DEL CLIENTE" +
                "\nNombre: " + nombre +
                "\nDirección: " + direccion +
                "\n\nDETALLES DEL TURNO" +
                "\nFecha: " + fecha +
                "\nHora: " + hora +
                "\n\nFACTURACIÓN" +
                "\nMétodo de Pago: " + detallePago +
                "\nTOTAL A ABONAR: $" + totalRedondeado;
        txtTicket.setText(ticket);

        btnFinalizar.setOnClickListener(v -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Map<String, Object> pedido = new HashMap<>();

            String emailUsuario = "";
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                emailUsuario = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            }

            pedido.put("usuarioEmail", emailUsuario);
            pedido.put("servicio", servicio);
            pedido.put("nombreCliente", nombre);
            pedido.put("direccion", direccion);
            pedido.put("fecha", fecha);
            pedido.put("hora", hora);
            pedido.put("metodoPago", metodoPago);
            pedido.put("total", totalRedondeado);
            pedido.put("estado", "Pendiente");

            db.collection("Solicitudes")
                    .add(pedido)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(ResumenOrderActivity.this, "¡Turno Confirmado!", Toast.LENGTH_LONG).show();
                        Intent intentHome = new Intent(ResumenOrderActivity.this, MainActivity.class);
                        intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentHome);
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(ResumenOrderActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
        });
    }
}