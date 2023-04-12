package com.ceashell.critterhop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

import static com.ceashell.critterhop.Main.*;

public class PlayState extends State {
    SpriteBatch sb;
    Texture img;
//    how do I make the grid I use vary based on what level we on? maybe do it in grid..?
    Grid grid;
    int level;
    ArrayList<Level> levels;
    ShapeRenderer sr;
    Critter critter;
    Texture background1;
    Texture border1;
//    Texture gridBackground1;


    public PlayState() {
        this.level = Main.level;
        sb = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        this.levels = Main.levels;
        grid = new Grid(this.levels.get(level));
        sr = new ShapeRenderer();
        critter = new Critter(0);
        background1 = new Texture("gameBackground.png");
        border1 = new Texture("border_with_background.png");
//        gridBackground1 = new Texture("grid_background.png");
    }

    public void render() {
        ScreenUtils.clear(1, 1, 1, 1);
        sb.begin();
        sb.draw(background1, 0,0, WIDTH, HEIGHT);
        sb.draw(border1, 0,0, WIDTH, HEIGHT);
//        sb.draw(gridBackground1, 0,0, WIDTH, HEIGHT);
        grid.render(sb);
        critter.render(sb, grid);
        sb.end();
    }

    public void update() {
        float delta = Gdx.graphics.getDeltaTime();
        grid.update();
        critter.update(grid);
    }


// 🤔


    public void handleInput() {
        grid.handleInput();
    }
}
