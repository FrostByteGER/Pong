/**
 * 
 */
package de.frostbyteger.pong.core;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.frostbyteger.pong.engine.Ball;
import de.frostbyteger.pong.engine.Border;
import de.frostbyteger.pong.engine.Pad;

/**
 * @author Kevin
 *
 */
public class Game extends BasicGameState {

	protected static final int ID = 003;
	
	private final String[] MENU_DIFFICULTY_ARRAY = {"Easy","Medium","Hard","Unbeatable"};
	private final String[] MENU_DIFFICULTY_EXPL_ARRAY = {"1/4 Speed of Player - For N00bs","1/2 Speed of Player- For average players","Same Speed as Player - For Pr0 Gamers","Alot faster than Player - Hacks are for pussies!"};

	
	protected Pad pad1;
	protected Pad pad2;
	protected Ball ball;

	protected Border lastcollision;
	protected Border lastpadcollision;
	
	public Image arrow_left;
	public Image arrow_right;
	
	/**
	 * 
	 */
	public Game() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		arrow_left = new Image("data/arrow_left.png");
		arrow_right = new Image("data/arrow_right.png");
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getID() {
		return ID;
	}

}
