package com.ceashell.critterhop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.ceashell.critterhop.Main;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("CritterHop");
//		config.setWindowSizeLimits(700, 700, 700, 700);
		config.setResizable(false);
		config.setWindowedMode(750,750);
		new Lwjgl3Application(new Main(), config);
	}
}
