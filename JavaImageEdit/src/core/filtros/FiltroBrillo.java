package core.filtros;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class FiltroBrillo extends Filtro {
    private final double delta;
    public FiltroBrillo(double delta) {
        this.delta = delta;
    }
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
                double r = Math.min(1.0, Math.max(0.0, color.getRed() + delta));
                double g = Math.min(1.0, Math.max(0.0, color.getGreen() + delta));
                double b = Math.min(1.0, Math.max(0.0, color.getBlue() + delta));
                writer.setColor(x, y, new Color(r, g, b, color.getOpacity()));
            }
        }
        return output;
    }
}
