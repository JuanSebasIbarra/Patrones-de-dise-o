# Patrones-de-diseño — Builder/Prototype con UI Swing

Proyecto de ejemplo en Java que demuestra los patrones Builder y Prototype aplicados a
paquetes de vacaciones, con una interfaz gráfica en Swing para crear, editar, clonar
y mostrar paquetes de viaje; el UI soporta selección de imágenes y miniaturas.

Compilación y ejecución (Linux, fish):

```fish
mkdir -p out
javac -d out src/*.java src/gui/*.java src/ui/*.java src/ui/impl/*.java
java -cp out Main    # abre la GUI (necesita entorno gráfico)
java -cp out Main --cli  # ejecuta el demo por consola
```

Uso de imágenes:
- En el formulario, pulsa "Seleccionar imagen" para elegir un archivo (jpg/png).
- La ruta se guarda en el paquete y la miniatura aparece en la lista.

Mejoras sugeridas:
- Persistencia (guardar/cargar JSON) y manejo de imágenes internas.
- Cache de miniaturas y filtrado de tipos en el selector.
- Reemplazar JList por JTable para una presentación en columnas.
