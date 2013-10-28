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
import de.frostbyteger.pong.engine.IngameState;
import de.frostbyteger.pong.engine.OptionState;
import de.frostbyteger.pong.engine.Pad;
import de.frostbyteger.pong.engine.Player;
import de.frostbyteger.pong.engine.graphics.FontHelper;
import de.frostbyteger.pong.engine.graphics.ui.gui.AbstractComponent;
import de.frostbyteger.pong.engine.graphics.ui.gui.Box;
import de.frostbyteger.pong.engine.graphics.ui.gui.Cell;
import de.frostbyteger.pong.engine.graphics.ui.gui.ComponentListener;
import de.frostbyteger.pong.start.Pong;

/**
 * @author Kevin
 *
 */
public class Game extends BasicGameState implements ComponentListener{

	protected static final int ID = 003;
	
	private StateBasedGame game;
	
	private final String[] MENU_GAME_CHOICE     = {"Player vs. CPU","Player vs. Player","Challenge Mode","Back"};
	private final String[] MENU_DIFFICULTY      = {"Easy","Medium","Hard","Unbeatable"};
	private final String[] MENU_DIFFICULTY_EXPL = {"1/4 Speed of Player\nFor N00bs",
												   "1/2 Speed of Player\nFor average players",
												   "Same Speed as Player\nFor Pr0 Gamers",
												   "Alot faster than Player\nHacks are for pussies!"};
	
	private final String[] COMMANDS_MENU_GAME_CHOICE     = {"cpu","pvp","challenge","back"};
	private final String[] COMMANDS_MENU_GAME_DIFFICULTY = {"easy","medium","hard","unbeatable"};
	// Game options
	private static int S_ballRadius       = 5;
	private static int S_goal             = 10;
	private static float S_gravity      = 0.00981f;
	protected static boolean S_Debug_AI = false;
	
	// Game objects
	private Player player1;
	private Player player2;
	private Ball ball;
		
	private GameState gState     = GameState.Main;
	private IngameState igState  = IngameState.None;
	private Border lastCollision = Border.None;
	
	// Game GUI
	private Box gamemodeChoice;
	private Box aiDifficultyChoice;
	private Cell aiDifficultyExpl;
	
	
	/**
	 * 
	 */
	public Game() {
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.game = game;
		
		aiDifficultyExpl = new Cell(Pong.FONT, 40, Pong.S_resX/2 - 100, Pong.S_resY/2 + 25, 200, 50, container);
		aiDifficultyExpl.setCentered();
		aiDifficultyExpl.setAutoAdjust(false);
		aiDifficultyExpl.setCellText(MENU_DIFFICULTY_EXPL[1]);
		aiDifficultyExpl.setClickable(false);
		
		gamemodeChoice = new Box(1, 4, Pong.S_resX/2 - 175, Pong.S_resY/2 - 100, Pong.FONT, 40, 350, 50, container);
		gamemodeChoice.getHeader().setFontsize(60);
		gamemodeChoice.getHeader().createNewFont();
		gamemodeChoice.setHeaderTitle("Choose Gamemode");
		gamemodeChoice.getHeader().setFontColor(Color.cyan);
		gamemodeChoice.getHeader().setAutoAdjust(false);
		gamemodeChoice.getHeader().setEdged(false);
		gamemodeChoice.setHeaderActive(true);
		gamemodeChoice.setBoxCentered();
		gamemodeChoice.setEdged(false);
		gamemodeChoice.setKeyInput(true);
		gamemodeChoice.setFocus(true);
		gamemodeChoice.setBoxKeyCoordinates(new int[] {1,1});
		gamemodeChoice.setAllCellTitles(MENU_GAME_CHOICE);
		gamemodeChoice.setAllActionCommands(COMMANDS_MENU_GAME_CHOICE);
		gamemodeChoice.addBoxListener(this);
		
		aiDifficultyChoice = new Box(4, 1, Pong.S_resX/2 - 525, Pong.S_resY/2 - 100, Pong.FONT, 50, 350, 50, container);
		aiDifficultyChoice.getHeader().setCellX(Pong.S_resX/2 - 700);
		aiDifficultyChoice.getHeader().setFontsize(60);
		aiDifficultyChoice.getHeader().createNewFont();
		aiDifficultyChoice.setHeaderTitle("Choose AI-Difficulty");
		aiDifficultyChoice.getHeader().setFontColor(Color.cyan);
		aiDifficultyChoice.getHeader().setAutoAdjust(false);
		aiDifficultyChoice.getHeader().setEdged(false);
		aiDifficultyChoice.setHeaderActive(true);
		aiDifficultyChoice.setBoxCentered();
		aiDifficultyChoice.setEdged(false);
		aiDifficultyChoice.setKeyInput(true);
		aiDifficultyChoice.setFocus(true);
		aiDifficultyChoice.setBoxKeyCoordinates(new int[] {2,1});
		for(int i = 0; i < 4;i++){
			aiDifficultyChoice.getSources().get(i).setVisible(false);
			if(i == 0){
				i += 1;
			}
		}
		aiDifficultyChoice.setAllCellTitles(MENU_DIFFICULTY);
		aiDifficultyChoice.setAllActionCommands(COMMANDS_MENU_GAME_DIFFICULTY);
		aiDifficultyChoice.addBoxListener(this);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		if(igState == IngameState.None){
			MainMenu.mainHeader.drawCell();
		}
		if(gState == GameState.Main){
			gamemodeChoice.render();
		}else if(gState == GameState.PvCPU){
			aiDifficultyChoice.render();
			aiDifficultyExpl.drawCell();
		}else if(gState == GameState.PvP){
			
		}else if(gState == GameState.Challenge){
			
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if(gState == GameState.Main){
			gamemodeChoice.update();
		}else if(gState == GameState.PvCPU){
			aiDifficultyChoice.update();
		}else if(gState == GameState.PvP){
			
		}else if(gState == GameState.Challenge){
			
		}
	}
	
	public void keyPressed(int key, char c) {
		if(gState == GameState.Main){
			if(key == Input.KEY_UP && gamemodeChoice.getBoxKeyY() > 1){
				gamemodeChoice.setBoxKeyY(gamemodeChoice.getBoxKeyY() - 1);
			}else if(key == Input.KEY_DOWN && gamemodeChoice.getBoxKeyY() < gamemodeChoice.getBoxHeight()){
				gamemodeChoice.setBoxKeyY(gamemodeChoice.getBoxKeyY() + 1);
			}
		}else if(gState == GameState.PvCPU){
			if(key == Input.KEY_LEFT && aiDifficultyChoice.getBoxKeyX() > 1){
				aiDifficultyChoice.setBoxKeyX(aiDifficultyChoice.getBoxKeyX() - 1);
				aiDifficultyChoice.setBoxX(aiDifficultyChoice.getBoxX() + 350);
				aiDifficultyChoice.getSources().get(aiDifficultyChoice.getBoxKeyX()).setVisible(false);
				aiDifficultyExpl.setCellText(MENU_DIFFICULTY_EXPL[aiDifficultyChoice.getBoxKeyX() - 1]);
				aiDifficultyChoice.getSources().get(aiDifficultyChoice.getBoxKeyX() - 1).setVisible(true);
			}else if(key == Input.KEY_RIGHT && aiDifficultyChoice.getBoxKeyX() < aiDifficultyChoice.getBoxWidth()){
				aiDifficultyChoice.setBoxKeyX(aiDifficultyChoice.getBoxKeyX() + 1);
				aiDifficultyChoice.setBoxX(aiDifficultyChoice.getBoxX() - 350);
				aiDifficultyExpl.setCellText(MENU_DIFFICULTY_EXPL[aiDifficultyChoice.getBoxKeyX() - 1]);
				aiDifficultyChoice.getSources().get(aiDifficultyChoice.getBoxKeyX() - 2).setVisible(false);
				aiDifficultyChoice.getSources().get(aiDifficultyChoice.getBoxKeyX() - 1).setVisible(true);
			}
		}else if(gState == GameState.PvP){
			
		}else if(gState == GameState.Challenge){
			
		}
	}
	
	@Override
	public void componentActivated(AbstractComponent source) {		
		if(gState == GameState.Main){
			if(source.getActionCommand().equals(COMMANDS_MENU_GAME_CHOICE[0])){
				gState = GameState.PvCPU;
			}else if(source.getActionCommand().equals(COMMANDS_MENU_GAME_CHOICE[1])){
				gState = GameState.PvP;
			}else if(source.getActionCommand().equals(COMMANDS_MENU_GAME_CHOICE[2])){
				gState = GameState.Challenge;
			}else if(source.getActionCommand().equals(COMMANDS_MENU_GAME_CHOICE[3])){
				game.enterState(MainMenu.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
				gamemodeChoice.setBoxKeyCoordinates(new int[] {1,1});
			}
		}else if(gState == GameState.PvCPU){
			
		}else if(gState == GameState.PvP){
			
		}
	}

	@Override
	public int getID() {
		return ID;
	}



}
