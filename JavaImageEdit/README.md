# JavaImageEdit

Editor de imágenes modular en Java usando POO, JavaFX y Maven

## Descripción

JavaImageEdit es un editor de imágenes desarrollado en Java, con una arquitectura orientada a objetos y una interfaz gráfica construida con JavaFX. Permite aplicar operaciones y filtros a imágenes de manera sencilla y visual, con un menú lateral organizado y scrollable.

## Características principales

- **Interfaz gráfica**: Menú lateral con scroll visual.
- **Botones inteligentes**: Los botones de edición y filtros solo están habilitados si hay una imagen cargada. "Deshacer" y "Rehacer" solo si hay acciones disponibles.
- **Filtros y operaciones**: Escala de grises, rotaciones, espejado, brillo, redimensionar, desenfoque, bordes, realzar, sepia, invertir, binarizar, etc.
- **POO y modularidad**: Código dividido en clases y módulos (`Imagen`, `EditorImagen`, `EditorGUI`, filtros como clases hijas de `Filtro`).
- **Soporte para deshacer y rehacer**: Historial de acciones para revertir o rehacer cambios fácilmente.

## Estructura del proyecto

```
JavaImageEdit/
├── src/
│   ├── Main.java                # Punto de entrada
│   ├── core/
│   │   ├── Imagen.java          # Clase Imagen (carga, historial, guardar, etc.)
│   │   ├── EditorImagen.java    # Lógica de edición y filtros (clase EditorImagen)
│   │   └── filtros/
│   │       ├── Filtro.java      # Clase base abstracta para filtros
│   │       ├── FiltroGrises.java
│   │       ├── FiltroSepia.java
│   │       ├── ...              # Otros filtros
│   └── ui/
│       └── EditorGUI.java       # Interfaz gráfica y lógica de botones
├── bin/                        # Archivos compilados
├── lib/                        # Dependencias externas
├── run_javafx.bat              # Script para ejecutar la aplicación
├── README.md
```

## Requisitos

- Java 11 o superior
- Maven
- JavaFX SDK (agregado en el classpath)

## Instalación de dependencias

Asegúrate de tener Java, Maven y JavaFX instalados. Si usas Maven, las dependencias se gestionan automáticamente.

## Uso

Ejecuta el editor desde la terminal de Windows:

```powershell
./run_javafx.bat
```

O bien, puedes compilar y ejecutar manualmente con Maven:

```powershell
mvn clean javafx:run
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
- Usa la barra de scroll del menú lateral si hay muchos botones.
