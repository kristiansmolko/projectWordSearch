package game;

import graphics.Graphics;
import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import static graphics.Graphics.background;
import static graphics.Graphics.refresh;

public class Game {
    private static int previousRow = -1;
    private static int previousCol = -1;
    private static final char[][] field = testCharField();

    private Game(){
        throw new IllegalStateException("Utility class");
    }

    public static EventHandler<MouseEvent> labelClickedEvent(Label label, int posR, int posC) {
        return mouseEvent -> {
            if (previousRow == -1 && previousCol == -1){
                previousRow = posR;
                previousCol = posC;
                label.setBackground(background());
            } else {
                System.out.println("Word: " + checkWord(previousRow, previousCol, posR, posC));
                label.setBackground(background());
                previousRow = -1;
                previousCol = -1;
                System.out.println("Initializing pause");
                var pause = new PauseTransition(Duration.millis(2000));
                pause.setOnFinished(event -> {
                    refresh();
                    System.out.println("Pause done, field refreshed!");
                });
                pause.play();
            }
        };
    }

    private static String checkWord(int firstRow, int firstCol, int lastRow, int lastCol){
        if(firstRow == lastRow)
            return checkRow(firstCol, lastCol, firstRow);
        else if (firstCol == lastCol)
            return checkCol(firstRow, lastRow, firstCol);
        else if ((firstCol != lastCol) && (firstRow != lastRow))
            return checkDiagonale(firstRow, firstCol, lastRow, lastCol);
        return "";
    }

    private static String checkRow(int firstCol, int lastCol, int firstRow){
        var text = new StringBuilder();
        if (firstCol < lastCol) {
            for (var i = 0; i < 10; i++)
                if (i >= firstCol && i <= lastCol)
                    text.append(field[firstRow][i]);
        } else {
            for (var i = 9; i >= 0; i--)
                if (i >= lastCol && i <= firstCol)
                    text.append(field[firstRow][i]);
        }
        return text.toString();
    }

    private static String checkCol(int firstRow, int lastRow, int firstCol){
        var text = new StringBuilder();
        if (firstRow < lastRow){
            for (var i = 0; i < 10; i++)
                if (i >= firstRow && i <= lastRow)
                    text.append(field[i][firstCol]);
        } else {
            for (var i = 9; i >= 0; i--)
                if (i >= lastRow && i <= firstRow)
                    text.append(field[i][firstCol]);
        }
        return text.toString();
    }

    private static String checkDiagonale(int firstRow, int firstCol, int lastRow, int lastCol){
        var text = new StringBuilder();
        var diff = 0;
        if ((firstRow < lastRow) && (firstCol < lastCol) && (lastRow-firstRow == lastCol-firstCol)){
            diff = lastRow - firstRow;
            for (var i = 0; i <= diff; i++)
                text.append(field[firstRow+i][firstCol+i]);
        } else if ((firstRow < lastRow) && (firstCol > lastCol) && (lastRow-firstRow == firstCol-lastCol)){
            diff = lastRow - firstRow;
            for (var i = 0; i <= diff; i++)
                text.append(field[firstRow+i][firstCol-i]);
        } else if ((firstRow > lastRow) && (firstCol < lastCol) && (firstRow-lastRow == lastCol-firstCol)){
            diff = firstRow - lastRow;
            for (var i = 0; i <= diff; i++)
                text.append(field[firstRow-i][firstCol+i]);
        } else if ((firstRow > lastRow) && (firstCol > lastCol) && (firstRow-lastRow == firstCol-lastCol)){
            diff = firstRow - lastRow;
            for (var i = 0; i <= diff; i++)
                text.append(field[firstRow-i][firstCol-i]);
        }
        return text.toString();
    }

    public static char[][] testCharField(){
        var field = new char[10][10];
        var character = 60;
        for(var i = 0; i < 10; i++)
            for(var j = 0; j < 10; j++){
                field[i][j] = ((char)(character++));
            }

        return field;
    }
}
