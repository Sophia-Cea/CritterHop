package com.ceashell.critterhop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.awt.*;

import static com.ceashell.critterhop.Main.HEIGHT;

public class Tile {
    int GridPosX;
    int GridPosY;
    int realPosX;
    int realPosY;
    TextureRegion img;
    Rectangle rect;
    double tileSize;
    int tileType;
    public Tile(int tileType, int gridPosX, int gridPosY, int margin, int yMargin, double tileSize) {
        this.tileType = tileType;
        this.tileSize = tileSize;
        this.GridPosX = gridPosX;
        this.GridPosY = gridPosY;
        rect = new Rectangle(2+(int)(margin + gridPosX * tileSize), (int)(HEIGHT - tileSize - (yMargin + gridPosY * tileSize)), (int)(tileSize+1), (int)(tileSize+1));
        realPosX = rect.x;
        realPosY = rect.y;
    }

    public void render(SpriteBatch sb) {
        sb.draw(img, rect.x, rect.y, (int)tileSize, (int)tileSize);
    }

    public void update() {

    }
}
