package core.filtros;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class FiltroGrises extends Filtro {
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
                double gray = 0.2126 * color.getRed() + 0.7152 * color.getGreen() + 0.0722 * color.getBlue();
                writer.setColor(x, y, new Color(gray, gray, gray, color.getOpacity()));
            }
        }
        return output;
    }
}
