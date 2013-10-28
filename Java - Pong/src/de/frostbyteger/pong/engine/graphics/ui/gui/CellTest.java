package de.frostbyteger.pong.engine.graphics.ui.gui;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import de.frostbyteger.messagelogger.MessageLogger;

public class CellTest extends BasicGame implements ComponentListener{
	
	private Cell cell;
	private Cell cell2;
	private Cell cell3;
	public static MessageLogger logger;

	public CellTest() {
		super("Cell Test");
	}
	
	@Override
	public void init(GameContainer container) throws SlickException {
		logger = new MessageLogger();
		logger.getFrame().setAlwaysOnTop(true);
		
		cell = new Cell("Button1",100, 100, 200, 50, container);
		cell.setEdged(false);
		//cell.setImagePath("data/test_button.png");
		//cell.createNewImage();
		cell.setFontPath("data/Alexis.ttf");
		cell.setFontsize(50);
		cell.createNewFont();
		cell.setCellText("this is a test...");
		cell.addListener(this);
		cell.setActionCommand("TEST");
		
		cell2 = new Cell("Button2",100, 175, 200, 50, container);
		cell2.setEdged(true);
		cell2.setFontPath("data/Alexis.ttf");
		cell2.setFontsize(50);
		cell2.createNewFont();
		cell2.setCellText("this is a test too");
		cell2.addListener(this);
		cell2.setActionCommand("TEST2");
		
		cell3 = new Cell("Button3",100, 250, 200, 50, container);
		cell3.setImagePath("data/test_button.png");
		cell3.createNewImage();
		cell3.setFontPath("data/Alexis.ttf");
		cell3.setFontsize(50);
		cell3.createNewFont();
		cell3.setCellText("this is test 3");
		cell3.addListener(this);
		cell3.setActionCommand("TEST3");
		

	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		cell.drawCell();
		cell2.drawCell();
		cell3.drawCell();
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

	@Override
	public void componentActivated(AbstractComponent source) {
		logger.addMessage("ACTIVL : " + source.getClass());
		if (source == cell) {
			logger.addMessage("Area " + source + " Activated");
		}	
		else if(source == cell2) {
			logger.addMessage("Area " + source + " Activated");
		}	
	}

}
