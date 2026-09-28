# TechQuote - Aplicación Móvil

Proyecto para el Parcial 1 de **Aplicaciones Móviles** (Escuela Da Vinci).

* **Cátedra:** Sergio Medina
* **Equipo:** Mauricio Rocha & Miguel Rocha

---

## Entorno de Ejecución y Compatibilidad
* **Min SDK:** API 23 (Android 6.0 Marshmallow)
* **Target / Compile SDK:** API 34
* **Dispositivo de Prueba Recomendado:** Emulador Medium Phone (API 24 - Android 7.0 Nougat) o celular físico vía USB.
* **Lenguaje:** Java / XML nativo

---

## Arquitectura y Tecnologías
* **Layout Principal:** `ConstraintLayout` como raíz de pantalla única.
* **Estructura Interna:** `LinearLayout` vertical y horizontal para modularización de los componentes.
* **Inyección Dinámica de Vistas:** Creación e inserción en tiempo de ejecución (desde Java) de un overlay modal con `ScrollView`, `Button` de cierre ("✕") y texto detallado del servicio.
* **Clean Code:** Organización de recursos en `strings.xml`, `colors.xml` y `dimens.xml`. Eliminación de textos hardcodeados.
* **Historial de Versiones:** Commits cruzados bajo el estándar Conventional Commits (`feat:`).

---

## Estructura del Proyecto
* `app/src/main/java/com/example/techquote/MainActivity.java`: Control de interacción, eventos de clic (lambdas) e inyección dinámica.
* `app/src/main/res/layout/activity_main.xml`: Estructura estática del catálogo de servicios.
* `app/src/main/res/drawable/`: Recursos visuales (`img_servicio`, `bg_card.xml`, `bg_badge_disponible.xml`).
* `app/src/main/res/values/`: Archivos de recursos para colores, dimensiones y textos de la app.