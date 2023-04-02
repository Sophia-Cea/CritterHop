package com.ceashell.critterhop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class PlayState extends State {
    SpriteBatch batch;
    Texture img;
//    how do I make the grid I use vary based on what level we on? maybe do it in grid..?
    Grid grid;
    public static int level = 0;
    ArrayList<Level> levels;
    ShapeRenderer sr;

    public PlayState(ArrayList<Level> levels) {
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        this.levels = levels;
        grid = new Grid(this.levels.get(level));
        sr = new ShapeRenderer();
    }

    public void render() {
        ScreenUtils.clear(1, 1, 1, 1);
//        batch.begin();
//        batch.draw(img, 0, 0);
//        batch.end();
        sr.begin(ShapeRenderer.ShapeType.Filled);
        grid.render(sr);
        sr.end();
    }

    public void update() {
        float delta = Gdx.graphics.getDeltaTime();
    }

    public void handleInput() {

    }
}
