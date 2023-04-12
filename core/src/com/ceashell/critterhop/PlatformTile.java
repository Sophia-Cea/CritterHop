package com.ceashell.critterhop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlatformTile extends Tile {
    TextureRegion[] platforms;
    int tileType;
    public PlatformTile(int platformType, int gridPosX, int gridPosY, int margin, int yMargin, double tileSize) {
        super(platformType, gridPosX, gridPosY, margin, yMargin, tileSize);
        Texture platformImg = new Texture("lava_and_platform.png");
        tileType = platformType;
        platforms = new TextureRegion[] {new TextureRegion(platformImg, 48,0,12,12), new TextureRegion(platformImg, 60,0,12,12), new TextureRegion(platformImg, 72, 0, 12, 12)};
        img = platforms[platformType];
    }
}
