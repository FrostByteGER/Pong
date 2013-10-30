package de.frostbyteger.pong.core;

import java.util.ArrayList;
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
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.frostbyteger.pong.engine.Ball;
import de.frostbyteger.pong.engine.Border;
import de.frostbyteger.pong.engine.Difficulty;
import de.frostbyteger.pong.engine.GameState;
import de.frostbyteger.pong.engine.Pad;
import de.frostbyteger.pong.engine.io.ConfigHelper;

public class CorePong extends BasicGame {	

	
	private int challengecounter = 0;

	
	private float time = 0.0f;
	
	private boolean collision = false;

	public void render(GameContainer gc, Graphics g) throws SlickException {
		if(currentmenustate == MenuState.CPU || currentmenustate == MenuState.PvP || currentmenustate == MenuState.LAN || currentmenustate == MenuState.Challenge){
			if (S_Debug == true) {
				g.drawString("DEBUG Monitor", 75, 25);
				g.drawString("Ballvelocity: " + Double.toString(ball.getPadVelocity()), 75,40);
				g.drawString("LastCollision:" + lastcollision.toString(), 75, 55);
				g.drawString("LastPadCollision:" + lastpadcollision.toString(), 75, 70);
				g.drawString("Actual Vector: " + Float.toString(ball.getVectorX()) + "|" + Float.toString(ball.getVectorY()), 75, 85);
				g.drawString("Pad1 Position: " + Float.toString(pad1.getShape().getCenterY()) + " Pad2 Position: " + Float.toString(pad2.getShape().getCenterY()), 75, 100);
				g.drawString("Pad1 Spinspeed: " + Float.toString(pad1.getSpinspeed()) + "|" + "Pad2 Spinspeed: " + Float.toString(pad2.getSpinspeed()), 75, 115);
				g.drawString("Ball Position: " + Float.toString(ball.getBall().getCenterX()) + "|" + Float.toString(ball.getBall().getCenterY()), 75, 130);
				g.drawString("Delta: " + Float.toString(1.0f/FPS), 75, 145);
				gc.setShowFPS(true);
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
		if(currentmenustate == MenuState.CPU || currentmenustate == MenuState.LAN || currentmenustate == MenuState.PvP || currentmenustate == MenuState.Challenge){
			// Pause Game
			
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
										for(int i = 0; i <= pad2.getPadVelocity();i++){
											pad2.getShape().setCenterY(pad2.getShape().getCenterY() - 1.0f);	
										}
									}
									if(ball.getRoundedEtimatedY() > pad2.getShape().getCenterY() && pad2.getShape().getMaxY() <= S_resY){
										for(int i = 0; i <= pad2.getPadVelocity();i++){
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
						ball.addBallVelocity(S_gravity, delta);
					}else{
						ball.addBallVelocity(0.03, delta, lastcollision);
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
							ball.addBallSpin(pad1.getSpinspeed());
						}
						ball.setVectorXY(-ball.getVectorX(), ball.getVectorY());
						lastpadcollision = Border.LEFT;
						lastcollision = Border.LEFT;
					}
	
					if (pad2.intersects(ball.getShape())) {
						if(currentmenustate == MenuState.PvP || currentmenustate == MenuState.LAN ){
							if(pad2.getSpinspeed() > 0.0f){
								ball.addBallSpin(-pad2.getSpinspeed());
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

}
