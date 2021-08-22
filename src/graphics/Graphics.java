package graphics;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static game.Game.*;

public class Graphics {
    private static final char[][] field = testCharField();
    private static StackPane labelField = labelField();
    private static final GridPane wordField = wordField(getWords());
    private static final BorderPane root = new BorderPane();
    private static final List<Line> lines = new ArrayList<>();

    public BorderPane board(){
        root.setCenter(labelField);
        root.setBottom(wordField);

        return root;
    }

    private static StackPane labelField(){
        var stack = new StackPane();

        stack.setTranslateX(-250);
        stack.setTranslateY(-200);

        for(var i = 0; i < 10; i++) {
            for (var j = 0; j < 10; j++) {
                var label = new Label(String.valueOf(field[i][j]));
                label.setTranslateY((40*i));
                label.setTranslateX((40*j));
                label.setOnMouseClicked(labelClickedEvent(label, i, j));
                label.setStyle("-fx-label-padding: 0 0 0 0.75em;");
                label.setPrefHeight(30);
                label.setPrefWidth(30);
                stack.getChildren().add(label);
            }
        }
        if (lines != null && !lines.isEmpty())
            for (Line line : lines)
                stack.getChildren().add(line);

        return stack;
    }

    public static void addCrossOut(int firstRow, int firstCol, int lastRow, int lastCol){
        var line = new Line();
        line.setTranslateX((lastCol*40 + firstCol*40)/2);
        line.setTranslateY((lastRow*40 + firstRow*40)/2);
        line.setStartX(40*firstCol);
        line.setStartY(40*firstRow);
        line.setEndX(40*lastCol);
        line.setEndY(40*lastRow);

        lines.add(line);
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
        labelField = labelField();
        root.setCenter(labelField);
        root.setBottom(wordField(getWords()));
    }
}
