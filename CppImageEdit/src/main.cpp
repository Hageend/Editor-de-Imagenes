#include "core/ImageEditor.h"
#include "filters/GrayscaleFilter.h"
#include "filters/InvertFilter.h"
#include "filters/BrightnessFilter.h"
#include "filters/ThresholdFilter.h"
#include "filters/SepiaFilter.h"
#include <iostream>
#include <memory>

void printMenu() {
    std::cout << "\nEditor de Imágenes (consola)\n";
    std::cout << "1. Cargar imagen\n";
    std::cout << "2. Aplicar filtro gris\n";
    std::cout << "3. Aplicar filtro inversión\n";
    std::cout << "4. Aplicar filtro brillo\n";
    std::cout << "5. Aplicar filtro binarización\n";
    std::cout << "6. Aplicar filtro sepia\n";
    std::cout << "7. Guardar imagen\n";
    std::cout << "8. Info de imagen\n";
    std::cout << "0. Salir\n";
    std::cout << "Opción: ";
}

int main() {
    ImageEditor editor;
    int opcion;
    std::string filename;
    bool running = true;
    while (running) {
        printMenu();
        std::cin >> opcion;
        switch (opcion) {
            case 1:
                std::cout << "Archivo a cargar: ";
                std::cin >> filename;
                if (editor.loadImage(filename))
                    std::cout << "Imagen cargada correctamente.\n";
                else
                    std::cout << "Error al cargar la imagen.\n";
                break;
            case 2:
                editor.addFilter(std::make_unique<GrayscaleFilter>());
                editor.applyFilters();
                std::cout << "Filtro gris aplicado.\n";
                break;
            case 3:
                editor.addFilter(std::make_unique<InvertFilter>());
                editor.applyFilters();
                std::cout << "Filtro inversión aplicado.\n";
                break;
            case 4: {
                int delta;
                std::cout << "Valor de brillo (-255 a 255): ";
                std::cin >> delta;
                editor.addFilter(std::make_unique<BrightnessFilter>(delta));
                editor.applyFilters();
                std::cout << "Filtro brillo aplicado.\n";
                break;
            }
            case 5: {
                int t;
                std::cout << "Umbral (0-255): ";
                std::cin >> t;
                editor.addFilter(std::make_unique<ThresholdFilter>((unsigned char)t));
                editor.applyFilters();
                std::cout << "Filtro binarización aplicado.\n";
                break;
            }
            case 6:
                editor.addFilter(std::make_unique<SepiaFilter>());
                editor.applyFilters();
                std::cout << "Filtro sepia aplicado.\n";
                break;
            case 7:
                std::cout << "Archivo para guardar: ";
                std::cin >> filename;
                if (editor.saveImage(filename))
                    std::cout << "Imagen guardada correctamente.\n";
                else
                    std::cout << "Error al guardar la imagen.\n";
                break;
            case 8: {
                Image& img = editor.getImage();
                std::cout << "Dimensiones: " << img.width << "x" << img.height << ", canales: " << img.channels << "\n";
                break;
            }
            case 0:
                running = false;
                break;
            default:
                std::cout << "Opción no válida.\n";
        }
    }
    return 0;
}
