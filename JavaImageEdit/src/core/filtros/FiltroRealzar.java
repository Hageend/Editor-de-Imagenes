package core.filtros;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class FiltroRealzar extends Filtro {
    @Override
    public Image aplicar(Image imagen) {
        int width = (int) imagen.getWidth();
        int height = (int) imagen.getHeight();
        WritableImage output = new WritableImage(width, height);
        PixelReader reader = imagen.getPixelReader();
        PixelWriter writer = output.getPixelWriter();
        int[][] kernel = {
            {0, -1, 0},
            {-1, 5, -1},
            {0, -1, 0}
        };
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                double r = 0, g = 0, b = 0;
                for (int ky = -1; ky <= 1; ky++) {
                    for (int kx = -1; kx <= 1; kx++) {
                        Color c = reader.getColor(x + kx, y + ky);
                        int k = kernel[ky + 1][kx + 1];
                        r += c.getRed() * k;
                        g += c.getGreen() * k;
                        b += c.getBlue() * k;
                    }
                }
                r = Math.min(Math.max(r, 0), 1);
                g = Math.min(Math.max(g, 0), 1);
                b = Math.min(Math.max(b, 0), 1);
                writer.setColor(x, y, new Color(r, g, b, reader.getColor(x, y).getOpacity()));
            }
        }
        return output;
    }
}
