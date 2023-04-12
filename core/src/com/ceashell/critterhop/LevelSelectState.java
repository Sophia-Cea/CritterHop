package com.ceashell.critterhop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.*;
import java.util.ArrayList;

import static com.ceashell.critterhop.Main.*;

public class LevelSelectState extends State {
    ArrayList<Boolean> levelsUnlocked;
    ArrayList<Level> levels;
    ArrayList<Rectangle> levelRects;
    SpriteBatch sb;
    ShapeRenderer sr;
    int levelTileSize;
    final int xMargin = 80;
    final int yMarginTop = 150;
    final int spacing = 8;
    final int maxTilesPerRow = 4;
    Texture background;
    Texture bubbleIcon;
    Texture lockIcon;
    SwimmingFish[] fishes;
    public LevelSelectState() {
        this.levelsUnlocked = Main.levelsUnlocked;
        this.levels = Main.levels;
        sb = new SpriteBatch();
        sr = new ShapeRenderer();
        background = new Texture("level_select_bg.png");
        bubbleIcon = new Texture("bubble_icon30.png");
        lockIcon = new Texture("lock.png");
        levelTileSize = (WIDTH - 2*xMargin - spacing*(maxTilesPerRow-1))/maxTilesPerRow;
        levelRects = new ArrayList<>();
        for (int i=0; i<this.levelsUnlocked.size(); i++) {
            levelRects.add(new Rectangle(xMargin+(levelTileSize+spacing)*(i-maxTilesPerRow*(int)(i/maxTilesPerRow)), HEIGHT-(yMarginTop+levelTileSize+(i/maxTilesPerRow)*(levelTileSize+spacing)), levelTileSize, levelTileSize));
        }
        fishes = new SwimmingFish[(int)(Math.random()*3)+3];
        for (int i=0; i<fishes.length; i++) {
            fishes[i] = new SwimmingFish();
        }
    }

    public void render() {
//        ScreenUtils.clear(1, 1, 1, 1);
        sb.begin();
        sb.draw(background, 0,0, WIDTH, HEIGHT);
        for (int i=0; i<fishes.length; i++) {
            fishes[i].render(sb);
        }
//        sb.end();
//        sr.begin(ShapeRenderer.ShapeType.Filled);
        for (int i=0; i<levelsUnlocked.size(); i++) {
            if (levelsUnlocked.get(i)) {
//                sr.setColor(Color.YELLOW);
//                sb.draw(bubbleIcon, );
                sb.draw(bubbleIcon, levelRects.get(i).x, levelRects.get(i).y, levelRects.get(i).width, levelRects.get(i).height);
            } else {
//                sr.setColor(Color.PURPLE);
                sb.draw(bubbleIcon, levelRects.get(i).x, levelRects.get(i).y, levelRects.get(i).width, levelRects.get(i).height);
                sb.draw(lockIcon, levelRects.get(i).x, levelRects.get(i).y, levelRects.get(i).width, levelRects.get(i).height);
            }
//            sr.rect(levelRects.get(i).x, levelRects.get(i).y, levelRects.get(i).width, levelRects.get(i).height);
        }
//        sr.end();
        sb.end();
    }

    public void update() {
        for (int i=0; i<fishes.length; i++) {
            fishes[i].update();
        }
    }

    public void handleInput() {
        if (Gdx.input.justTouched()) {
            double[] pos = new double[] {Gdx.input.getX(), Gdx.input.getY()};
            for (int i=0; i<levelRects.size(); i++) {
                if (levelRects.get(i).contains(pos[0], HEIGHT-pos[1])) {
                    if (levelsUnlocked.get(i)) {
                        stateManager.push(new PlayState());
                        return;
                    }
                }
            }
        }
    }
}
