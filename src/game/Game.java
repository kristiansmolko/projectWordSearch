package game;

import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.util.*;

import static graphics.Graphics.*;

public class Game {
    private static int previousRow = -1;
    private static int previousCol = -1;
    private static final char[][] field = testCharField();
    private static final Map<String, Boolean> words = initWords();

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
                var quess = checkWord(previousRow, previousCol, posR, posC).toLowerCase();
                System.out.println("Word: " + quess);
                label.setBackground(background());
                if (isWordCorrect(quess)) {
                    changeWord(quess);
//                    System.out.println(previousRow + ":" + previousCol + "\n" + posR + ":" + posC);
                    addCrossOut(previousRow, previousCol, posR, posC);
                }
                previousRow = -1;
                previousCol = -1;
                var pause = new PauseTransition(Duration.millis(1000));
                pause.setOnFinished(event -> refresh());
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

    private static Map<String, Boolean> initWords(){
        var map = new LinkedHashMap<String, Boolean>();

        map.put("yes", false);
        map.put("no", false);
        map.put("print", false);
        map.put("why", false);
        map.put("abc", false);

        map.put("on", false);
        map.put("cool", false);
        map.put("duh", false);
        map.put("fool", false);
        map.put("pool", false);

        return map;
    }

    public static Map<String, Boolean> getWords(){
        return words;
    }

    public static char[][] testCharField(){
        var field = new char[10][10];
        var character = 60;
        for(var i = 0; i < 10; i++)
            for(var j = 0; j < 10; j++){
                field[i][j] = ((char)(character++));
            }
        field[0][0] = 'y';
        field[0][1] = 'e';
        field[0][2] = 's';

        field[5][4] = 'n';
        field[6][5] = 'o';
        return field;
    }

    private static boolean isWordCorrect(String word){
        for (Map.Entry<String, Boolean> entry : words.entrySet())
            if (word.equals(entry.getKey()))
                return true;
        return false;
    }

    private static void changeWord(String word){
        for (Map.Entry<String, Boolean> entry : words.entrySet())
            if (word.equals(entry.getKey()))
                words.put(word, true);
    }
}
