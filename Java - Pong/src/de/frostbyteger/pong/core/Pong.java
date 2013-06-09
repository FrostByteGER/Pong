package de.frostbyteger.pong.core;

import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import de.frostbyteger.pong.engine.Ball;
import de.frostbyteger.pong.engine.Border;
import de.frostbyteger.pong.engine.Difficulty;
import de.frostbyteger.pong.engine.GameState;
import de.frostbyteger.pong.engine.MenuState;
import de.frostbyteger.pong.engine.Pad;
import de.frostbyteger.pong.engine.PropertyHelper;


/**
 * @author Kevin
 * TODO: Volume wont get really changed
 * TODO: Scale Ballspeed with increasing resolution
 */
public class Pong extends BasicGame {

	protected Pad pad1, pad2;
	protected Ball ball;

	protected Border lastcollision;
	protected Border lastpadcollision;
	protected GameState currentgamestate = GameState.Start;
	protected MenuState currentmenustate = MenuState.Main;
	protected Difficulty cpudifficulty;
	
	private static final int BALLRADIUS = 5;
	private static final int GOAL = 10;
	
	private static float S_gravity = 0.00981f;
	
	private static final float EASY = 2.5f;
	private static final float MEDIUM = 5.0f;
	private static final float HARD = 10.0f;
	private static final float UNBEATABLE = 15.0f;
	
	private static final int[][] RES_ARRAY = {{640,480},{800,600},{1024,768},{1280,960},{1280,1024}};
	
	private final String AI = "AI-Difficulty";
	
	// Options
	public static int S_resX = 800;
	public static int S_resY = 600;
	
	public static final int FPS = 60;
	
	// Version info
	public static final String TITLE = "Pong";
	public static final String VERSION = "v1.0";
	
	private final String[] MENU_ARRAY = {"Player vs. CPU","Player vs. Player","LAN-Mode - Coming soon","Challenge Mode","Options","Help","Quit Game"};
	private final String[] MENU_OPTIONS_ARRAY = {"Resolution: ","Volume: ","Volume","DEBUG MODE","Save","Exit"};
	private final String[] MENU_DIFFICULTY_ARRAY = {"Easy","Medium","Hard","Unbeatable"};
	private final String[] MENU_DIFFICULTY_EXPL_ARRAY = {"1/4 Speed of Player - For N00bs","1/2 Speed of Player- For average players","Same Speed as Player - For Pr0 Gamers","Alot faster than Player - Hacks are for pussies!"};
	private Color cpuselection = Color.gray;
	private Color pvpselection = Color.gray;
	private Color lanselection = Color.gray;
	private Color challengeselection = Color.gray;
	private Color optionselection = Color.gray;
	private Color resselection = Color.gray;
	private Color volselection = Color.gray;
	private Color volstatselection = Color.gray;
	private Color debugselection = Color.red;
	private Color saveselection = Color.gray;
	private Color exitselection = Color.gray;
	private Color helpselection = Color.gray;
	private Color quitselection = Color.gray;
	private UnicodeFont smallfont;
	private UnicodeFont normalfont;
	private UnicodeFont mediumfont;
	private UnicodeFont bigfont;
	
	private int playerselection = 0;
	private int difficultyselection = 1;
	private int configselection = 0;
	private int resolutionselection = 0;
	
	private int challengecounter = 0;
	private float time = 0.0f;
	
	private static boolean S_Debug = true;
	private static boolean S_Debug_AI = false;
	
	private boolean collision = false;
	
	private Image arrow_left;
	private Image arrow_right;
	
	private PropertyHelper prophelper;
	
	public static AppGameContainer S_Container;
	
	protected double hip = 0;
	protected Random rndm = new Random();

	//TODO: Add description
	public Pong(String title) {
		super(title);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		
		smallfont = newFont("data/alexis.ttf", 25, false, false);
		
		normalfont = newFont("data/alexis.ttf", 40, false, false);
		
		mediumfont = newFont("data/alexis.ttf", 50, false, false);
		
		bigfont = newFont("data/alexis.ttf", 120, false, false);
		
		arrow_left = new Image("data/arrow_left.png");
		arrow_right = new Image("data/arrow_right.png");

		lastcollision = Border.NONE;
		lastpadcollision = Border.NONE;
		currentgamestate = GameState.Play;
		
		//TODO: Warning, not safe
		prophelper = new PropertyHelper();
		prophelper.loadPropertiesFile();
		
		try{
			S_resX = Integer.parseInt(prophelper.loadProperty("resX"));
			S_resY = Integer.parseInt(prophelper.loadProperty("resY"));
			gc.setMusicVolume(Float.parseFloat(prophelper.loadProperty("volume")));
			gc.setMusicOn(Boolean.parseBoolean(prophelper.loadProperty("vol_on")));
			S_Debug = Boolean.parseBoolean(prophelper.loadProperty("debug"));
		}catch(NumberFormatException nfe){
			nfe.printStackTrace();
		}
		S_Container.setDisplayMode(S_resX, S_resY, false);
		//Ensures that the displayed resolution in the optionsmenu equals the actual gamewindow resolution when
		//starting the game
		for(int i = 0; i < RES_ARRAY.length;i++){
			if(S_resX == RES_ARRAY[i][0]){
				resolutionselection = i;
			}
		}

	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		if(currentmenustate == MenuState.Main){
			bigfont.drawString(S_resX/2 - bigfont.getWidth("Pong")/2, 20 + bigfont.getHeight("Pong"), "Pong", Color.white);	
			normalfont.drawString(S_resX/2 - normalfont.getWidth(MENU_ARRAY[0])/2, S_resY/2, MENU_ARRAY[0], cpuselection);		
			normalfont.drawString(S_resX/2 - normalfont.getWidth(MENU_ARRAY[1])/2, S_resY/2 + 20, MENU_ARRAY[1], pvpselection);
			normalfont.drawString(S_resX/2 - normalfont.getWidth(MENU_ARRAY[2])/2, S_resY/2 + 40, MENU_ARRAY[2], lanselection);
			normalfont.drawString(S_resX/2 - normalfont.getWidth(MENU_ARRAY[3])/2, S_resY/2 + 60, MENU_ARRAY[3], challengeselection);
			normalfont.drawString(S_resX/2 - normalfont.getWidth(MENU_ARRAY[4])/2, S_resY/2 + 80, MENU_ARRAY[4], optionselection);
			normalfont.drawString(S_resX/2 - normalfont.getWidth(MENU_ARRAY[5])/2, S_resY/2 + 100, MENU_ARRAY[5], helpselection);
			normalfont.drawString(S_resX/2 - normalfont.getWidth(MENU_ARRAY[6])/2, S_resY/2 + 120, MENU_ARRAY[6], quitselection);
			g.drawString("RELEASE " + VERSION, S_resX - 115, S_resY - 15);
		}
		
		if(currentmenustate == MenuState.CPUSelection){
			bigfont.drawString(S_resX/2 - bigfont.getWidth("Pong")/2, 20 + bigfont.getHeight("Pong"), "Pong", Color.white);	
			mediumfont.drawString(S_resX/2 - mediumfont.getWidth(AI)/2, S_resY/2 - 30, AI,Color.cyan);
			normalfont.drawString(S_resX/2 - normalfont.getWidth(MENU_DIFFICULTY_ARRAY[difficultyselection])/2, S_resY/2, MENU_DIFFICULTY_ARRAY[difficultyselection],Color.white);
			smallfont.drawString(S_resX/2 - smallfont.getWidth(MENU_DIFFICULTY_EXPL_ARRAY[difficultyselection])/2, S_resY/2 + 20, MENU_DIFFICULTY_EXPL_ARRAY[difficultyselection],Color.lightGray);
			arrow_left.draw(S_resX/2 - normalfont.getWidth(MENU_DIFFICULTY_ARRAY[difficultyselection])/2 - 45, S_resY/2 + 2, 0.4f);
			arrow_right.draw(S_resX/2 + normalfont.getWidth(MENU_DIFFICULTY_ARRAY[difficultyselection])/2 + 13, S_resY/2 + 2, 0.4f);
		}
		
		if(currentmenustate == MenuState.Options){
			bigfont.drawString(S_resX/2 - bigfont.getWidth("Pong")/2, 20 + bigfont.getHeight("Pong"), "Pong", Color.white);	
			normalfont.drawString(100, S_resY/2, MENU_OPTIONS_ARRAY[0],resselection);
			normalfont.drawString(100 + normalfont.getWidth(MENU_OPTIONS_ARRAY[0]), S_resY/2, RES_ARRAY[resolutionselection][0] + "x" + RES_ARRAY[resolutionselection][1], resselection);
			normalfont.drawString(100, S_resY/2 + 20, MENU_OPTIONS_ARRAY[1],volselection);
			normalfont.drawString(100 + normalfont.getWidth(MENU_OPTIONS_ARRAY[1]), S_resY/2 + 20, Integer.toString((int)gc.getMusicVolume()*100), volselection);
			normalfont.drawString(100, S_resY/2 + 40, MENU_OPTIONS_ARRAY[2],volstatselection);
			if(gc.isMusicOn() == true){
				normalfont.drawString(100 + normalfont.getWidth(MENU_OPTIONS_ARRAY[2]) + 20, S_resY/2 + 40, "on", volstatselection);
			}else{
				normalfont.drawString(100 + normalfont.getWidth(MENU_OPTIONS_ARRAY[2]) + 20, S_resY/2 + 40, "off", volstatselection);	
			}
			normalfont.drawString(100, S_resY/2 + 90, MENU_OPTIONS_ARRAY[4],saveselection);
			normalfont.drawString(100 + normalfont.getWidth(MENU_OPTIONS_ARRAY[4]) + 40, S_resY/2 + 90, MENU_OPTIONS_ARRAY[5],exitselection);
			if(S_Debug == true){
				normalfont.drawString(100, S_resY/2 + 60, MENU_OPTIONS_ARRAY[3],debugselection);
				normalfont.drawString(100 + normalfont.getWidth(MENU_OPTIONS_ARRAY[3]) + 20, S_resY/2 + 60, Boolean.toString(S_Debug_AI),Color.white);
			}
		}
		
		if(currentmenustate == MenuState.CPU || currentmenustate == MenuState.PvP || currentmenustate == MenuState.LAN || currentmenustate == MenuState.Challenge){
			pad1.draw(g);
			pad2.draw(g);
			ball.draw(g);
			//TODO: Change this or use another font
			g.drawString(Integer.toString(pad1.getPoints()), S_resX / 2 - 20, S_resY / 2);
			g.drawString(":", S_resX / 2, S_resY / 2);
			g.drawString(Integer.toString(pad2.getPoints()), S_resX / 2 + 20, S_resY / 2);
			if (S_Debug == true) {
				g.drawString("DEBUG Monitor", 75, 25);
				g.drawString("Ballvelocity: " + Double.toString(ball.getVelocity()), 75,40);
				g.drawString("LastCollision:" + lastcollision.toString(), 75, 55);
				g.drawString("LastPadCollision:" + lastpadcollision.toString(), 75, 70);
				g.drawString("Actual Vector: " + Float.toString(ball.getVectorX()) + "|" + Float.toString(ball.getVectorY()), 75, 85);
				g.drawString("Pad1 Position: " + Float.toString(pad1.getShape().getCenterY()) + " Pad2 Position: " + Float.toString(pad2.getShape().getCenterY()), 75, 100);
				g.drawString("Pad1 Spinspeed: " + Float.toString(pad1.getSpinspeed()), 75, 115);
				g.drawString("Ball Position: " + Float.toString(ball.getBall().getCenterX()) + "|" + Float.toString(ball.getBall().getCenterY()), 75, 130);
				g.drawString("Delta: " + Float.toString(1.0f/FPS), 75, 145);
				gc.setShowFPS(true);
			}
			
			if(gc.isPaused() == true){
				g.drawString("GAME PAUSED, PRESS " + "P" + " TO RESUME", S_resX / 2 - 135, S_resY / 2 + 50);
			}

			if (currentgamestate == GameState.BallIsOut) {
				g.drawString("Press ENTER to spawn a new ball!", S_resX / 2 - 135, S_resY / 2 + 30);
			}

			if (currentgamestate == GameState.Player1Wins) {
				g.drawString("Player 1 wins!", S_resX / 2 - 50, S_resY / 2 + 30);
				g.drawString("Press Enter to return to the mainmenu", S_resX / 2 - 160, S_resY / 2 + 50);
			}else if(currentgamestate == GameState.Player2Wins) {
				g.drawString("Player 2 wins!", S_resX / 2 - 50, S_resY / 2 + 30);
				g.drawString("Press Enter to return to the mainmenu", S_resX / 2 - 160, S_resY / 2 + 50);
			}	
			if(currentmenustate == MenuState.Challenge){
				g.drawString("Seconds survived: " + Integer.toString(challengecounter), S_resX / 2 - 75, 50);
			}
		}


	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		
		Input input = gc.getInput();
		if(currentmenustate == MenuState.Main){
			if(playerselection == 0){
				cpuselection = Color.white;
				pvpselection = Color.gray;
			}else if(playerselection == 1){
				cpuselection = Color.gray;
				pvpselection = Color.white;
				lanselection = Color.gray;
			}else if(playerselection == 2){
				pvpselection = Color.gray;
				lanselection = Color.white;
				challengeselection = Color.gray;
			}else if(playerselection == 3){
				lanselection = Color.gray;
				challengeselection = Color.white;
				optionselection = Color.gray;
			}else if(playerselection == 4){
				challengeselection = Color.gray;
				optionselection = Color.white;
				helpselection = Color.gray;
			}else if(playerselection == 5){
				optionselection = Color.gray;
				helpselection = Color.white;
				quitselection = Color.gray;
			}else if(playerselection == 6){
				helpselection = Color.gray;
				quitselection = Color.white;
			}
			
			if(input.isKeyPressed(Input.KEY_UP) && playerselection > 0){
				playerselection -= 1;
			}
			if(input.isKeyPressed(Input.KEY_DOWN) && playerselection < 6){
				playerselection += 1;
			}
			if(input.isKeyPressed(Input.KEY_ENTER)){
				if(playerselection == 0){
					currentmenustate = MenuState.CPUSelection;
				}
				if(playerselection == 1){
					currentmenustate = MenuState.PvP;
					newGame(HARD);
					currentgamestate = GameState.Play;
				}
				if(playerselection == 2){
					/*TODO
					 *Add Lanmode to game
					 *currentmenustate = MenuState.LAN;
					 *newGame();
					 */
				}
				if(playerselection == 3){
					currentmenustate = MenuState.Challenge;
					newGame(HARD);
					currentgamestate = GameState.Play;
				}
				if(playerselection == 4){
					currentmenustate = MenuState.Options;
				}
				if(playerselection == 5){
					
				}
				if(playerselection == 6){
					gc.exit();
				}

				//TODO: Add Help to the menu
				
			}
		}
		
		if(currentmenustate == MenuState.CPUSelection){
			if(input.isKeyPressed(Input.KEY_ESCAPE)){
				abort();	
			}
			
			if(input.isKeyPressed(Input.KEY_LEFT) && difficultyselection > 0){
				difficultyselection -= 1;
			}
			if(input.isKeyPressed(Input.KEY_RIGHT) && difficultyselection < 3){
				difficultyselection += 1;
			}
			if(input.isKeyPressed(Input.KEY_ENTER)){
				if(difficultyselection == 0){
					currentmenustate = MenuState.CPU;
					newGame(EASY);
					currentgamestate = GameState.Play;
				}
				if(difficultyselection == 1){
					currentmenustate = MenuState.CPU;
					newGame(MEDIUM);
					currentgamestate = GameState.Play;
				}
				if(difficultyselection == 2){
					currentmenustate = MenuState.CPU;
					newGame(HARD);
					currentgamestate = GameState.Play;
				}
				if(difficultyselection == 3){
					currentmenustate = MenuState.CPU;
					newGame(UNBEATABLE);
					currentgamestate = GameState.Play;
				}
			}
			
		}
		
		if(currentmenustate == MenuState.Options){
			if(input.isKeyPressed(Input.KEY_UP) && configselection > 0){
				if(configselection == 4 && S_Debug == false){
					configselection -= 2;
				}else{
					configselection -= 1;
				}
			}
			if(input.isKeyPressed(Input.KEY_DOWN) && configselection < 5){
				if(configselection == 2 && S_Debug == false){
					configselection += 2;
				}else{
					configselection += 1;
				}
			}
			
			if(configselection == 0){
				resselection = Color.white;
				volselection = Color.gray;
				if(input.isKeyPressed(Input.KEY_LEFT) && resolutionselection > 0){
					resolutionselection -= 1;
				}
				if(input.isKeyPressed(Input.KEY_RIGHT) && resolutionselection < RES_ARRAY.length-1){
					resolutionselection += 1;
				}
			}else if(configselection == 1){
				if(input.isKeyPressed(Input.KEY_LEFT) && gc.getMusicVolume() > 0){
					gc.setMusicVolume(gc.getMusicVolume()-0.01f);
				}
				if(input.isKeyPressed(Input.KEY_RIGHT) && gc.getMusicVolume() < 100){
					gc.setMusicVolume(gc.getMusicVolume()+0.01f);
				}
				resselection = Color.gray;
				volselection = Color.white;
				volstatselection = Color.gray;
			}else if(configselection == 2){
				if(input.isKeyPressed(Input.KEY_RIGHT) && gc.isMusicOn() == true ||  gc.isMusicOn() == true &&  input.isKeyPressed(Input.KEY_ENTER)){
					gc.setMusicOn(false);
				}
				if(input.isKeyPressed(Input.KEY_LEFT) && gc.isMusicOn() == false ||  gc.isMusicOn() == false && input.isKeyPressed(Input.KEY_ENTER)){
					gc.setMusicOn(true);
				}
				if(S_Debug == true){
					debugselection = Color.red;
				}
				volselection = Color.gray;
				volstatselection = Color.white;
				saveselection = Color.gray;
			}else if(configselection == 3){
				if(input.isKeyPressed(Input.KEY_RIGHT) && S_Debug_AI == true ||  S_Debug_AI == true &&  input.isKeyPressed(Input.KEY_ENTER)){
					S_Debug_AI = false;
				}
				if(input.isKeyPressed(Input.KEY_LEFT) && S_Debug_AI == false ||  S_Debug_AI == false && input.isKeyPressed(Input.KEY_ENTER)){
					S_Debug_AI = true;
				}
				volstatselection = Color.gray;
				debugselection = Color.pink;
				saveselection = Color.gray;
			}else if(configselection == 4){
				if(S_Debug == false){
					volstatselection = Color.gray;
				}else{
					debugselection = Color.red;
				}
				if(input.isKeyPressed(Input.KEY_ENTER)){
					try{
						S_resX = RES_ARRAY[resolutionselection][0];
						S_resY = RES_ARRAY[resolutionselection][1];
						prophelper.saveProperty("resX", Integer.toString(S_resX));
						prophelper.saveProperty("resY", Integer.toString(S_resY));
						prophelper.saveProperty("volume", Integer.toString((int)gc.getMusicVolume()));
						prophelper.saveProperty("vol_on", Boolean.toString(gc.isMusicOn()));
						prophelper.savePropertiesFile();
					}catch(NumberFormatException nfe){
						nfe.printStackTrace();
					}
					S_Container.setDisplayMode(S_resX, S_resY, false);
				}
				saveselection = Color.white;
				exitselection = Color.gray;
			}else if(configselection == 5){
				if(input.isKeyPressed(Input.KEY_ENTER)){
					currentmenustate = MenuState.Main;	
				}
				saveselection = Color.gray;
				exitselection = Color.white;
			}
		}
		
		if(currentmenustate == MenuState.CPU || currentmenustate == MenuState.LAN || currentmenustate == MenuState.PvP || currentmenustate == MenuState.Challenge){
			// Pause Game
			if (gc.hasFocus() == false || gc.isPaused() == false && input.isKeyPressed(Input.KEY_P) ) {
				gc.setPaused(true);
			}
			if(gc.hasFocus() == true && input.isKeyPressed(Input.KEY_P) && gc.isPaused() == true){
				gc.setPaused(false);
			}
			
			// Abort Game
			if(input.isKeyPressed(Input.KEY_ESCAPE)){
				if(currentmenustate == MenuState.Challenge){
					challengeselection = Color.gray;
				}
				if(currentmenustate == MenuState.LAN){
					lanselection = Color.gray;
				}
				abort();
			}
			
			if (gc.isPaused() == false) {
	
				if (currentgamestate == GameState.Play || currentgamestate == GameState.BallIsOut) {
					// For player 1
					if (input.isKeyDown(Input.KEY_UP) ^ input.isKeyDown(Input.KEY_DOWN)) {
						if (pad1.getShape().getMinY() > 0.0 && pad1.getShape().getMaxY() < S_resY) {
							pad1.addSpinSpeed(0.005f * delta);
						}
					}else{
						pad1.resetSpinSpeed();
					}
					if (input.isKeyDown(Input.KEY_UP)) {
						if (pad1.getShape().getMinY() > 0.0) {
							pad1.getShape().setY((float) ((pad1.getShape().getY() - 10.0)));
						}
	
					}
					if (input.isKeyDown(Input.KEY_DOWN)) {
						if (pad1.getShape().getMaxY() < S_resY) {
							pad1.getShape().setY((float) ((pad1.getShape().getY() + 10.0)));
						}
	
					}
					
					if(currentmenustate == MenuState.PvP || currentmenustate == MenuState.LAN ){
						// For player 2
						if (input.isKeyDown(Input.KEY_W) ^ input.isKeyDown(Input.KEY_S)) {
							if (pad2.getShape().getMinY() > 0.0 && pad2.getShape().getMaxY() < S_resY) {
								pad2.addSpinSpeed(0.005f * delta);
							}
						}else{
							pad2.resetSpinSpeed();
						}
						
						if (input.isKeyDown(Input.KEY_W)) {
							if (pad2.getShape().getMinY() > 0.0) {
								pad2.getShape().setY((float) ((pad2.getShape().getY() - 10.0)));
							}
	
						}
						if (input.isKeyDown(Input.KEY_S)) {
							if (pad2.getShape().getMaxY() < S_resY) {
								pad2.getShape().setY((float) ((pad2.getShape().getY() + 10.0)));
							}
	
						}
					}
	
				}
	
				if (currentgamestate == GameState.Play) {
					
					//Controls for ballspeed manipulation
					if(S_Debug){
						if(input.isKeyDown(Input.KEY_R)){
							ball.addDebugVelocity(0.25f, delta);
						}
						if(input.isKeyDown(Input.KEY_T)){
							ball.addDebugVelocity(-0.25f, delta);
						}
					}
					
					if(currentmenustate == MenuState.CPU || currentmenustate == MenuState.Challenge){
						if(S_Debug_AI || currentmenustate == MenuState.Challenge) {
							if(ball.getShape().getMinY() >= S_resY - pad2.getHEIGHT() / 2) {
							}else if(ball.getShape().getMaxY() <= 0 + pad2.getHEIGHT() / 2) {
							}else{
								pad2.getShape().setCenterY(ball.getShape().getCenterY());
							}
						}else{
							if(ball.getShape().getCenterX() > S_resX/2 + 10 && collision == false){
								ball.calcTrajectory(ball.getVector().copy(), ball.getShape().getCenterX(), ball.getShape().getCenterY());
								collision = true;
							}if(pad2.getShape().getCenterY() != S_resY/2 && lastpadcollision == Border.RIGHT){
								
								//Prevents that the AI pad is glitching while floating back to middle
								if(pad2.getShape().getCenterY() == S_resY/2 -1 ||pad2.getShape().getCenterY() == S_resY/2 +1 ){
									pad2.getShape().setCenterY(S_resY/2);
								}else{
									if(pad2.getShape().getCenterY() > S_resY/2){
										for(int i = 0; i <= 2.0f;i++){
											pad2.getShape().setCenterY(pad2.getShape().getCenterY() - 1.0f);
										}
									}else if(pad2.getShape().getCenterY() < S_resY/2){
										for(int i = 0; i <= 2.0f;i++){
											pad2.getShape().setCenterY(pad2.getShape().getCenterY() + 1.0f);
										}
									}
								}
							}if(ball.getShape().getCenterX() > S_resX/2 + 10 && lastpadcollision != Border.RIGHT){
								if(ball.getShape().getMaxY() > S_resY) {
									
								}else if(ball.getShape().getMinY() < 0) {
									
								}else{
									if(ball.getRoundedEtimatedY() < pad2.getShape().getCenterY() && pad2.getShape().getMinY() >= 0.0){
										for(int i = 0; i <= pad2.getVelocity();i++){
											pad2.getShape().setCenterY(pad2.getShape().getCenterY() - 1.0f);	
										}
									}
									if(ball.getRoundedEtimatedY() > pad2.getShape().getCenterY() && pad2.getShape().getMaxY() <= S_resY){
										for(int i = 0; i <= pad2.getVelocity();i++){
											pad2.getShape().setCenterY(pad2.getShape().getCenterY() + 1.0f);
										}
									}
								}
							}
						}
					}
	
	
					ball.getShape().setCenterX(ball.getShape().getCenterX() + ball.getVectorX());
					ball.getShape().setCenterY(ball.getShape().getCenterY() + ball.getVectorY());
					
					if(currentmenustate == MenuState.Challenge){
						time += delta;
						if(time >= 1000){
							challengecounter += 1;
							time = 0;
						}
						ball.addVelocityGravity(S_gravity, delta);
					}else{
						ball.addVelocity(0.03, delta, lastcollision);
					}

	
					if (ball.getShape().getMinY() <= 0 && lastcollision != Border.TOP) {
						ball.setVectorXY(ball.getVectorX(), -ball.getVectorY());
						lastcollision = Border.TOP;
					}
	
					if (ball.getShape().getMaxY() >= S_resY && lastcollision != Border.BOTTOM) {
						ball.setVectorXY(ball.getVectorX(), -ball.getVectorY());
						lastcollision = Border.BOTTOM;
					}
	
					if (pad1.intersects(ball.getShape()) && lastcollision != Border.LEFT) {
						if(pad1.getSpinspeed() > 0.0f){
							ball.addSpin(pad1.getSpinspeed());
						}
						ball.setVectorXY(-ball.getVectorX(), ball.getVectorY());
						lastpadcollision = Border.LEFT;
						lastcollision = Border.LEFT;
					}
	
					if (pad2.intersects(ball.getShape()) && lastcollision != Border.RIGHT) {
						if(currentmenustate == MenuState.PvP || currentmenustate == MenuState.LAN ){
							if(pad2.getSpinspeed() > 0.0f){
								System.out.println(ball.getVectorX() + "|" + ball.getVectorY());
								ball.addSpin(-pad2.getSpinspeed());
								System.out.println(ball.getVectorX() + "|" + ball.getVectorY());
							}
						}
						ball.setVectorXY(-ball.getVectorX(), ball.getVectorY());
						lastpadcollision = Border.RIGHT;
						lastcollision = Border.RIGHT;
	
					}
					
					if(ball.getShape().getCenterX() < S_resX/2 && collision == true){
						collision = false;
					}
	
					if(ball.getShape().getMaxX() < 0) {
						pad2.addPoint();
						currentgamestate = GameState.BallIsOut;
						lastcollision = Border.NONE;
						if(pad2.getPoints() >= GOAL) {
							currentgamestate = GameState.Player2Wins;
						}
					}
	
					if(ball.getShape().getMinX() > S_resX) {
						pad1.addPoint();
						currentgamestate = GameState.BallIsOut;
						lastcollision = Border.NONE;
						if (pad1.getPoints() >= GOAL) {
							currentgamestate = GameState.Player1Wins;
						}
					}
				}
				
				if(input.isKeyPressed(Input.KEY_ENTER)){
					newBall();
				}
	
				}
				if(currentgamestate == GameState.BallIsOut) {
					if(input.isKeyDown(Input.KEY_ENTER)) {
						ball = new Ball(S_resX / 2 - BALLRADIUS / 2, S_resY / 2 - BALLRADIUS / 2, BALLRADIUS);
						currentgamestate = GameState.Play;
					}
				}
				if(currentgamestate == GameState.Player1Wins || currentgamestate == GameState.Player2Wins){
					if(input.isKeyDown(Input.KEY_ENTER)) {
						currentgamestate = GameState.Start;
						currentmenustate = MenuState.Main;
					}
				}
		}
	}
	
	private void newGame(float paddifficulty){
		pad1 = new Pad(0 + 10, S_resY / 2, paddifficulty, 0);
		pad1.getShape().setCenterY(S_resY/2);
		pad2 = new Pad(S_resX - 20, S_resY / 2, paddifficulty, 0);
		pad2.getShape().setCenterY(S_resY/2);
		ball = new Ball(S_resX / 2 - BALLRADIUS / 2, S_resY / 2 - BALLRADIUS / 2, BALLRADIUS);
	}
	
	private UnicodeFont newFont(String font,int fontsize, boolean bold, boolean italic) throws SlickException{
		UnicodeFont unifont = new UnicodeFont(font,fontsize , bold, italic);
		unifont.addAsciiGlyphs();
		unifont.getEffects().add(new ColorEffect());
		unifont.loadGlyphs();
		return unifont;
	}
	
	private void newBall(){
		lastcollision = Border.NONE;
		lastpadcollision = Border.NONE;
		time = 0;
		challengecounter = 0;
		ball = new Ball(S_resX / 2 - BALLRADIUS / 2, S_resY / 2 - BALLRADIUS / 2, BALLRADIUS);
		currentgamestate = GameState.Play;
	}
	
	private void abort(){
		playerselection = 0;
		difficultyselection = 1;
		currentgamestate = GameState.Start;
		time = 0;
		challengecounter = 0;
		lastcollision = Border.NONE;
		lastpadcollision = Border.NONE;
		currentmenustate = MenuState.Main;
	}

}
