package com.ceashell.critterhop;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.awt.*;

import static com.ceashell.critterhop.Main.HEIGHT;

public class GridTile extends Tile {
    boolean transitioning;
    int intendedXPos;
    int intendedYPos;
    int intendedGridPosI;
    int intendedGridPosJ;
    TextureRegion[] tiles;
//    int tileType;
    static double lerpConst = 4;
    double lerpX;
    double lerpY;
    boolean finishedTransitioning;
    GridTile tileToSwitchWith;
    public GridTile(int color, int gridPosX, int gridPosY, int margin, int yMargin, double tileSize) {
        super(color, gridPosX, gridPosY, margin, yMargin, tileSize);
        lerpX = 0;
        lerpY = 0;
        Texture tileTexture = new Texture("tiles.png");
        tiles = new TextureRegion[]{new TextureRegion(tileTexture, 0, 0, 12, 12), new TextureRegion(tileTexture, 12, 0, 12, 12), new TextureRegion(tileTexture, 24, 0, 12, 12), new TextureRegion(tileTexture, 36, 0, 12, 12), new TextureRegion(tileTexture, 48, 0, 12, 12), new TextureRegion(tileTexture, 60, 0, 12, 12), new TextureRegion(tileTexture, 72, 0, 12, 12)};
    }


    public void update() {
        rect.x = realPosX;
        rect.y = realPosY;

        if (transitioning) {
            realPosX += lerpX;
            realPosY += lerpY;
            if (Math.abs(realPosX-intendedXPos) <= 4 && Math.abs(realPosY-intendedYPos) <= 4) {
                transitioning = false;
                finishedTransitioning = true;
                realPosY = intendedYPos;
                realPosX = intendedXPos;
//                GridPosY = intendedGridPosI;
//                GridPosX = intendedGridPosJ;
                lerpY = 0;
                lerpX = 0;

                tileToSwitchWith.transitioning = false;
                tileToSwitchWith.finishedTransitioning = true;
                tileToSwitchWith.realPosY = tileToSwitchWith.intendedYPos;
                tileToSwitchWith.realPosX = tileToSwitchWith.intendedXPos;
//                tileToSwitchWith.GridPosY = tileToSwitchWith.intendedGridPosI;
//                tileToSwitchWith.GridPosX = tileToSwitchWith.intendedGridPosJ;
                tileToSwitchWith.lerpY = 0;
                tileToSwitchWith.lerpX = 0;

                tileToSwitchWith = null;
            }
        }
    }

    public void render(SpriteBatch sb) {
        if (transitioning) {

        }
        if (tileType > -1) {
            sb.draw(tiles[tileType], rect.x, rect.y, (int) tileSize, (int) tileSize);
        }
    }

    public void switchTiles(GridTile tileToSwitchWith) {
        this.tileToSwitchWith = tileToSwitchWith;
            if (!transitioning) {
                transitioning = true;
                intendedYPos = tileToSwitchWith.realPosY;
                intendedXPos = tileToSwitchWith.realPosX;
                intendedGridPosI = tileToSwitchWith.GridPosY;
                intendedGridPosJ = tileToSwitchWith.GridPosX;


                if (realPosX - intendedXPos < -3) {
                    lerpX = lerpConst;
//                    tileToSwitchWith.lerpX = -1 * lerpConst;
                } else if (realPosX - intendedXPos > 3) {
                    lerpX = -1 * lerpConst;
//                    tileToSwitchWith.lerpX = lerpConst;
                } else {
                    lerpX = 0;
                }

                if (realPosY - intendedYPos < -3) {
                    lerpY = lerpConst;
//                    tileToSwitchWith.lerpY = -1 * lerpConst;
                } else if (realPosY - intendedYPos > 3) {
                    lerpY = -1 * lerpConst;
//                    tileToSwitchWith.lerpY = lerpConst;
                } else {
                    lerpY = 0;
                }

                if (tileToSwitchWith != null) {
                    tileToSwitchWith.switchTiles(this);
                }
            }
//        System.out.println(lerpX + " " + lerpY);

    }
}
