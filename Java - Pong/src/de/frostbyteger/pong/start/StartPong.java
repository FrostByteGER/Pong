package de.frostbyteger.pong.start;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import de.frostbyteger.pong.core.Pong;

public class StartPong {

	public static void main(String[] args) throws SlickException {

		AppGameContainer container = new AppGameContainer(new Pong(Pong.title + " " + Pong.version));
		container.setDisplayMode(Pong.resX, Pong.resY, false);
		container.setTargetFrameRate(Pong.fps);
		container.setShowFPS(false); //TODO: Delete this
		container.start();

	}
	
}
