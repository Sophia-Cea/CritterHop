package com.ceashell.critterhop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;


//todo:
// establish states √
// make main class run the states √
// set up basic grid and rendering √
// set up critter class
// add lava and landing pad (water?)
// set up basic level and make controls
// set up level editing
// add a save file somewhere in here
// add critter behavior
// add game over
// add level select
// make levels you havent done yet locked
// add menu screen
// make a bunch of levels
// add polish: animations, something cute when you finish a level, particles, background animation, moosic

public class Main extends ApplicationAdapter {
	StateManager stateManager;
//	array of 2d arrays of integer. is there better way?
	ArrayList<Level> levels;
	public static int WIDTH;
	public static int HEIGHT;
	
	@Override
	public void create () {
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		stateManager = new StateManager();
		levels = new ArrayList<Level>();
		MakeAllTheLevels();
		//	maybe pass in a critter...? probz not, worth a thought tho
		stateManager.push(new PlayState(levels));
	}

	public void MakeAllTheLevels() {
		levels.add(new Level( new int[][]
				{
						{0, 0, 0, 0, 0, 0},
						{0, 0, 0, 0, 0, 0},
						{0, 1, 1, 0, 0, 0},
						{0, 1, 1, 0, 0, 0},
						{0, 0, 0, 0, 0, 0},
						{0, 0, 0, 0, 2, 2}
				}
		));
	}

	@Override
	public void render () {
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		stateManager.run();
	}




	@Override
	public void dispose () {
//		batch.dispose();
//		img.dispose();
	}
}
