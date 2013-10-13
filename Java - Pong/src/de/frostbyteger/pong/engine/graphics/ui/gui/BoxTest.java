package de.frostbyteger.pong.engine.graphics.ui.gui;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import de.frostbyteger.messagelogger.MessageLogger;

public class BoxTest extends BasicGame implements ComponentListener, Runnable{
	
	public static MessageLogger logger;
	
	public Box box, box3,box4;
	
	public Rectangle overlay;
	public Box box2;
	
	public int x = 1;

	public BoxTest() {
		super("Box Test");
	}
	
	@Override
	public void init(GameContainer container) throws SlickException {
		overlay = new Rectangle(0, 0, container.getWidth(), container.getHeight());
		/*try{
			box2 = new Box(2, 1, container.getWidth()/2 - 200, container.getHeight()/2 - 50, "data/Alexis.ttf", 40, 200, 0, container); // TODO: Change font to systemfont with throws
			box2.getSources().get(0).setCellText("Yes");
			box2.getSources().get(1).setCellText("No");
		}catch(IllegalCellArgumentException e){
			e.printStackTrace();
		}*/

		
		
		Thread t1 = new Thread(this);
		t1.start();
		box = new Box(2, 4, 100, 100,"data/Alexis.ttf", 40, 200, 50, container);
		box.setHeaderTitle("Box_01");
		box.setHeaderActive(true);
		for(int i = 0; i < box.getSources().size();i++){
			box.getSources().get(i).setCellText("Button_0" + x);
			box.getSources().get(i).setActionCommand("TEST" + i);
			box.getSources().get(i).addListener(this);
			box.getSources().get(i).setKeysActive(true);
			x++;
		}
		box3 = new Box(2, 4, 500, 100,"data/Alexis.ttf", 40, 200, 50, container);
		box3.setHeaderTitle("Box_02");
		box3.setHeaderActive(true);
		for(int i = 0; i < box3.getSources().size();i++){
			box3.getSources().get(i).setCellText("Button_" + x);
			box3.getSources().get(i).addListener(this);
			box3.getSources().get(i).setClickable(true);
			x++;
		}
		box3.setFocus(false);
		box.setFocus(true);
		box.setKeyInput(true);
		box.setBoxKeyCoordinates(new int[] {1,1});
		
		box4 = new Box(1, 4, 100, 500,"data/Alexis.ttf", 40, 200, 50, container);
		box4.setHeaderTitle("Box_03");
		box4.setHeaderActive(true);
		int x2 = x;
		for(int i = 0; i < box4.getSources().size();i++){
			box4.getSources().get(i).setCellText("Button_" + x);
			box4.getSources().get(i).addListener(this);
			box4.getSources().get(i).setClickable(true);
			x++;

		}
		x = x2;
		box4.setFocus(false);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		if(box.isFocused()){
			g.drawString("Focused", 225, 25);
		}else if(box3.isFocused()){
			g.drawString("Focused", 725, 25);
		}
		box.render();
		box3.render();
		box4.render();
		g.drawString("Press TABULATOR to switch boxes", 350, 350);
		g.drawString("Controls for Box 1:", 225, 400);
		g.drawString("Bewege die Pfeiltasten", 200, 425);
		g.drawString("und drücke ENTER zum bestätigen", 200, 450);
		g.drawString("Controls for Box 2:", 600, 400);
		g.drawString("Bewege die Pfeiltasten", 575, 425);
		g.drawString("und drücke ENTER zum bestätigen", 550, 450);
		g.drawString("Oder nutze die Maus!", 550, 475);
		//Box.showOptionBox(overlay, box2,container, "Continue?", BoxOptionSelection.YES_NO_BOX);
		
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		box.update();
		box3.update();
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
		for(int j = 0; j < box3.getSources().size();j++){
			if (source == box3.getSources().get(j)) {
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
	
	public void keyPressed(int key, char c) {
		if(key == Input.KEY_TAB && box.isFocused() == false){
			box3.setFocus(false);
			box.setFocus(true);
			box.setKeyInput(true);
			box.setBoxKeyCoordinates(new int[] {1,1});
			try {
				box4.setBoxWidth(4);
				box4.setBoxHeight(2);
				for(int i = 0; i < box4.getCells().size();i++){
					for(int j = 0; j < box4.getCells().get(i).size();j++){
						box4.getCells().get(i).get(j).setCellText("Button_" + x);
						box4.getCells().get(i).get(j).addListener(this);
						box4.getCells().get(i).get(j).setClickable(true);
						x++;
					}
				}
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(key == Input.KEY_TAB && box.isFocused() == true){
			box.setFocus(false);
			box3.setFocus(true);
			box3.setBoxKeyCoordinates(new int[] {1,1});
			//box3.setKeyInput(true);
			//box3.setBoxKeyCoordinates(new int[] {1,1});
		}
		if(box.isKeyInputActivated() == true && box.isFocused() == true){
			if(key == Input.KEY_UP && box.getBoxKeyY() > 1){
				box.setBoxKeyY(box.getBoxKeyY() - 1);
			}else if(key == Input.KEY_DOWN && box.getBoxKeyY() < box.getBoxHeight()){
				box.setBoxKeyY(box.getBoxKeyY() + 1);
			}else if(key == Input.KEY_RIGHT && box.getBoxKeyX() < box.getBoxWidth()){
				box.setBoxKeyX(box.getBoxKeyX() + 1);
			}else if(key == Input.KEY_LEFT && box.getBoxKeyX() > 1){
				box.setBoxKeyX(box.getBoxKeyX() - 1);
			}
		}else if(box3.isKeyInputActivated() == true && box3.isFocused() == true){
			if(key == Input.KEY_UP && box3.getBoxKeyY() > 1){
				box3.setBoxKeyY(box3.getBoxKeyY() - 1);
			}else if(key == Input.KEY_DOWN && box3.getBoxKeyY() < box3.getBoxHeight()){
				box3.setBoxKeyY(box3.getBoxKeyY() + 1);
			}else if(key == Input.KEY_RIGHT && box3.getBoxKeyX() < box3.getBoxWidth()){
				box3.setBoxKeyX(box3.getBoxKeyX() + 1);
			}else if(key == Input.KEY_LEFT && box3.getBoxKeyX() > 1){
				box3.setBoxKeyX(box3.getBoxKeyX() - 1);
			}
		}
		if(key == Input.KEY_F1){
			box3.setKeyInput(true);
			box3.setBoxKeyCoordinates(new int[] {1,1});
		}else if(key == Input.KEY_F2){
			box3.setKeyInput(false);
		}
	}
	


}
