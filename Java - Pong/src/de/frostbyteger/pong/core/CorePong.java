package de.frostbyteger.pong.core;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.frostbyteger.pong.engine.Ball;
import de.frostbyteger.pong.engine.Border;
import de.frostbyteger.pong.engine.Difficulty;
import de.frostbyteger.pong.engine.GameState;
import de.frostbyteger.pong.engine.MenuState;
import de.frostbyteger.pong.engine.Pad;
import de.frostbyteger.pong.engine.io.PropertyHelper;


/**
 * @author Kevin
 * TODO: Music on/off Switch wont work
 * TODO: Scale Ballspeed with increasing resolution
 */
public class CorePong extends BasicGameState {

	protected Border lastcollision;
	protected Border lastpadcollision;
	protected GameState currentgamestate = GameState.Start;
	protected MenuState currentmenustate = MenuState.Main;
	
	private static final int BALLRADIUS = 5;
	private static final int GOAL = 10;
	
	private static float S_gravity = 0.00981f;
	
	private static final int[][] RES_ARRAY = {{640,480},{800,600},{1024,768},{1280,960},{1280,1024}};
	
	private final String AI = "AI-Difficulty";
	

	

	private int difficultyselection = 1;
	private int configselection = 0;
	private int resolutionselection = 0;
	
	private int challengecounter = 0;
	private int presstimer = 0;
	private int press = 0;
	
	private float time = 0.0f;


	private static boolean S_Debug_AI = false;
	
	private boolean collision = false;
	

	
	
	protected double hip = 0;
	protected Random rndm = new Random();

	//TODO: Add description
	public CorePong(String title) {
		//super(title);
	}

	public void init(GameContainer gc) throws SlickException {
		



		lastcollision = Border.NONE;
		lastpadcollision = Border.NONE;
		currentgamestate = GameState.Play;
		
		
		
		
		//Ensures that the displayed resolution in the optionsmenu equals the actual gamewindow resolution when
		//starting the game
		for(int i = 0; i < RES_ARRAY.length;i++){
			if(S_resX == RES_ARRAY[i][0]){
				resolutionselection = i;
			}
		}

	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		
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
			normalfont.drawString(100, S_resY/2, MENU_OPTIONS_ARRAY[0],selectionArray.get(7));
			normalfont.drawString(100 + normalfont.getWidth(MENU_OPTIONS_ARRAY[0]), S_resY/2, RES_ARRAY[resolutionselection][0] + "x" + RES_ARRAY[resolutionselection][1], selectionArray.get(7));
			normalfont.drawString(100, S_resY/2 + 20, MENU_OPTIONS_ARRAY[1],selectionArray.get(8));
			normalfont.drawString(100 + normalfont.getWidth(MENU_OPTIONS_ARRAY[1]), S_resY/2 + 20, (Integer.toString((int)(gc.getMusicVolume()*100.0f))), selectionArray.get(8));
			normalfont.drawString(100, S_resY/2 + 40, MENU_OPTIONS_ARRAY[2],selectionArray.get(9));
			if(gc.isMusicOn() == true){
				normalfont.drawString(100 + normalfont.getWidth(MENU_OPTIONS_ARRAY[2]) + 20, S_resY/2 + 40, "on", selectionArray.get(9));
			}else{
				normalfont.drawString(100 + normalfont.getWidth(MENU_OPTIONS_ARRAY[2]) + 20, S_resY/2 + 40, "off", selectionArray.get(9));	
			}
			normalfont.drawString(100, S_resY/2 + 90, MENU_OPTIONS_ARRAY[4],selectionArray.get(10));
			normalfont.drawString(100 + normalfont.getWidth(MENU_OPTIONS_ARRAY[4]) + 40, S_resY/2 + 90, MENU_OPTIONS_ARRAY[5],selectionArray.get(11));
			if(S_Debug == true){
				normalfont.drawString(100, S_resY/2 + 60, MENU_OPTIONS_ARRAY[3],selectionArray.get(12));
				normalfont.drawString(100 + normalfont.getWidth(MENU_OPTIONS_ARRAY[3]) + 20, S_resY/2 + 60, Boolean.toString(S_Debug_AI),selectionArray.get(12));
			}
		}
		
		if(currentmenustate == MenuState.CPU || currentmenustate == MenuState.PvP || currentmenustate == MenuState.LAN || currentmenustate == MenuState.Challenge){
			pad1.draw(g);
			pad2.draw(g);
			ball.draw(g);
			if(currentmenustate == MenuState.CPU || currentmenustate == MenuState.PvP || currentmenustate == MenuState.LAN){
				g.drawString(Integer.toString(pad1.getPoints()), S_resX / 2 - 20, S_resY / 2);
				g.drawString(":", S_resX / 2, S_resY / 2);
				g.drawString(Integer.toString(pad2.getPoints()), S_resX / 2 + 20, S_resY / 2);
			}
			if (S_Debug == true) {
				g.drawString("DEBUG Monitor", 75, 25);
				g.drawString("Ballvelocity: " + Double.toString(ball.getVelocity()), 75,40);
				g.drawString("LastCollision:" + lastcollision.toString(), 75, 55);
				g.drawString("LastPadCollision:" + lastpadcollision.toString(), 75, 70);
				g.drawString("Actual Vector: " + Float.toString(ball.getVectorX()) + "|" + Float.toString(ball.getVectorY()), 75, 85);
				g.drawString("Pad1 Position: " + Float.toString(pad1.getShape().getCenterY()) + " Pad2 Position: " + Float.toString(pad2.getShape().getCenterY()), 75, 100);
				g.drawString("Pad1 Spinspeed: " + Float.toString(pad1.getSpinspeed()) + "|" + "Pad2 Spinspeed: " + Float.toString(pad2.getSpinspeed()), 75, 115);
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

	public void update(GameContainer gc, int delta) throws SlickException {
		
		
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
					newGame(Difficulty.EASY.getDifficulty());
					currentgamestate = GameState.Play;
				}
				if(difficultyselection == 1){
					currentmenustate = MenuState.CPU;
					newGame(Difficulty.MEDIUM.getDifficulty());
					currentgamestate = GameState.Play;
				}
				if(difficultyselection == 2){
					currentmenustate = MenuState.CPU;
					newGame(Difficulty.HARD.getDifficulty());
					currentgamestate = GameState.Play;
				}
				if(difficultyselection == 3){
					currentmenustate = MenuState.CPU;
					newGame(Difficulty.UNBEATABLE.getDifficulty());
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
				for(int e = 0;e < selectionArray.size();e++){
					selectionArray.set(e, Color.gray);
				}
				selectionArray.set(7, Color.white);
				if(input.isKeyPressed(Input.KEY_LEFT) && resolutionselection > 0){
					resolutionselection -= 1;
				}
				if(input.isKeyPressed(Input.KEY_RIGHT) && resolutionselection < RES_ARRAY.length-1){
					resolutionselection += 1;
				}
			}else if(configselection == 1){
					if(input.isKeyDown(Input.KEY_LEFT) && gc.getMusicVolume() > 0){
						if(press <= 4 && presstimer == 35){
							gc.setMusicVolume(gc.getMusicVolume()-0.01f);
							presstimer = 0;
							press += 1;
						}else if(press > 4 && presstimer == 10){
							gc.setMusicVolume(gc.getMusicVolume()-0.01f);
							presstimer = 0;
						}else{
							presstimer += 5;
						}
						
					}else if(input.isKeyDown(Input.KEY_RIGHT) && gc.getMusicVolume() < 100){
					
						if(press <= 4 && presstimer == 35){
							gc.setMusicVolume(gc.getMusicVolume()+0.01f);
							presstimer = 0;
							press += 1;
						}else if(press > 4 && presstimer == 10){
							System.out.println("TEST");
							gc.setMusicVolume(gc.getMusicVolume()+0.01f);
							presstimer = 0;
						}else{
							presstimer += 5;
						}
						
					}else{
						press = 0;
					}
					
				for(int e = 0;e < selectionArray.size();e++){
					selectionArray.set(e, Color.gray);
				}
				selectionArray.set(8, Color.white);
			}else if(configselection == 2){
				if(input.isKeyPressed(Input.KEY_RIGHT) && gc.isMusicOn() == true || gc.isMusicOn() == true &&  input.isKeyPressed(Input.KEY_ENTER)){
					gc.setMusicOn(false);
				}
				if(input.isKeyPressed(Input.KEY_LEFT) && gc.isMusicOn() == false || gc.isMusicOn() == false && input.isKeyPressed(Input.KEY_ENTER)){
					gc.setMusicOn(true);
				}
				for(int e = 0;e < selectionArray.size();e++){
					selectionArray.set(e, Color.gray);
				}
				selectionArray.set(9, Color.white);
			}else if(configselection == 3){
				if(input.isKeyPressed(Input.KEY_RIGHT) && S_Debug_AI == true ||  S_Debug_AI == true &&  input.isKeyPressed(Input.KEY_ENTER)){
					S_Debug_AI = false;
				}
				if(input.isKeyPressed(Input.KEY_LEFT) && S_Debug_AI == false ||  S_Debug_AI == false && input.isKeyPressed(Input.KEY_ENTER)){
					S_Debug_AI = true;
				}
				for(int e = 0;e < selectionArray.size();e++){
					selectionArray.set(e, Color.gray);
				}
				selectionArray.set(12, Color.white);
			}else if(configselection == 4){
				if(S_Debug == false){
					for(int e = 0;e < selectionArray.size();e++){
						selectionArray.set(e, Color.gray);
					}
					selectionArray.set(9, Color.white);
				}else{
					for(int e = 0;e < selectionArray.size();e++){
						selectionArray.set(e, Color.gray);
					}
					selectionArray.set(12, Color.white);
				}
				if(input.isKeyPressed(Input.KEY_ENTER)){
					try{
						S_resX = RES_ARRAY[resolutionselection][0];
						S_resY = RES_ARRAY[resolutionselection][1];
						prophelper.saveProperty("resX", Integer.toString(S_resX));
						prophelper.saveProperty("resY", Integer.toString(S_resY));
						prophelper.saveProperty("volume", Float.toString((int)(gc.getMusicVolume()*100)/100.0f));
						prophelper.saveProperty("vol_on", Boolean.toString(gc.isMusicOn()));
						prophelper.savePropertiesFile();
					}catch(NumberFormatException nfe){
						nfe.printStackTrace();
					}
					S_Container.setDisplayMode(S_resX, S_resY, false);
				}
				for(int e = 0;e < selectionArray.size();e++){
					selectionArray.set(e, Color.gray);
				}
				selectionArray.set(10, Color.white);
			}else if(configselection == 5){
				if(input.isKeyPressed(Input.KEY_ENTER)){
					currentmenustate = MenuState.Main;	
				}
				for(int e = 0;e < selectionArray.size();e++){
					selectionArray.set(e, Color.gray);
				}
				selectionArray.set(11, Color.white);
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
								pad2.resetSpinSpeed();
							}else if(ball.getShape().getMaxY() <= 0 + pad2.getHEIGHT() / 2) {
								pad2.resetSpinSpeed();
							}else{
								pad2.getShape().setCenterY(ball.getShape().getCenterY());
								pad2.addSpinSpeed(0.005f * delta);
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
	
					//TODO: outsource the collision detection and everything else that belongs to the ball
					//to the ball class
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

	
					if (ball.getShape().getMinY() <= 0) {
						ball.setVectorXY(ball.getVectorX(), -ball.getVectorY());
						lastcollision = Border.TOP;
					}
	
					if (ball.getShape().getMaxY() >= S_resY) {
						ball.setVectorXY(ball.getVectorX(), -ball.getVectorY());
						lastcollision = Border.BOTTOM;
					}
	
					if (pad1.intersects(ball.getShape())) {
						if(pad1.getSpinspeed() > 0.0f){
							ball.addSpin(pad1.getSpinspeed());
						}
						ball.setVectorXY(-ball.getVectorX(), ball.getVectorY());
						lastpadcollision = Border.LEFT;
						lastcollision = Border.LEFT;
					}
	
					if (pad2.intersects(ball.getShape())) {
						if(currentmenustate == MenuState.PvP || currentmenustate == MenuState.LAN ){
							if(pad2.getSpinspeed() > 0.0f){
								ball.addSpin(-pad2.getSpinspeed());
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
				
				if(input.isKeyPressed(Input.KEY_ENTER) && S_Debug == true){
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
