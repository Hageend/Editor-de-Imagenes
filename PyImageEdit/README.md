# PyImageEdit

Editor de imágenes modular en Python usando POO y Pygame

## Descripción

PyImageEdit es un editor de imágenes desarrollado en Python, con una arquitectura orientada a objetos y una interfaz gráfica construida con Pygame. Permite aplicar operaciones y filtros a imágenes de manera sencilla y visual, con un menú lateral adaptable y scrollable.

## Características principales

- **Interfaz gráfica adaptable**: Menú lateral con scroll visual.
- **Botones inteligentes**: Los botones de edición y filtros solo están habilitados si hay una imagen cargada. "Deshacer" y "Rehacer" solo si hay acciones disponibles.
- **Filtros y operaciones**: Escala de grises, rotaciones, espejado, brillo, redimensionar, desenfoque, bordes, realzar, sepia, invertir, binarizar, etc.
- **POO y modularidad**: Código dividido en clases y módulos (`Imagen`, `EditorImagen`, `EditorGUI`, filtros como clases hijas).

## Estructura del proyecto

```
PyImageEdit/
├── editor_gui.py         # Interfaz gráfica y lógica de botones
├── editor_imagen.py      # Lógica de edición y filtros (clase EditorImagen y filtros)
├── imagen.py             # Clase Imagen (carga, historial, guardar, etc.)
├── main.py               # Punto de entrada
```

## Requisitos

- Python 3.8+
- pygame
- pillow

Instala dependencias con:

```
pip install pygame pillow
```

## Uso

Ejecuta el editor desde la terminal:

```
python PyImageEdit/main.py
```

## Controles y explicación de botones

### Menú Archivo
- **Abrir imagen**: Selecciona y carga una imagen desde tu PC.
- **Guardar**: Guarda la imagen editada en el disco.

### Menú Edición
- **Escala de Grises**: Convierte la imagen a blanco y negro.
- **Rotar 90°**: Rota la imagen 90 grados en sentido horario.
- **Rotar -90°**: Rota la imagen 90 grados en sentido antihorario.
- **Redimensionar...**: Permite cambiar el ancho y alto de la imagen manualmente.
- **Espejar H**: Refleja la imagen horizontalmente.
- **Espejar V**: Refleja la imagen verticalmente.
- **Brillo +**: Aumenta el brillo de la imagen.
- **Brillo -**: Disminuye el brillo de la imagen.
- **Deshacer**: Revierte la última acción realizada.
- **Rehacer**: Reaplica una acción deshecha.

### Menú Filtros
- **Desenfoque**: Aplica un filtro de desenfoque a la imagen.
- **Bordes**: Resalta los bordes de la imagen.
- **Realzar**: Realza los detalles de la imagen.
- **Sepia**: Aplica un filtro sepia para dar un tono antiguo.
- **Invertir**: Invierte los colores de la imagen.
- **Binarizar**: Convierte la imagen a blanco y negro puro (umbral).

- Los botones de edición y filtros solo están habilitados si hay una imagen cargada.

- Usa la rueda del mouse para desplazarte por el menú lateral si hay muchos botones.
