package com.ceashell.critterhop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.ceashell.critterhop.Main.*;

public class Critter {
    double xPos;
    double yPos;
    int[] posInArray;
    Texture tempSprite;
    Animation fishFlop;
//    add the animation textureregions, max fall distance (maybe health too?)
//    need function to check if he toucheth landing pad or lava, and to look for places to go down

    public Critter(int startingXPosInGrid) {
        posInArray = new int[]{-1, startingXPosInGrid};
        tempSprite = new Texture("cockatielSprite.png");
        fishFlop = new Animation(new TextureRegion(new Texture("fishAnimation.png")), 8, 1);
    }

    public void render(SpriteBatch sb, Grid grid) {
//        System.out.println((70 + startPos*tileSize) + ", " + (70+(gridArray.length + 1)*tileSize));
        xPos = grid.margin + posInArray[1]*grid.tileSize;
        yPos = HEIGHT-((posInArray[0]+1.3)*grid.tileSize)-grid.yMargin;
//        sb.draw(tempSprite, (int)xPos, (int)yPos, (int)(grid.tileSize*1.2), (int)(grid.tileSize*1.2));
        sb.draw(fishFlop.getFrame(), (int)xPos-10, (int)yPos, (int)(grid.tileSize*1.4), (int)(grid.tileSize*1.1));
    }

    public void update(Grid grid) {
        fishFlop.update(Gdx.graphics.getDeltaTime());
        if (!grid.checkFallingTiles()) {
            if (posInArray[0] < grid.tileGrid.length - 1) {
                if (grid.tileGrid[posInArray[0] + 1][posInArray[1]].tileType == -1 && grid.tileGrid[posInArray[0] + 1][posInArray[1]] instanceof GridTile) {
                    if (posInArray[0] < grid.tileGrid.length - 1) {
                        posInArray[0] += 1;
                    }
                } else {
                    int nearestZeroInRow = grid.findClosestZeroInRow(posInArray[0] + 1, posInArray[1]);
                    if (nearestZeroInRow >= 0) {
                        posInArray[0] += 1;
                        posInArray[1] = nearestZeroInRow;
                    }
                }
            }
            if (posInArray[0] > -1 && posInArray[0] < grid.tileGrid.length - 1) {
                if (grid.tileGrid[posInArray[0] + 1][posInArray[1]] instanceof LavaTile) {
                    stateManager.push(new GameOverState());
                }
                if (grid.tileGrid[posInArray[0] + 1][posInArray[1]] instanceof PlatformTile) {
                    if (level < levels.size() - 1) {
                        level++;
                    }
                    stateManager.push(new YouWinState());
                }
            }
            if (posInArray[0] > -1 && grid.tileGrid[posInArray[0]][posInArray[1]].tileType != -1) { //checks if he was squshed by a tile :/
                stateManager.push(new GameOverState());
            }
        }

    }



}
