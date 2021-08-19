package graphics;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import static game.Game.*;

public class Graphics {
    private static final char[][] field = testCharField();
    private static final GridPane labelField = labelField();
    private static final BorderPane root = new BorderPane();

    public BorderPane board(){
        root.setCenter(labelField);

        return root;
    }

    private static GridPane labelField(){
        var grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        for(var i = 0; i < 10; i++) {
            for (var j = 0; j < 10; j++) {
                var label = new Label(String.valueOf(field[i][j]));
                label.setOnMouseClicked(labelClickedEvent(label, i, j));
                label.setStyle("-fx-label-padding: 0 0 0 0.75em;");
                label.setPrefHeight(30);
                label.setPrefWidth(30);
                grid.addRow(i, label);
            }
        }

        return grid;
    }

    public static Background background(){
        var backgroundImage = new Image("circle.jpg");
        var bSize = new BackgroundSize(50, 50, false, false, true, false);
        return new Background(new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bSize));
    }

    public static void refresh(){
        root.setCenter(labelField());
    }
}
