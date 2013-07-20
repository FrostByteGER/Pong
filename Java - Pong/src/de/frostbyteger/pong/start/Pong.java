package de.frostbyteger.pong.start;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.frostbyteger.pong.core.CorePong;

public class Pong extends StateBasedGame{

	public Pong(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) throws SlickException {
		CorePong.S_Container = new AppGameContainer(new Pong(CorePong.TITLE + " " + CorePong.VERSION));
		CorePong.S_Container.setDisplayMode(CorePong.S_resX, CorePong.S_resY, false);
		CorePong.S_Container.setTargetFrameRate(CorePong.FPS);
		CorePong.S_Container.setShowFPS(false); //TODO: Delete this
		CorePong.S_Container.start();

	}
}
