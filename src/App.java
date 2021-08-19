import graphics.Graphics;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    Graphics graphics = new Graphics();
    @Override
    public void start(Stage stage) throws Exception {
        var scene = new Scene(graphics.board(), 1000, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
