# Proyecto General: Editor de Imágenes

Este proyecto es un editor de imágenes modular y extensible, diseñado para demostrar principios de programación orientada a objetos (POO) y buenas prácticas de desarrollo de software.

## Características
- **Modularidad**: Código organizado en módulos y clases.
- **Extensibilidad**: Fácil de agregar nuevos filtros y funcionalidades.
- **Interactividad**: Menú interactivo por consola para aplicar filtros y operaciones.
- **Soporte de formatos**: Carga y guarda imágenes en formatos populares como PNG, JPG, BMP, y TGA.

## Estructura del Proyecto
```
Proyecto/
├── src/
│   ├── core/         # Clases principales como Image, ImageEditor, IFilter
│   ├── filters/      # Implementaciones de filtros como Grayscale, Sepia, etc.
│   ├── main.cpp      # Punto de entrada del programa
│   ├── stb_image.h   # Librería para cargar imágenes
│   └── stb_image_write.h # Librería para guardar imágenes
├── README.md         # Documentación del proyecto
├── .gitignore        # Archivos y carpetas ignorados por Git
```

## Requisitos
- **Lenguaje**: C++17 o superior
- **Librerías**: stb_image, stb_image_write
- **Compilador**: g++, clang++ o equivalente

## Compilación
Ejecuta el siguiente comando para compilar el proyecto:
```sh
g++ -Isrc/core -Isrc/filters src/main.cpp src/core/*.cpp src/filters/*.cpp -o imageedit.exe
```

## Uso
1. Ejecuta el programa:
   ```sh
   ./imageedit.exe
   ```
2. Sigue las instrucciones del menú para cargar imágenes, aplicar filtros y guardar los resultados.

