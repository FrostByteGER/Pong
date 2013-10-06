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
import de.frostbyteger.pong.start.Pong;

/**
 * @author Kevin
 *
 */
public class Profile extends BasicGameState implements ComponentListener{
	
	protected static final int ID = 004;
	
	private LinkedHashMap<String, String> profileData;
	private LinkedHashMap<String, String> achievementData;
	
	private final String[] PROFILE_OPTIONS           = {"save","delete","load","back"};
	private final String[] PROFILE_DESC_ACHIEVEMENTS = {"PLACEHOLDER","PLACEHOLDER","PLACEHOLDER","PLACEHOLDER","PLACEHOLDER","PLACEHOLDER"};
	private final String[] PROFILE_DESC_DATA         = {"Time played:","Time played in CPU-Mode:","Time played in LAN-Mode:",
														"Time played in Challenge-Mode:","Matches played:","Matches played in CPU-Mode:",
														"Matches played in LAN-Mode:","Matches played in Challenge-Mode:","Matches won:",
														"Matches won in CPU-Mode:","Matches won in LAN-Mode:",
														"Matches won in Challenge-Mode:"};
	private final String[] PROFILE_DATA_COMMANDS = {};
	private final int OFFSET_X = 25;
	
	private ProfileState pState = ProfileState.Show; //TODO: Change to none
	
	private ProfileHelper profileHelper;
	
	private TextField profileCreation;
	
	private Box profileInfos;
	private Box profileAchievements;
	private Box profileChooser;
	private Box profileDeleter;
	private Box profileOptions;
	
	private Cell mainHeader;
	private Cell profileNameHeader;
	private Cell profileAchievementHeader;

	/**
	 * 
	 */
	public Profile() {
	}


	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		
		// Global Header
		mainHeader = new Cell(Pong.FONT, 160, Pong.S_resX/2 - 350/2, 20, 350, 250, container);
		mainHeader.setAutoAdjust(false);
		mainHeader.setCellText(Pong.TITLE);
		mainHeader.setClickable(false);
		
		// Local Header
		profileNameHeader = new Cell(Pong.FONT, OFFSET_X, Pong.S_resY/2 - 200, 20, 100, 50, container);
		profileNameHeader.setAutoAdjust(true);
		//profileNameHeader.setCellText("");
		profileNameHeader.setClickable(false);
		
		profileInfos = new Box(2, PROFILE_DESC_DATA.length, OFFSET_X, Pong.S_resY/2 - 150, Pong.FONT, 40, 300, 25, container);
		profileInfos.setAllAutoAdjust(true);
		profileInfos.setBoxLeft();
		profileInfos.setEdged(true);
		profileInfos.setKeyInput(false);
		profileInfos.setFocus(false);
		profileInfos.setClickable(false);
		//profileInfos.setBoxKeyCoordinates(new int[] {1,1});
		profileInfos.setColumnTitles(0, PROFILE_DESC_DATA);
		//profileInfos.setAutoAdjustBox(true);
		
		profileOptions = new Box(4, 1, OFFSET_X, Pong.S_resY/2 + 300, Pong.FONT, 30, 100, 30, container);
		profileOptions.setAllAutoAdjust(false);
		profileOptions.setBoxLeft();
		profileOptions.setEdged(false);
		profileOptions.setKeyInput(true);
		profileOptions.setFocus(false);
		profileOptions.setBoxKeyCoordinates(new int[] {1,1});
		for(int i = 0; i < profileOptions.getCells().size();i++){
			for(int j = 0; j < profileOptions.getCells().get(i).size();j++){
				profileOptions.getCells().get(0).get(0).setCellText(PROFILE_OPTIONS[i]);
				profileOptions.getCells().get(0).get(0).setActionCommand(PROFILE_OPTIONS[i]);
			}
		}
		profileOptions.addBoxListener(this);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		if(pState == ProfileState.Show){
			mainHeader.drawCell();
			profileNameHeader.drawCell();
			profileInfos.render();
		}else if(pState == ProfileState.Create){
			
		}else if(pState == ProfileState.Delete){
			
		}else if(pState == ProfileState.Load){
			for(int i = 0; i < PROFILE_DATA_COMMANDS.length;i++){ //TODO: Change place
				profileInfos.getCells().get(1).get(i).setCellText(profileData.get(PROFILE_DATA_COMMANDS[i]));
			}
		}else if(pState == ProfileState.Save){
			
		}else if(pState == ProfileState.None){
			
		}
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
