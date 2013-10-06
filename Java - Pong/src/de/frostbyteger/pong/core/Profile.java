/**
 * 
 */
package de.frostbyteger.pong.core;

import java.util.LinkedHashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.frostbyteger.pong.engine.ProfileState;
import de.frostbyteger.pong.engine.graphics.ui.gui.AbstractComponent;
import de.frostbyteger.pong.engine.graphics.ui.gui.Box;
import de.frostbyteger.pong.engine.graphics.ui.gui.Cell;
import de.frostbyteger.pong.engine.graphics.ui.gui.ComponentListener;
import de.frostbyteger.pong.engine.io.ProfileHelper;

/**
 * @author Kevin
 *
 */
public class Profile extends BasicGameState implements ComponentListener{
	
	protected static final int ID = 004;
	
	private LinkedHashMap<String, String> profileData;
	private LinkedHashMap<String, String> achievementData;
	
	private ProfileState pState = ProfileState.None;
	
	private ProfileHelper profileHelper;
	
	private TextField profileCreation;
	
	private Box profileInfos;
	private Box profileAchievements;
	private Box profileChooser;
	private Box profileDeleter;
	private Box profileSaveReturn;
	
	private Cell profileName;
	private Cell profileAchievementHeader;

	/**
	 * 
	 */
	public Profile() {
	}


	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		//profileInfos = new Box(, boxHeight, boxX, boxY, boxFontPath, boxFontSize, cellWidth, cellHeight, container)
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
	
	public void createProfile(){
		
	}
	
	public void saveProfile(){
		
	}
	
	public void loadProfile(){
		
	}
	
	public static void deleteProfile(){
		
	}


	/**
	 * @return the pstate
	 */
	public ProfileState getPstate() {
		return pState;
	}


	/**
	 * @param pstate the pstate to set
	 */
	public void setPstate(ProfileState pState) {
		this.pState = pState;
	}

}
