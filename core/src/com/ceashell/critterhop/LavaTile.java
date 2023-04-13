package com.ceashell.critterhop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class LavaTile extends Tile {
//    TextureRegion[] lavas;
    Animation[] lavas;
    int tileType;
    static float cycleTime = 2;
    Animation lavaAnimation1 = new Animation(new TextureRegion(new Texture("lava_and_platform.png"), 0,0, 24,12), 2, cycleTime);
    Animation lavaAnimation2 = new Animation(new TextureRegion(new Texture("lava_and_platform.png"), 24,0, 24,12), 2, cycleTime);
    Animation animation;
    public LavaTile(int lavaType, int gridPosX, int gridPosY, int margin, int yMargin, double tileSize) {
        super(lavaType, gridPosX, gridPosY, margin, yMargin, tileSize);
        tileType = lavaType;
//        Texture lava = new Texture("lava_and_platform.png");
//        lavas = new TextureRegion[] {new TextureRegion(lava, 0,0,12,12), new TextureRegion(lava, 12, 0, 12,12)};
        lavas = new Animation[] {lavaAnimation1, lavaAnimation2};
//        img = lavas[lavaType];
        animation = lavas[lavaType];
    }

    public void update() {
        animation.update(Gdx.graphics.getDeltaTime());
    }

    public void render(SpriteBatch sb) {
        sb.draw(animation.getFrame(), rect.x, rect.y, (int)tileSize, (int)tileSize);
    }
}
