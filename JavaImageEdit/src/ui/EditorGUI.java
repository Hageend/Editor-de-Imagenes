package ui;

import core.Imagen;
import core.EditorImagen;
import core.filtros.FiltroGrises;
import core.filtros.FiltroInvertir;
import core.filtros.FiltroSepia;
import core.filtros.FiltroRotar90;
import core.filtros.FiltroRotarMenos90;
import core.filtros.FiltroEspejarH;
import core.filtros.FiltroEspejarV;
import core.filtros.FiltroBrillo;
import core.filtros.FiltroRedimensionar;
import core.filtros.FiltroBinarizar;
import core.filtros.FiltroDesenfoque;
import core.filtros.FiltroBordes;
import core.filtros.FiltroRealzar;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;

public class EditorGUI {
    private BorderPane root;
    private VBox menuLateral;
    private ImageView imageView;
    private Imagen imagen;
    private EditorImagen editor;
    private Button btnGrises, btnInvertir, btnSepia, btnDeshacer, btnRehacer, btnGuardar;
    // Añadir referencias a todos los botones de edición y filtros
    private Button btnRotar90, btnRotarMenos90, btnRedimensionar, btnEspejarH, btnEspejarV, btnBrilloMas, btnBrilloMenos;
    private Button btnDesenfoque, btnBordes, btnRealzar, btnBinarizar;

    public void start(Stage primaryStage) {
        root = new BorderPane();
        menuLateral = new VBox(18);
        menuLateral.setStyle("-fx-background-color: #23272e; -fx-padding: 18px;");
        new ArrayList<>();
        imagen = new Imagen();
        editor = new EditorImagen(imagen);

        // Menú Archivo
        Label lblArchivo = new Label("Archivo");
        lblArchivo.setFont(Font.font("Segoe UI", 18));
        lblArchivo.setTextFill(Color.WHITE);
        Button btnAbrir = new Button("Abrir imagen");
        btnAbrir.setMaxWidth(Double.MAX_VALUE);
        btnAbrir.setOnAction(e -> abrirImagen(primaryStage));
        btnGuardar = new Button("Guardar");
        btnGuardar.setMaxWidth(Double.MAX_VALUE);
        btnGuardar.setOnAction(e -> guardarImagen(primaryStage));

        // Menú Edición
        Label lblEdicion = new Label("Edición");
        lblEdicion.setFont(Font.font("Segoe UI", 18));
        lblEdicion.setTextFill(Color.WHITE);
        btnRotar90 = new Button("Rotar 90°");
        btnRotar90.setMaxWidth(Double.MAX_VALUE);
        btnRotar90.setOnAction(e -> aplicarFiltroEdicion(new FiltroRotar90()));
        btnRotarMenos90 = new Button("Rotar -90°");
        btnRotarMenos90.setMaxWidth(Double.MAX_VALUE);
        btnRotarMenos90.setOnAction(e -> aplicarFiltroEdicion(new FiltroRotarMenos90()));
        btnRedimensionar = new Button("Redimensionar...");
        btnRedimensionar.setMaxWidth(Double.MAX_VALUE);
        btnRedimensionar.setOnAction(e -> mostrarDialogoRedimensionar());
        btnEspejarH = new Button("Espejar H");
        btnEspejarH.setMaxWidth(Double.MAX_VALUE);
        btnEspejarH.setOnAction(e -> aplicarFiltroEdicion(new FiltroEspejarH()));
        btnEspejarV = new Button("Espejar V");
        btnEspejarV.setMaxWidth(Double.MAX_VALUE);
        btnEspejarV.setOnAction(e -> aplicarFiltroEdicion(new FiltroEspejarV()));
        btnBrilloMas = new Button("Brillo +");
        btnBrilloMas.setMaxWidth(Double.MAX_VALUE);
        btnBrilloMas.setOnAction(e -> aplicarFiltroEdicion(new FiltroBrillo(0.1)));
        btnBrilloMenos = new Button("Brillo -");
        btnBrilloMenos.setMaxWidth(Double.MAX_VALUE);
        btnBrilloMenos.setOnAction(e -> aplicarFiltroEdicion(new FiltroBrillo(-0.1)));
        btnDeshacer = new Button("Deshacer");
        btnDeshacer.setMaxWidth(Double.MAX_VALUE);
        btnDeshacer.setOnAction(e -> {
            editor.deshacer();
            imageView.setImage(imagen.getImagen());
            actualizarEstadoBotones();
        });
        btnRehacer = new Button("Rehacer");
        btnRehacer.setMaxWidth(Double.MAX_VALUE);
        btnRehacer.setOnAction(e -> {
            editor.rehacer();
            imageView.setImage(imagen.getImagen());
            actualizarEstadoBotones();
        });

        // Menú Filtros
        Label lblFiltros = new Label("Filtros");
        lblFiltros.setFont(Font.font("Segoe UI", 18));
        lblFiltros.setTextFill(Color.WHITE);
        btnGrises = new Button("Escala de Grises");
        btnGrises.setMaxWidth(Double.MAX_VALUE);
        btnGrises.setOnAction(e -> aplicarFiltroConPopup(new FiltroGrises()));
        btnDesenfoque = new Button("Desenfoque");
        btnDesenfoque.setMaxWidth(Double.MAX_VALUE);
        btnDesenfoque.setOnAction(e -> aplicarFiltroConPopup(new FiltroDesenfoque()));
        btnBordes = new Button("Bordes");
        btnBordes.setMaxWidth(Double.MAX_VALUE);
        btnBordes.setOnAction(e -> aplicarFiltroConPopup(new FiltroBordes()));
        btnRealzar = new Button("Realzar");
        btnRealzar.setMaxWidth(Double.MAX_VALUE);
        btnRealzar.setOnAction(e -> aplicarFiltroConPopup(new FiltroRealzar()));
        btnSepia = new Button("Sepia");
        btnSepia.setMaxWidth(Double.MAX_VALUE);
        btnSepia.setOnAction(e -> aplicarFiltroConPopup(new FiltroSepia()));
        btnInvertir = new Button("Invertir");
        btnInvertir.setMaxWidth(Double.MAX_VALUE);
        btnInvertir.setOnAction(e -> aplicarFiltroConPopup(new FiltroInvertir()));
        btnBinarizar = new Button("Binarizar");
        btnBinarizar.setMaxWidth(Double.MAX_VALUE);
        btnBinarizar.setOnAction(e -> mostrarDialogoBinarizar());

        VBox menuArchivo = new VBox(8, lblArchivo, btnAbrir, btnGuardar);
        VBox menuEdicion = new VBox(8, lblEdicion, btnRotar90, btnRotarMenos90, btnRedimensionar, btnEspejarH, btnEspejarV, btnBrilloMas, btnBrilloMenos, btnDeshacer, btnRehacer);
        VBox menuFiltros = new VBox(8, lblFiltros, btnGrises, btnDesenfoque, btnBordes, btnRealzar, btnSepia, btnInvertir, btnBinarizar);
        menuArchivo.setStyle("-fx-background-color: transparent;");
        menuEdicion.setStyle("-fx-background-color: transparent;");
        menuFiltros.setStyle("-fx-background-color: transparent;");

        menuLateral.getChildren().clear();
        menuLateral.getChildren().addAll(menuArchivo, new Separator(), menuEdicion, new Separator(), menuFiltros);

        ScrollPane scrollMenu = new ScrollPane(menuLateral);
        scrollMenu.setFitToWidth(true);
        scrollMenu.setPrefWidth(220);
        scrollMenu.setStyle("-fx-background: #23272e; -fx-border-color: #23272e;");

        imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(650);
        imageView.setFitHeight(500);
        imageView.setStyle("-fx-effect: dropshadow(gaussian, #00000088, 16, 0.2, 0, 2);");

        root.setLeft(scrollMenu);
        root.setCenter(imageView);
        root.setStyle("-fx-background-color: #181a20;");

        Scene scene = new Scene(root, 1050, 650);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaImageEdit - JavaFX");
        primaryStage.show();
        actualizarEstadoBotones();
    }

    private void abrirImagen(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir imagen");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif")
        );
        File archivo = fileChooser.showOpenDialog(stage);
        if (archivo != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cargando");
            alert.setHeaderText(null);
            alert.setContentText("Cargando imagen, por favor espere...");
            alert.initOwner(stage);
            alert.show();
            Platform.runLater(() -> {
                imagen.cargarDesdeArchivo(archivo);
                imageView.setImage(imagen.getImagen());
                editor = new EditorImagen(imagen); // reinicia historial
                actualizarEstadoBotones();
                alert.close();
            });
        }
    }

    private void guardarImagen(Stage stage) {
        if (imagen.estaVacia()) return;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar imagen");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("PNG", "*.png"),
            new FileChooser.ExtensionFilter("JPEG", "*.jpg", "*.jpeg"),
            new FileChooser.ExtensionFilter("BMP", "*.bmp")
        );
        File archivo = fileChooser.showSaveDialog(stage);
        if (archivo != null) {
            // Guardar usando javafx.embed.swing.SwingFXUtils
            try {
                String ext = "png";
                String nombre = archivo.getName().toLowerCase();
                if (nombre.endsWith(".jpg") || nombre.endsWith(".jpeg")) ext = "jpg";
                else if (nombre.endsWith(".bmp")) ext = "bmp";
                javax.imageio.ImageIO.write(
                    javafx.embed.swing.SwingFXUtils.fromFXImage(imagen.getImagen(), null),
                    ext, archivo
                );
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // Filtros de edición
    private void aplicarFiltroEdicion(core.filtros.Filtro filtro) {
        if (!imagen.estaVacia()) {
            editor.aplicarFiltro(filtro);
            imageView.setImage(imagen.getImagen());
        }
        actualizarEstadoBotones();
    }

    // Filtros de menú Filtros
    private void aplicarFiltroConPopup(core.filtros.Filtro filtro) {
        if (!imagen.estaVacia()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cargando");
            alert.setHeaderText(null);
            alert.setContentText("Aplicando filtro, por favor espere...");
            alert.initOwner(root.getScene().getWindow());
            alert.show();
            Platform.runLater(() -> {
                editor.aplicarFiltro(filtro);
                imageView.setImage(imagen.getImagen());
                alert.close();
                actualizarEstadoBotones();
            });
        } else {
            actualizarEstadoBotones();
        }
    }

    private void actualizarEstadoBotones() {
        boolean hayImagen = !imagen.estaVacia();
        btnGrises.setDisable(!hayImagen);
        btnInvertir.setDisable(!hayImagen);
        btnSepia.setDisable(!hayImagen);
        btnGuardar.setDisable(!hayImagen);
        btnDeshacer.setDisable(!hayImagen || !editor.puedeDeshacer());
        btnRehacer.setDisable(!hayImagen || !editor.puedeRehacer());
        // Deshabilitar todos los botones de edición y filtros si no hay imagen
        btnRotar90.setDisable(!hayImagen);
        btnRotarMenos90.setDisable(!hayImagen);
        btnRedimensionar.setDisable(!hayImagen);
        btnEspejarH.setDisable(!hayImagen);
        btnEspejarV.setDisable(!hayImagen);
        btnBrilloMas.setDisable(!hayImagen);
        btnBrilloMenos.setDisable(!hayImagen);
        btnDesenfoque.setDisable(!hayImagen);
        btnBordes.setDisable(!hayImagen);
        btnRealzar.setDisable(!hayImagen);
        btnBinarizar.setDisable(!hayImagen);
    }

    // Diálogo para redimensionar
    private void mostrarDialogoRedimensionar() {
        if (imagen.estaVacia()) return;
        javafx.scene.control.TextInputDialog dialog = new javafx.scene.control.TextInputDialog();
        dialog.setTitle("Redimensionar imagen");
        dialog.setHeaderText("Introduce el nuevo ancho y alto (ej: 400x300)");
        dialog.setContentText("Ancho x Alto:");
        dialog.getEditor().setText((int)imagen.getImagen().getWidth() + "x" + (int)imagen.getImagen().getHeight());
        dialog.showAndWait().ifPresent(input -> {
            String[] partes = input.toLowerCase().replace(" ", "").split("x");
            if (partes.length == 2) {
                try {
                    int w = Integer.parseInt(partes[0]);
                    int h = Integer.parseInt(partes[1]);
                    if (w > 0 && h > 0) {
                        aplicarFiltroEdicion(new FiltroRedimensionar(w, h));
                    }
                } catch (Exception ignored) {}
            }
        });
    }

    // Diálogo para binarizar
    private void mostrarDialogoBinarizar() {
        if (imagen.estaVacia()) return;
        javafx.scene.control.TextInputDialog dialog = new javafx.scene.control.TextInputDialog("0.5");
        dialog.setTitle("Binarizar imagen");
        dialog.setHeaderText("Introduce el umbral (0.0 a 1.0)");
        dialog.setContentText("Umbral:");
        dialog.showAndWait().ifPresent(input -> {
            try {
                double umbral = Double.parseDouble(input);
                if (umbral >= 0.0 && umbral <= 1.0) {
                    aplicarFiltroConPopup(new FiltroBinarizar(umbral));
                }
            } catch (Exception ignored) {}
        });
    }
}
