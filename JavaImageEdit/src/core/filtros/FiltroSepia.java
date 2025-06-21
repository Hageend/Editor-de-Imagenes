package core.filtros;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class FiltroSepia extends Filtro {
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
                double r = color.getRed();
                double g = color.getGreen();
                double b = color.getBlue();
                double tr = 0.393 * r + 0.769 * g + 0.189 * b;
                double tg = 0.349 * r + 0.686 * g + 0.168 * b;
                double tb = 0.272 * r + 0.534 * g + 0.131 * b;
                tr = Math.min(1.0, tr);
                tg = Math.min(1.0, tg);
                tb = Math.min(1.0, tb);
                Color sepia = new Color(tr, tg, tb, color.getOpacity());
                writer.setColor(x, y, sepia);
            }
        }
        return output;
    }
}
