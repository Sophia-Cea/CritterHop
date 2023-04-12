package com.ceashell.critterhop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SwipeParticle {
    int xPos;
    int yPos;
    double size;
    double opacity;
    static Texture img = new Texture("swipe_particle.png");
    public SwipeParticle(int x, int y) {
        xPos = x;
        yPos = y;
        size = 7;
        opacity = 1;
    }

    public void render(SpriteBatch sb) {
        sb.draw(img, xPos, yPos, (int)size, (int)size);
        if (size > 0) {
            size -= .4;
        }
    }

    public boolean particleDone() {
        if (size <= .1) {
            return true;
        }
        return false;
    }
}
