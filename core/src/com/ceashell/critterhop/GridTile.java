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
    TextureRegion[] tiles;
//    int tileType;
    static double lerpConst = 4;
    double lerpX;
    double lerpY;
    public GridTile(int color, int gridPosX, int gridPosY, int margin, int yMargin, double tileSize) {
        super(color, gridPosX, gridPosY, margin, yMargin, tileSize);
        lerpX = 0;
        lerpY = 0;
//        System.out.println("color: " + color);
//        System.out.println("tiletype: " + tileType);
        Texture tileTexture = new Texture("tiles.png");
        tiles = new TextureRegion[]{new TextureRegion(tileTexture, 0, 0, 12, 12), new TextureRegion(tileTexture, 12, 0, 12, 12), new TextureRegion(tileTexture, 24, 0, 12, 12), new TextureRegion(tileTexture, 36, 0, 12, 12), new TextureRegion(tileTexture, 48, 0, 12, 12), new TextureRegion(tileTexture, 60, 0, 12, 12), new TextureRegion(tileTexture, 72, 0, 12, 12)};
    }


    public void update() {
        rect.x = realPosX;
        rect.y = realPosY;
        if (transitioning) {
            realPosX += lerpX;
            realPosY += lerpY;
//            checks if its close enough to it's new intended position
            if (realPosX > intendedXPos - 3 && realPosX < intendedXPos + 3 && realPosY > intendedYPos - 3 && realPosY < intendedYPos + 3) {
                realPosX = intendedXPos;
                realPosY = intendedYPos;
                transitioning = false;
                lerpX = 0;
                lerpY = 0;
            }
        }
    }

    public void render(SpriteBatch sb) {
        if (transitioning) {
//            System.out.println(intendedYPos);
//            System.out.println(realPosY);
        }
        if (tileType > -1) {
            sb.draw(tiles[tileType], rect.x, rect.y, (int) tileSize, (int) tileSize);
        }
    }

    public void switchTiles(GridTile tileToSwitchWith) {
        if (!transitioning && !tileToSwitchWith.transitioning) {
            transitioning = true;
            tileToSwitchWith.transitioning = true;
            this.intendedXPos = tileToSwitchWith.realPosX;
            this.intendedYPos = tileToSwitchWith.realPosY;
            tileToSwitchWith.intendedXPos = realPosX;
            tileToSwitchWith.intendedYPos = realPosY;
            if (realPosX - intendedXPos != 0) {
                if (realPosX - intendedXPos < 0) {
                    lerpX = lerpConst;
                    tileToSwitchWith.lerpX = -1 * lerpConst;
                } else {
                    lerpX = -1 * lerpConst;
                    tileToSwitchWith.lerpX = lerpConst;
                }
            }
            if (realPosY - intendedYPos != 0) {
                if (realPosY - intendedYPos < 0) {
                    lerpY = lerpConst;
                    tileToSwitchWith.lerpY = -1 * lerpConst;
                } else {
                    lerpY = -1 * lerpConst;
                    tileToSwitchWith.lerpY = lerpConst;
                }
            }
        }
    }
}
