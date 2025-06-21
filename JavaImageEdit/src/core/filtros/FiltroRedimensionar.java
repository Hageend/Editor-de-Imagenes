package core.filtros;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;

public class FiltroRedimensionar extends Filtro {
    private final int nuevoAncho;
    private final int nuevoAlto;
    public FiltroRedimensionar(int nuevoAncho, int nuevoAlto) {
        this.nuevoAncho = nuevoAncho;
        this.nuevoAlto = nuevoAlto;
    }
    @Override
    public Image aplicar(Image imagen) {
        WritableImage output = new WritableImage(nuevoAncho, nuevoAlto);
        PixelReader reader = imagen.getPixelReader();
        PixelWriter writer = output.getPixelWriter();
        double escalaX = imagen.getWidth() / nuevoAncho;
        double escalaY = imagen.getHeight() / nuevoAlto;
        for (int y = 0; y < nuevoAlto; y++) {
            for (int x = 0; x < nuevoAncho; x++) {
                int srcX = (int) (x * escalaX);
                int srcY = (int) (y * escalaY);
                writer.setColor(x, y, reader.getColor(srcX, srcY));
            }
        }
        return output;
    }
}
