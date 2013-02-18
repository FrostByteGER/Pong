package de.frostbyteger.pong.core;

import java.util.Random;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Circle;
import de.frostbyteger.pong.engine.Ball;
import de.frostbyteger.pong.engine.Border;
import de.frostbyteger.pong.engine.Difficulty;
import de.frostbyteger.pong.engine.GameState;
import de.frostbyteger.pong.engine.MenuState;
import de.frostbyteger.pong.engine.Pad;


/**
 * @author Kevin
 * 
 */
public class Pong extends BasicGame implements KeyListener {

	protected Pad pad1, pad2;
	protected Ball ball;

	protected Border lastcollision;
	protected Border lastpadcollision;
	protected GameState currentgamestate = GameState.Start;
	protected MenuState currentmenustate = MenuState.Main;
	protected Difficulty cpudifficulty;
	
	private static final int ballradius = 5;
	private static final int goal = 10;
	private static final boolean DEBUG_AI = false;
	@SuppressWarnings("unused") //TODO: Delete this
	private static final double gravity = 9.81; // TODO: Add gravity to challenge mode
	
	private static final float easy = 2.5f;
	private static final float medium = 5.0f;
	private static final float hard = 10.0f;
	private static final float unbeatable = 15.0f;
	
	private final String ai = "AI-Difficulty";
	
	// Options
	public static int resX = 800;
	public static int resY = 600;
	//TODO: Add this variables
	public static int volume = 100;
	
	public static final int fps = 60;
	
	private String[] menu = {"Player vs. CPU","Player vs. Player","LAN-Mode - Coming soon","Challenge Mode","Achievements","Options","Help","Quit Game"};
	private String[] options = {""}; //TODO: Fill with information
	private String[] help = {"How to Play:","Player 1 Controls:","Player 2 Controls:","How to navigate:","Menu Controls:"};
	private String[] difficultymenu = {"Easy","Medium","Hard","Unbeatable"};
	private String[] difficultyexplanation = {"1/4 Speed of Player - For N00bs","1/2 Speed of Player- For average players","Same Speed as Player - For Pr0 Gamers","Alot faster than Player - Hacks are for pussies!"};
	private Color cpuselection = Color.gray;
	private Color pvpselection = Color.gray;
	private Color lanselection = Color.gray;
	private Color quitselection = Color.gray;
	
	private int playerselection = 0;
	private int difficultyselection = 1;

	private static boolean DEBUG = true;
	private boolean collision = false;
	private UnicodeFont smallfont;
	private UnicodeFont normalfont;
	private UnicodeFont mediumfont;
	private UnicodeFont bigfont;
	
	private Image arrow_left;
	private Image arrow_right;
	
	private Circle estimatedPoint;
	
	protected double hip = 0;
	protected Random rndm = new Random();

	//TODO: Add description
	public Pong(String title) {
		super(title);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer arg0) throws SlickException {
		smallfont = new UnicodeFont("data/alexis.ttf", 25, false, false);
		smallfont.addAsciiGlyphs();
		smallfont.getEffects().add(new ColorEffect());
		smallfont.loadGlyphs();
		
		normalfont = new UnicodeFont("data/alexis.ttf", 40, false, false);
		normalfont.addAsciiGlyphs();
		normalfont.getEffects().add(new ColorEffect());
		normalfont.loadGlyphs();
		
		mediumfont = new UnicodeFont("data/alexis.ttf", 50, false, false);
		mediumfont.addAsciiGlyphs();
		mediumfont.getEffects().add(new ColorEffect());
		mediumfont.loadGlyphs();
		
		bigfont = new UnicodeFont("data/alexis.ttf", 120, false, false);
		bigfont.addAsciiGlyphs();
		bigfont.getEffects().add(new ColorEffect());
		bigfont.loadGlyphs();
		
		arrow_left = new Image("data/arrow_left.png");
		arrow_right = new Image("data/arrow_right.png");
		
		estimatedPoint = new Circle(-10, -10, 1f);

		lastcollision = Border.NONE;
		lastpadcollision = Border.LEFT;
		currentgamestate = GameState.Play;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		if(currentmenustate == MenuState.Main){
			bigfont.drawString(resX/2 - bigfont.getWidth("Pong")/2, 20 + bigfont.getHeight("Pong"), "Pong", Color.white);	
			normalfont.drawString(resX/2 - normalfont.getWidth(menu[0])/2, resY/2, menu[0], cpuselection);		
			normalfont.drawString(resX/2 - normalfont.getWidth(menu[1])/2, resY/2 + 20, menu[1], pvpselection);
			normalfont.drawString(resX/2 - normalfont.getWidth(menu[2])/2, resY/2 + 40, menu[2], lanselection);
			normalfont.drawString(resX/2 - normalfont.getWidth(menu[7])/2, resY/2 + 60, menu[7], quitselection);
			g.drawString("BETA 0.9f", resX - 85, resY - 15);

		}
		
		if(currentmenustate == MenuState.CPUSelection){
			bigfont.drawString(resX/2 - bigfont.getWidth("Pong")/2, 20 + bigfont.getHeight("Pong"), "Pong", Color.white);	
			mediumfont.drawString(resX/2 - mediumfont.getWidth(ai)/2, resY/2 - 30, ai,Color.cyan);
			normalfont.drawString(resX/2 - normalfont.getWidth(difficultymenu[difficultyselection])/2, resY/2, difficultymenu[difficultyselection],Color.white);
			smallfont.drawString(resX/2 - smallfont.getWidth(difficultyexplanation[difficultyselection])/2, resY/2 + 20, difficultyexplanation[difficultyselection],Color.lightGray);
			arrow_left.draw(resX/2 - normalfont.getWidth(difficultymenu[difficultyselection])/2 - 45, resY/2 + 2, 0.4f);
			arrow_right.draw(resX/2 + normalfont.getWidth(difficultymenu[difficultyselection])/2 + 13, resY/2 + 2, 0.4f);
		}
		
		if(currentmenustate == MenuState.CPU || currentmenustate == MenuState.PvP || currentmenustate == MenuState.LAN){
			pad1.draw(g);
			pad2.draw(g);
			ball.draw(g);
			g.fill(estimatedPoint);
			//TODO: Change this or use another font
			g.drawString(Integer.toString(pad1.getPoints()), resX / 2 - 20, resY / 2);
			g.drawString(":", resX / 2, resY / 2);
			g.drawString(Integer.toString(pad2.getPoints()), resX / 2 + 20, resY / 2);
			if (DEBUG == true) {
				g.drawString("DEBUG Monitor", 75, 25);
				g.drawString("Ballvelocity: " + Double.toString(ball.getVelocity()), 75,40);
				g.drawString("LastCollision:" + lastcollision.toString(), 75, 55);
				g.drawString("Actual Vector: " + Float.toString(ball.getVectorX()) + "|" + Float.toString(ball.getVectorY()), 75, 70);
				g.drawString("Pad1 Position: " + Float.toString(pad1.getShape().getCenterY()) + " Pad2 Position: " + Float.toString(pad2.getShape().getCenterY()), 75, 85);
				gc.setShowFPS(true);
				
			}
			
			if(gc.isPaused() == true){
				//TODO: Change P to a variable
				g.drawString("GAME PAUSED, PRESS " + "P" + " TO RESUME", resX / 2 - 135, resY / 2 + 50);
			}
			
			if(ball.getShape().getCenterX() > resX/2 + 20){
				//TODO: Delete this or add it to the DEBUG Monitor
				g.drawString(".", resX - 10, ball.getEtimatedY());
			}

			if (currentgamestate == GameState.BallIsOut) {
				g.drawString("Press ENTER to spawn a new ball!", resX / 2 - 135, resY / 2 + 30);
			}

			if (currentgamestate == GameState.Player1Wins) {
				g.drawString("Player 1 wins!", resX / 2 - 50, resY / 2 + 30);
				g.drawString("Press Enter to return to the mainmenu", resX / 2 - 160, resY / 2 + 50);
			}else if(currentgamestate == GameState.Player2Wins) {
				g.drawString("Player 2 wins!", resX / 2 - 50, resY / 2 + 30);
				g.drawString("Press Enter to return to the mainmenu", resX / 2 - 160, resY / 2 + 50);
			}			
		}


	}

	@SuppressWarnings("static-access")
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
				quitselection = Color.gray;
			}else if(playerselection == 3){
				lanselection = Color.gray;
				quitselection = Color.white;
			}
			
			if(input.isKeyPressed(Input.KEY_UP) && playerselection > 0){
				playerselection -= 1;
			}
			if(input.isKeyPressed(Input.KEY_DOWN) && playerselection < 3){
				playerselection += 1;
			}
			if(input.isKeyPressed(Input.KEY_ENTER)){
				if(playerselection == 0){
					currentmenustate = MenuState.CPUSelection;
				}
				if(playerselection == 1){
					currentmenustate = MenuState.PvP;
					newGame(hard);
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
					gc.exit();
				}
				//TODO: Add Challenge Mode, Achievements, Options and Help to the menu
				
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
					newGame(easy);
					currentgamestate = GameState.Play;
				}
				if(difficultyselection == 1){
					currentmenustate = MenuState.CPU;
					newGame(medium);
					currentgamestate = GameState.Play;
				}
				if(difficultyselection == 2){
					currentmenustate = MenuState.CPU;
					newGame(hard);
					currentgamestate = GameState.Play;
				}
				if(difficultyselection == 3){
					currentmenustate = MenuState.CPU;
					newGame(unbeatable);
					currentgamestate = GameState.Play;
				}
			}
			
		}
		
		if(currentmenustate == MenuState.CPU || currentmenustate == MenuState.LAN || currentmenustate == MenuState.PvP ){
			// Pause Game
			if (gc.hasFocus() == false || gc.isPaused() == false && input.isKeyPressed(Input.KEY_P) ) {
				gc.setPaused(true);
			}
			if(gc.hasFocus() == true && input.isKeyPressed(Input.KEY_P) && gc.isPaused() == true){
				gc.setPaused(false);
			}
			
			if(input.isKeyPressed(Input.KEY_ESCAPE)){
				abort();
			}
			
			if (gc.isPaused() == false) {
	
				if (currentgamestate == GameState.Play || currentgamestate == GameState.BallIsOut) {
	
					// For player 1
					if (input.isKeyDown(Input.KEY_UP)) {
						if (pad1.getShape().getMinY() > 0.0) {
							pad1.getShape().setY((float) ((pad1.getShape().getY() - 10.0)));
						}
	
					}
					if (input.isKeyDown(Input.KEY_DOWN)) {
						if (pad1.getShape().getMaxY() < resY) {
							pad1.getShape().setY((float) ((pad1.getShape().getY() + 10.0)));
						}
	
					}
					
					if(currentmenustate == MenuState.PvP || currentmenustate == MenuState.LAN ){
						// For player 2
						if (input.isKeyDown(Input.KEY_W)) {
							if (pad2.getShape().getMinY() > 0.0) {
								pad2.getShape().setY((float) ((pad2.getShape().getY() - 10.0)));
							}
	
						}
						if (input.isKeyDown(Input.KEY_S)) {
							if (pad2.getShape().getMaxY() < resY) {
								pad2.getShape().setY((float) ((pad2.getShape().getY() + 10.0)));
							}
	
						}
					}
	
				}
	
				if (currentgamestate == GameState.Play) {
					
					//TODO
					if(input.isKeyDown(Input.KEY_R)){
						ball.addDebugVelocity(0.25f, delta);
					}
					
					if(currentmenustate == MenuState.CPU){
						
						if(DEBUG_AI) {
							if(ball.getShape().getMinY() >= resY - pad2.getHEIGHT() / 2) {
							}else if(ball.getShape().getMaxY() <= 0 + pad2.getHEIGHT() / 2) {
							}else{
								pad2.getShape().setCenterY(ball.getShape().getCenterY());
							}
						}else{
							/* TODO: Fix AI bugs:
							 * Glitching out of the map boundaries <- FIXED
							 * Glitching/Hopping while standing on one position if ball is in AI's side of the field
							 * Strange static position on the top of the mapboundary if ball flies on startup into the enemy side of the field
							 */
							if(ball.getShape().getCenterX() > resX/2 + 10 && collision == false){
								ball.calcTrajectory(ball.getVector().copy(), ball.getShape().getCenterX(), ball.getShape().getCenterY());
								estimatedPoint.setLocation(resX -20, ball.getEtimatedY());
								System.out.println(ball.getEtimatedY());
								collision = true;
							}
								
							}if(lastpadcollision == lastpadcollision.RIGHT ){
								if(pad2.getShape().getCenterY() > resY/2){
									pad2.getShape().setCenterY(pad2.getShape().getCenterY() - 2.0f);
								}else if(pad2.getShape().getCenterY() < resY/2){
									pad2.getShape().setCenterY(pad2.getShape().getCenterY() + 2.0f);
								}
							}else if(ball.getShape().getCenterX() > resX/2 + 10 && lastpadcollision != lastpadcollision.RIGHT){
								if(ball.getShape().getMaxY() > resY) {
									
								}else if(ball.getShape().getMinY() < 0) {
									
								}else{
									if(pad2.intersects(estimatedPoint)){		
										System.out.println("HIT");
									}else{
										if(ball.getEtimatedY() < pad2.getShape().getCenterY() && pad2.getShape().getMinY() >= 0.0){
											pad2.getShape().setCenterY(pad2.getShape().getCenterY() - pad2.getVelocity());	
										}else if(ball.getEtimatedY() > pad2.getShape().getCenterY() && pad2.getShape().getMaxY() <= resY){
											pad2.getShape().setCenterY(pad2.getShape().getCenterY() + pad2.getVelocity());
										}else{
											
										}
									}
								}
						}
					}
	
	
					ball.getShape().setCenterX(ball.getShape().getCenterX() + ball.getVectorX());
					ball.getShape().setCenterY(ball.getShape().getCenterY() + ball.getVectorY());
					ball.addVelocity(0.03, delta, lastcollision);
	
					if (ball.getShape().getMinY() <= 0 && lastcollision != Border.TOP) {
						ball.setVectorXY(ball.getVectorX(), -ball.getVectorY());
						lastcollision = Border.TOP;
					}
	
					if (ball.getShape().getMaxY() >= resY && lastcollision != Border.BOTTOM) {
						ball.setVectorXY(ball.getVectorX(), -ball.getVectorY());
						lastcollision = Border.BOTTOM;
					}
	
					if (pad1.intersects(ball.getShape()) && lastcollision != Border.LEFT) {
						ball.setVectorXY(-ball.getVectorX(), ball.getVectorY());
						lastpadcollision = Border.LEFT;
						lastcollision = Border.LEFT;
					}
	
					if (pad2.intersects(ball.getShape()) && lastcollision != Border.RIGHT) {
						ball.setVectorXY(-ball.getVectorX(), ball.getVectorY());
						System.out.println(ball.getShape().getCenterY());
						lastpadcollision = Border.RIGHT;
						lastcollision = Border.RIGHT;
	
					}
					
					if(ball.getShape().getCenterX() < resX/2 && collision == true){
						collision = false;
					}
					
					if(ball.getShape().getCenterX() < resX/2){
						estimatedPoint.setLocation(-10, -10);
					}
	
					// DEVTEST
					if(DEBUG == true) {
						if(ball.getShape().getX() < 0 || ball.getShape().getX() > resX) {
						}
					}
	
					if(ball.getShape().getMaxX() < 0) {
						pad2.addPoint();
						currentgamestate = GameState.BallIsOut;
						lastcollision = Border.NONE;
						if(pad2.getPoints() >= goal) {
							currentgamestate = GameState.Player2Wins;
						}
					}
	
					if(ball.getShape().getMinX() > resX) {
						pad1.addPoint();
						currentgamestate = GameState.BallIsOut;
						lastcollision = Border.NONE;
						if (pad1.getPoints() >= goal) {
							currentgamestate = GameState.Player1Wins;
						}
					}
				}
				
				if(input.isKeyPressed(Input.KEY_ENTER)){
					debugNewBall();
				}
	
				}
				if(currentgamestate == GameState.BallIsOut) {
					if(input.isKeyDown(Input.KEY_ENTER)) {
						ball = new Ball(resX / 2 - ballradius / 2, resY / 2 - ballradius / 2, ballradius);
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
		pad1 = new Pad(0 + 10, resY / 2, paddifficulty, 0);
		pad1.getShape().setCenterY(resY/2);
		pad2 = new Pad(resX - 20, resY / 2, paddifficulty, 0);
		pad2.getShape().setCenterY(resY/2);
		ball = new Ball(resX / 2 - ballradius / 2, resY / 2 - ballradius / 2, ballradius);
	}
	
	private void newFont(int fontsize){
		//TODO: Add font constructor
	}
	
	private void abort(){
		playerselection = 0;
		difficultyselection = 1;
		currentgamestate = GameState.Start;
		lastcollision = Border.NONE;
		lastpadcollision = Border.NONE;
		currentmenustate = MenuState.Main;
	}
	
	private void debugNewBall(){
		lastcollision = Border.NONE;
		ball = new Ball(resX / 2 - ballradius / 2, resY / 2 - ballradius / 2, ballradius);
		currentgamestate = GameState.Play;
	}

}
