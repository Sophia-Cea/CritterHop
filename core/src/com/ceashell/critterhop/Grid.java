package com.ceashell.critterhop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static com.ceashell.critterhop.Main.HEIGHT;
import static com.ceashell.critterhop.Main.WIDTH;
import static com.ceashell.critterhop.Main.level;



//need to add a thingy to the update that will loop thru all the thingies and make sure they're in the correct array position.
// bc when they switch spaces, they switch "gridPos" but they don't switch actual array positions.
// I can do that OR alternatively I can just consistently reset gridPos to it's array position which ngl might be the better choise..

// shit i messed up so bad
// why did i think it was a good idea to refactor without comitting ;;;nnn;;;
public class Grid {
//    int[][] gridArray;
    Tile[][] tileGrid;
//    ArrayList<Tile> tiles;
    ArrayList<SwipeParticle> swipeParticles;
    int tileSize;
    final int margin = 140;
    final int yMargin = 213;
    double[] mousePos1;
    double[] mousePos2;

    public Grid(Level currentLevel) {
        tileGrid = new Tile[currentLevel.levelGrid.length][currentLevel.levelGrid[0].length];
        swipeParticles = new ArrayList();
        tileSize = ((WIDTH - margin*2)/currentLevel.levelGrid.length);
        for (int i=0; i<currentLevel.levelGrid.length; i++) {
            for (int j=0; j<currentLevel.levelGrid[0].length; j++) {
//                can be improved by making a thingy to check if the i,j is in bound, if so return type, if not return -1.
                if (currentLevel.levelGrid[i][j] == 1) {
                    if (i>0 && currentLevel.levelGrid[i-1][j] != 1) {
                        tileGrid[i][j] = new LavaTile(0, j, i, margin, yMargin, tileSize);
                    } else {
                        tileGrid[i][j] = new LavaTile(1, j, i, margin, yMargin, tileSize);
                    }
                } else if (currentLevel.levelGrid[i][j] == 2) {
                    if (j > 0) {
                        if (currentLevel.levelGrid[i][j-1] != 2) {
                            tileGrid[i][j] = new PlatformTile(0, j, i, margin, yMargin, tileSize);
                        }
                    } else {
                        tileGrid[i][j] = new PlatformTile(0, j, i, margin, yMargin, tileSize);
                    }
                    if (j<currentLevel.levelGrid.length-1) {
                        if (currentLevel.levelGrid[i][j + 1] != 2) {
                            tileGrid[i][j] = new PlatformTile(2, j, i, margin, yMargin, tileSize);
                        }
                    } else {
                        tileGrid[i][j] = new PlatformTile(2, j, i, margin, yMargin, tileSize);
                    }
                    if (j>0 && j<currentLevel.levelGrid.length-1) {
                        if (currentLevel.levelGrid[i][j - 1] == 2 && currentLevel.levelGrid[i][j + 1] == 2) {
                            tileGrid[i][j] = new PlatformTile(1, j, i, margin, yMargin, tileSize);
                        }
                    }

                } else {
                    int yee = (int)(Math.random()*6);
//                    System.out.println(yee);
                    tileGrid[i][j] = new GridTile(yee, j, i, margin, yMargin, tileSize);
                }
            }
        }
        clearBoardFromMatches();
//        printTileGrid();
    }

    public void printTileGrid() {
        System.out.println();
        for (int i=0; i<tileGrid.length; i++) {
            for (int j=0; j<tileGrid[0].length; j++) {
                System.out.print(tileGrid[i][j].tileType + ", ");
            }
            System.out.println();
        }
    }

    public void randomizeBlankSpaces() {
//        for (int i=0; i<gridArray.length; i++) {
//            for (int j=0; j<gridArray[0].length; j++) {
//                if (gridArray[i][j] == 0) {
//                    gridArray[i][j] = (int)(Math.random()*6+3);
//                }
//            }
//        }
        for (int i=0; i<tileGrid.length; i++) {
            for (int j = 0; j < tileGrid[0].length; j++) {
                if (tileGrid[i][j].tileType == -1) {
                    tileGrid[i][j].tileType = (int)(Math.random()*6);
                }
            }
        }
    }

    public void render(SpriteBatch sb) {
        for (int i=0; i<tileGrid.length; i++) {
            for (int j=0; j<tileGrid[0].length; j++) {
                tileGrid[i][j].render(sb);
            }
        }
        for (int i=0; i<swipeParticles.size(); i++) {
            swipeParticles.get(i).render(sb);
        }
//        printTileGrid();
    }

    public ArrayList<int[]> findMatches() {
//        returns array list full of [i,j] positions
        ArrayList<int[]> returnArray = new ArrayList<>();
//        first loop thru all the rows
        for (int i=0; i<tileGrid.length; i++) {
            ArrayList<Integer> matches =  findMatchInRow(tileGrid[i]); //findMatchInRow(gridArray[i]);
            if (matches != null) {
                for (int j: matches) {
                    returnArray.add(new int[]{i,j});
                }
            }
        }
//        then loop thru all the colums
        for (int j=0; j<tileGrid[0].length; j++) {
            Tile[] colToRow = new Tile[tileGrid.length];
            for (int k=0; k<tileGrid.length; k++) {
                colToRow[k] = tileGrid[k][j];
            }
            ArrayList<Integer> matches = findMatchInRow(colToRow);
            if (matches != null) {
                for (int i: matches) {
                    returnArray.add(new int[]{i,j});
                }
            }
        }
        return returnArray;
    }
// i can use this same function if I convert a colum into a row and pass it in
    public ArrayList<Integer> findMatchInRow(Tile[] row) {
        ArrayList<Integer> matches = new ArrayList();
        for (int i=0; i<row.length-2; i++) {
            if (row[i].tileType==row[i+1].tileType && row[i+1].tileType==row[i+2].tileType && !(row[i] instanceof LavaTile) && !(row[i] instanceof PlatformTile)) {
                matches.add(i);
                matches.add(i+1);
                matches.add(i+2);
                if (i+3<row.length) {
                    if (row[i + 2].tileType == row[i + 3].tileType) {
                        matches.add(i + 3);
                        if (i+4<row.length) {
                            if (row[i + 3].tileType == row[i + 4].tileType) {
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
            tileGrid[match[0]][match[1]].tileType = -1;
        }
    }

    public boolean checkIfZeroesInGridArray() {
        for (int i=0; i< tileGrid.length; i++) {
            for (int j=0; j< tileGrid[0].length; j++) {
                if (tileGrid[i][j].tileType == -1) {
                    return true;
                }
            }
        }
        return false;
    }

    public int findClosestZeroInRow(int row, int critterXIndex) {
        ArrayList<Integer> allZeroesInRow = new ArrayList();
        ArrayList<Integer> proximityToCritter = new ArrayList();
        for (int i=0; i<tileGrid[row].length; i++) {
            if (tileGrid[row][i].tileType == -1) {
                allZeroesInRow.add(i);
                proximityToCritter.add(Math.abs(critterXIndex-i));
            }
        }
        if (proximityToCritter.size() == 1) {
            return allZeroesInRow.get(0);
        } else if (proximityToCritter.size() > 1) {
            Random rand = new Random();
            return allZeroesInRow.get(rand.nextInt(allZeroesInRow.size()));
        }
        return -1;
    }

    public void clearBoardFromMatches() {
        removeMatches();
        while (checkIfZeroesInGridArray()) {
            randomizeBlankSpaces();
            removeMatches();
        }
    }

    public boolean isInGridBounds(double[] pos) {
        if (pos != null) {
//            Somebody PLEASE explain why the FUCK ITS THIS WAY AND NOT THE OTHER WAY?!?!?!
            if (pos[0] > margin && pos[0] < WIDTH - margin && pos[1] > yMargin && pos[1] < HEIGHT - (HEIGHT - yMargin - tileSize * tileGrid.length)) {
//            if (pos[0] > margin && pos[0] < WIDTH - margin && pos[1] > HEIGHT - yMargin - tileSize * gridArray.length && pos[1] < HEIGHT - yMargin) {
                return true;
            }
        }
        return false;
    }

    public int[] pixelsToGridPos(double[] pos) {
        int[] gridPos = new int[2];
//        gridPos[0] = (int) ((pos[1] - (HEIGHT-yMargin-tileSize*tileGrid.length))/tileSize); // yPos in grid
        gridPos[0] = (int) ((pos[1] - yMargin)/tileSize); // yPos in grid
        gridPos[1] = (int)((pos[0] - margin)/tileSize); // xPos in grid
//        for (int i=0; i<tileGrid.length; i++) {
//            for (int j=0; j<tileGrid[0].length; j++) {
//                if (tileGrid[i][j].rect.contains(pos[0], HEIGHT - pos[1])) {
//                    gridPos[0] = tileGrid[i][j].GridPosY;
//                    gridPos[1] = tileGrid[i][j].GridPosX;
//                }
//            }
//        }
//        System.out.println("[" + gridPos[0] + ", " + gridPos[1] + "]");
        return gridPos;
    }

    public void update() {
        System.out.println(checkFallingTiles());
        mousePos2 = new double[]{Gdx.input.getX(), Gdx.input.getY()};
        for (int i=0; i<swipeParticles.size(); i++) {
            if (swipeParticles.get(i).particleDone()) {
                swipeParticles.remove(i);
            }
        }
        for (int i=0; i<tileGrid.length; i++) {
            for (int j=0; j<tileGrid[0].length; j++) {
                if (tileGrid[i][j] instanceof GridTile) {
                    (tileGrid[i][j]).update();
                    if (((GridTile) tileGrid[i][j]).finishedTransitioning) {
                        switchTiles(i, j, ((GridTile) tileGrid[i][j]).intendedGridPosI, ((GridTile) tileGrid[i][j]).intendedGridPosJ);
                    }
                } else if (tileGrid[i][j] instanceof LavaTile) {
                    (tileGrid[i][j]).update();
                }
            }
        }
        updateTilePositions();
//        printTileGrid();
    }

    public void switchTiles(int i1, int j1, int i2, int j2) {
        GridTile temp = (GridTile) tileGrid[i1][j1];
        tileGrid[i1][j1] = tileGrid[i2][j2];
        tileGrid[i2][j2] = temp;
        ((GridTile) tileGrid[i1][j1]).finishedTransitioning = false;
        ((GridTile) tileGrid[i2][j2]).finishedTransitioning = false;
        ((GridTile) tileGrid[i1][j1]).GridPosX = ((GridTile) tileGrid[i1][j1]).intendedGridPosJ;
        ((GridTile) tileGrid[i2][j2]).GridPosX = ((GridTile) tileGrid[i2][j2]).intendedGridPosJ;
        ((GridTile) tileGrid[i1][j1]).GridPosY = ((GridTile) tileGrid[i1][j1]).intendedGridPosI;
        ((GridTile) tileGrid[i2][j2]).GridPosY = ((GridTile) tileGrid[i2][j2]).intendedGridPosI;
    }

    public void updateTilePositions() {
        for (int i=0; i<tileGrid.length; i++) {
            for (int j=0; j<tileGrid.length; j++) {
//                int[] gridPos = pixelsToGridPos(new double[]{tileGrid[i][j].rect.getCenterX(), tileGrid[i][j].rect.getCenterY()});
//                if (gridPos != new int[]{tileGrid[i][j].GridPosY, tileGrid[i][j].GridPosX}) {
////                    System.out.println("tru");
//                    tileGrid[i][j].GridPosX = gridPos[1];
//                    tileGrid[i][j].GridPosY = gridPos[0];
//                }
                int[] gridPos = pixelsToGridPos(new double[]{tileGrid[i][j].rect.getCenterX(), tileGrid[i][j].rect.getCenterY()});
                tileGrid[i][j].GridPosX = j;
                tileGrid[i][j].GridPosY = i;
            }
        }
    }

    public boolean checkFallingTiles() {
        for (int i=1; i<tileGrid.length-1; i++) {
            for (int j=0; j<tileGrid[0].length; j++) {
                if (tileGrid[i][j].tileType == -1 && tileGrid[i-1][j].tileType != -1) {
                    return false;
                }
            }
        }
        return true;
    }

    public void dropFloatingTiles() {
//        writing down b4 i forget.
//        loop backwards thru array, if hit white space, add 1 to the 1 above it
//        or just use the switchtiles method to switch them. that should cause all of them to fall i think.
//        IT WORKS FIRST TRY!!! when did i get so smart?? it must be my hat
        for (int i=tileGrid.length-1; i>0; i--) {
            for (int j=0; j<tileGrid[0].length; j++) {
                if (tileGrid[i][j].tileType == -1) {
//                    switchTiles(new int[]{i,j}, new int[]{i-1,j});
                    ((GridTile) tileGrid[i][j]).switchTiles((GridTile) tileGrid[i-1][j]);
                }
            }
        }
    }

    public void handleInput() {
        if (Gdx.input.justTouched()) {
            mousePos1 = new double[]{Gdx.input.getX(), Gdx.input.getY()};
            int[] gridPos = pixelsToGridPos(mousePos1);
            printTileGrid();
        }
        if (Gdx.input.isTouched() && isInGridBounds(mousePos1)) {
            swipeParticles.add(new SwipeParticle((int)mousePos2[0], HEIGHT-(int)mousePos2[1]));
            int[] mouseInGridCoords = pixelsToGridPos(mousePos1);

            if (mousePos1 != null && mousePos2 != null) {
                if (mousePos1[1] - mousePos2[1] < -15) {
//                    System.out.println("down swipe");
                    if (tileGrid[mouseInGridCoords[0]][mouseInGridCoords[1]] instanceof GridTile && tileGrid[mouseInGridCoords[0] + 1][mouseInGridCoords[1]] instanceof GridTile && mouseInGridCoords[0]+1 < tileGrid.length) {
//                        System.out.println("tru");
                        ((GridTile) tileGrid[mouseInGridCoords[0]][mouseInGridCoords[1]]).switchTiles((GridTile) tileGrid[mouseInGridCoords[0] + 1][mouseInGridCoords[1]]);
                    }
//                    if (!((GridTile) tileGrid[mouseInGridCoords[0]][mouseInGridCoords[1]]).transitioning) {
//                        if (mouseInGridCoords[0]+1 < tileGrid.length) {
////                            switchTiles(mouseInGridCoords, new int[]{mouseInGridCoords[0] + 1, mouseInGridCoords[1]});
//                        }
//                    }
                } else if (mousePos1[1] - mousePos2[1] > 15) {
//                    System.out.println("up swipe");
                    if (tileGrid[mouseInGridCoords[0]][mouseInGridCoords[1]] instanceof GridTile && tileGrid[mouseInGridCoords[0] - 1][mouseInGridCoords[1]] instanceof GridTile && mouseInGridCoords[0]-1 >= 0) {
                        ((GridTile) tileGrid[mouseInGridCoords[0]][mouseInGridCoords[1]]).switchTiles((GridTile) tileGrid[mouseInGridCoords[0] - 1][mouseInGridCoords[1]]);
                    }
                }
                if (mousePos1[0] - mousePos2[0] < -15) {
//                    System.out.println("right swipe");
                    if (tileGrid[mouseInGridCoords[0]][mouseInGridCoords[1]] instanceof GridTile && tileGrid[mouseInGridCoords[0]][mouseInGridCoords[1] + 1] instanceof GridTile && mouseInGridCoords[1]+1 < tileGrid[0].length) {
                        ((GridTile) tileGrid[mouseInGridCoords[0]][mouseInGridCoords[1]]).switchTiles((GridTile) tileGrid[mouseInGridCoords[0]][mouseInGridCoords[1]+1]);
                    }
                } else if (mousePos1[0] - mousePos2[0] > 15) {
//                    System.out.println("left swipe");
                    if (tileGrid[mouseInGridCoords[0]][mouseInGridCoords[1]] instanceof GridTile && tileGrid[mouseInGridCoords[0]][mouseInGridCoords[1]-1] instanceof GridTile && mouseInGridCoords[1]-1 > 0) {
                        ((GridTile) tileGrid[mouseInGridCoords[0]][mouseInGridCoords[1]]).switchTiles((GridTile) tileGrid[mouseInGridCoords[0]][mouseInGridCoords[1]-1]);
                    }
                }
            }
        } else {
            mousePos1 = null;
            mousePos2 = null;
//            switched = false;
        }
            removeMatches();
            dropFloatingTiles();
//        }
    }
}
