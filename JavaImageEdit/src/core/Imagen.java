package core;

import javafx.scene.image.Image;
import java.io.File;

public class Imagen {
    private Image imagen;
    private File archivoOrigen;

    public Imagen() {
        this.imagen = null;
        this.archivoOrigen = null;
    }

    public boolean estaVacia() {
        return imagen == null;
    }

    public void cargarDesdeArchivo(File archivo) {
        this.imagen = new Image(archivo.toURI().toString());
        this.archivoOrigen = archivo;
    }

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image nuevaImagen) {
        this.imagen = nuevaImagen;
    }

    public File getArchivoOrigen() {
        return archivoOrigen;
    }

    // Métodos para guardar, historial, etc. pueden agregarse aquí
}
