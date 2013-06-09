package de.frostbyteger.pong.start;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import de.frostbyteger.pong.core.Pong;

public class StartPong {

	public static void main(String[] args) throws SlickException {

		Pong.S_Container = new AppGameContainer(new Pong(Pong.TITLE + " " + Pong.VERSION));
		Pong.S_Container.setDisplayMode(Pong.S_resX, Pong.S_resY, false);
		Pong.S_Container.setTargetFrameRate(Pong.FPS);
		Pong.S_Container.setShowFPS(false); //TODO: Delete this
		Pong.S_Container.start();

	}
	
}
