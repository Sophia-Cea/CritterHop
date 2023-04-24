package com.ceashell.critterhop;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameOverState extends State {
    ShapeRenderer sr;
    SpriteBatch sb;
    float fadeVal = 0f;
    float fadeInc = .01f;
    public GameOverState() {

    }

    public void render() {
        ScreenUtils.clear(1, 1, 1, fadeVal);
    }

    @Override
    public void update() {
        fadeVal += fadeInc;
    }
}
