/**
 * 
 */
package de.frostbyteger.pong.core;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import de.frostbyteger.pong.engine.graphics.ui.gui.AbstractComponent;
import de.frostbyteger.pong.engine.graphics.ui.gui.Box;
import de.frostbyteger.pong.engine.graphics.ui.gui.Cell;
import de.frostbyteger.pong.engine.graphics.ui.gui.ComponentListener;
import de.frostbyteger.pong.engine.io.ConfigHelper;
import de.frostbyteger.pong.start.Pong;

/**
 * @author Kevin
 *
 */
public class MainMenu extends BasicGameState implements ComponentListener {
	
	protected static final int ID = 001;
	
	private final String[] COMMANDS   = {"game","lan","options","profile","exit"};
	private final String[] MENU_ARRAY = {"New Game", "LAN-Mode", "Options", "Profiles", "Quit Game"};
	
	private StateBasedGame game;
	
	// Menu
	private Box menuBox;
	public static Cell mainHeader;
		
	private Music msc;
	
	public static ConfigHelper ch = new ConfigHelper("data//", "config",".xml");

	/**
	 * 
	 */
	public MainMenu() {
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.game = game;
		
		mainHeader = new Cell(Pong.FONT, 160, Pong.S_resX/2 - 175, 20, 350, 250, container);
		mainHeader.setAutoAdjust(false);
		mainHeader.setCellText(Pong.TITLE);
		mainHeader.setClickable(false);
		
		menuBox = new Box(1, MENU_ARRAY.length, Pong.S_resX/2 - 125, Pong.S_resY/2 - 125, Pong.FONT, 40, 250, 50, container);
		menuBox.setEdged(false);
		menuBox.setKeyInput(true);
		menuBox.setFocus(true);
		menuBox.setBoxKeyCoordinates(new int[] {1,1});
		menuBox.setAllCellTitles(MENU_ARRAY);
		menuBox.setAllActionCommands(COMMANDS);
		menuBox.addBoxListener(this);
		msc = new Music("data/snd/sample.wav");
		//msc.loop();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		if(!Pong.S_firstStart && !Pong.S_profileNotFound){
			g.drawString(Pong.VERSION_STATUS + " " + Pong.VERSION, Pong.S_resX - 128, Pong.S_resY - 15);
			mainHeader.render();
			menuBox.render();
		}

		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if(!Pong.S_firstStart && !Pong.S_profileNotFound){
			menuBox.update();
		}
	}
	
	public void keyPressed(int key, char c) {
		if(key == Input.KEY_UP && menuBox.getBoxKeyY() > 1){
			menuBox.setBoxKeyY(menuBox.getBoxKeyY() - 1);
		}else if(key == Input.KEY_DOWN && menuBox.getBoxKeyY() < menuBox.getBoxHeight()){
			menuBox.setBoxKeyY(menuBox.getBoxKeyY() + 1);
		}else if(key == Input.KEY_RIGHT && menuBox.getBoxKeyX() < menuBox.getBoxWidth()){
			menuBox.setBoxKeyX(menuBox.getBoxKeyX() + 1);
		}else if(key == Input.KEY_LEFT && menuBox.getBoxKeyX() > 1){
			menuBox.setBoxKeyX(menuBox.getBoxKeyX() - 1);
		}else if(key == Input.KEY_ESCAPE){
			Pong.S_container.exit();
		}
	}

	@Override
	public void componentActivated(AbstractComponent source) {
		if(source.getActionCommand().equals(COMMANDS[0])){
			menuBox.setBoxKeyCoordinates(new int[] {1,1});	
			game.enterState(Game.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		}else if(source.getActionCommand().equals(COMMANDS[1])){
			menuBox.setBoxKeyCoordinates(new int[] {1,1});	
			//game.enterState(Lan.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		}else if(source.getActionCommand().equals(COMMANDS[2])){
			menuBox.setBoxKeyCoordinates(new int[] {1,1});	
			game.enterState(Options.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		}else if(source.getActionCommand().equals(COMMANDS[3])){
			menuBox.setBoxKeyCoordinates(new int[] {1,1});	
			game.enterState(Profiles.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		}else if(source.getActionCommand().equals(COMMANDS[4])){
			Pong.S_container.exit();
		}
		
	}
	
	@Override
	public int getID() {
		return ID;
	}

}
