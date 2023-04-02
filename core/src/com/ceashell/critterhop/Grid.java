package com.ceashell.critterhop;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

import static com.ceashell.critterhop.Main.HEIGHT;
import static com.ceashell.critterhop.Main.WIDTH;
import static com.ceashell.critterhop.PlayState.level;

public class Grid {
    int[][] gridArray;
    int tileSize;
    final int margin = 70;
    final int borderSize = 5;
    Color[] colors;
    public Grid(Level currentLevel) {
        gridArray = currentLevel.levelGrid;
        randomizeBlankSpaces();
//        TODO: add spacing between tiles
        tileSize = (int)(WIDTH - margin*2 - borderSize*2)/gridArray.length;
        colors = new Color[]{Color.WHITE, Color.SCARLET, Color.GRAY,Color.LIME, Color.BLUE, Color.GREEN, Color.YELLOW, Color.CYAN, Color.MAGENTA, Color.ORANGE};
        clearBoardFromMatches();
    }

    public void randomizeBlankSpaces() {
        for (int i=0; i<gridArray.length; i++) {
            for (int j=0; j<gridArray[0].length; j++) {
                if (gridArray[i][j] == 0) {
                    gridArray[i][j] = (int)(Math.random()*6+3);
                }
            }
        }
    }

    public void render(ShapeRenderer sr) {
        for (int i=0; i<gridArray.length; i++){
            for (int j=0; j<gridArray[i].length; j++) {
                sr.setColor(colors[gridArray[i][j]]);
                sr.rect(margin+borderSize+j*tileSize, HEIGHT - tileSize - (margin+borderSize+i*tileSize), tileSize, tileSize);
            }
        }
    }

    public ArrayList<int[]> findMatches() {
//        returns array list full of [i,j] positions
        ArrayList<int[]> returnArray = new ArrayList<int[]>();
//        first loop thru all the rows
        for (int i=0; i<gridArray.length; i++) {
            ArrayList<Integer> matches = findMatchInRow(gridArray[i]);
            if (matches != null) {
                System.out.println("found a horizontal match!");
                for (int j: matches) {
                    returnArray.add(new int[]{i,j});
                }
            }
        }
//        then loop thru all the colums
        for (int j=0; j<gridArray[0].length; j++) {
            int[] colToRow = new int[gridArray.length];
            for (int k=0; k<gridArray.length; k++) {
                colToRow[k] = gridArray[k][j];
            }
            ArrayList<Integer> matches = findMatchInRow(colToRow);
            if (matches != null) {
                System.out.println("found a vertical match!");
                for (int i: matches) {
                    returnArray.add(new int[]{i,j});
                }
            }
        }
        return returnArray;
    }
// i can use this same function if I convert a colum into a row and pass it in
    public ArrayList<Integer> findMatchInRow(int[] row) {
        ArrayList<Integer> matches = new ArrayList();
        for (int i=0; i<row.length-2; i++) {
            if (row[i]==row[i+1] && row[i+1]==row[i+2]) {
                matches.add(i);
                matches.add(i+1);
                matches.add(i+2);
                if (i+3<row.length) {
                    if (row[i + 2] == row[i + 3]) {
                        matches.add(i + 3);
                        if (i+4<row.length) {
                            if (row[i + 3] == row[i + 4]) {
                                matches.add(i + 4);
                            }
                        }
                    }
                }
            }
        }
        if (matches.size()>0) {
            return matches;
        } else {
            return null;
        }
    }

    public void removeMatches() {
        ArrayList<int[]> matches = findMatches();
        for (int[] match: matches) {
            gridArray[match[0]][match[1]] = 0;
        }
    }

    public boolean checkIfZeroesInGridArray() {
        for (int i=0; i< gridArray.length; i++) {
            for (int j=0; j< gridArray[0].length; j++) {
                if (gridArray[i][j] == 0) {
                    return true;
                }
            }
        }
        return false;
    }
    public void clearBoardFromMatches() {
        removeMatches();
        while (checkIfZeroesInGridArray()) {
            System.out.println("theres a match! removing now...");
            randomizeBlankSpaces();
            removeMatches();
        }
    }
}
