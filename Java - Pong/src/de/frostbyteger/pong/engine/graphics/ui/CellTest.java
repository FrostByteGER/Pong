package de.frostbyteger.pong.engine.graphics.ui;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import de.frostbyteger.errorlogger.ErrorLogger;

public class CellTest extends BasicGame{
	
	private Cell cell;
	public static ErrorLogger logger;

	public CellTest() {
		super("Cell Test");
	}
	
	@Override
	public void init(GameContainer container) throws SlickException {
		logger = new ErrorLogger();
		cell = new Cell(100, 100, 500, 200);
		cell.setFontPath("data/Alexis.ttf");
		cell.setSize(100);
		cell.createNewFont();
		cell.setCellText("this is a test...");
		

	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		cell.drawCell(g);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		
	}
	
	public static void main(String[] args) throws SlickException {
		CellTest pong = new CellTest();
		AppGameContainer container = new AppGameContainer(pong);
		container.setAlwaysRender(true);
		container.setDisplayMode(640, 480, false);
		container.setTargetFrameRate(60);
		container.setShowFPS(true);
		container.start();

	}

}
