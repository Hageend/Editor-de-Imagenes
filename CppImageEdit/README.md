# CppImageEdit

Editor de imágenes modular en C++ usando POO y consola

## Descripción
CppImageEdit es un editor de imágenes desarrollado en C++ con arquitectura orientada a objetos y un menú interactivo por consola. Permite aplicar filtros y operaciones a imágenes de manera sencilla, modular y extensible.

## Características principales
- **Menú interactivo por consola**: Navega y aplica filtros fácilmente.
- **Filtros y operaciones**: Escala de grises, inversión, brillo, binarización, sepia.
- **POO y modularidad**: Código dividido en clases y módulos (`Image`, `ImageEditor`, filtros como clases hijas de `IFilter`).
- **Carga y guardado real de imágenes**: Soporte para PNG, JPG, BMP, TGA usando stb_image y stb_image_write.

## Estructura del proyecto
```
CppImageEdit/
├── src/
│   ├── core/
│   │   ├── Image.h / Image.cpp         # Clase Imagen (carga, guardar, datos)
│   │   ├── ImageEditor.h / .cpp        # Lógica de edición y filtros
│   │   └── IFilter.h                   # Interfaz base para filtros
│   ├── filters/
│   │   ├── GrayscaleFilter.*           # Filtros concretos (grises, inversión, etc.)
│   │   ├── InvertFilter.*
│   │   ├── BrightnessFilter.*
│   │   ├── ThresholdFilter.*
│   │   └── SepiaFilter.*
│   ├── main.cpp                        # Punto de entrada
│   ├── stb_image.h                     # Librería de carga de imágenes
│   └── stb_image_write.h               # Librería de guardado de imágenes
├── README.md
├── .gitignore
```

## Requisitos
- C++17 o superior
- [stb_image.h](https://github.com/nothings/stb/blob/master/stb_image.h) y [stb_image_write.h](https://github.com/nothings/stb/blob/master/stb_image_write.h) en `src/`
- Compilador compatible (g++, clang++)

## Instalación y compilación

En Windows (MSYS2/MinGW):
```sh
g++ -Isrc/core -Isrc/filters src/main.cpp src/core/Image.cpp src/core/ImageEditor.cpp src/filters/GrayscaleFilter.cpp src/filters/InvertFilter.cpp src/filters/BrightnessFilter.cpp src/filters/ThresholdFilter.cpp src/filters/SepiaFilter.cpp -o imageedit.exe
```

## Uso
Ejecuta el editor desde la terminal:
```sh
./imageedit.exe
```
Sigue el menú para cargar, aplicar filtros y guardar imágenes.

## Controles y explicación de menú
- **1. Cargar imagen**: Selecciona y carga una imagen desde tu PC.
- **2. Aplicar filtro gris**: Convierte la imagen a escala de grises.
- **3. Aplicar filtro inversión**: Invierte los colores de la imagen.
- **4. Aplicar filtro brillo**: Ajusta el brillo de la imagen.
- **5. Aplicar filtro binarización**: Convierte la imagen a blanco y negro puro (umbral).
- **6. Aplicar filtro sepia**: Da un tono antiguo a la imagen.
- **7. Guardar imagen**: Guarda la imagen editada en el disco.
- **8. Info de imagen**: Muestra dimensiones y canales de la imagen cargada.
- **0. Salir**: Cierra el programa.

> Puedes aplicar varios filtros en cadena antes de guardar.
