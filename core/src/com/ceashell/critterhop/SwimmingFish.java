package com.ceashell.critterhop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.awt.*;

import static com.ceashell.critterhop.Main.HEIGHT;
import static com.ceashell.critterhop.Main.WIDTH;

public class SwimmingFish {
    Rectangle rect;
    static Texture fishPic = new Texture("fishes.png");
    static TextureRegion[] fishes = new TextureRegion[]{new TextureRegion(fishPic, 0, 0, 39, 19), new TextureRegion(fishPic, 39,0,22, 17), new TextureRegion(fishPic, 61, 0, 65, 21)};
    TextureRegion img;
    int speed;
    boolean facingLeft;
    public SwimmingFish() {
        reset();
    }

    public void render(SpriteBatch sb) {
        if (facingLeft) {
            sb.draw(img, rect.x+rect.width, rect.y, rect.width, rect.height);
        } else {
            sb.draw(img, rect.x-rect.width, rect.y, rect.width, rect.height);
        }
    }

    public void reset() {
        img = fishes[(int) (Math.random()*3)];
        facingLeft = (int) (Math.random()*2) == 1;
        rect = new Rectangle();
        rect.width = img.getRegionWidth() * 7/2;
        if (facingLeft) {
            rect.width *= -1;
            rect.x = WIDTH - 2*rect.width;
        } else {
            rect.x = -rect.width - 5;
        }
        rect.y = (int) (Math.random()*(HEIGHT*2/3-60)) + (int)(HEIGHT/3);
        rect.height = img.getRegionHeight() * 7/2;
        speed = (int)(Math.random()*5) + 1;
    }

    public void update() {
        if (facingLeft) {
            rect.x -= speed;
            if (rect.x < -rect.width) {
                reset();
            }
        } else {
            rect.x += speed;
            if (rect.x > WIDTH+ rect.width+5) {
                reset();
            }
        }
    }
}
