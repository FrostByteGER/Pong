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
	private final String[] MENU_OPTIONS_GRAPHICS = {"Resolution: ","Volume: ","Music","DEBUG MODE","Save","Back"};
	private final String[] MENU_OPTIONS_CONTROLS = {"PLACEHOLDER","PLACEHOLDER","PLACEHOLDER","PLACEHOLDER","Save","Back"};
	private final String[] MENU_OPTIONS_NETWORK  = {"IP","Port","PLACEHOLDER","Save","Back"};
	
	private final String[] COMMANDS_MENU_OPTIONS_MAIN     = {"audiovisual","controls","network","return"};
	private final String[] COMMANDS_MENU_OPTIONS_GRAPHICS = {"resolution","","volume","debug","save","return"};
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
	private Box optionBoxControls;
	private Box optionBoxNetwork;
	private Cell mainHeader;
	private Cell optionHeader;
	private Cell graphicsHeader;
	private Cell controlHeader;
	private Cell netWorkHeader;
	
	
		
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

		optionBoxGraphics = new Box(2, MENU_OPTIONS_GRAPHICS.length, OFFSET_X, Pong.S_resY/2 - 75, Pong.FONT, 30, 200, 40, container);
		optionBoxGraphics.setAllAutoAdjust(false);
		optionBoxGraphics.setBoxLeft();
		optionBoxGraphics.setEdged(false);
		optionBoxGraphics.setKeyInput(true);
		optionBoxGraphics.setFocus(true);
		optionBoxGraphics.setBoxKeyCoordinates(new int[] {1,1});
		optionBoxGraphics.setAllCellTitles(MENU_OPTIONS_GRAPHICS);
		optionBoxGraphics.getCells().get(1).get(0).setCellText(RESOLUTIONS[resolutionselection][0] + "x" + RESOLUTIONS[resolutionselection][1]);
		optionBoxGraphics.setAllActionCommands(COMMANDS_MENU_OPTIONS_GRAPHICS);
		optionBoxGraphics.addBoxListener(this);
		optionBoxGraphics.setAutoAdjustBox(true);
		


		
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
		}else if(ostate == OptionState.Controls){
			
		}else if(ostate == OptionState.Network){
			
		}

		/*
		FontHelper.bigfont.drawString(Pong.S_resX/2 - FontHelper.bigfont.getWidth(PONG)/2, 20 + FontHelper.bigfont.getHeight(PONG), PONG, Color.white);	
		
		FontHelper.mediumfont.drawString(OFFSET_X, Pong.S_resY/2 - 30, OPTIONS, Color.cyan);	

		else if(ostate == OptionState.Graphics){
			FontHelper.normalfont.drawString(OFFSET_X, Pong.S_resY/2, MENU_OPTIONS_GRAPHICS[0],optionArray.get(0));
			FontHelper.normalfont.drawString(OFFSET_X + FontHelper.normalfont.getWidth(MENU_OPTIONS_GRAPHICS[0]), Pong.S_resY/2, RESOLUTIONS[resolutionselection][0] + "x" + RESOLUTIONS[resolutionselection][1], optionArray.get(0));
			
			FontHelper.normalfont.drawString(OFFSET_X, Pong.S_resY/2 + 20, MENU_OPTIONS_GRAPHICS[1],optionArray.get(1));
			FontHelper.normalfont.drawString(OFFSET_X + FontHelper.normalfont.getWidth(MENU_OPTIONS_GRAPHICS[1]), Pong.S_resY/2 + 20, (Integer.toString((int)(container.getMusicVolume()*100.0f))), optionArray.get(1));
			
			FontHelper.normalfont.drawString(OFFSET_X, Pong.S_resY/2 + 40, MENU_OPTIONS_GRAPHICS[2],optionArray.get(2));
			if(container.isMusicOn() == true){
				FontHelper.normalfont.drawString(OFFSET_X + FontHelper.normalfont.getWidth(MENU_OPTIONS_GRAPHICS[2]) + OFFSET_SPACE, Pong.S_resY/2 + 40, "on", optionArray.get(2));
			}else{
				FontHelper.normalfont.drawString(OFFSET_X + FontHelper.normalfont.getWidth(MENU_OPTIONS_GRAPHICS[2]) + OFFSET_SPACE, Pong.S_resY/2 + 40, "off", optionArray.get(2));	
			}
			if(Pong.S_debug == true){
				FontHelper.normalfont.drawString(OFFSET_X, Pong.S_resY/2 + 60, MENU_OPTIONS_GRAPHICS[3],optionArray.get(5));
				FontHelper.normalfont.drawString(OFFSET_X + FontHelper.normalfont.getWidth(MENU_OPTIONS_GRAPHICS[3]) + OFFSET_SPACE, Pong.S_resY/2 + 60, Boolean.toString(Game.S_Debug_AI),optionArray.get(5));
			}
			
			FontHelper.normalfont.drawString(OFFSET_X, Pong.S_resY/2 + 90, MENU_OPTIONS_GRAPHICS[4],optionArray.get(3));
			FontHelper.normalfont.drawString(OFFSET_X + FontHelper.normalfont.getWidth(MENU_OPTIONS_GRAPHICS[4]) + 40, Pong.S_resY/2 + 90, MENU_OPTIONS_GRAPHICS[5],optionArray.get(4));
		
		} else if(ostate == OptionState.Controls){
			for(int i = 0,localoffset = 0; i < 4; i++){
				FontHelper.normalfont.drawString(OFFSET_X, Pong.S_resY/2 + localoffset, MENU_OPTIONS_CONTROLS[i],optionArray.get(i));
				localoffset += 20;
			}
			FontHelper.normalfont.drawString(OFFSET_X, Pong.S_resY/2 + 90, MENU_OPTIONS_CONTROLS[4],optionArray.get(4));
			FontHelper.normalfont.drawString(OFFSET_X + FontHelper.normalfont.getWidth(MENU_OPTIONS_CONTROLS[4]) + 40, Pong.S_resY/2 + 90, MENU_OPTIONS_CONTROLS[5],optionArray.get(5));
		
		} else if(ostate == OptionState.Network){
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
		}else if(ostate == OptionState.Controls){
			
		}else if(ostate == OptionState.Network){
			
		}

		/*
		System.out.println(networkconfigselection);

		//Timer for savemessage
		if(savebool == true){
			savetimer += delta;
		}else{
			savetimer = 0;
			
		}
		*/
	}
	
	public void keyPressed(int key, char c) {
		if(ostate == OptionState.Main){
			if(key == Input.KEY_UP && optionBoxMain.getBoxKeyY() > 1){
				optionBoxMain.setBoxKeyY(optionBoxMain.getBoxKeyY() - 1);
			}else if(key == Input.KEY_DOWN && optionBoxMain.getBoxKeyY() < optionBoxMain.getBoxHeight()){
				optionBoxMain.setBoxKeyY(optionBoxMain.getBoxKeyY() + 1);
			}else if(key == Input.KEY_RIGHT && optionBoxMain.getBoxKeyX() < optionBoxMain.getBoxWidth()){
				optionBoxMain.setBoxKeyX(optionBoxMain.getBoxKeyX() + 1);
			}else if(key == Input.KEY_LEFT && optionBoxMain.getBoxKeyX() > 1){
				optionBoxMain.setBoxKeyX(optionBoxMain.getBoxKeyX() - 1);
			}
		}else if(ostate == OptionState.Graphics){
			if(key == Input.KEY_UP && optionBoxGraphics.getBoxKeyY() > 1){
				optionBoxGraphics.setBoxKeyY(optionBoxGraphics.getBoxKeyY() - 1);
			}else if(key == Input.KEY_DOWN && optionBoxGraphics.getBoxKeyY() < optionBoxGraphics.getBoxHeight()){
				optionBoxGraphics.setBoxKeyY(optionBoxGraphics.getBoxKeyY() + 1);
			}else if(key == Input.KEY_RIGHT && optionBoxGraphics.getBoxKeyX() < optionBoxGraphics.getBoxWidth()){
				optionBoxGraphics.setBoxKeyX(optionBoxGraphics.getBoxKeyX() + 1);
			}else if(key == Input.KEY_LEFT && optionBoxGraphics.getBoxKeyX() > 1){
				optionBoxGraphics.setBoxKeyX(optionBoxGraphics.getBoxKeyX() - 1);
			}
		}else if(ostate == OptionState.Controls){
		}else if(ostate == OptionState.Network){
		}
	}
	

	/**
	 * 
	 * @param game
	 */
	/*
	private void mainHelper(int key){
		if(key == Input.KEY_UP && mainconfigselection > 0){
			mainconfigselection -= 1;
		}else if(key == Input.KEY_DOWN && mainconfigselection < MENU_OPTIONS_MAIN.length - 1){
			mainconfigselection += 1;
		}
		if(mainconfigselection == 0){
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(0, Color.white);
			if(key == Input.KEY_ENTER){
				for(int e = 0;e < optionArray.size();e++){
					optionArray.set(e, Color.gray);
				}
				ostate = OptionState.Graphics;
				mainconfigselection = 0;
				return;
			}
		}else if(mainconfigselection == 1){
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(1, Color.white);
			if(key == Input.KEY_ENTER){
				for(int e = 0;e < optionArray.size();e++){
					optionArray.set(e, Color.gray);
				}
				ostate = OptionState.Controls;
				mainconfigselection = 0;
				return;
			}
		}else if(mainconfigselection == 2){
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(2, Color.white);
			if(key == Input.KEY_ENTER){
				for(int e = 0;e < optionArray.size();e++){
					optionArray.set(e, Color.gray);
				}
				ostate = OptionState.Network;
				mainconfigselection = 0;
				return;
			}
		}else if(mainconfigselection == 3){
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(3, Color.white);
			if(key == Input.KEY_ENTER){
				ostate = OptionState.Main;
				mainconfigselection = 0;
				game.enterState(MainMenu.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
				return;
			}
		}

	}
	
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
	
	private void graphicsHelper(int key){
		if(key == Input.KEY_UP && graphicsconfigselection > 0){
			if(graphicsconfigselection == 4 && Pong.S_debug == false){
				graphicsconfigselection -= 2;
			}else{
				graphicsconfigselection -= 1;
			}
		}else if(key == Input.KEY_DOWN && graphicsconfigselection < MENU_OPTIONS_GRAPHICS.length - 1){
			if(graphicsconfigselection == 2 && Pong.S_debug == false){
				graphicsconfigselection += 2;
			}else{
				graphicsconfigselection += 1;
			}
		}

	
		if(graphicsconfigselection == 0){
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(0, Color.white);
			if(key == Input.KEY_LEFT && resolutionselection > 0){
				resolutionselection -= 1;
			}
			if(key == Input.KEY_RIGHT && resolutionselection < RESOLUTIONS.length - 1){
				resolutionselection += 1;
			}
		
		}else if(graphicsconfigselection == 1){
				if(key == Input.KEY_LEFT && Pong.S_container.getMusicVolume() > 0){
					if(press <= 4 && presstimer == 35){
						Pong.S_container.setMusicVolume(Pong.S_container.getMusicVolume()-0.01f);
						presstimer = 0;
						press += 1;
					}else if(press > 4 && presstimer == 10){
						Pong.S_container.setMusicVolume(Pong.S_container.getMusicVolume()-0.01f);
						presstimer = 0;
					}else{
						presstimer += 5;
					}
					
				}else if(key == Input.KEY_RIGHT && Pong.S_container.getMusicVolume() < 100){
				
					if(press <= 4 && presstimer == 35){
						Pong.S_container.setMusicVolume(Pong.S_container.getMusicVolume()+0.01f);
						presstimer = 0;
						press += 1;
					}else if(press > 4 && presstimer == 10){
						Pong.S_container.setMusicVolume(Pong.S_container.getMusicVolume()+0.01f);
						presstimer = 0;
					}else{
						presstimer += 5;
					}
					
				}else{
					press = 0;
				}
				
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(1, Color.white);
		
		}else if(graphicsconfigselection == 2){
			if(key == Input.KEY_RIGHT && Pong.S_container.isMusicOn() == true){
				Pong.S_container.setMusicOn(false);
			}
			if(key == Input.KEY_LEFT && Pong.S_container.isMusicOn() == false){
				Pong.S_container.setMusicOn(true);
			}
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(2, Color.white);
		
		}else if(graphicsconfigselection == 3){
			if(key == Input.KEY_RIGHT && Game.S_Debug_AI == true ||  Game.S_Debug_AI == true &&  key == Input.KEY_ENTER){
				Game.S_Debug_AI = false;
			}
			if(key == Input.KEY_LEFT && Game.S_Debug_AI == false ||  Game.S_Debug_AI == false && key == Input.KEY_ENTER){
				Game.S_Debug_AI = true;
			}
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(5, Color.white);
		
		}else if(graphicsconfigselection == 4){
			if(Pong.S_debug == false){
				for(int e = 0;e < optionArray.size();e++){
					optionArray.set(e, Color.gray);
				}
				optionArray.set(2, Color.white);
			}else{
				for(int e = 0;e < optionArray.size();e++){
					optionArray.set(e, Color.gray);
				}
				optionArray.set(5, Color.white);
			}
			
			if(key == Input.KEY_ENTER){
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
			}
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(3, Color.white);
			
		}else if(graphicsconfigselection == 5){
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(4, Color.white);
			if(key == Input.KEY_ENTER){
				ostate = OptionState.Main;	
				graphicsconfigselection = 0;
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
			
		}else if(ostate == OptionState.Controls){
			
		}else if(ostate == OptionState.Network){
			
		}

		
	}

}
