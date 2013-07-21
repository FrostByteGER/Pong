/**
 * 
 */
package de.frostbyteger.pong.core;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import de.frostbyteger.pong.engine.FontHelper;
import de.frostbyteger.pong.engine.io.PropertyHelper;
import de.frostbyteger.pong.start.Pong;

/**
 * @author Kevin
 *
 */
public class MainMenu extends BasicGameState {
	
	protected static final int ID = 001;
	//"Player vs. CPU","Player vs. Player","Challenge Mode"
	private final String[] MENU_ARRAY = {"New Game", "LAN-Mode", "Options", "Profile", "Quit Game"};

	private ArrayList<Color> selectionArray = new ArrayList<Color>();
	
	private PropertyHelper prophelper;
	
	private int selection = 0;
	
	private Input input;

	/**
	 * 
	 */
	public MainMenu() {
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		FontHelper.smallfont = FontHelper.newFont(FontHelper.FONT, 25, false, false);
		FontHelper.normalfont = FontHelper.newFont(FontHelper.FONT, 40, false, false);
		FontHelper.mediumfont = FontHelper.newFont(FontHelper.FONT, 50, false, false);
		FontHelper.bigfont = FontHelper.newFont(FontHelper.FONT, 120, false, false);
	
		
		for(int e = 0;e <= 13;e++){
			selectionArray.add(Color.gray);
		}
		
		//TODO: Warning, not safe
		prophelper = new PropertyHelper();
		prophelper.loadPropertiesFile();
		
		try{
			Pong.S_resX = Integer.parseInt(prophelper.loadProperty("resX"));
			Pong.S_resY = Integer.parseInt(prophelper.loadProperty("resY"));
			container.setMusicVolume(Float.parseFloat(prophelper.loadProperty("volume")));
			container.setMusicOn(Boolean.parseBoolean(prophelper.loadProperty("vol_on")));
			Pong.S_Debug = Boolean.parseBoolean(prophelper.loadProperty("debug"));
		}catch(NumberFormatException nfe){
			nfe.printStackTrace();
		}
		
		Pong.S_Container.setDisplayMode(Pong.S_resX, Pong.S_resY, false);
		
		input = container.getInput();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		FontHelper.bigfont.drawString(Pong.S_resX/2 - FontHelper.bigfont.getWidth("Pong")/2, 20 + FontHelper.bigfont.getHeight("Pong"), "Pong", Color.white);	
		
		for(int i = 0, y_offset = 0;i < 5;i++){
			FontHelper.normalfont.drawString(Pong.S_resX/2 - FontHelper.normalfont.getWidth(MENU_ARRAY[i])/2, Pong.S_resY/2 + y_offset, MENU_ARRAY[i], selectionArray.get(i));		
			y_offset += 20;
		}
		g.drawString(Pong.VERSION_STATUS + " " + Pong.VERSION, Pong.S_resX - 125, Pong.S_resY - 15);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		//Coloration for menu
		if(selection == 0){
			for(int e = 0;e < selectionArray.size();e++){
				selectionArray.set(e, Color.gray);
			}
			selectionArray.set(0, Color.white);
		}else if(selection == 1){
			for(int e = 0;e < selectionArray.size();e++){
				selectionArray.set(e, Color.gray);
			}
			selectionArray.set(1, Color.white);
		}else if(selection == 2){
			for(int e = 0;e < selectionArray.size();e++){
				selectionArray.set(e, Color.gray);
			}
			selectionArray.set(2, Color.white);
		}else if(selection == 3){
			for(int e = 0;e < selectionArray.size();e++){
				selectionArray.set(e, Color.gray);
			}
			selectionArray.set(3, Color.white);
		}else if(selection == 4){
			for(int e = 0;e < selectionArray.size();e++){
				selectionArray.set(e, Color.gray);
			}
			selectionArray.set(4, Color.white);
		}
		
		//Menu-navigation
		if(input.isKeyPressed(Input.KEY_UP) && selection > 0){
			selection -= 1;
		}else if(input.isKeyPressed(Input.KEY_DOWN) && selection < MENU_ARRAY.length - 1){
			selection += 1;
		}
		if(input.isKeyPressed(Input.KEY_ENTER)){
			if(selection == 0){
				game.enterState(Game.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));

			}else if(selection == 1){
				game.enterState(Lan.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
				//currentmenustate = MenuState.PvP;
				//newGame(Difficulty.HARD.getDifficulty());
				//currentgamestate = GameState.Play;
			}else if(selection == 2){
				game.enterState(Options.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
			}else if(selection == 3){
				game.enterState(Profile.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
				//currentmenustate = MenuState.Challenge;
				//newGame(Difficulty.HARD.getDifficulty());
				//currentgamestate = GameState.Play;
			}else if(selection == 4){
				container.exit();
			}

		}else if(input.isKeyPressed(Input.KEY_ESCAPE)){
			container.exit();
		}
	}

	@Override
	public int getID() {
		return ID;
	}

}
