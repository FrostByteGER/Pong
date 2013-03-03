package de.frostbyteger.pong.start;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import de.frostbyteger.pong.core.Pong;

public class StartPong {

	public static void main(String[] args) throws SlickException {

		Pong.container = new AppGameContainer(new Pong(Pong.title + " " + Pong.version));
		Pong.container.setDisplayMode(Pong.resX, Pong.resY, false);
		Pong.container.setTargetFrameRate(Pong.fps);
		Pong.container.setShowFPS(false); //TODO: Delete this
		Pong.container.start();

	}
	
}
