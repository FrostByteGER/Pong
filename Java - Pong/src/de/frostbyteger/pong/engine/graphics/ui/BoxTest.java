package de.frostbyteger.pong.engine.graphics.ui;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import de.frostbyteger.messagelogger.MessageLogger;

public class BoxTest extends BasicGame implements ComponentListener, Runnable{
	
	public static MessageLogger logger;
	
	public Box box;
	
	public Rectangle overlay;
	public Box box2;

	public BoxTest() {
		super("Box Test");
	}
	
	@Override
	public void init(GameContainer container) throws SlickException {
		
		overlay = new Rectangle(0, 0, container.getWidth(), container.getHeight());
		try{
			box2 = new Box(2, 1, container.getWidth()/2 - 200, container.getHeight()/2 - 50, "data/Alexis.ttf", 40, 200, 0, container); // TODO: Change font to systemfont with throws
			box2.getSources().get(0).setCellText("Yes");
			box2.getSources().get(1).setCellText("No");
		}catch(IllegalCellArgumentException e){
			e.printStackTrace();
		}

		
		
		Thread t1 = new Thread(this);
		t1.start();
		box = new Box(2, 4, 100, 100,"data/Alexis.ttf", 40, 200, 50, container);
		box.setHeaderTitle("Box_01");
		box.setHeaderActive(true);
		for(int i = 0; i < box.getSources().size();i++){
			box.getSources().get(i).setActionCommand("TEST" + i);
			box.getSources().get(i).addListener(this);
		}
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		box.render();
		//Box.showOptionBox(overlay, box2,container, "Continue?", BoxOptionSelection.YES_NO_BOX);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
	}
	
	public static void main(String[] args) throws SlickException {
		BoxTest boxTest = new BoxTest();
		AppGameContainer container = new AppGameContainer(boxTest);
		container.setAlwaysRender(true);
		container.setDisplayMode(1024, 800, false);
		container.setTargetFrameRate(60);
		container.setShowFPS(true);
		container.start();
	}

	@Override
	public void componentActivated(AbstractComponent source) {
		logger.addMessage("ACTIVL : " + source.getClass());
		for(int i = 0; i < box.getSources().size();i++){
			if (source == box.getSources().get(i)) {
				logger.addMessage("Area " + source + " Activated");
				return;
			}

		}
	
	}

	@Override
	public void run() {
		logger = new MessageLogger();
		logger.getFrame().setAlwaysOnTop(true);
	}

}
