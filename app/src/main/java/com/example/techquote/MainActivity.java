package com.example.techquote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout mainLayout;
    private boolean panelGenerado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.mainLayout);

        ImageView imgServicio = findViewById(R.id.imgServicio);
        String urlImagen = "https://images.unsplash.com/photo-1597872200969-2b65d56bd16b?auto=format&fit=crop&w=800&q=80";

        Glide.with(this)
                .load(urlImagen)
                .centerCrop()
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(imgServicio);

        Button btnInfo = findViewById(R.id.btnInfo);
        Button btnContratar = findViewById(R.id.btnContratar);

        btnInfo.setOnClickListener(v -> mostrarPanelAzulInfo(getString(R.string.txt_detalle_servicio)));

        btnContratar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SolicitudServiceActivity.class);
            intent.putExtra("EXTRA_SERVICIO_TITULO", "Mantenimiento Preventivo & Optimización");
            intent.putExtra("EXTRA_SERVICIO_PRECIO", 25000);
            startActivity(intent);
        });

        ImageView imgServicio2 = findViewById(R.id.imgServicio2);
        Button btnInfo2 = findViewById(R.id.btnInfo2);
        Button btnContratar2 = findViewById(R.id.btnContratar2);

        Glide.with(this)
                .load("https://images.unsplash.com/photo-1587202372775-e229f172b9d7?w=800&q=80")
                .centerCrop()
                .placeholder(R.drawable.img_servicio)
                .error(R.drawable.img_servicio)
                .into(imgServicio2);

        String detalleArmadoPc = "ARMADO DE PC GAMER PRO\n\n" +
                "¿Querés armar tu setup desde cero o pasar tus componentes a un gabinete nuevo con flujo de aire óptimo?\n" +
                "Ensamblamos tu equipo con estándares de exhibición.\n\n" +
                "INCLUYE:\n" +
                "• Montaje completo de componentes y gestión de cables oculta (Cable Management).\n" +
                "• Actualización de BIOS a la última versión estable.\n" +
                "• Configuración de perfiles de memoria RAM (XMP / EXPO) y Resizable BAR.\n" +
                "• Optimización de curva de ventiladores (PWM) para bajo ruido.\n" +
                "• Instalación limpia de Sistema Operativo y drivers oficiales.\n" +
                "• Test de estabilidad y temperaturas bajo carga extrema (Cinebench / FurMark).\n\n" +
                "DURACIÓN ESTIMADA:\n" +
                "48 a 72 hs hábiles (incluye pruebas de burn-in).\n\n" +
                "GARANTÍA OFICIAL:\n" +
                "60 días corridos sobre el ensamblaje y configuración.";

        btnInfo2.setOnClickListener(v -> mostrarPanelAzulInfo(detalleArmadoPc));

        btnContratar2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SolicitudServiceActivity.class);
            intent.putExtra("EXTRA_SERVICIO_TITULO", "Armado de PC Gamer & Cable Management");
            intent.putExtra("EXTRA_SERVICIO_PRECIO", 45000);
            startActivity(intent);
        });

        agregarBotonAccesoPerfil();
    }

    private void agregarBotonAccesoPerfil() {
        ViewGroup rootView = findViewById(android.R.id.content);

        Button btnPerfil = new Button(this);
        btnPerfil.setText("Perfil");
        btnPerfil.setAllCaps(false);
        btnPerfil.setTextColor(Color.WHITE);
        btnPerfil.setTextSize(13f);
        btnPerfil.setPadding(40, 16, 40, 16);
        btnPerfil.setElevation(16f);

        android.graphics.drawable.GradientDrawable fondoRedondeado = new android.graphics.drawable.GradientDrawable();
        fondoRedondeado.setShape(android.graphics.drawable.GradientDrawable.RECTANGLE);
        fondoRedondeado.setCornerRadius(100f);
        fondoRedondeado.setColor(Color.parseColor("#1565C0"));
        fondoRedondeado.setStroke(3, Color.parseColor("#64B5F6"));
        btnPerfil.setBackground(fondoRedondeado);
        btnPerfil.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_perfil, 0, 0, 0);
        btnPerfil.setCompoundDrawablePadding(16);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.TOP | Gravity.END;
        params.setMargins(16, 28, 28, 16);
        btnPerfil.setLayoutParams(params);

        btnPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PerfilActivity.class);
            startActivity(intent);
        });

        rootView.addView(btnPerfil);
    }

    private void mostrarPanelAzulInfo(String contenidoDetalle) {
        if (!panelGenerado) {
            ViewGroup rootView = findViewById(android.R.id.content);

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
                rootView.removeView(overlay);
                panelGenerado = false;
            });

            TextView textoInfo = new TextView(this);
            textoInfo.setText(contenidoDetalle);
            textoInfo.setTextColor(Color.WHITE);
            textoInfo.setTextSize(15);
            textoInfo.setLineSpacing(6, 1.2f);

            panelAzul.addView(btnCerrar);
            panelAzul.addView(textoInfo);
            scrollView.addView(panelAzul);
            overlay.addView(scrollView);

            rootView.addView(overlay);
            panelGenerado = true;
        }
    }
}