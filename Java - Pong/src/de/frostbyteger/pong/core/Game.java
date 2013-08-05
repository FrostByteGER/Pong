package de.frostbyteger.pong.core;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import de.frostbyteger.pong.engine.Ball;
import de.frostbyteger.pong.engine.Border;
import de.frostbyteger.pong.engine.GameState;
import de.frostbyteger.pong.engine.OptionState;
import de.frostbyteger.pong.engine.Pad;
import de.frostbyteger.pong.engine.graphics.FontHelper;
import de.frostbyteger.pong.start.Pong;

/**
 * @author Kevin
 *
 */
public class Game extends BasicGameState {

	protected static final int ID = 003;
	
	private final String[] MENU_GAME_CHOICE_ARRAY     = {"Player vs. CPU","Player vs. Player","Challenge Mode","Back"};
	private final String[] MENU_DIFFICULTY_ARRAY      = {"Easy","Medium","Hard","Unbeatable"};
	private final String[] MENU_DIFFICULTY_EXPL_ARRAY = {"1/4 Speed of Player - For N00bs","1/2 Speed of Player- For average players","Same Speed as Player - For Pr0 Gamers","Alot faster than Player - Hacks are for pussies!"};

	private ArrayList<Color> gameArray = new ArrayList<Color>();
	
	// Game options
	private static int BALLRADIUS       = 5;
	private static int GOAL             = 10;
	private static float S_gravity      = 0.00981f;
	protected static boolean S_Debug_AI = false;
	
	private int gameselection = 0;
	private int diffselection = 0;
	
	private Pad pad1;
	private Pad pad2;
	private Ball ball;

	private Border lastcollision;
	private Border lastpadcollision;
	
	private Image arrow_left;
	private Image arrow_right;
	
	private Input input;
	
	private GameState gstate = GameState.Main;
	
	/**
	 * 
	 */
	public Game() {
		for(int e = 0;e <= 4;e++){
			gameArray.add(Color.gray);
		}
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		arrow_left = new Image("data/arrow_left.png");
		arrow_right = new Image("data/arrow_right.png");
		lastcollision = Border.NONE;
		lastpadcollision = Border.NONE;
		//currentgamestate = GameState.Play;
		input = container.getInput();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		FontHelper.bigfont.drawString(Pong.S_resX/2 - FontHelper.bigfont.getWidth("Pong")/2, 20 + FontHelper.bigfont.getHeight("Pong"), "Pong", Color.white);	
		
		for(int i = 0, y_offset = 0;i < 4;i++){
			FontHelper.normalfont.drawString(Pong.S_resX/2 - FontHelper.normalfont.getWidth(MENU_GAME_CHOICE_ARRAY[i])/2, Pong.S_resY/2 + y_offset, MENU_GAME_CHOICE_ARRAY[i], gameArray.get(i));		
			if(i == 2){
				y_offset += 40;
			}else{
				y_offset += 20;
			}
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
		if(gstate == GameState.Main){
			gameHelper(game);
		}
	}
	
	private void gameHelper(StateBasedGame game){
		if(gameselection == 0){
			for(int e = 0;e < gameArray.size();e++){
				gameArray.set(e, Color.gray);
			}
			gameArray.set(0, Color.white);
			if(input.isKeyPressed(Input.KEY_ENTER)){
				gstate = GameState.PvCPU;
				return;
			}
		}else if(gameselection == 1){
			for(int e = 0;e < gameArray.size();e++){
				gameArray.set(e, Color.gray);
			}
			gameArray.set(1, Color.white);
			if(input.isKeyPressed(Input.KEY_ENTER)){
				gstate = GameState.PvP;
				return;
			}
		}else if(gameselection == 2){
			for(int e = 0;e < gameArray.size();e++){
				gameArray.set(e, Color.gray);
			}
			gameArray.set(2, Color.white);
			if(input.isKeyPressed(Input.KEY_ENTER)){
				gstate = GameState.Challenge;
				return;
			}
		}else if(gameselection == 3){
			for(int e = 0;e < gameArray.size();e++){
				gameArray.set(e, Color.gray);
			}
			gameArray.set(3, Color.white);
			if(input.isKeyPressed(Input.KEY_ENTER)){
				gstate = GameState.Main;
				game.enterState(MainMenu.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
				return;
			}
		}
		
		// Menu-navigation
		if(input.isKeyPressed(Input.KEY_UP) && gameselection > 0){
			gameselection -= 1;
		}else if(input.isKeyPressed(Input.KEY_DOWN) && gameselection < MENU_GAME_CHOICE_ARRAY.length - 1){
			gameselection += 1;
		}
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			game.enterState(MainMenu.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		}
	}

	@Override
	public int getID() {
		return ID;
	}

}
