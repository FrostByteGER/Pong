/**
 * 
 */
package de.frostbyteger.pong.core;

import java.util.ArrayList;
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
import de.frostbyteger.pong.engine.io.ProfileHelper;
import de.frostbyteger.pong.start.Pong;

/**
 * @author Kevin
 *
 */
public class Profiles extends BasicGameState implements ComponentListener{
	
	public static final int ID = 004;
	
	private StateBasedGame game;
	
	private final String[] PROFILE_OPTIONS           = {"new","delete","change\nprofile","back"};
	private final String[] PROFILE_DESC_ACHIEVEMENTS = {"PLACEHOLDER","PLACEHOLDER","PLACEHOLDER","PLACEHOLDER","PLACEHOLDER","PLACEHOLDER"};
	private final String[] PROFILE_DESC_DATA         = {"Time played:","Time played in CPU-Mode:","Time played in LAN-Mode:",
														"Time played in Challenge-Mode:","Matches played:","Matches played in CPU-Mode:",
														"Matches played in LAN-Mode:","Matches played in Challenge-Mode:","Matches won:",
														"Matches won in CPU-Mode:","Matches won in LAN-Mode:",
														"Matches won in Challenge-Mode:"};
	private final int OFFSET_X = 25;
	
	private ProfileState pState = ProfileState.Show;
	
	private Profile saveProfile = null;
		
	private TextField profileCreation;
	
	private Box profileInfos;
	private Box profileAchievements;
	private Box profileChooser;
	private Box profileDeleter;
	private Box profileOptions;
	
	private Cell profileOptionHeader;
	private Cell profileOptionText;
	
	private boolean overwriteCheck = true;
	private boolean exists = false;
	private int saveTimer = -1;
	private int profileCount;

	/**
	 * 
	 */
	public Profiles() {
	}


	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.game = game;
		
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
		if(Pong.S_profiles.size() != 0 && !Pong.S_profileNotFound){
			profileInfos.setHeaderTitle("Profile:" + " " + Pong.S_profiles.get(Pong.S_activeProfile).getProfileName());
		}
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
		if(Pong.S_statisticsData.size() != 0){
			for(int i = 0;i < profileInfos.getCells().get(1).size();i++){
				profileInfos.getCells().get(1).get(i).setCellText(Integer.toString(Pong.S_statisticsData.get(Pong.KEYS_STATISTICS[i])));
			}
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
		if(Pong.S_profiles.size() == 1){
			profileOptions.getCells().get(2).get(0).setClickable(false);
			profileOptions.getCells().get(2).get(0).setFontColor(Color.darkGray);
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
		
		if(Pong.S_profiles.size() >= 1){
			profileCount = Pong.S_profiles.size();
		}
		if(profileCount >= 1){
			profileChooser = new Box(1, profileCount, Pong.S_resX/2 - 100, 320, Pong.FONT, 40, 200, 50, container);
		}else{
			profileChooser = new Box(1, 1, Pong.S_resX/2 - 100, 320, Pong.FONT, 40, 200, 50, container);
		}
		profileChooser.setAllAutoAdjust(true);
		profileChooser.setEdged(false);
		profileChooser.setKeyInput(true);
		profileChooser.setFocus(true);
		profileChooser.setBoxKeyCoordinates(new int[] {1,1});
		profileChooser.addBoxListener(this);
		
		if(Pong.S_firstStart){
			profileOptionHeader.setCellText("Create Profile");
			profileOptionText.setCellText("Please enter a profilename\nand press ENTER to confirm");
			pState = ProfileState.Create;
			profileCreation.setFocus(true);
		}else if(Pong.S_profileNotFound){
			Object[] profiles = Pong.S_profiles.values().toArray();
			ArrayList<Profile> profiles2 = new ArrayList<>(profileCount);
			for(int j = 0;j < profiles.length;j++){
				if(!((ProfileHelper) profiles[j]).getProfileName().toLowerCase().equals(Pong.S_activeProfile)){
					profiles2.add((Profile) profiles[j]);				
				}
			}
			for(int i = 0; i < profileChooser.getCells().get(0).size();i++){
				String temp = profiles2.get(i).getProfileName();
				profileChooser.getCells().get(0).get(i).setCellText(temp);
				profileChooser.getCells().get(0).get(i).setActionCommand(temp.toLowerCase());				
			}
			profileOptionHeader.setCellText("Load Profile");
			profileOptionText.setCellText("Please choose a profile and continue with ENTER.");
			profileChooser.setBoxKeyCoordinates(new int[] {1,1});
			pState = ProfileState.Load;
			profileChooser.setKeyInput(true);
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		MainMenu.mainHeader.drawCell();
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
			profileOptionHeader.drawCell();
			profileOptionText.drawCell();
			profileChooser.render();
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if(profileCount > 1 && !profileOptions.getCells().get(2).get(0).isClickable()){
			profileOptions.getCells().get(2).get(0).setClickable(true);
			profileOptions.getCells().get(2).get(0).setFontColor(Color.white);
		}else if(profileCount == 1 && profileOptions.getCells().get(2).get(0).isClickable()){
			profileOptions.getCells().get(2).get(0).setClickable(false);
			profileOptions.getCells().get(2).get(0).setFontColor(Color.darkGray);
		}
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
						if(profileCount > 1){
							profileChooser.setBoxHeight(profileCount - 1);
						}else{
							profileChooser.setBoxHeight(1);
						}
						Object[] profiles = Pong.S_profiles.values().toArray();
						ArrayList<Profile> profiles2 = new ArrayList<>(profileCount);
						for(int j = 0;j < profiles.length;j++){
							profiles2.add((Profile) profiles[j]);				
						}
						for(int i = 0; i < profileChooser.getCells().get(0).size();i++){
							String temp = profiles2.get(i).getProfileName();
							profileChooser.getCells().get(0).get(i).setCellText(temp);
							profileChooser.getCells().get(0).get(i).setActionCommand(temp.toLowerCase());				
						}
						profileChooser.setBoxKeyCoordinates(new int[] {1,1});

						profileChooser.setKeyInput(true);
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
			if(saveTimer > -1){
				if(saveTimer <= 2000){
					saveTimer += delta;
				}else if(saveTimer >= 2000){
					saveTimer = -1;			
					profileOptionHeader.setCellText("Load Profile");
					profileOptionText.setCellText("Please choose a profile and continue with ENTER.");
					pState = ProfileState.Show;	
					
				}
			}
		}
	}
	
	public void keyPressed(int key, char c) {
		if(pState == ProfileState.Show){
		if(key == Input.KEY_RIGHT){
			if(profileOptions.getBoxKeyX() < profileOptions.getBoxWidth()){
				if(profileOptions.getBoxKeyX() == 2 && !profileOptions.getCells().get(2).get(0).isClickable()){
					profileOptions.setBoxKeyX(profileOptions.getBoxKeyX() + 2);	
				}else{
					profileOptions.setBoxKeyX(profileOptions.getBoxKeyX() + 1);				
				}

			}
		}else if(key == Input.KEY_LEFT){
			if(profileOptions.getBoxKeyX() > 1){
				if(profileOptions.getBoxKeyX() == profileOptions.getBoxWidth() && !profileOptions.getCells().get(2).get(0).isClickable()){
					profileOptions.setBoxKeyX(profileOptions.getBoxKeyX() - 2);
				}else{
					profileOptions.setBoxKeyX(profileOptions.getBoxKeyX() - 1);					
				}

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
			if(key == Input.KEY_DOWN){
				if(profileChooser.getBoxKeyY() < profileChooser.getBoxHeight()){
					profileChooser.setBoxKeyY(profileChooser.getBoxKeyY() + 1);
				}
			}else if(key == Input.KEY_UP){
				if(profileChooser.getBoxKeyY() > 1){
					profileChooser.setBoxKeyY(profileChooser.getBoxKeyY() - 1);
				}
			}
		}
	}

	@Override
	public void componentActivated(AbstractComponent source) {
		if(pState == ProfileState.Show){
			if(source.getActionCommand().equals(PROFILE_OPTIONS[0])){
				pState = ProfileState.Create;
				profileOptionHeader.setCellText("Create new Profile");
				profileOptionText.setCellText("Please enter a profilename\nand press ENTER to confirm");
				profileCreation.setFocus(true);
			}else if(source.getActionCommand().equals(PROFILE_OPTIONS[1])){
				pState = ProfileState.Delete;
				profileDeleter.setKeyInput(true);
				profileOptionHeader.setCellText("Delete Profile");
				profileOptionText.setCellText("Do you really wanna delete your profile?");
			}else if(source.getActionCommand().equals(PROFILE_OPTIONS[2])){
				Object[] profiles = Pong.S_profiles.values().toArray();
				ArrayList<Profile> profiles2 = new ArrayList<>(profileCount);
				for(int j = 0;j < profiles.length;j++){
					if(!((ProfileHelper) profiles[j]).getProfileName().toLowerCase().equals(Pong.S_activeProfile)){
						profiles2.add((Profile) profiles[j]);				
					}
				}
				for(int i = 0; i < profileChooser.getCells().get(0).size();i++){
					String temp = profiles2.get(i).getProfileName();
					profileChooser.getCells().get(0).get(i).setCellText(temp);
					profileChooser.getCells().get(0).get(i).setActionCommand(temp.toLowerCase());				
				}
				profileOptionHeader.setCellText("Load Profile");
				profileOptionText.setCellText("Please choose a profile and continue with ENTER.");
				profileChooser.setBoxKeyCoordinates(new int[] {1,1});
				

				pState = ProfileState.Load;
				profileChooser.setKeyInput(true);
			}else if(source.getActionCommand().equals(PROFILE_OPTIONS[3])){
				game.enterState(MainMenu.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
			}
		}else if(pState == ProfileState.Create){
			if(source.getActionCommand().equals("enter")){
				if(saveTimer == -1){
					if(Pong.S_firstStart){
						Pong.S_firstStart = false;
					}
					if(overwriteCheck == true){
						LinkedHashMap<String, String> temp = new LinkedHashMap<>(Pong.S_statisticsData.size());
						for(int i = 0;i < Pong.S_statisticsData.size();i++){
							temp.put(Pong.KEYS_STATISTICS[i],Integer.toString(Pong.S_statisticsData.get(Pong.KEYS_STATISTICS[i])));
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
							exists = true;
							return;
						}else{
							saveTimer = 0;
							if(!exists){
								Pong.S_profiles.put(saveProfile.getProfileName().toLowerCase(), saveProfile);
								profileCount += 1;
								if(profileCount > 1){
									profileChooser.setBoxHeight(profileCount - 1);
								}else{
									profileChooser.setBoxHeight(1);
								}
								exists = false;
							}
							profileOptionText.setCellText("Profile successfully created");
							Pong.S_activeProfile = saveProfile.getProfileName().toLowerCase();
							profileInfos.setHeaderTitle("Profile:" + " " + saveProfile.getProfileName());
							LinkedHashMap<String, String> options = new LinkedHashMap<>();
							options.put("resX", Integer.toString(Pong.S_resX));
							options.put("resY", Integer.toString(Pong.S_resY));
							options.put("volume", Float.toString((int)(Pong.S_container.getMusicVolume()*100)/100.0f));
							options.put("vol_on", Boolean.toString(Pong.S_container.isMusicOn()));
							options.put("show_fps", Boolean.toString(Pong.S_showFPS));
							options.put("lastActiveProfile", Pong.S_activeProfile);
							MainMenu.ch.setOptions(options);
							MainMenu.ch.createConfigFile();
							profileOptions.setBoxKeyCoordinates(new int[] {PROFILE_OPTIONS.length,1});
							profileCreation.setFocus(false);
							profileCreation.setText("");
							return;
						}
					} catch (JAXBException | SlickException e) {
						JOptionPane.showMessageDialog(null,e.toString() + "An error occured during profilecreation!"); //TODO: Find better way
					}
				}
			}
		}else if(pState == ProfileState.Delete){
			if(saveTimer == -1){
				if(source.getActionCommand().equals(PROFILE_OPTIONS[1])){
					Pong.S_profiles.get(Pong.S_activeProfile).delete();
					Pong.S_profiles.remove(Pong.S_activeProfile);
					profileCount -= 1;
					saveTimer = 0;
					profileOptionText.setCellText("Profile successfully deleted");
				}else if(source.getActionCommand().equals(PROFILE_OPTIONS[3])){
					profileOptionHeader.setCellText("Create new Profile");
					profileOptionText.setCellText("Please enter a profilename\nand press ENTER to confirm");
					pState = ProfileState.Show;
				}
				profileOptions.setBoxKeyCoordinates(new int[] {PROFILE_OPTIONS.length,1});
				profileDeleter.setKeyInput(false);
				return;
			}
		}else if(pState == ProfileState.Load){
			if(saveTimer == -1){
				if(Pong.S_profileNotFound){
					Pong.S_profileNotFound = false;
				}
				Pong.S_activeProfile = source.getActionCommand();
				Profile active = Pong.S_profiles.get(Pong.S_activeProfile);
				profileInfos.setHeaderTitle("Profile:" + " " + Pong.S_profiles.get(source.getActionCommand()).getProfileName());
				profileOptionText.setCellText("Profile successfully loaded");
				for(int i = 0;i < active.getProfileData().size();i++){
					Pong.S_statisticsData.put(Pong.KEYS_STATISTICS[i], Integer.parseInt(active.getProfileData().get(Pong.KEYS_STATISTICS[i])));
				}
				/* TODO: Implement when achievements are made
				for(int i = 0;i < active.getProfileAchievements().size();i++){
					Pong.S_achievementData.put(Pong.KEYS_ACHIEVEMENTS[i], active.getProfileAchievements().get(Pong.KEYS_ACHIEVEMENTS[i]));
				}*/
				LinkedHashMap<String, String> options = new LinkedHashMap<>();
				options.put("resX", Integer.toString(Pong.S_resX));
				options.put("resY", Integer.toString(Pong.S_resY));
				options.put("volume", Float.toString((int)(Pong.S_container.getMusicVolume()*100)/100.0f));
				options.put("vol_on", Boolean.toString(Pong.S_container.isMusicOn()));
				options.put("show_fps", Boolean.toString(Pong.S_showFPS));
				options.put("lastActiveProfile", Pong.S_activeProfile);
				MainMenu.ch.setOptions(options);
				try {
					MainMenu.ch.createConfigFile();
				} catch (JAXBException e) {
					JOptionPane.showMessageDialog(null,e.toString() + "An error occured during profileloading!"); //TODO: Find better way
					Pong.S_container.exit();
				}
				profileChooser.setKeyInput(false);
				saveTimer = 0;
				profileOptions.setBoxKeyCoordinates(new int[] {PROFILE_OPTIONS.length,1});
				return;
			}
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
