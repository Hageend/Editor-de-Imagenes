package core.filtros;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class FiltroInvertir extends Filtro {
    @Override
    public Image aplicar(Image imagen) {
        int width = (int) imagen.getWidth();
        int height = (int) imagen.getHeight();
        WritableImage output = new WritableImage(width, height);
        PixelReader reader = imagen.getPixelReader();
        PixelWriter writer = output.getPixelWriter();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = reader.getColor(x, y);
                Color invertido = new Color(1.0 - color.getRed(), 1.0 - color.getGreen(), 1.0 - color.getBlue(), color.getOpacity());
                writer.setColor(x, y, invertido);
            }
        }
        return output;
    }
}
