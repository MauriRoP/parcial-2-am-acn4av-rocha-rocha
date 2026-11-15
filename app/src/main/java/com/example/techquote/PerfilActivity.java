package com.example.techquote;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class PerfilActivity extends AppCompatActivity {

    private TextView txtEmailPerfil;
    private TextView txtEstadoCarga;
    private LinearLayout layoutContenedorContratos;
    private FirebaseFirestore db;
    private String emailActual = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        txtEmailPerfil = findViewById(R.id.txtEmailPerfil);
        txtEstadoCarga = findViewById(R.id.txtEstadoCarga);
        layoutContenedorContratos = findViewById(R.id.layoutContenedorContratos);
        Button btnVolverCatalogo = findViewById(R.id.btnVolverCatalogo);
        Button btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null && user.getEmail() != null) {
            emailActual = user.getEmail();
            txtEmailPerfil.setText("Cuenta activa: " + emailActual);
            cargarContratosActivos();
        } else {
            txtEmailPerfil.setText("Sin sesión activa");
            txtEstadoCarga.setText("Iniciá sesión para ver tus contratos.");
        }

        btnVolverCatalogo.setOnClickListener(v -> finish());

        btnCerrarSesion.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(PerfilActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void cargarContratosActivos() {
        layoutContenedorContratos.removeAllViews();
        txtEstadoCarga.setVisibility(View.VISIBLE);
        txtEstadoCarga.setText("Cargando contratos activos...");
        layoutContenedorContratos.addView(txtEstadoCarga);

        db.collection("Solicitudes")
                .whereEqualTo("usuarioEmail", emailActual)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    layoutContenedorContratos.removeAllViews();

                    if (queryDocumentSnapshots.isEmpty()) {
                        txtEstadoCarga.setText("No tenés servicios ni contratos activos en este momento.");
                        layoutContenedorContratos.addView(txtEstadoCarga);
                        return;
                    }

                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String idDoc = doc.getId();
                        String servicio = doc.getString("servicio");
                        String fecha = doc.getString("fecha");
                        String hora = doc.getString("hora");
                        String direccion = doc.getString("direccion");
                        String estado = doc.getString("estado");
                        String metodoPago = doc.getString("metodoPago");
                        Long total = doc.getLong("total");

                        LinearLayout tarjeta = new LinearLayout(this);
                        tarjeta.setOrientation(LinearLayout.VERTICAL);
                        tarjeta.setBackgroundColor(Color.parseColor("#E3F2FD"));
                        tarjeta.setPadding(36, 32, 36, 32);

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        params.setMargins(0, 0, 0, 28);
                        tarjeta.setLayoutParams(params);

                        TextView txtDetalle = new TextView(this);
                        String info = "SERVICIO: " + (servicio != null ? servicio : "-") +
                                "\nEstado: " + (estado != null ? estado : "Pendiente") +
                                "\nTurno: " + (fecha != null ? fecha : "-") + " a las " + (hora != null ? hora : "-") +
                                "\nDirección: " + (direccion != null ? direccion : "-") +
                                "\nPago: " + (metodoPago != null ? metodoPago : "-") +
                                "\nTotal: $" + (total != null ? total : 0);
                        txtDetalle.setText(info);
                        txtDetalle.setTextColor(Color.parseColor("#0D47A1"));
                        txtDetalle.setTextSize(14f);
                        txtDetalle.setLineSpacing(4f, 1.1f);

                        Button btnCancelar = new Button(this);
                        btnCancelar.setText("CANCELAR SOLICITUD");
                        btnCancelar.setTextColor(Color.WHITE);
                        btnCancelar.setBackgroundColor(Color.parseColor("#E53935"));
                        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        btnParams.setMargins(0, 20, 0, 0);
                        btnCancelar.setLayoutParams(btnParams);

                        btnCancelar.setOnClickListener(v -> eliminarContrato(idDoc));

                        tarjeta.addView(txtDetalle);
                        tarjeta.addView(btnCancelar);
                        layoutContenedorContratos.addView(tarjeta);
                    }
                })
                .addOnFailureListener(e -> {
                    layoutContenedorContratos.removeAllViews();
                    txtEstadoCarga.setText("Error al cargar contratos: " + e.getMessage());
                    layoutContenedorContratos.addView(txtEstadoCarga);
                });
    }

    private void eliminarContrato(String documentoId) {
        db.collection("Solicitudes").document(documentoId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(PerfilActivity.this, "Solicitud cancelada", Toast.LENGTH_SHORT).show();
                    cargarContratosActivos();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(PerfilActivity.this, "Error al cancelar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}