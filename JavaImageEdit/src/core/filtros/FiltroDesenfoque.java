package core.filtros;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class FiltroDesenfoque extends Filtro {
    @Override
    public Image aplicar(Image imagen) {
        int width = (int) imagen.getWidth();
        int height = (int) imagen.getHeight();
        WritableImage output = new WritableImage(width, height);
        PixelReader reader = imagen.getPixelReader();
        PixelWriter writer = output.getPixelWriter();
        int[] dx = {-1, 0, 1};
        int[] dy = {-1, 0, 1};
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double r = 0, g = 0, b = 0;
                int count = 0;
                for (int i : dx) {
                    for (int j : dy) {
                        int nx = x + i;
                        int ny = y + j;
                        if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                            Color c = reader.getColor(nx, ny);
                            r += c.getRed();
                            g += c.getGreen();
                            b += c.getBlue();
                            count++;
                        }
                    }
                }
                writer.setColor(x, y, new Color(r / count, g / count, b / count, reader.getColor(x, y).getOpacity()));
            }
        }
        return output;
    }
}
