package core;

import core.filtros.Filtro;
import javafx.scene.image.Image;
import java.util.Stack;

public class EditorImagen {
    private Imagen imagen;
    private Stack<Image> historial;
    private Stack<Image> rehacerPila;

    public EditorImagen(Imagen imagen) {
        this.imagen = imagen;
        this.historial = new Stack<>();
        this.rehacerPila = new Stack<>();
    }

    public void aplicarFiltro(Filtro filtro) {
        if (!imagen.estaVacia()) {
            historial.push(imagen.getImagen());
            Image nueva = filtro.aplicar(imagen.getImagen());
            imagen.setImagen(nueva);
            rehacerPila.clear();
        }
    }

    public void deshacer() {
        if (!historial.isEmpty()) {
            rehacerPila.push(imagen.getImagen());
            imagen.setImagen(historial.pop());
        }
    }

    public void rehacer() {
        if (!rehacerPila.isEmpty()) {
            historial.push(imagen.getImagen());
            imagen.setImagen(rehacerPila.pop());
        }
    }

    // Métodos para otras operaciones (rotar, brillo, etc.) pueden agregarse aquí

    /**
     * Indica si se puede deshacer una acción (si hay historial disponible).
     * @return true si hay acciones para deshacer, false en caso contrario.
     */
    public boolean puedeDeshacer() {
        return !historial.isEmpty();
    }

    /**
     * Indica si se puede rehacer una acción (si hay acciones deshechas disponibles).
     * @return true si hay acciones para rehacer, false en caso contrario.
     */
    public boolean puedeRehacer() {
        return !rehacerPila.isEmpty();
    }
}
