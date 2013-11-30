package de.frostbyteger.pong.core;

import java.util.LinkedHashMap;

import javax.swing.JOptionPane;
import javax.xml.bind.JAXBException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.InputListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import de.frostbyteger.pong.engine.Achievement;
import de.frostbyteger.pong.engine.Ball;
import de.frostbyteger.pong.engine.Border;
import de.frostbyteger.pong.engine.Difficulty;
import de.frostbyteger.pong.engine.GameState;
import de.frostbyteger.pong.engine.IngameState;
import de.frostbyteger.pong.engine.Pad;
import de.frostbyteger.pong.engine.Player;
import de.frostbyteger.pong.engine.Profile;
import de.frostbyteger.pong.engine.graphics.ui.gui.AbstractComponent;
import de.frostbyteger.pong.engine.graphics.ui.gui.Box;
import de.frostbyteger.pong.engine.graphics.ui.gui.Cell;
import de.frostbyteger.pong.engine.graphics.ui.gui.ComponentListener;
import de.frostbyteger.pong.start.Pong;

/**
 * @author Kevin
 * TODO: The ball glitches out of its boundaries when touching a border and a pad at the same time; maybe implement a better collision detection?
 * TODO: AI Pad glitches sometimes whenthe ball flies in its direction and touches the top and bottom border.
 */
public class Game extends BasicGameState implements ComponentListener, InputListener{

	protected static final int ID = 003;
	
	private StateBasedGame game;
	
	private final String[] MENU_YES_NO_CHOICE   = {"Yes","No"};
	private final String[] MENU_GAME_CHOICE     = {"Player vs. CPU","Player vs. Player","Challenge Mode","Back"};
	private final String[] MENU_DIFFICULTY      = {"Easy","Medium","Hard","Unbeatable"};
	private final String[] MENU_DIFFICULTY_EXPL = {"1/4 Speed of Player\nFor N00bs",
												   "1/2 Speed of Player\nFor average players",
												   "Same Speed as Player\nFor Pr0 Gamers",
												   "Alot faster than Player\nHacks are for pussies!"};
	
	private final String[] COMMANDS_MENU_GAME_CHOICE     = {"cpu","pvp","challenge","back"};
	private final String[] COMMANDS_MENU_GAME_DIFFICULTY = {"easy","medium","hard","unbeatable"};
	private final String[] COMMANDS_MENU_YES_NO_CHOICE   = {"yes","no"};
	
	// Game options
	private int ballRadius                 = 5;
	private int goal                       = 1;
	private boolean challenge              = false;
	private static float S_gravity         = 0.981f;
	protected static boolean S_Debug_AI    = true;
	protected static boolean S_Debug_AI_MP = true;
	
	// Game objects
	private Player player1;
	private Player player2;
	private Ball ball;
	
	// Statistics
	private int time           = 0;
	private int challengeCount = 0;
		
	private GameState gState     = GameState.Main;
	private IngameState igState  = IngameState.None;
	private Border lastCollision = Border.None;
	
	// Game GUI
	private Box gameModeChoice;
	private Box aiDifficultyChoice;
	private Box playAgain;
	private Cell aiDifficultyExpl;
	
	// Other
	private final int boxCellHeight = 50;
	
	
	/**
	 * 
	 */
	public Game() {
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.game = game;
		
		aiDifficultyExpl = new Cell(Pong.FONT, 40, Pong.S_resX/2 - 100, Pong.S_resY/2 + 25, 200, boxCellHeight, container);
		aiDifficultyExpl.setCentered();
		aiDifficultyExpl.setAutoAdjust(false);
		aiDifficultyExpl.setCellText(MENU_DIFFICULTY_EXPL[1]);
		aiDifficultyExpl.setClickable(false);
		
		gameModeChoice = new Box(1, 4, Pong.S_resX/2 - 175, Pong.S_resY/2 - 100, Pong.FONT, 40, 350, boxCellHeight, container);
		gameModeChoice.getHeader().setFontsize(60);
		gameModeChoice.getHeader().createNewFont();
		gameModeChoice.setHeaderTitle("Choose Gamemode");
		gameModeChoice.getHeader().setFontColor(Color.cyan);
		gameModeChoice.getHeader().setAutoAdjust(false);
		gameModeChoice.getHeader().setEdged(false);
		gameModeChoice.setHeaderActive(true);
		gameModeChoice.setBoxCentered();
		gameModeChoice.setEdged(false);
		gameModeChoice.setKeyInput(true);
		gameModeChoice.setFocus(true);
		gameModeChoice.setBoxKeyCoordinates(new int[] {1,1});
		gameModeChoice.setAllCellTitles(MENU_GAME_CHOICE);
		gameModeChoice.setAllActionCommands(COMMANDS_MENU_GAME_CHOICE);
		gameModeChoice.addBoxListener(this);
		
		aiDifficultyChoice = new Box(4, 1, Pong.S_resX/2 - 525, Pong.S_resY/2 - 100, Pong.FONT, 50, 350, boxCellHeight, container);
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
		
		playAgain = new Box(2, 1, Pong.S_resX/2 - 100, Pong.S_resY/ 2 - 25, Pong.FONT, 40, 100, 50, container);
		playAgain.setHeaderTitle("Play again?");
		playAgain.setHeaderEdging(false);
		playAgain.setHeaderActive(true);
		playAgain.setBoxCentered();
		playAgain.setEdged(true);
		playAgain.setKeyInput(true);
		playAgain.setFocus(true);
		playAgain.setBoxKeyCoordinates(new int[] {1,1});
		playAgain.setAllCellTitles(MENU_YES_NO_CHOICE);
		playAgain.setAllActionCommands(COMMANDS_MENU_YES_NO_CHOICE);
		playAgain.addBoxListener(this);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		if(igState == IngameState.None){
			MainMenu.mainHeader.render();
			if(gState == GameState.Main){
				gameModeChoice.render();
			}else if(gState == GameState.PvCPU){
				aiDifficultyChoice.render();
				aiDifficultyExpl.render();
			}else if(gState == GameState.PvP){
				
			}else if(gState == GameState.Challenge){
				
			}else if(gState == GameState.PlayAgain){
				playAgain.render();
			}
		}else if(igState == IngameState.Play || igState == IngameState.BallIsOut || igState == IngameState.Player1Wins || igState == IngameState.Player2Wins){
			player1.getPlayerPad().draw(g);
			player2.getPlayerPad().draw(g);
			ball.draw(g);
			if(Pong.S_debug){
				showDebugMonitor(g);
			}
			if(!challenge){
				g.drawString(Integer.toString(player1.getPoints()), Pong.S_resX / 2 - 20, Pong.S_resY / 2);
				g.drawString(":", Pong.S_resX / 2, Pong.S_resY / 2);
				g.drawString(Integer.toString(player2.getPoints()), Pong.S_resX / 2 + 20, Pong.S_resY / 2);
			}
			if(game.getContainer().isPaused()){
				g.drawString("GAME PAUSED, PRESS " + "P" + " TO RESUME", Pong.S_resX / 2 - 135, Pong.S_resY / 2 + 50);
			}
			if (igState == IngameState.BallIsOut) {
				g.drawString("Press ENTER to spawn a new ball!", Pong.S_resX / 2 - 135, Pong.S_resY / 2 + 30);
			}
			if (igState == IngameState.Player1Wins && !challenge) {
				g.drawString("Player 1 wins!", Pong.S_resX / 2 - 50, Pong.S_resY / 2 + 30);
				g.drawString("Press Enter to return to the mainmenu", Pong.S_resX / 2 - 160, Pong.S_resY / 2 + 50);
			}else if(igState == IngameState.Player2Wins && !challenge) {
				g.drawString("Player 2 wins!", Pong.S_resX / 2 - 50, Pong.S_resY / 2 + 30);
				g.drawString("Press Enter to return to the mainmenu", Pong.S_resX / 2 - 160, Pong.S_resY / 2 + 50);
			}

		}
		if(challenge && igState == IngameState.Play){
			g.drawString("Seconds survived: " + Integer.toString(challengeCount), Pong.S_resX / 2 - 75, 50);
		}

		
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if(igState == IngameState.Play){
			if (!game.getContainer().hasFocus() && !game.getContainer().isPaused()) {
				game.getContainer().setPaused(true);
			}
			if(!game.getContainer().isPaused()){
				
				if(Pong.S_debug){
					if(game.getContainer().getInput().isKeyDown(Input.KEY_Q)){
						ball.addDebugVelocity(0.25f, delta);
					}
					if(game.getContainer().getInput().isKeyDown(Input.KEY_E)){
						ball.addDebugVelocity(-0.25f, delta);
					}
					if(game.getContainer().getInput().isKeyDown(Input.KEY_A)){
						player1.getPlayerPad().addSpinSpeed(1.0f);
					}
					if(game.getContainer().getInput().isKeyDown(Input.KEY_D)){
						player1.getPlayerPad().reduceSpinSpeed(1.0f);
					}
				}
				
				if(!S_Debug_AI_MP){
					if (game.getContainer().getInput().isKeyDown(Input.KEY_UP) ^ game.getContainer().getInput().isKeyDown(Input.KEY_DOWN)) {
						if (player1.getPlayerPad().getPadMinY() > 0.0 && player1.getPlayerPad().getPadMaxY() < Pong.S_resY) {
							player1.getPlayerPad().addSpinSpeed(0.005f * delta);
						}
					}else{
						player1.getPlayerPad().resetSpinSpeed();
					}
					if (game.getContainer().getInput().isKeyDown(Input.KEY_UP)) {
						if (player1.getPlayerPad().getPadMinY() > 0.0) {
							player1.getPlayerPad().setPadY((float) ((player1.getPlayerPad().getPadY() - 10.0)));
						}
					}
					if (game.getContainer().getInput().isKeyDown(Input.KEY_DOWN)) {
						if (player1.getPlayerPad().getPadMaxY() < Pong.S_resY) {
							player1.getPlayerPad().setPadY((float) ((player1.getPlayerPad().getPadY() + 10.0)));
						}
					}
				}

				if(!player2.isCpuControlled()){
					// For player 2
					if (game.getContainer().getInput().isKeyDown(Input.KEY_W) ^ game.getContainer().getInput().isKeyDown(Input.KEY_S)) {
						if (player2.getPlayerPad().getPadMinY() > 0.0 && player2.getPlayerPad().getPadMaxY() < Pong.S_resY) {
							player2.getPlayerPad().addSpinSpeed(0.005f * delta);
						}
					}else{
						player2.getPlayerPad().resetSpinSpeed();
					}
					
					if (game.getContainer().getInput().isKeyDown(Input.KEY_W)) {
						if (player2.getPlayerPad().getPadMinY() > 0.0) {
							player2.getPlayerPad().setPadY((float) ((player2.getPlayerPad().getPadY() - 10.0)));
						}

					}
					if (game.getContainer().getInput().isKeyDown(Input.KEY_S)) {
						if (player2.getPlayerPad().getPadMaxY() < Pong.S_resY) {
							player2.getPlayerPad().setPadY((float) ((player2.getPlayerPad().getPadY() + 10.0)));
						}

					}
				}
				handleAIPad(delta);
				moveBall();
				detectBallCollision();
				time += delta;
				if(challenge){
					playChallenge();
				}else if(!challenge && !player2.isCpuControlled()){
					playPvP();
				}else if(!challenge && player2.isCpuControlled()){
					playVsCPU();
				}
				if(time >= 1000){
					Pong.S_statisticsData.put(Pong.KEYS_STATISTICS[0], Pong.S_statisticsData.get(Pong.KEYS_STATISTICS[0]) + 1);
					time = 0;
				}

			}
		}else if(igState == IngameState.BallIsOut){
			
		}else if(igState == IngameState.Player1Wins){
			
		}else if(igState == IngameState.Player2Wins){
			
		}else if(igState == IngameState.None){
			if(gState == GameState.Main){
				gameModeChoice.update();
			}else if(gState == GameState.PvCPU){
				aiDifficultyChoice.update();
			}else if(gState == GameState.PlayAgain){
				playAgain.update();;
			}
		}
	}
	
	/**
	 * 
	 */
	private void win(){
		if(!challenge){
			if(ball.getBall().getMaxX() < 0) {
				player2.addPoint();
				igState = IngameState.BallIsOut;
				lastCollision = Border.None;
				if(player2.getPoints() >= goal) {
					igState = IngameState.Player2Wins;
					if(!player2.isCpuControlled()){
						Pong.S_statisticsData.put(Pong.KEYS_STATISTICS[8], Pong.S_statisticsData.get(Pong.KEYS_STATISTICS[8]) + 1);
						Pong.S_statisticsData.put(Pong.KEYS_STATISTICS[10], Pong.S_statisticsData.get(Pong.KEYS_STATISTICS[10]) + 1);
					}
				}
			}else if(ball.getBall().getMinX() > Pong.S_resX) {
				player1.addPoint();
				igState = IngameState.BallIsOut;
				lastCollision = Border.None;
				if (player1.getPoints() >= goal) {
					igState = IngameState.Player1Wins;
					if(!player2.isCpuControlled()){
						Pong.S_statisticsData.put(Pong.KEYS_STATISTICS[8], Pong.S_statisticsData.get(Pong.KEYS_STATISTICS[8]) + 1);
						Pong.S_statisticsData.put(Pong.KEYS_STATISTICS[10], Pong.S_statisticsData.get(Pong.KEYS_STATISTICS[10]) + 1);
					}else if(player2.isCpuControlled()){
						Pong.S_statisticsData.put(Pong.KEYS_STATISTICS[8], Pong.S_statisticsData.get(Pong.KEYS_STATISTICS[8]) + 1);
						Pong.S_statisticsData.put(Pong.KEYS_STATISTICS[9], Pong.S_statisticsData.get(Pong.KEYS_STATISTICS[9]) + 1);
					}
				}
			}
		}else{
			if(ball.getBall().getMaxX() < 0) {
				//TODO: Add functionality
			}else if(ball.getBall().getMinX() > Pong.S_resX) {
				//TODO: Add achievement unlocked: do the impossible!
			}
		}

	}
	
	/**
	 * 
	 * @param delta
	 */
	private void handleAIPad(int delta){ //TODO: Change methodname
		if(S_Debug_AI || challenge) {
			if(ball.getBall().getMinY() >= Pong.S_resY - player2.getPlayerPad().getPadHeight() / 2) {
				player2.getPlayerPad().resetSpinSpeed();
			}else if(ball.getBall().getMaxY() <= 0 + player2.getPlayerPad().getPadHeight() / 2) {
				player2.getPlayerPad().resetSpinSpeed();
			}else{
				player2.getPlayerPad().setPadCenterY(ball.getBall().getCenterY());
				player2.getPlayerPad().addSpinSpeed(0.005f * delta);
			}
			if(S_Debug_AI_MP){
				if(ball.getBall().getMinY() >= Pong.S_resY - player1.getPlayerPad().getPadHeight() / 2) {
					//player1.getPlayerPad().resetSpinSpeed();
				}else if(ball.getBall().getMaxY() <= 0 + player1.getPlayerPad().getPadHeight() / 2) {
					//player1.getPlayerPad().resetSpinSpeed();
				}else{
					player1.getPlayerPad().setPadCenterY(ball.getBall().getCenterY());
					//player1.getPlayerPad().addSpinSpeed(0.005f * delta); //TODO: Change algorithm to prevent glitching through the game
				}
			}
		}else{
			if(ball.getBall().getCenterX() > Pong.S_resX/2 + 10 && !player2.getPlayerPad().isCollided()){
				ball.calcTrajectory(ball.getVector().copy(), ball.getBall().getCenterX(), ball.getBall().getCenterY());
			}if(player2.getPlayerPad().getPadCenterY() != Pong.S_resY/2 && player2.getPlayerPad().isCollided()){
				
				//Prevents that the AI pad is glitching while floating back to middle
				if(player2.getPlayerPad().getPadCenterY() == Pong.S_resY/2 -1 || player2.getPlayerPad().getPadCenterY() == Pong.S_resY/2 +1 ){
					player2.getPlayerPad().setPadCenterY(Pong.S_resY/2);
				}else{
					if(player2.getPlayerPad().getPadCenterY() > Pong.S_resY/2){
						for(int i = 0; i <= 2.0f;i++){
							player2.getPlayerPad().setPadCenterY(player2.getPlayerPad().getPadCenterY() - 1.0f);
						}
					}else if(player2.getPlayerPad().getPadCenterY() < Pong.S_resY/2){
						for(int i = 0; i <= 2.0f;i++){
							player2.getPlayerPad().setPadCenterY(player2.getPlayerPad().getPadCenterY() + 1.0f);
						}
					}
				}
			}
			if(ball.getBall().getCenterX() > Pong.S_resX/2 + 10 && !player2.getPlayerPad().isCollided()){
				if(ball.getBall().getMaxY() > Pong.S_resY) {
					
				}else if(ball.getBall().getMinY() < 0) {
					
				}else{
					if(ball.getRoundedEtimatedY() < player2.getPlayerPad().getPadCenterY() && player2.getPlayerPad().getPadMinY() >= 0.0){
						for(int i = 0; i <= player2.getPlayerPad().getPadVelocity();i++){
							player2.getPlayerPad().setPadCenterY(player2.getPlayerPad().getPadCenterY() - 1.0f);	
						}
					}
					if(ball.getRoundedEtimatedY() > player2.getPlayerPad().getPadCenterY() && player2.getPlayerPad().getPadMaxY() <= Pong.S_resY){
						for(int i = 0; i <= player2.getPlayerPad().getPadVelocity();i++){
							player2.getPlayerPad().setPadCenterY(player2.getPlayerPad().getPadCenterY() + 1.0f);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 
	 */
	private void playVsCPU(){
		if(time >= 1000){
			Pong.S_statisticsData.put(Pong.KEYS_STATISTICS[1], Pong.S_statisticsData.get(Pong.KEYS_STATISTICS[1]) + 1);
		}
		if(lastCollision == Border.Left || lastCollision == Border.Right){
			ball.addBallVelocity(0.03f);
		}
		if(ball.getBall().getMaxX() < 0 || ball.getBall().getMinX() > Pong.S_resX){
			win();
		}
	}
	
	/**
	 * 
	 */
	private void playPvP(){		
		if(time >= 1000){
			Pong.S_statisticsData.put(Pong.KEYS_STATISTICS[2], Pong.S_statisticsData.get(Pong.KEYS_STATISTICS[2]) + 1);
		}
		if(lastCollision == Border.Left || lastCollision == Border.Right){
			ball.addBallVelocity(0.03f);
		}
		if(ball.getBall().getMaxX() < 0 || ball.getBall().getMinX() > Pong.S_resX){
			win();
		}
	}
	
	/**
	 * 
	 * @param delta
	 */
	private void playChallenge(){
		if(time >= 1000){
			Pong.S_statisticsData.put(Pong.KEYS_STATISTICS[3], Pong.S_statisticsData.get(Pong.KEYS_STATISTICS[3]) + 1);
			challengeCount += 1;
		}
		ball.addBallVelocityGravity(S_gravity);
	}
	
	/**
	 * 
	 */
	private void detectBallCollision(){
		if (ball.getBall().getMinY() <= 0 && ball.getVectorY() <= 0 ) {
			ball.setVectorXY(ball.getVectorX(), -ball.getVectorY());
			lastCollision = Border.Top;
		}

		if (ball.getBall().getMaxY() >= Pong.S_resY && ball.getVectorY() >= 0 ) {
			ball.setVectorXY(ball.getVectorX(), -ball.getVectorY());
			lastCollision = Border.Bottom;
		}

		if (player1.getPlayerPad().intersects(ball.getBall()) && ball.getVectorX() <= 0 ) {
			if(player1.getPlayerPad().getSpinSpeed() > 0.0f){
				ball.addBallSpin(player1.getPlayerPad().getSpinSpeed());
			}
			ball.setVectorXY(-ball.getVectorX(), ball.getVectorY());
			player1.getPlayerPad().setCollided(true);
			player2.getPlayerPad().setCollided(false);
			lastCollision = Border.Left;
		}

		if (player2.getPlayerPad().intersects(ball.getBall()) && ball.getVectorX() >= 0 ) {
			if(!player2.isCpuControlled()){
				if(player2.getPlayerPad().getSpinSpeed() > 0.0f){
					ball.addBallSpin(-player1.getPlayerPad().getSpinSpeed());
				}
			}
			ball.setVectorXY(-ball.getVectorX(), ball.getVectorY());
			player2.getPlayerPad().setCollided(true);
			player1.getPlayerPad().setCollided(false);
			lastCollision = Border.Right;

		}
	}
	
	/**
	 * 
	 */
	private void moveBall(){
		ball.getBall().setCenterX(ball.getBall().getCenterX() + ball.getVectorX());
		ball.getBall().setCenterY(ball.getBall().getCenterY() + ball.getVectorY());
	}
	
	/**
	 * 
	 */
	private void pauseGame(){
		if (!game.getContainer().isPaused()) {
			game.getContainer().setPaused(true);
		}else if(game.getContainer().isPaused()){
			game.getContainer().setPaused(false);
		}
	}
	
	/**
	 * 
	 * @param paddifficulty
	 */
	private void newGame(float paddifficulty){
		player1 = new Player(new Pad(0 + 10, Pong.S_resY/2, paddifficulty) ,0);
		player1.getPlayerPad().setPadCenterY(Pong.S_resY/2);
		player2 = new Player(new Pad(Pong.S_resX - 20, Pong.S_resY / 2, paddifficulty), 0);
		player2.getPlayerPad().setPadCenterY(Pong.S_resY/2);
		ball = new Ball(Pong.S_resX/2 - ballRadius/2, Pong.S_resY/2 - ballRadius/2, ballRadius);
	}

	/**
	 * 
	 */
	private void newBall(){
		lastCollision = Border.None;
		player1.getPlayerPad().setCollided(false);
		player2.getPlayerPad().setCollided(false);
		time = 0;
		//challengecounter = 0;
		ball = new Ball(Pong.S_resX / 2 - ballRadius / 2, Pong.S_resY / 2 - ballRadius / 2, ballRadius);
		igState = IngameState.Play;
	}
	
	/**
	 * 
	 */
	private void abortGame(){
		time = 0;
		//challengecounter = 0;
		if(challenge){
			Pong.S_statisticsData.put(Pong.KEYS_STATISTICS[7], Pong.S_statisticsData.get(Pong.KEYS_STATISTICS[7]) + 1);
		}else if(!challenge && !player2.isCpuControlled()){
			Pong.S_statisticsData.put(Pong.KEYS_STATISTICS[6], Pong.S_statisticsData.get(Pong.KEYS_STATISTICS[6]) + 1);
		}else if(!challenge && player2.isCpuControlled()){
			Pong.S_statisticsData.put(Pong.KEYS_STATISTICS[5], Pong.S_statisticsData.get(Pong.KEYS_STATISTICS[5]) + 1);
		}
		player2.setCpuControlled(false);
		Pong.S_statisticsData.put(Pong.KEYS_STATISTICS[4], Pong.S_statisticsData.get(Pong.KEYS_STATISTICS[4]) + 1);
		saveStatisticsData();
		lastCollision = Border.None;
		igState = IngameState.None;
		gState = GameState.Main;
	}
	
	private void saveStatisticsData(){
		LinkedHashMap<String, String> temp = new LinkedHashMap<>(Pong.S_statisticsData.size());
		LinkedHashMap<String, Achievement> temp2 = new LinkedHashMap<>(Pong.S_achievementData.size());
		for(int i = 0;i < Pong.S_statisticsData.size();i++){
			temp.put(Pong.KEYS_STATISTICS[i],Integer.toString(Pong.S_statisticsData.get(Pong.KEYS_STATISTICS[i])));
		}
		for(int i = 0;i < Pong.S_achievementData.size();i++){
			temp2.put(Pong.KEYS_ACHIEVEMENTS[i],Pong.S_achievementData.get(Pong.KEYS_ACHIEVEMENTS[i]));
		}
		Profile saveProfile = new Profile(Pong.PROFILE_PATH, Pong.S_profiles.get(Pong.S_activeProfile).getProfileName(), temp, temp2);
		try {
			saveProfile.createProfile(true);
		} catch (JAXBException jaxbe) {
			JOptionPane.showMessageDialog(null,jaxbe.toString() + "\n\nThe game encountered a serious Error. Game exits!");
			Pong.S_container.exit();
		}
	}

	@Override
	public void keyPressed(int key, char c) {
		if(igState == IngameState.Play){
			if(key == Input.KEY_P){
				pauseGame();
			}else if(key == Input.KEY_ESCAPE){
				abortGame();
			}else if(key == Input.KEY_F1){
				game.getContainer().setTargetFrameRate(10);
			}else if(key == Input.KEY_F2){
				game.getContainer().setTargetFrameRate(20);
			}else if(key == Input.KEY_F3){
				game.getContainer().setTargetFrameRate(30);
			}else if(key == Input.KEY_F4){
				game.getContainer().setTargetFrameRate(40);
			}else if(key == Input.KEY_F5){
				game.getContainer().setTargetFrameRate(50);
			}else if(key == Input.KEY_F6){
				game.getContainer().setTargetFrameRate(60);
			}else if(key == Input.KEY_F7){
				game.getContainer().setTargetFrameRate(70);
			}else if(key == Input.KEY_F8){
				game.getContainer().setTargetFrameRate(80);
			}else if(key == Input.KEY_F9){
				game.getContainer().setTargetFrameRate(90);
			}else if(key == Input.KEY_F10){
				game.getContainer().setTargetFrameRate(100);
			}else if(key == Input.KEY_F11){
				game.getContainer().setTargetFrameRate(110);
			}else if(key == Input.KEY_F12){
				game.getContainer().setTargetFrameRate(120);
			}else if(key == Input.KEY_ENTER && Pong.S_debug){
				newBall();
			}
		}else if(igState == IngameState.BallIsOut) {
			if(key == Input.KEY_ENTER) {
				player1.getPlayerPad().setPadCenterY(Pong.S_resY/2);
				player2.getPlayerPad().setPadCenterY(Pong.S_resY/2);
				ball = new Ball(Pong.S_resX / 2 - ballRadius / 2, Pong.S_resY / 2 - ballRadius / 2, ballRadius);
				igState = IngameState.Play;
				
			}
		}else if(igState == IngameState.Player1Wins || igState == IngameState.Player2Wins){
			if(key == Input.KEY_ENTER) {
				time = 0;
				//challengecounter = 0;
				igState = IngameState.None;
				gState = GameState.PlayAgain;
			}
		}else if(igState == IngameState.None){
			if(gState == GameState.Main){
				if(key == Input.KEY_UP && gameModeChoice.getBoxKeyY() > 1){
					gameModeChoice.setBoxKeyY(gameModeChoice.getBoxKeyY() - 1);
				}else if(key == Input.KEY_DOWN && gameModeChoice.getBoxKeyY() < gameModeChoice.getBoxHeight()){
					gameModeChoice.setBoxKeyY(gameModeChoice.getBoxKeyY() + 1);
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
			}else if(gState == GameState.PlayAgain){
				if(key == Input.KEY_LEFT && playAgain.getBoxKeyX() > 1){
					playAgain.setBoxKeyX(playAgain.getBoxKeyX() - 1);
				}else if(key == Input.KEY_RIGHT && playAgain.getBoxKeyX() < playAgain.getBoxWidth()){
					playAgain.setBoxKeyX(playAgain.getBoxKeyX() + 1);
				}
			}
		}
	}
	
	@Override
	public void componentActivated(AbstractComponent source) {		
		if(gState == GameState.Main){
			if(source.getActionCommand().equals(COMMANDS_MENU_GAME_CHOICE[0])){
				gState = GameState.PvCPU;
			}else if(source.getActionCommand().equals(COMMANDS_MENU_GAME_CHOICE[1])){
				gState = GameState.PvP;
				newGame(Difficulty.Hard.getDifficulty());
				igState = IngameState.Play;
			}else if(source.getActionCommand().equals(COMMANDS_MENU_GAME_CHOICE[2])){
				gState = GameState.Challenge;
				newGame(Difficulty.Hard.getDifficulty());
				challenge = true;
				igState = IngameState.Play;
			}else if(source.getActionCommand().equals(COMMANDS_MENU_GAME_CHOICE[3])){
				game.enterState(MainMenu.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
				gameModeChoice.setBoxKeyCoordinates(new int[] {1,1});
			}
		}else if(gState == GameState.PvCPU){
			if(source.getActionCommand().equals(COMMANDS_MENU_GAME_DIFFICULTY[0])){
				newGame(Difficulty.Easy.getDifficulty());
				player2.setCpuControlled(true);
				igState = IngameState.Play;
			}else if(source.getActionCommand().equals(COMMANDS_MENU_GAME_DIFFICULTY[1])){
				newGame(Difficulty.Medium.getDifficulty());
				player2.setCpuControlled(true);
				igState = IngameState.Play;
			}else if(source.getActionCommand().equals(COMMANDS_MENU_GAME_DIFFICULTY[2])){
				newGame(Difficulty.Hard.getDifficulty());
				player2.setCpuControlled(true);
				igState = IngameState.Play;
			}else if(source.getActionCommand().equals(COMMANDS_MENU_GAME_DIFFICULTY[3])){
				newGame(Difficulty.Unbeatable.getDifficulty());
				player2.setCpuControlled(true);
				igState = IngameState.Play;
			}
		}else if(gState == GameState.PlayAgain){
			if(source.getActionCommand().equals(COMMANDS_MENU_YES_NO_CHOICE[0])){
				playAgain.setBoxKeyCoordinates(1, 1);
				saveStatisticsData();
				aiDifficultyChoice.setBoxKeyCoordinates(2,1);
				gState = GameState.PvCPU;
				player2.setCpuControlled(true);
			}else if(source.getActionCommand().equals(COMMANDS_MENU_YES_NO_CHOICE[1])){
				playAgain.setBoxKeyCoordinates(1, 1);
				abortGame();
				gState = GameState.Main;
			}
		}
	}

	@Override
	public int getID() {
		return ID;
	}
	
	private void showDebugMonitor(Graphics g){
		g.drawString("DEBUG Monitor", 75, 25);
		g.drawString("Ballvelocity: " + Double.toString(ball.getBallVelocity()), 75,40);
		g.drawString("LastCollision:" + lastCollision.toString(), 75, 55);
		g.drawString("Pad1Collision:" + player1.getPlayerPad().isCollided() + "|" + "Pad2Collision:" + player2.getPlayerPad().isCollided(), 75, 70);
		g.drawString("Actual Vector: " + Float.toString(ball.getVectorX()) + "|" + Float.toString(ball.getVectorY()), 75, 85);
		g.drawString("Pad1 Position: " + Float.toString(player1.getPlayerPad().getPad().getCenterY()) + " Pad2 Position: " + Float.toString(player2.getPlayerPad().getPad().getCenterY()), 75, 100);
		g.drawString("Pad1 Spinspeed: " + Float.toString(player1.getPlayerPad().getSpinSpeed()) + "|" + "Pad2 Spinspeed: " + Float.toString(player2.getPlayerPad().getSpinSpeed()), 75, 115);
		g.drawString("Ball Position: " + Float.toString(ball.getBall().getCenterX()) + "|" + Float.toString(ball.getBall().getCenterY()), 75, 130);
		g.drawString("Delta: " + Float.toString(1.0f/Pong.FPS), 75, 145);
	}



}
