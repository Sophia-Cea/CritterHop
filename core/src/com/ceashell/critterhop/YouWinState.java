package com.ceashell.critterhop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;

import static com.ceashell.critterhop.Main.stateManager;

public class YouWinState extends State{
    public YouWinState() {

    }

    public void render() {
//        ScreenUtils.clear(1,0,0,1);
        System.out.println("You win!!");
    }

    public void handleInput() {
        if (Gdx.input.isTouched()) {
            stateManager.push(new PlayState());
        }
    }
}
