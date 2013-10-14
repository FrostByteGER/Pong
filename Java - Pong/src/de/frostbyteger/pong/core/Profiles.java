/**
 * 
 */
package de.frostbyteger.pong.core;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.swing.JOptionPane;
import javax.xml.bind.JAXBException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import de.frostbyteger.pong.engine.Profile;
import de.frostbyteger.pong.engine.ProfileState;
import de.frostbyteger.pong.engine.graphics.FontHelper;
import de.frostbyteger.pong.engine.graphics.ui.gui.AbstractComponent;
import de.frostbyteger.pong.engine.graphics.ui.gui.Box;
import de.frostbyteger.pong.engine.graphics.ui.gui.Cell;
import de.frostbyteger.pong.engine.graphics.ui.gui.ComponentListener;
import de.frostbyteger.pong.engine.graphics.ui.gui.TextField;
import de.frostbyteger.pong.start.Pong;

/**
 * @author Kevin
 *
 */
public class Profiles extends BasicGameState implements ComponentListener{
	
	protected static final int ID = 004;
	
	private StateBasedGame game;
	
	private final String[] PROFILE_OPTIONS           = {"new","delete","change\nprofile","back"};
	private final String[] PROFILE_DESC_ACHIEVEMENTS = {"PLACEHOLDER","PLACEHOLDER","PLACEHOLDER","PLACEHOLDER","PLACEHOLDER","PLACEHOLDER"};
	private final String[] PROFILE_DESC_DATA         = {"Time played:","Time played in CPU-Mode:","Time played in LAN-Mode:",
														"Time played in Challenge-Mode:","Matches played:","Matches played in CPU-Mode:",
														"Matches played in LAN-Mode:","Matches played in Challenge-Mode:","Matches won:",
														"Matches won in CPU-Mode:","Matches won in LAN-Mode:",
														"Matches won in Challenge-Mode:"};
	private final String[] PROFILE_DATA_COMMANDS = {};
	private final int OFFSET_X = 25;
	
	private ProfileState pState = ProfileState.Show; //TODO: Change to none
	
	private Profile saveProfile = null;
		
	private TextField profileCreation;
	
	private Box profileInfos;
	private Box profileAchievements;
	private Box profileChooser;
	private Box profileDeleter;
	private Box profileOptions;
	
	private Cell mainHeader;
	private Cell profileOptionHeader;
	private Cell profileOptionText;
	
	private boolean overwriteCheck = true;
	private int saveTimer = -1;

	/**
	 * 
	 */
	public Profiles() {
	}


	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.game = game;
		
		// Global Header
		mainHeader = new Cell(Pong.FONT, 160, Pong.S_resX/2 - 175, 20, 350, 250, container);
		mainHeader.setAutoAdjust(false);
		mainHeader.setCellText(Pong.TITLE);
		mainHeader.setClickable(false);
		
		// Local Header
		profileOptionHeader = new Cell(Pong.FONT, 50, Pong.S_resX/2 - 50, 250, 100, 50, container);
		profileOptionHeader.setFontColor(Color.cyan);
		profileOptionHeader.setAutoAdjust(false);
		profileOptionHeader.setCellText("Create new Profile");
		profileOptionHeader.setClickable(false);
		
		profileOptionText = new Cell(Pong.FONT, 30, Pong.S_resX/2 - 50, 300, 100, 50, container);
		profileOptionText.setCentered();
		profileOptionText.setAutoAdjust(false);
		profileOptionText.setCellText("Please enter a profilename\nand press ENTER to confirm");
		profileOptionText.setClickable(false);
		
		// Textfield
		profileCreation = new TextField(container, FontHelper.newFont(Pong.FONT, 75, false, false), Pong.S_resX/2 - 200, 370, 400, 38, this);
		profileCreation.setActionCommand("enter");
		profileCreation.setAllowWhitespaces(false);
		
		// Boxes
		profileInfos = new Box(2, PROFILE_DESC_DATA.length, OFFSET_X, Pong.S_resY/2 - 150, Pong.FONT, 40, 300, 25, container);
		profileInfos.setHeaderTitle("Profile:" + " " + Pong.S_profiles.get(Pong.S_activeProfile).getProfileName());
		profileInfos.getHeader().setFontColor(Color.cyan);
		profileInfos.getHeader().setLeft();
		profileInfos.setHeaderEdging(false);
		profileInfos.setHeaderActive(true);
		profileInfos.setBoxLeft();
		profileInfos.setEdged(true);
		profileInfos.setKeyInput(false);
		profileInfos.setFocus(false);
		profileInfos.setClickable(false);
		profileInfos.setColumnTitles(0, PROFILE_DESC_DATA);
		for(int i = 0;i < profileInfos.getCells().get(1).size();i++){
			profileInfos.getCells().get(1).get(i).setCellText(Integer.toString(Pong.S_statisticsData.get(Pong.STATISTICS_KEYS[i])));
		}
		
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
		
		profileOptions = new Box(PROFILE_OPTIONS.length, 1, Pong.S_resX/2 - (PROFILE_OPTIONS.length*150)/2, Pong.S_resY/2 + 200, Pong.FONT, 50, 150, 50, container);
		profileOptions.setAllAutoAdjust(true);
		profileOptions.setEdged(false);
		profileOptions.setKeyInput(true);
		profileOptions.setFocus(true);
		profileOptions.setBoxKeyCoordinates(new int[] {PROFILE_OPTIONS.length,1});
		for(int i = 0; i < profileOptions.getCells().size();i++){
			for(int j = 0; j < profileOptions.getCells().get(i).size();j++){
				profileOptions.getCells().get(i).get(0).setCellText(PROFILE_OPTIONS[i]);
				profileOptions.getCells().get(i).get(0).setActionCommand(PROFILE_OPTIONS[i]);
			}
		}
		profileOptions.addBoxListener(this);
		
		profileDeleter = new Box(2, 1, Pong.S_resX/2 - 100, 320, Pong.FONT, 40, 100, 50, container);
		profileDeleter.setAllAutoAdjust(true);
		profileDeleter.setEdged(true);
		profileDeleter.setKeyInput(true);
		profileDeleter.setFocus(true);
		profileDeleter.setBoxKeyCoordinates(new int[] {1,1});
		profileDeleter.getCells().get(0).get(0).setCellText("Yes");
		profileDeleter.getCells().get(0).get(0).setActionCommand(PROFILE_OPTIONS[1]);
		profileDeleter.getCells().get(1).get(0).setCellText("No");
		profileDeleter.getCells().get(1).get(0).setActionCommand(PROFILE_OPTIONS[3]);
		profileDeleter.addBoxListener(this);
		
		profileChooser = new Box(1, Pong.S_profiles.size(), Pong.S_resX/2 - 100, 320, Pong.FONT, 40, 200, 50, container);
		profileChooser.setAllAutoAdjust(true);
		profileChooser.setEdged(false);
		profileChooser.setKeyInput(true);
		profileChooser.setFocus(true);
		profileChooser.setBoxKeyCoordinates(new int[] {1,1});
		Collection<Profile> c = Pong.S_profiles.values();
		Iterator<Profile> itr = c.iterator();
		for(int i = 0; i < profileChooser.getCells().get(0).size();i++){
			String temp = itr.next().getProfileName();
			profileChooser.getCells().get(0).get(i).setCellText(temp);
			profileChooser.getCells().get(0).get(i).setActionCommand(temp.toLowerCase());
		}
		profileChooser.addBoxListener(this);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		mainHeader.drawCell();
		if(pState == ProfileState.Show){
			profileInfos.render();
			profileAchievements.render();
			profileOptions.render();
		}else if(pState == ProfileState.Create){
			profileOptionHeader.drawCell();
			profileOptionText.drawCell();
			profileCreation.render(container, g);
		}else if(pState == ProfileState.Delete){
			profileOptionHeader.drawCell();
			profileOptionText.drawCell();
			profileDeleter.render();
		}else if(pState == ProfileState.Load){
			profileOptionHeader.drawCell(); //TODO: Add loading functionality
			profileOptionText.drawCell();
			profileChooser.render();
		}else if(pState == ProfileState.None){
			
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if(pState == ProfileState.Show){
			profileOptions.update();
		}else if(pState == ProfileState.Create){
			if(overwriteCheck && saveTimer > -1){
				if(saveTimer <= 2000){
					saveTimer += delta;
				}else if(saveTimer >= 2000){
					saveTimer = -1;
					profileCreation.setFocus(false);
					pState = ProfileState.Show;	
					
				}
			}
		}else if(pState == ProfileState.Delete){
			profileDeleter.update();
			if(saveTimer > -1){
				if(saveTimer <= 2000){
					saveTimer += delta;
				}else if(saveTimer >= 2000){
					saveTimer = -1;
					if(!Pong.S_profiles.isEmpty()){
						profileOptionHeader.setCellText("Load Profile");
						profileOptionText.setCellText("Please choose a profile and continue with ENTER.");
						pState = ProfileState.Load;
					}else{
						profileOptionHeader.setCellText("Create new Profile");
						profileOptionText.setCellText("You deleted your last profile.\nPlease enter a profilename\nand press ENTER to confirm");
						profileCreation.setFocus(true);
						pState = ProfileState.Create;
					}					
				}
			}
		}else if(pState == ProfileState.Load){
			profileChooser.update();
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
		}else if(pState == ProfileState.Delete){
			if(key == Input.KEY_RIGHT){
				if(profileDeleter.getBoxKeyX() < profileDeleter.getBoxWidth()){
					profileDeleter.setBoxKeyX(profileDeleter.getBoxKeyX() + 1);
				}
			}else if(key == Input.KEY_LEFT){
				if(profileDeleter.getBoxKeyX() > 1){
					profileDeleter.setBoxKeyX(profileDeleter.getBoxKeyX() - 1);
				}
			}
		}else if(pState == ProfileState.Load){

		}else if(pState == ProfileState.None){
			
		}
	}

	@Override
	public void componentActivated(AbstractComponent source) {
		if(pState == ProfileState.Show){
			if(source.getActionCommand().equals(PROFILE_OPTIONS[0])){
				pState = ProfileState.Create;
				profileCreation.setFocus(true);
			}else if(source.getActionCommand().equals(PROFILE_OPTIONS[1])){
				pState = ProfileState.Delete;
				profileOptionHeader.setCellText("Delete Profile");
				profileOptionText.setCellText("Do you really wanna delete your profile?");
			}else if(source.getActionCommand().equals(PROFILE_OPTIONS[2])){
				
			}else if(source.getActionCommand().equals(PROFILE_OPTIONS[3])){
				game.enterState(MainMenu.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
			}
		}else if(pState == ProfileState.Create){
			if(source.getActionCommand().equals("enter")){
				if(saveTimer == -1){
					if(overwriteCheck == true){
						LinkedHashMap<String, String> temp = new LinkedHashMap<>(Pong.S_statisticsData.size());
						for(int i = 0;i < Pong.S_statisticsData.size();i++){
							temp.put(Pong.STATISTICS_KEYS[i],Integer.toString(Pong.S_statisticsData.get(Pong.STATISTICS_KEYS[i])));
						}
						saveProfile = new Profile(Pong.PROFILE_PATH, profileCreation.getText(), temp, Pong.S_achievementData);
					}
					try {
						if(overwriteCheck){
							overwriteCheck = saveProfile.createProfile(false);
						}else{
							overwriteCheck = saveProfile.createProfile(true);
						}
						if(!overwriteCheck){
							profileOptionText.setCellText("The profile already exists, do you wanna overwrite it?\n Press ENTER to continue, ESCAPE to abort");
							return;
						}else{
							saveTimer = 0;
							Pong.S_profiles.put(saveProfile.getProfileName().toLowerCase(), saveProfile);
							profileOptionText.setCellText("Profile successfully created");
							Pong.S_activeProfile = saveProfile.getProfileName().toLowerCase();
							profileInfos.setHeaderTitle("Profile:" + " " + saveProfile.getProfileName());
							LinkedHashMap<String, String> options = new LinkedHashMap<>();
							options.put("resX", Integer.toString(Pong.S_resX));
							options.put("resY", Integer.toString(Pong.S_resY));
							options.put("volume", Float.toString((int)(Pong.S_container.getMusicVolume()*100)/100.0f));
							options.put("vol_on", Boolean.toString(Pong.S_container.isMusicOn()));
							options.put("debug", Boolean.toString(Pong.S_debug));
							options.put("show_fps", Boolean.toString(Pong.S_showFPS));
							options.put("lastActiveProfile", Pong.S_activeProfile);
							MainMenu.ch.setOptions(options);
							MainMenu.ch.createConfigFile();
							profileOptions.setBoxKeyCoordinates(new int[] {PROFILE_OPTIONS.length,1});
							profileCreation.setText("");
							Collection<Profile> c = Pong.S_profiles.values();
							Iterator<Profile> itr = c.iterator();
							try {
								profileChooser.setBoxHeight(Pong.S_profiles.size());
							} catch (SlickException e) {
								e.printStackTrace(); //TODO: Add catch
							}
							for(int i = 0; i < profileChooser.getCells().get(0).size();i++){
								String temp = itr.next().getProfileName();
								profileChooser.getCells().get(0).get(i).setCellText(temp);
								profileChooser.getCells().get(0).get(i).setActionCommand(temp.toLowerCase());
							}
							return;
						}
					} catch (JAXBException e) {
						JOptionPane.showMessageDialog(null,e.toString() + "An error occured during profilecreation!"); //TODO: Find better way
					}
				}
			}
		}else if(pState == ProfileState.Delete){
			if(saveTimer == -1){
				if(source.getActionCommand().equals(PROFILE_OPTIONS[1])){
					Pong.S_profiles.get(Pong.S_activeProfile).delete();
					Pong.S_profiles.remove(Pong.S_activeProfile);
					Collection<Profile> c = Pong.S_profiles.values();
					Iterator<Profile> itr = c.iterator();
					try {
						profileChooser.setBoxHeight(Pong.S_profiles.size());
					} catch (SlickException e) {
						e.printStackTrace(); //TODO: Add catch
					}
					for(int i = 0; i < profileChooser.getCells().get(0).size();i++){
						String temp = itr.next().getProfileName();
						profileChooser.getCells().get(0).get(i).setCellText(temp);
						profileChooser.getCells().get(0).get(i).setActionCommand(temp.toLowerCase());
					}
					saveTimer = 0;
					profileOptionText.setCellText("Profile successfully deleted");
					return;
				}else if(source.getActionCommand().equals(PROFILE_OPTIONS[3])){
					profileOptionHeader.setCellText("Create new Profile");
					profileOptionText.setCellText("Please enter a profilename\nand press ENTER to confirm");
					pState = ProfileState.Show;
					return;
				}
			}
		}else if(pState == ProfileState.Load){

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