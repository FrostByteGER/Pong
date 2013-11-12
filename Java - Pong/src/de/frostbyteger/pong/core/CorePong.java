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
		}


	}

}
