package com.ceashell.critterhop;

//note to self: dave ramsey on 7 baby steps for super savings

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
// set up critter class √
// add lava and landing pad (water?) √
// set up basic level and make controls √
// make stuff clear when you match stuff √
// add gravity, decide later whether to use or not √
// add critter behavior √
// add level select √
// make levels you havent done yet locked √
// ********
// add a save file somewhere in here
// make critter path find
// add UI like buttons n shit to switch between states
// add game over
// move the platform UNDER the grid instead of inside it.
// add menu screen
// make a bunch of levels
// add polish: animations, something cute when you finish a level, particles, background animation, moosic

public class Main extends ApplicationAdapter {
	public static StateManager stateManager;
	public static int level;
	public static ArrayList<Level> levels;
	public static ArrayList<Boolean> levelsUnlocked;
	public static int WIDTH;
	public static int HEIGHT;
	
	@Override
	public void create () {
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		stateManager = new StateManager();
		levels = new ArrayList();
		levelsUnlocked = new ArrayList();
		MakeAllTheLevels();
		//	maybe pass in a critter...? probz not, worth a thought tho
		stateManager.push(new PlayState());
//		stateManager.push(new LevelSelectState());
	}

	public void MakeAllTheLevels() {
		levels.add(new Level( new int[][]
				{
						{0, 0, 0, 0, 0, 0, 0, 0},
						{0, 0, 0, 0, 0, 0, 0, 0},
						{0, 0, 1, 1, 0, 0, 0, 0},
						{0, 0, 1, 1, 0, 0, 0, 0},
						{0, 0, 0, 0, 0, 0, 0, 0},
						{0, 0, 0, 0, 0, 0, 0, 0},
						{0, 0, 0, 0, 0, 0, 0, 0},
						{1, 1, 1, 1, 1, 2, 2, 1}
				}
		));
		levels.add(new Level( new int[][]
				{
						{0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,1,1,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,1,1,0,0,0,0,0,0,0,0},
						{0,0,1,1,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,1,1,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,1,1,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0},
						{1,1,1,1,2,2,2,1,1,1,1,1}
				}
				));
		levelsUnlocked.add(true);
		levelsUnlocked.add(false);
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

//	TODO: sprites
//	background for gameplay
//  level select icons
//  background for level select state
//  buttons for ui
//  animations for critter
//  border(s)
//  menu screen bg and text..?
//  game over bg
//  lava
//  landing pad.
}
