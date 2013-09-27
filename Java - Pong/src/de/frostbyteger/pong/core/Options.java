/**
 * 
 */
package de.frostbyteger.pong.core;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import de.frostbyteger.pong.engine.OptionState;
import de.frostbyteger.pong.engine.graphics.FontHelper;
import de.frostbyteger.pong.engine.graphics.ui.gui.AbstractComponent;
import de.frostbyteger.pong.engine.graphics.ui.gui.Box;
import de.frostbyteger.pong.engine.graphics.ui.gui.Cell;
import de.frostbyteger.pong.engine.graphics.ui.gui.ComponentListener;
import de.frostbyteger.pong.start.Pong;

/**
 * @author Kevin
 * TODO: Implement save function for submenus
 */
public class Options extends BasicGameState implements ComponentListener{

	protected final static int ID = 002;
	
	private StateBasedGame game;

	private final String[] MENU_OPTIONS_MAIN     = {"Graphics/Sound","Controls","Network","Back"};	
	private final String[] MENU_OPTIONS_GRAPHICS = {"Resolution: ","Volume: ","Music:","Save","Back"};
	private final String[] MENU_OPTIONS_CONTROLS = {"PLACEHOLDER","PLACEHOLDER","PLACEHOLDER","PLACEHOLDER","Save","Back"};
	private final String[] MENU_OPTIONS_NETWORK  = {"IP","Port","PLACEHOLDER","Save","Back"};
	
	private final String[] COMMANDS_MENU_OPTIONS_MAIN     = {"audiovisual","controls","network","return"};
	private final String[] COMMANDS_MENU_OPTIONS_GRAPHICS = {"resolution","volume","sound","save","return"};
	private final String[] COMMANDS_MENU_OPTIONS_CONTROLS = {"PLACEHOLDER","PLACEHOLDER","PLACEHOLDER","PLACEHOLDER","save","return"};
	private final String[] COMMANDS_MENU_OPTIONS_NETWORK  = {"ip","port","PLACEHOLDER","save","return"};
	
	private final String PONG    = "Pong";
	private final String OPTIONS = "Options";
	
	private static final int[][] RESOLUTIONS = {{640,480},{800,600},{1024,768},{1280,960},{1280,1024}};
	private static final int OFFSET_X      = 100;
	private static final int OFFSET_SPACE  = 20;

	private int mainconfigselection     = 0;
	private int graphicsconfigselection = 0;
	private int controlconfigselection  = 0;
	private int networkconfigselection  = 0;
	private int resolutionselection     = 0;
	private int presstimer              = 0;
	private int press                   = 0;
	private int savetimer               = 0;
	
	private OptionState ostate = OptionState.Main;
	
	private boolean savebool = false;
	
	private Box optionBoxMain;
	private Box optionBoxGraphics;
	private Box optionBoxVariables;
	private Box optionBoxControls;
	private Box optionBoxNetwork;
	private Box optionBoxSaveReturn;
	private Cell mainHeader;
	private Cell optionHeader;
	private Cell graphicsHeader;
	private Cell controlHeader;
	private Cell networkHeader;
	private Cell saveCell;
	
	
		
	/**
	 * 
	 */
	public Options() {
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.game = game;
		
		//Ensures that the displayed resolution in the optionsmenu equals the actual gamewindow resolution when
		//starting the game
		for(int i = 0; i < RESOLUTIONS.length;i++){
			if(Pong.S_resX == RESOLUTIONS[i][0]){
				resolutionselection = i;
			}
		} 
		
		// Global Header
		mainHeader = new Cell(Pong.FONT, 160, Pong.S_resX/2 - 350/2, 20, 350, 250, container);
		mainHeader.setAutoAdjust(false);
		mainHeader.setCellText(PONG);
		mainHeader.setClickable(false);
		
		// Local Headers
		optionHeader = new Cell(Pong.FONT, 60, OFFSET_X, Pong.S_resY/2 - 100, 250, 100, container);
		optionHeader.setLeft();
		optionHeader.setCellText(OPTIONS);
		optionHeader.setFontColor(Color.cyan);
		optionHeader.setClickable(false);
		
		graphicsHeader = new Cell("data/alexis.ttf", 60, OFFSET_X, Pong.S_resY/2 - 100, 250, 100, container);
		graphicsHeader.setAutoAdjust(false);
		graphicsHeader.setLeft();
		graphicsHeader.setCellText(MENU_OPTIONS_MAIN[0]);
		graphicsHeader.setFontColor(Color.cyan);
		graphicsHeader.setClickable(false);
		
		controlHeader = new Cell("data/alexis.ttf", 60, OFFSET_X, Pong.S_resY/2 - 100, 250, 100, container);
		controlHeader.setAutoAdjust(false);
		controlHeader.setLeft();
		controlHeader.setCellText(MENU_OPTIONS_MAIN[1]);
		controlHeader.setFontColor(Color.cyan);
		controlHeader.setClickable(false);
		
		networkHeader = new Cell("data/alexis.ttf", 60, OFFSET_X, Pong.S_resY/2 - 100, 250, 100, container);
		networkHeader.setAutoAdjust(false);
		networkHeader.setLeft();
		networkHeader.setCellText(MENU_OPTIONS_MAIN[1]);
		networkHeader.setFontColor(Color.cyan);
		networkHeader.setClickable(false);
		
		saveCell = new Cell("data/alexis.ttf", 30, OFFSET_X, Pong.S_resY/2 + 125, 100, 30, container);
		saveCell.setAutoAdjust(false);
		saveCell.setLeft();
		saveCell.setCellText("Options saved!");
		saveCell.setFontColor(Color.green);
		saveCell.setClickable(false);
		
		optionBoxSaveReturn = new Box(2, 1, OFFSET_X, Pong.S_resY/2 + 50, Pong.FONT, 30, 100, 30, container);
		optionBoxSaveReturn.setAllAutoAdjust(false);
		optionBoxSaveReturn.setBoxLeft();
		optionBoxSaveReturn.setEdged(false);
		optionBoxSaveReturn.setKeyInput(true);
		optionBoxSaveReturn.setFocus(false);
		optionBoxSaveReturn.setBoxKeyCoordinates(new int[] {1,1});
		optionBoxSaveReturn.getCells().get(0).get(0).setCellText(MENU_OPTIONS_GRAPHICS[3]);
		optionBoxSaveReturn.getCells().get(1).get(0).setCellText(MENU_OPTIONS_GRAPHICS[4]);
		optionBoxSaveReturn.getCells().get(0).get(0).setActionCommand(COMMANDS_MENU_OPTIONS_GRAPHICS[3]);
		optionBoxSaveReturn.getCells().get(1).get(0).setActionCommand(COMMANDS_MENU_OPTIONS_GRAPHICS[4]);
		optionBoxSaveReturn.addBoxListener(this);
		
		// Options
		optionBoxMain = new Box(1, MENU_OPTIONS_MAIN.length, OFFSET_X, Pong.S_resY/2 - 75, Pong.FONT, 40, 200, 50, container);
		optionBoxMain.setAllAutoAdjust(false);
		optionBoxMain.setBoxLeft();
		optionBoxMain.setEdged(false);
		optionBoxMain.setKeyInput(true);
		optionBoxMain.setFocus(true);
		optionBoxMain.setBoxKeyCoordinates(new int[] {1,1});
		optionBoxMain.setAllCellTitles(MENU_OPTIONS_MAIN);
		optionBoxMain.setAllActionCommands(COMMANDS_MENU_OPTIONS_MAIN);
		optionBoxMain.addBoxListener(this);
		optionBoxMain.setAutoAdjustBox(true);

		optionBoxGraphics = new Box(1, MENU_OPTIONS_GRAPHICS.length - 2, OFFSET_X, Pong.S_resY/2 - 75, Pong.FONT, 30, 200, 40, container);
		optionBoxGraphics.setAllAutoAdjust(false);
		optionBoxGraphics.setBoxLeft();
		optionBoxGraphics.setEdged(false);
		optionBoxGraphics.setKeyInput(true);
		optionBoxGraphics.setFocus(true);
		optionBoxGraphics.setBoxKeyCoordinates(new int[] {1,1});
		for(int i = 0; i < MENU_OPTIONS_GRAPHICS.length - 2;i++){
			optionBoxGraphics.getCells().get(0).get(i).setCellText(MENU_OPTIONS_GRAPHICS[i]);
			optionBoxGraphics.getCells().get(0).get(i).setActionCommand(COMMANDS_MENU_OPTIONS_GRAPHICS[i]);
		}
		optionBoxGraphics.addBoxListener(this);
		optionBoxGraphics.setAutoAdjustBox(true);
		
		optionBoxVariables = new Box(1, MENU_OPTIONS_GRAPHICS.length - 2, OFFSET_X * 3 + 10, Pong.S_resY/2 - 75, Pong.FONT, 30, 200, 40, container);
		optionBoxVariables.setAllAutoAdjust(false);
		optionBoxVariables.setBoxLeft();
		optionBoxVariables.setEdged(false);
		optionBoxVariables.setKeyInput(true);
		optionBoxVariables.setFocus(false);
		optionBoxVariables.setBoxKeyCoordinates(new int[] {1,1});
		optionBoxVariables.getCells().get(0).get(0).setCellText(RESOLUTIONS[resolutionselection][0] + "x" + RESOLUTIONS[resolutionselection][1]);
		optionBoxVariables.getCells().get(0).get(1).setCellText((Integer.toString((int)(container.getMusicVolume()*100.0f))));
		if(container.isMusicOn()){
			optionBoxVariables.getCells().get(0).get(2).setCellText("on");
		}else{
			optionBoxVariables.getCells().get(0).get(2).setCellText("off");
		}
		optionBoxVariables.setAutoAdjustBox(true);
		
		optionBoxControls = new Box(4, MENU_OPTIONS_CONTROLS.length - 2, OFFSET_X, Pong.S_resY/2 - 75, Pong.FONT, 30, 200, 40, container);
		optionBoxControls.setAllAutoAdjust(false);
		optionBoxControls.setBoxLeft();
		optionBoxControls.setEdged(true);
		optionBoxControls.setKeyInput(true);
		optionBoxControls.setFocus(true);
		optionBoxControls.setBoxKeyCoordinates(new int[] {1,1});
		for(int i = 0; i < MENU_OPTIONS_CONTROLS.length - 2;i++){
			optionBoxControls.getCells().get(0).get(i).setCellText(MENU_OPTIONS_CONTROLS[i]);
			optionBoxControls.getCells().get(0).get(i).setActionCommand(COMMANDS_MENU_OPTIONS_CONTROLS[i]);
		}
		optionBoxControls.addBoxListener(this);
		optionBoxControls.setAutoAdjustBox(true);



		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		mainHeader.drawCell();
		if(ostate == OptionState.Main){
			optionHeader.drawCell();
			optionBoxMain.render();
		}else if(ostate == OptionState.Graphics){
			graphicsHeader.drawCell();
			optionBoxGraphics.render();
			optionBoxVariables.render();
			optionBoxSaveReturn.render();
			if(savebool == true && savetimer <= 2000){
				saveCell.drawCell();
			}else{
				savebool = false;
			}
		}else if(ostate == OptionState.Controls){
			controlHeader.drawCell();
			optionBoxControls.render();
			optionBoxSaveReturn.render();
		}else if(ostate == OptionState.Network){
			
		}

		/*else if(ostate == OptionState.Network){
			for(int i = 0,localoffset = 0; i < 3; i++){
				FontHelper.normalfont.drawString(OFFSET_X, Pong.S_resY/2 + localoffset, MENU_OPTIONS_NETWORK[i],optionArray.get(i));
				localoffset += 20;
			}
			FontHelper.normalfont.drawString(OFFSET_X, Pong.S_resY/2 + 70, MENU_OPTIONS_NETWORK[3],optionArray.get(3));
			FontHelper.normalfont.drawString(OFFSET_X + FontHelper.normalfont.getWidth(MENU_OPTIONS_NETWORK[3]) + 40, Pong.S_resY/2 + 70, MENU_OPTIONS_NETWORK[4],optionArray.get(4));
		}
		
		if(savebool == true && savetimer <= 2000){
				FontHelper.normalfont.drawString(Pong.S_resX/2 - FontHelper.normalfont.getWidth("Options saved!")/2,Pong.S_resY/2 + Pong.S_resY/4 + FontHelper.normalfont.getHeight("Options saved!"),"Options saved!");		
		}else{
			savebool = false;
		}
	*/
	}
	
	/**
	 * 
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if(ostate == OptionState.Main){
			optionBoxMain.update();	
		}else if(ostate == OptionState.Graphics){
			optionBoxGraphics.update();
			optionBoxSaveReturn.update();
			optionBoxVariables.update();
			optionBoxVariables.getCells().get(0).get(0).setCellText(RESOLUTIONS[resolutionselection][0] + "x" + RESOLUTIONS[resolutionselection][1]);
			optionBoxVariables.getCells().get(0).get(1).setCellText((Integer.toString((int)(container.getMusicVolume()*100.0f))));
			if(container.isMusicOn()){
				optionBoxVariables.getCells().get(0).get(2).setCellText("on");
			}else{
				optionBoxVariables.getCells().get(0).get(2).setCellText("off");
			}
		}else if(ostate == OptionState.Controls){
			optionBoxControls.update();
			optionBoxSaveReturn.update();
		}else if(ostate == OptionState.Network){
			
		}
		//Timer for savemessage
		if(savebool == true){
			savetimer += delta;
		}else{
			savetimer = 0;
			
		}
	}
	
	
	public void keyPressed(int key, char c) {
		if(ostate == OptionState.Main){
			if(key == Input.KEY_UP && optionBoxMain.getBoxKeyY() > 1){
				optionBoxMain.setBoxKeyY(optionBoxMain.getBoxKeyY() - 1);
			}else if(key == Input.KEY_DOWN && optionBoxMain.getBoxKeyY() < optionBoxMain.getBoxHeight()){
				optionBoxMain.setBoxKeyY(optionBoxMain.getBoxKeyY() + 1);
			}
		}else if(ostate == OptionState.Graphics){
			if(key == Input.KEY_UP){
				if((optionBoxGraphics.getBoxKeyY() > 1) && optionBoxGraphics.isFocused()){
					optionBoxGraphics.setBoxKeyY(optionBoxGraphics.getBoxKeyY() - 1);
				}else if((optionBoxSaveReturn.getBoxKeyY() == optionBoxSaveReturn.getBoxHeight()) && optionBoxSaveReturn.isFocused()){
					optionBoxGraphics.setFocus(true);
					optionBoxGraphics.setBoxKeyY(optionBoxGraphics.getBoxHeight());
					optionBoxSaveReturn.setFocus(false);
				}

			}else if(key == Input.KEY_DOWN){
				if(optionBoxGraphics.getBoxKeyY() < optionBoxGraphics.getBoxHeight()){
					optionBoxGraphics.setBoxKeyY(optionBoxGraphics.getBoxKeyY() + 1);
				}else if(optionBoxGraphics.getBoxKeyY() == optionBoxGraphics.getBoxHeight()){
					optionBoxGraphics.setFocus(false);
					optionBoxGraphics.setBoxKeyY(optionBoxGraphics.getBoxHeight());
					optionBoxSaveReturn.setFocus(true);
				}

			}else if(key == Input.KEY_RIGHT){
				if(optionBoxGraphics.getCellFocus(0, 0)){
					if(resolutionselection < RESOLUTIONS.length - 1){
						resolutionselection += 1;
					}
				}else if(optionBoxGraphics.getCellFocus(0, 1)){
					if(Pong.S_container.getMusicVolume() < 100){
							Pong.S_container.setMusicVolume(Pong.S_container.getMusicVolume()+0.01f);
					}
				}else if(optionBoxGraphics.getCellFocus(0, 2)){
					if(Pong.S_container.isMusicOn()){
						Pong.S_container.setMusicOn(false);
					}			
				}else if((optionBoxSaveReturn.getBoxKeyX() < optionBoxSaveReturn.getBoxWidth()) && optionBoxSaveReturn.isFocused()){
					optionBoxSaveReturn.setBoxKeyX(optionBoxSaveReturn.getBoxKeyX() + 1);
				}
			}else if(key == Input.KEY_LEFT){
				if(optionBoxGraphics.getCellFocus(0, 0)){
					if(resolutionselection > 0){
						resolutionselection -= 1;
					}
				}else if(optionBoxGraphics.getCellFocus(0, 1)){
					if(Pong.S_container.getMusicVolume() > 0){
						Pong.S_container.setMusicVolume(Pong.S_container.getMusicVolume()-0.01f);
					}
				}else if(optionBoxGraphics.getCellFocus(0, 2)){
					if(!Pong.S_container.isMusicOn()){
						Pong.S_container.setMusicOn(true);
					}
				}else if((optionBoxSaveReturn.getBoxKeyX() > 1) && optionBoxSaveReturn.isFocused()){
					optionBoxSaveReturn.setBoxKeyX(optionBoxSaveReturn.getBoxKeyX() - 1);
				}
			}
		}else if(ostate == OptionState.Controls){
			if(key == Input.KEY_UP){
				if((optionBoxControls.getBoxKeyY() > 1) && optionBoxControls.isFocused()){
					optionBoxControls.setBoxKeyY(optionBoxControls.getBoxKeyY() - 1);
				}else if((optionBoxSaveReturn.getBoxKeyY() == optionBoxSaveReturn.getBoxHeight()) && optionBoxSaveReturn.isFocused()){
					optionBoxControls.setFocus(true);
					optionBoxControls.setBoxKeyY(optionBoxControls.getBoxHeight());
					optionBoxSaveReturn.setFocus(false);
				}
			}else if(key == Input.KEY_DOWN){
				if(optionBoxControls.getBoxKeyY() < optionBoxControls.getBoxHeight()){
					optionBoxControls.setBoxKeyY(optionBoxControls.getBoxKeyY() + 1);
				}else if(optionBoxControls.getBoxKeyY() == optionBoxControls.getBoxHeight()){
					optionBoxControls.setFocus(false);
					optionBoxControls.setBoxKeyY(optionBoxControls.getBoxHeight());
					optionBoxSaveReturn.setFocus(true);
				}
			}else if(key == Input.KEY_RIGHT){
				if(optionBoxControls.getBoxKeyX() < optionBoxControls.getBoxWidth()){
					optionBoxControls.setBoxKeyX(optionBoxControls.getBoxKeyX() + 1);
				}
			}else if(key == Input.KEY_LEFT){
				if(optionBoxControls.getBoxKeyX() > 1){
					optionBoxControls.setBoxKeyX(optionBoxControls.getBoxKeyX() - 1);
				}
			}
		}else if(ostate == OptionState.Network){
		}
	}
	
	/*
	private void controlHelper(int key){
		if(key == Input.KEY_UP && controlconfigselection > 0){
			controlconfigselection -= 1;
		}else if(key == Input.KEY_DOWN && controlconfigselection < MENU_OPTIONS_CONTROLS.length - 1){
			controlconfigselection += 1;
		}
		if(controlconfigselection == 0){
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(0, Color.white);
			if(key == Input.KEY_ENTER){
			}
		}else if(controlconfigselection == 1){
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(1, Color.white);
			if(key == Input.KEY_ENTER){
			}
		}else if(controlconfigselection == 2){
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(2, Color.white);
			if(key == Input.KEY_ENTER){
			}
		}else if(controlconfigselection == 3){
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(3, Color.white);
			if(key == Input.KEY_ENTER){
			}
		}else if(controlconfigselection == 4){
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(4, Color.white);
			if(key == Input.KEY_ENTER){
			}
		}else if(controlconfigselection == 5){
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(5, Color.white);
			if(key == Input.KEY_ENTER){
				ostate = OptionState.Main;	
				controlconfigselection = 0;
				return;
			}
		}
	}
	
	private void networkHelper(int key){
		if(key == Input.KEY_UP && networkconfigselection > 0){
			networkconfigselection -= 1;
		}else if(key == Input.KEY_DOWN && networkconfigselection < MENU_OPTIONS_NETWORK.length - 1){
			networkconfigselection += 1;
		}
		if(networkconfigselection == 0){
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(0, Color.white);
			if(key == Input.KEY_ENTER){
			}
		}else if(networkconfigselection == 1){
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(1, Color.white);
			if(key == Input.KEY_ENTER){
			}
		}else if(networkconfigselection == 2){
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(2, Color.white);
			if(key == Input.KEY_ENTER){
			}
		}else if(networkconfigselection == 3){
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(3, Color.white);
			if(key == Input.KEY_ENTER){
			}
		}else if(networkconfigselection == 4){
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(4, Color.white);
			if(key == Input.KEY_ENTER){
				ostate = OptionState.Main;	
				networkconfigselection = 0;
				return;
			}
		}
	}
	*/
	
	/**
	 * 
	 */
	@Override
	public int getID() {
		return ID;
	}
	
	@Override
	public void componentActivated(AbstractComponent source) {
		if(ostate == OptionState.Main){
			if(source.getActionCommand().equals(COMMANDS_MENU_OPTIONS_MAIN[0])){
				optionBoxMain.setBoxKeyCoordinates(new int[] {1,1});
				ostate = OptionState.Graphics;
			}else if(source.getActionCommand().equals(COMMANDS_MENU_OPTIONS_MAIN[1])){
				optionBoxMain.setBoxKeyCoordinates(new int[] {1,1});
				optionBoxSaveReturn.setBoxY(Pong.S_resY/2 + 150);
				ostate = OptionState.Controls;
			}else if(source.getActionCommand().equals(COMMANDS_MENU_OPTIONS_MAIN[2])){
				optionBoxMain.setBoxKeyCoordinates(new int[] {1,1});
				ostate = OptionState.Network;
			}else if(source.getActionCommand().equals(COMMANDS_MENU_OPTIONS_MAIN[3])){
				optionBoxMain.setBoxKeyCoordinates(new int[] {1,1});
				ostate = OptionState.Main;
				game.enterState(MainMenu.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
			}
		}else if(ostate == OptionState.Graphics){
			if(source.getActionCommand().equals(COMMANDS_MENU_OPTIONS_GRAPHICS[2])){
				if(!Pong.S_container.isMusicOn()){
					Pong.S_container.setMusicOn(true);
				}else if(Pong.S_container.isMusicOn()){
					Pong.S_container.setMusicOn(false);
				}
			}else if(source.getActionCommand().equals(COMMANDS_MENU_OPTIONS_GRAPHICS[3])){
				try{
					Pong.S_resX = RESOLUTIONS[resolutionselection][0];
					Pong.S_resY = RESOLUTIONS[resolutionselection][1];
					LinkedHashMap<String, String> options = new LinkedHashMap<>();
					options.put("resX", Integer.toString(Pong.S_resX));
					options.put("resY", Integer.toString(Pong.S_resY));
					options.put("volume", Float.toString((int)(Pong.S_container.getMusicVolume()*100)/100.0f));
					options.put("vol_on", Boolean.toString(Pong.S_container.isMusicOn()));
					options.put("debug", Boolean.toString(Pong.S_debug));
					options.put("show_fps", Boolean.toString(Pong.S_showFPS));
					MainMenu.ch.setOptions(options);
					MainMenu.ch.createConfigFile();
					savebool = true;
				}catch(NumberFormatException nfe){
					nfe.printStackTrace();
				}
				try {
					Pong.S_container.setDisplayMode(Pong.S_resX, Pong.S_resY, false);
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}else if(source.getActionCommand().equals(COMMANDS_MENU_OPTIONS_GRAPHICS[4])){
				optionBoxGraphics.setFocus(true);
				optionBoxGraphics.setBoxKeyCoordinates(new int[] {1,1});
				optionBoxSaveReturn.setFocus(false);
				optionBoxSaveReturn.setBoxKeyCoordinates(new int[] {1,1});
				ostate = OptionState.Main;
			}

		}else if(ostate == OptionState.Controls){
			
		}else if(ostate == OptionState.Network){
			
		}

		
	}

}
