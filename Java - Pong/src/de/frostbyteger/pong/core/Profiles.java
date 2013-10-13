/**
 * 
 */
package de.frostbyteger.pong.core;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

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
public class Profiles extends BasicGameState implements ComponentListener{
	
	protected static final int ID = 004;
	
	private StateBasedGame game;
	
	private final String[] PROFILE_OPTIONS           = {"new","save","delete","change profile","back"};
	private final String[] PROFILE_DESC_ACHIEVEMENTS = {"PLACEHOLDER","PLACEHOLDER","PLACEHOLDER","PLACEHOLDER","PLACEHOLDER","PLACEHOLDER"};
	private final String[] PROFILE_DESC_DATA         = {"Time played:","Time played in CPU-Mode:","Time played in LAN-Mode:",
														"Time played in Challenge-Mode:","Matches played:","Matches played in CPU-Mode:",
														"Matches played in LAN-Mode:","Matches played in Challenge-Mode:","Matches won:",
														"Matches won in CPU-Mode:","Matches won in LAN-Mode:",
														"Matches won in Challenge-Mode:"};
	private final String[] PROFILE_DATA_COMMANDS = {};
	private final int OFFSET_X = 25;
	
	private ProfileState pState = ProfileState.Show; //TODO: Change to none
		
	private TextField profileCreation;
	
	private Box profileInfos;
	private Box profileAchievements;
	private Box profileChooser;
	private Box profileDeleter;
	private Box profileOptions;
	
	private Cell mainHeader;

	/**
	 * 
	 */
	public Profiles() {
	}


	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.game = game;
		
		// Global Header
		mainHeader = new Cell(Pong.FONT, 160, Pong.S_resX/2 - 350/2, 20, 350, 250, container);
		mainHeader.setAutoAdjust(false);
		mainHeader.setCellText(Pong.TITLE);
		mainHeader.setClickable(false);
		
		// Boxes
		profileInfos = new Box(2, PROFILE_DESC_DATA.length, OFFSET_X, Pong.S_resY/2 - 150, Pong.FONT, 40, 300, 25, container);
		profileInfos.setHeaderTitle("Profiles:" + " " + "Kevin");
		profileInfos.getHeader().setLeft();
		profileInfos.setHeaderEdging(false);
		profileInfos.setHeaderActive(true);
		profileInfos.setBoxLeft();
		profileInfos.setEdged(true);
		profileInfos.setKeyInput(false);
		profileInfos.setFocus(false);
		profileInfos.setClickable(false);
		profileInfos.setColumnTitles(0, PROFILE_DESC_DATA);
		
		profileAchievements = new Box(1, PROFILE_DESC_ACHIEVEMENTS.length, OFFSET_X + (int)(profileInfos.getBoxWidth() * profileInfos.getBoxCellWidth()), Pong.S_resY/2 - 150, Pong.FONT, 40, 375, 25, container);
		profileAchievements.setHeaderTitle("Achievements");
		profileAchievements.getHeader().setLeft();
		profileAchievements.setHeaderEdging(false);
		profileAchievements.setHeaderActive(true);
		profileAchievements.setBoxLeft();
		profileAchievements.setEdged(true);
		profileAchievements.setKeyInput(false);
		profileAchievements.setFocus(false);
		profileAchievements.setClickable(false);
		profileAchievements.setColumnTitles(0, PROFILE_DESC_ACHIEVEMENTS);
		
		profileOptions = new Box(5, 1, Pong.S_resX/2 - 375, Pong.S_resY/2 + 200, Pong.FONT, 50, 150, 50, container);
		//profileOptions.setBoxLeft();
		profileOptions.setAllAutoAdjust(true);
		profileOptions.setEdged(false);
		profileOptions.setKeyInput(true);
		profileOptions.setFocus(true);
		profileOptions.setBoxKeyCoordinates(new int[] {1,1});
		for(int i = 0; i < profileOptions.getCells().size();i++){
			for(int j = 0; j < profileOptions.getCells().get(i).size();j++){
				profileOptions.getCells().get(i).get(0).setCellText(PROFILE_OPTIONS[i]);
				profileOptions.getCells().get(i).get(0).setActionCommand(PROFILE_OPTIONS[i]);
			}
		}
		profileOptions.addBoxListener(this);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		if(pState == ProfileState.Show){
			mainHeader.drawCell();
			profileInfos.render();
			profileAchievements.render();
			profileOptions.render();
		}else if(pState == ProfileState.Create){
			
		}else if(pState == ProfileState.Delete){
			
		}else if(pState == ProfileState.Load){
			/*for(int i = 0; i < PROFILE_DATA_COMMANDS.length;i++){ //TODO: Change place
				profileInfos.getCells().get(1).get(i).setCellText(Pong.S_profileData.get(PROFILE_DATA_COMMANDS[i]));
			}*/
		}else if(pState == ProfileState.Save){
			
		}else if(pState == ProfileState.None){
			
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if(pState == ProfileState.Show){
			profileOptions.update();
		}else if(pState == ProfileState.Create){
			
		}else if(pState == ProfileState.Delete){
			
		}else if(pState == ProfileState.Load){

		}else if(pState == ProfileState.Save){
			
		}else if(pState == ProfileState.None){
			
		}
	}
	
	public void keyPressed(int key, char c) {
		if(pState == ProfileState.Show){
		if(key == Input.KEY_RIGHT){
			if(profileOptions.getBoxKeyX() < profileOptions.getBoxWidth()){
				profileOptions.setBoxKeyX(profileOptions.getBoxKeyX() + 1);
			}
		}else if(key == Input.KEY_LEFT){
			if(profileOptions.getBoxKeyX() > 1){
				profileOptions.setBoxKeyX(profileOptions.getBoxKeyX() - 1);
			}
		}
		}else if(pState == ProfileState.Create){
			
		}else if(pState == ProfileState.Delete){
			
		}else if(pState == ProfileState.Load){

		}else if(pState == ProfileState.Save){
			
		}else if(pState == ProfileState.None){
			
		}

	}

	@Override
	public void componentActivated(AbstractComponent source) {
		if(pState == ProfileState.Show){
			if(source.getActionCommand().equals(PROFILE_OPTIONS[0])){

			}else if(source.getActionCommand().equals(PROFILE_OPTIONS[1])){
				
			}else if(source.getActionCommand().equals(PROFILE_OPTIONS[2])){
				
			}else if(source.getActionCommand().equals(PROFILE_OPTIONS[3])){
				
			}else if(source.getActionCommand().equals(PROFILE_OPTIONS[4])){
				game.enterState(MainMenu.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
			}
		}else if(pState == ProfileState.Create){
			
		}else if(pState == ProfileState.Delete){
			
		}else if(pState == ProfileState.Load){

		}else if(pState == ProfileState.Save){
			
		}else if(pState == ProfileState.None){
			
		}
	}
	
	@Override
	public int getID() {
		return ID;
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
