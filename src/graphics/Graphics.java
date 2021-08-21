package graphics;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;

import java.util.Map;

import static game.Game.*;

public class Graphics {
    private static final char[][] field = testCharField();
    private static final GridPane labelField = labelField();
    private static final GridPane wordField = wordField(getWords());
    private static final BorderPane root = new BorderPane();

    public BorderPane board(){
        root.setCenter(labelField);
        root.setBottom(wordField);

        return root;
    }

    private static GridPane labelField(){
        var grid = new GridPane();
        grid.setTranslateX(200);
        grid.setTranslateY(20);
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

    private static GridPane wordField(Map<String, Boolean> list){
        var words = new GridPane();
        words.setTranslateX(300);
        words.setHgap(20);
        words.setVgap(20);
        var row = 0;
        var i = 0;
        for (Map.Entry<String, Boolean> entry : list.entrySet()){
            var label = new Label(entry.getKey());

            var line = new Line();
            line.setStartX(0);
            line.setEndX(30);

            var stack = new StackPane();
            stack.getChildren().add(label);
            if (Boolean.TRUE.equals(entry.getValue()))
                stack.getChildren().add(line);

            if (i % 5 == 0) row++;
            words.addRow(row, stack);
            i++;
        }

        return words;
    }

    public static Background background(){
        var backgroundImage = new Image("circle.jpg");
        var bSize = new BackgroundSize(50, 50, false, false, true, false);
        return new Background(new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bSize));
    }

    public static void refresh(){
        root.setCenter(labelField());
        root.setBottom(wordField(getWords()));
    }
}
