package com.example.techquote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout mainLayout;
    private boolean panelGenerado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnInfo = findViewById(R.id.btnInfo);
        Button btnContratar = findViewById(R.id.btnContratar);
        mainLayout = findViewById(R.id.mainLayout);

        btnInfo.setOnClickListener(v -> mostrarPanelAzulInfo());

        // Navegación explícita
        btnContratar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SolicitudServiceActivity.class);
            intent.putExtra("EXTRA_SERVICIO_TITULO", "Mantenimiento Preventivo & Optimización");
            intent.putExtra("EXTRA_SERVICIO_PRECIO", 25000);
            startActivity(intent);
        });
    }

    private void mostrarPanelAzulInfo() {
        if (!panelGenerado) {
            RelativeLayout overlay = new RelativeLayout(this);
            overlay.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            ));
            overlay.setBackgroundColor(Color.parseColor("#99000000"));
            overlay.setElevation(50f);
            overlay.setClickable(true);

            ScrollView scrollView = new ScrollView(this);
            RelativeLayout.LayoutParams scrollParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            scrollParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            scrollParams.setMargins(64, 128, 64, 128);
            scrollView.setLayoutParams(scrollParams);

            LinearLayout panelAzul = new LinearLayout(this);
            panelAzul.setOrientation(LinearLayout.VERTICAL);
            panelAzul.setBackgroundColor(Color.parseColor("#1565C0"));
            panelAzul.setPadding(56, 48, 56, 56);

            Button btnCerrar = new Button(this);
            btnCerrar.setText("✕ CERRAR INFO");
            btnCerrar.setTextColor(Color.WHITE);
            btnCerrar.setBackgroundColor(Color.TRANSPARENT);
            btnCerrar.setTextSize(14);
            btnCerrar.setGravity(android.view.Gravity.END | android.view.Gravity.CENTER_VERTICAL);

            btnCerrar.setOnClickListener(v -> {
                mainLayout.removeView(overlay);
                panelGenerado = false;
            });

            TextView textoInfo = new TextView(this);
            textoInfo.setText(getString(R.string.txt_detalle_servicio));
            textoInfo.setTextColor(Color.WHITE);
            textoInfo.setTextSize(15);
            textoInfo.setLineSpacing(6, 1.2f);

            panelAzul.addView(btnCerrar);
            panelAzul.addView(textoInfo);
            scrollView.addView(panelAzul);
            overlay.addView(scrollView);

            mainLayout.addView(overlay);
            panelGenerado = true;
        }
    }
}