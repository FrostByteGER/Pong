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

import de.frostbyteger.pong.engine.FontHelper;
import de.frostbyteger.pong.engine.OptionState;
import de.frostbyteger.pong.start.Pong;

/**
 * @author Kevin
 * TODO: Implement save function for submenus
 */
public class Options extends BasicGameState {

	protected final static int ID = 002;

	private final String[] MENU_OPTIONS_MAIN_ARRAY     = {"Graphics/Sound","Controls","Network","Back"};	
	private final String[] MENU_OPTIONS_GRAPHICS_ARRAY = {"Resolution: ","Volume: ","Volume","DEBUG MODE","Save","Back"};
	private final String[] MENU_OPTIONS_CONTROLS_ARRAY = {"PLACEHOLDER","PLACEHOLDER","PLACEHOLDER","PLACEHOLDER","Save","Back"};
	private final String[] MENU_OPTIONS_NETWORK_ARRAY  = {"IP","Port","PLACEHOLDER","Save","Back"};
	
	private final String PONG    = "Pong";
	private final String OPTIONS = "Options";
	
	private static final int[][] RES_ARRAY = {{640,480},{800,600},{1024,768},{1280,960},{1280,1024}};
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
	
	private ArrayList<Color> optionArray = new ArrayList<Color>();
	
	private Input input;

	/**
	 * 
	 */
	public Options() {
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		//Ensures that the displayed resolution in the optionsmenu equals the actual gamewindow resolution when
		//starting the game
		for(int i = 0; i < RES_ARRAY.length;i++){
			if(Pong.S_resX == RES_ARRAY[i][0]){
				resolutionselection = i;
			}
		}
		for(int e = 0;e <= 13;e++){
			optionArray.add(Color.gray);
		}
		
		input = container.getInput();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		FontHelper.bigfont.drawString(Pong.S_resX/2 - FontHelper.bigfont.getWidth(PONG)/2, 20 + FontHelper.bigfont.getHeight(PONG), PONG, Color.white);	
		
		FontHelper.mediumfont.drawString(OFFSET_X, Pong.S_resY/2 - 30, OPTIONS, Color.cyan);	

		if(ostate == OptionState.Main){
			for(int i = 0,localoffset = 0; i < 4; i++){
				if(localoffset == 60){
					localoffset += 10;
				}
				FontHelper.normalfont.drawString(OFFSET_X, Pong.S_resY/2 + localoffset, MENU_OPTIONS_MAIN_ARRAY[i],optionArray.get(i));
				localoffset += 20;
			}
			
		} else if(ostate == OptionState.Graphics){
			FontHelper.normalfont.drawString(OFFSET_X, Pong.S_resY/2, MENU_OPTIONS_GRAPHICS_ARRAY[0],optionArray.get(0));
			FontHelper.normalfont.drawString(OFFSET_X + FontHelper.normalfont.getWidth(MENU_OPTIONS_GRAPHICS_ARRAY[0]), Pong.S_resY/2, RES_ARRAY[resolutionselection][0] + "x" + RES_ARRAY[resolutionselection][1], optionArray.get(0));
			
			FontHelper.normalfont.drawString(OFFSET_X, Pong.S_resY/2 + 20, MENU_OPTIONS_GRAPHICS_ARRAY[1],optionArray.get(1));
			FontHelper.normalfont.drawString(OFFSET_X + FontHelper.normalfont.getWidth(MENU_OPTIONS_GRAPHICS_ARRAY[1]), Pong.S_resY/2 + 20, (Integer.toString((int)(container.getMusicVolume()*100.0f))), optionArray.get(1));
			
			FontHelper.normalfont.drawString(OFFSET_X, Pong.S_resY/2 + 40, MENU_OPTIONS_GRAPHICS_ARRAY[2],optionArray.get(2));
			if(container.isMusicOn() == true){
				FontHelper.normalfont.drawString(OFFSET_X + FontHelper.normalfont.getWidth(MENU_OPTIONS_GRAPHICS_ARRAY[2]) + OFFSET_SPACE, Pong.S_resY/2 + 40, "on", optionArray.get(2));
			}else{
				FontHelper.normalfont.drawString(OFFSET_X + FontHelper.normalfont.getWidth(MENU_OPTIONS_GRAPHICS_ARRAY[2]) + OFFSET_SPACE, Pong.S_resY/2 + 40, "off", optionArray.get(2));	
			}
			if(Pong.S_debug == true){
				FontHelper.normalfont.drawString(OFFSET_X, Pong.S_resY/2 + 60, MENU_OPTIONS_GRAPHICS_ARRAY[3],optionArray.get(5));
				FontHelper.normalfont.drawString(OFFSET_X + FontHelper.normalfont.getWidth(MENU_OPTIONS_GRAPHICS_ARRAY[3]) + OFFSET_SPACE, Pong.S_resY/2 + 60, Boolean.toString(Game.S_Debug_AI),optionArray.get(5));
			}
			
			FontHelper.normalfont.drawString(OFFSET_X, Pong.S_resY/2 + 90, MENU_OPTIONS_GRAPHICS_ARRAY[4],optionArray.get(3));
			FontHelper.normalfont.drawString(OFFSET_X + FontHelper.normalfont.getWidth(MENU_OPTIONS_GRAPHICS_ARRAY[4]) + 40, Pong.S_resY/2 + 90, MENU_OPTIONS_GRAPHICS_ARRAY[5],optionArray.get(4));
		
		} else if(ostate == OptionState.Controls){
			for(int i = 0,localoffset = 0; i < 4; i++){
				FontHelper.normalfont.drawString(OFFSET_X, Pong.S_resY/2 + localoffset, MENU_OPTIONS_CONTROLS_ARRAY[i],optionArray.get(i));
				localoffset += 20;
			}
			FontHelper.normalfont.drawString(OFFSET_X, Pong.S_resY/2 + 90, MENU_OPTIONS_CONTROLS_ARRAY[4],optionArray.get(4));
			FontHelper.normalfont.drawString(OFFSET_X + FontHelper.normalfont.getWidth(MENU_OPTIONS_CONTROLS_ARRAY[4]) + 40, Pong.S_resY/2 + 90, MENU_OPTIONS_CONTROLS_ARRAY[5],optionArray.get(5));
		
		} else if(ostate == OptionState.Network){
			for(int i = 0,localoffset = 0; i < 3; i++){
				FontHelper.normalfont.drawString(OFFSET_X, Pong.S_resY/2 + localoffset, MENU_OPTIONS_NETWORK_ARRAY[i],optionArray.get(i));
				localoffset += 20;
			}
			FontHelper.normalfont.drawString(OFFSET_X, Pong.S_resY/2 + 70, MENU_OPTIONS_NETWORK_ARRAY[3],optionArray.get(3));
			FontHelper.normalfont.drawString(OFFSET_X + FontHelper.normalfont.getWidth(MENU_OPTIONS_NETWORK_ARRAY[3]) + 40, Pong.S_resY/2 + 70, MENU_OPTIONS_NETWORK_ARRAY[4],optionArray.get(4));
		}
		
		if(savebool == true && savetimer <= 2000){
				FontHelper.normalfont.drawString(Pong.S_resX/2 - FontHelper.normalfont.getWidth("Options saved!")/2,Pong.S_resY/2 + Pong.S_resY/4 + FontHelper.normalfont.getHeight("Options saved!"),"Options saved!");		
		}else{
			savebool = false;
		}

	}
	
	/**
	 * 
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		//Timer for savemessage
		if(savebool == true){
			savetimer += delta;
		}else{
			savetimer = 0;
			
		}
		
		if(ostate == OptionState.Main){
			mainHelper(game);
		}else if(ostate == OptionState.Graphics){
			graphicsHelper(container);
		}else if(ostate == OptionState.Controls){
			controlHelper();
		}else if(ostate == OptionState.Network){
			networkHelper();
		}
	}
	
	/**
	 * 
	 * @param game
	 */
	private void mainHelper(StateBasedGame game){
		if(mainconfigselection == 0){
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(0, Color.white);
			if(input.isKeyPressed(Input.KEY_ENTER)){
				ostate = OptionState.Graphics;
				mainconfigselection = 0;
				return;
			}
		}else if(mainconfigselection == 1){
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(1, Color.white);
			if(input.isKeyPressed(Input.KEY_ENTER)){
				ostate = OptionState.Controls;
				mainconfigselection = 0;
				return;
			}
		}else if(mainconfigselection == 2){
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(2, Color.white);
			if(input.isKeyPressed(Input.KEY_ENTER)){
				ostate = OptionState.Network;
				mainconfigselection = 0;
				return;
			}
		}else if(mainconfigselection == 3){
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(3, Color.white);
			if(input.isKeyPressed(Input.KEY_ENTER)){
				ostate = OptionState.Main;
				mainconfigselection = 0;
				game.enterState(MainMenu.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
				return;
			}
		}
			
		if(input.isKeyPressed(Input.KEY_UP) && mainconfigselection > 0){
			mainconfigselection -= 1;
		}else if(input.isKeyPressed(Input.KEY_DOWN) && mainconfigselection < MENU_OPTIONS_MAIN_ARRAY.length - 1){
			mainconfigselection += 1;
		}

	}
	
	/**
	 * 
	 */
	private void controlHelper(){
		if(controlconfigselection == 0){
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(0, Color.white);
			if(input.isKeyPressed(Input.KEY_ENTER)){
			}
		}else if(controlconfigselection == 1){
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(1, Color.white);
			if(input.isKeyPressed(Input.KEY_ENTER)){
			}
		}else if(controlconfigselection == 2){
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(2, Color.white);
			if(input.isKeyPressed(Input.KEY_ENTER)){
			}
		}else if(controlconfigselection == 3){
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(3, Color.white);
			if(input.isKeyPressed(Input.KEY_ENTER)){
			}
		}else if(controlconfigselection == 4){
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(4, Color.white);
			if(input.isKeyPressed(Input.KEY_ENTER)){
			}
		}else if(controlconfigselection == 5){
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(5, Color.white);
			if(input.isKeyPressed(Input.KEY_ENTER)){
				ostate = OptionState.Main;	
				controlconfigselection = 0;
				return;
			}
		}
			
		if(input.isKeyPressed(Input.KEY_UP) && controlconfigselection > 0){
			controlconfigselection -= 1;
		}else if(input.isKeyPressed(Input.KEY_DOWN) && controlconfigselection < MENU_OPTIONS_CONTROLS_ARRAY.length - 1){
			controlconfigselection += 1;
		}
	}
	
	/**
	 * 
	 */
	private void networkHelper(){
		if(networkconfigselection == 0){
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(0, Color.white);
			if(input.isKeyPressed(Input.KEY_ENTER)){
			}
		}else if(networkconfigselection == 1){
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(1, Color.white);
			if(input.isKeyPressed(Input.KEY_ENTER)){
			}
		}else if(networkconfigselection == 2){
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(2, Color.white);
			if(input.isKeyPressed(Input.KEY_ENTER)){
			}
		}else if(networkconfigselection == 3){
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(3, Color.white);
			if(input.isKeyPressed(Input.KEY_ENTER)){
			}
		}else if(networkconfigselection == 4){
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(4, Color.white);
			if(input.isKeyPressed(Input.KEY_ENTER)){
				ostate = OptionState.Main;	
				networkconfigselection = 0;
				return;
			}
		}
			
		if(input.isKeyPressed(Input.KEY_UP) && networkconfigselection > 0){
			networkconfigselection -= 1;
		}else if(input.isKeyPressed(Input.KEY_DOWN) && networkconfigselection < MENU_OPTIONS_NETWORK_ARRAY.length - 1){
			networkconfigselection += 1;
		}
	}
	
	/**
	 * 
	 * @param container
	 * @throws SlickException
	 */
	private void graphicsHelper(GameContainer container) throws SlickException{
		if(input.isKeyPressed(Input.KEY_UP) && graphicsconfigselection > 0){
			if(graphicsconfigselection == 4 && Pong.S_debug == false){
				graphicsconfigselection -= 2;
			}else{
				graphicsconfigselection -= 1;
			}
		}else if(input.isKeyPressed(Input.KEY_DOWN) && graphicsconfigselection < MENU_OPTIONS_GRAPHICS_ARRAY.length){
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
			if(input.isKeyPressed(Input.KEY_LEFT) && resolutionselection > 0){
				resolutionselection -= 1;
			}
			if(input.isKeyPressed(Input.KEY_RIGHT) && resolutionselection < RES_ARRAY.length-1){
				resolutionselection += 1;
			}
		
		}else if(graphicsconfigselection == 1){
				if(input.isKeyDown(Input.KEY_LEFT) && container.getMusicVolume() > 0){
					if(press <= 4 && presstimer == 35){
						container.setMusicVolume(container.getMusicVolume()-0.01f);
						presstimer = 0;
						press += 1;
					}else if(press > 4 && presstimer == 10){
						container.setMusicVolume(container.getMusicVolume()-0.01f);
						presstimer = 0;
					}else{
						presstimer += 5;
					}
					
				}else if(input.isKeyDown(Input.KEY_RIGHT) && container.getMusicVolume() < 100){
				
					if(press <= 4 && presstimer == 35){
						container.setMusicVolume(container.getMusicVolume()+0.01f);
						presstimer = 0;
						press += 1;
					}else if(press > 4 && presstimer == 10){
						System.out.println("TEST");
						container.setMusicVolume(container.getMusicVolume()+0.01f);
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
			if(input.isKeyPressed(Input.KEY_RIGHT) && container.isMusicOn() == true || container.isMusicOn() == true &&  input.isKeyPressed(Input.KEY_ENTER)){
				container.setMusicOn(false);
			}
			if(input.isKeyPressed(Input.KEY_LEFT) && container.isMusicOn() == false || container.isMusicOn() == false && input.isKeyPressed(Input.KEY_ENTER)){
				container.setMusicOn(true);
			}
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(2, Color.white);
		
		}else if(graphicsconfigselection == 3){
			if(input.isKeyPressed(Input.KEY_RIGHT) && Game.S_Debug_AI == true ||  Game.S_Debug_AI == true &&  input.isKeyPressed(Input.KEY_ENTER)){
				Game.S_Debug_AI = false;
			}
			if(input.isKeyPressed(Input.KEY_LEFT) && Game.S_Debug_AI == false ||  Game.S_Debug_AI == false && input.isKeyPressed(Input.KEY_ENTER)){
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
			
			if(input.isKeyPressed(Input.KEY_ENTER)){
				try{
					Pong.S_resX = RES_ARRAY[resolutionselection][0];
					Pong.S_resY = RES_ARRAY[resolutionselection][1];
					LinkedHashMap<String, String> options = new LinkedHashMap<>();
					options.put("resX", Integer.toString(Pong.S_resX));
					options.put("resY", Integer.toString(Pong.S_resY));
					options.put("volume", Float.toString((int)(container.getMusicVolume()*100)/100.0f));
					options.put("vol_on", Boolean.toString(container.isMusicOn()));
					MainMenu.ch.setOptions(options);
					MainMenu.ch.createConfigFile();
					savebool = true;
				}catch(NumberFormatException nfe){
					nfe.printStackTrace();
				}
				Pong.S_Container.setDisplayMode(Pong.S_resX, Pong.S_resY, false);
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
			if(input.isKeyPressed(Input.KEY_ENTER)){
				ostate = OptionState.Main;	
				graphicsconfigselection = 0;
				return;
			}
		}
	}
	
	/**
	 * 
	 */
	@Override
	public int getID() {
		return ID;
	}

}
