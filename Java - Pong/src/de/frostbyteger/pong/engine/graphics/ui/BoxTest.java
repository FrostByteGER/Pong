package de.frostbyteger.pong.engine.graphics.ui;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import de.frostbyteger.errorlogger.ErrorLogger;

public class BoxTest extends BasicGame implements ComponentListener{
	
	public static ErrorLogger logger;

	public BoxTest() {
		super("Box Test");
	}
	
	@Override
	public void init(GameContainer container) throws SlickException {
		logger = new ErrorLogger();

		

	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		g.drawString("TEST", 100, 100);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
	}
	
	public static void main(String[] args) throws SlickException {
		BoxTest boxTest = new BoxTest();
		AppGameContainer container = new AppGameContainer(boxTest);
		container.setAlwaysRender(true);
		container.setDisplayMode(1280, 800, false);
		container.setTargetFrameRate(60);
		container.setShowFPS(true);
		container.start();
	}

	@Override
	public void componentActivated(AbstractComponent source) {
		logger.addError("ACTIVL : "+source);
		if (true) {
			System.out.println("TEST");
			logger.addError("Area " + source.getClass().getSimpleName() + " Activated");
		}		
	}

}
