import javafx.application.Application;
import javafx.stage.Stage;
import ui.EditorGUI;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        EditorGUI editor = new EditorGUI();
        editor.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

