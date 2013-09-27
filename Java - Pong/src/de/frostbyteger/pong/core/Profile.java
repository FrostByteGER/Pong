/**
 * 
 */
package de.frostbyteger.pong.core;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.frostbyteger.pong.engine.ProfileState;
import de.frostbyteger.pong.engine.graphics.ui.gui.AbstractComponent;
import de.frostbyteger.pong.engine.graphics.ui.gui.ComponentListener;
import de.frostbyteger.pong.engine.io.ProfileHelper;

/**
 * @author Kevin
 *
 */
public class Profile extends BasicGameState implements ComponentListener{
	
	protected static final int ID = 004;
	
	private ProfileState pstate = ProfileState.None;
	
	private ProfileHelper profileHelper;
	
	private TextField profileCreation;

	/**
	 * 
	 */
	public Profile() {
	}


	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

	}
	
	public void keyPressed(int key, char c) {
	}

	@Override
	public void componentActivated(AbstractComponent source) {		
	}
	
	@Override
	public int getID() {
		return ID;
	}


	/**
	 * @return the pstate
	 */
	public ProfileState getPstate() {
		return pstate;
	}


	/**
	 * @param pstate the pstate to set
	 */
	public void setPstate(ProfileState pstate) {
		this.pstate = pstate;
	}

}
