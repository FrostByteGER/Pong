/**
 * 
 */
package de.frostbyteger.pong.core;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.frostbyteger.pong.engine.FontHelper;
import de.frostbyteger.pong.start.Pong;

/**
 * @author Kevin
 * TODO: Optionsmenüs unterteilen in: Grafik/Sound, Controls, Network
 */
public class Options extends BasicGameState {

	protected final static int ID = 002;
	
	private final String[] MENU_OPTIONS_ARRAY = {"Resolution: ","Volume: ","Volume","DEBUG MODE","Save","Exit"};
	
	private static final int[][] RES_ARRAY = {{640,480},{800,600},{1024,768},{1280,960},{1280,1024}};
	private static final int OFFSET_X = 100;
	private static final int OFFSET_SPACE = 20;

	private int configselection = 0;
	private int resolutionselection = 0;
	private int presstimer = 0;
	private int press = 0;
	private int savetimer = 0;
	
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
		FontHelper.bigfont.drawString(Pong.S_resX/2 - FontHelper.bigfont.getWidth("Pong")/2, 20 + FontHelper.bigfont.getHeight("Pong"), "Pong", Color.white);	
		
		FontHelper.normalfont.drawString(OFFSET_X, Pong.S_resY/2, MENU_OPTIONS_ARRAY[0],optionArray.get(0));
		FontHelper.normalfont.drawString(OFFSET_X + FontHelper.normalfont.getWidth(MENU_OPTIONS_ARRAY[0]), Pong.S_resY/2, RES_ARRAY[resolutionselection][0] + "x" + RES_ARRAY[resolutionselection][1], optionArray.get(0));
		
		FontHelper.normalfont.drawString(OFFSET_X, Pong.S_resY/2 + 20, MENU_OPTIONS_ARRAY[1],optionArray.get(1));
		FontHelper.normalfont.drawString(OFFSET_X + FontHelper.normalfont.getWidth(MENU_OPTIONS_ARRAY[1]), Pong.S_resY/2 + 20, (Integer.toString((int)(container.getMusicVolume()*100.0f))), optionArray.get(1));
		
		FontHelper.normalfont.drawString(OFFSET_X, Pong.S_resY/2 + 40, MENU_OPTIONS_ARRAY[2],optionArray.get(2));
		if(container.isMusicOn() == true){
			FontHelper.normalfont.drawString(OFFSET_X + FontHelper.normalfont.getWidth(MENU_OPTIONS_ARRAY[2]) + OFFSET_SPACE, Pong.S_resY/2 + 40, "on", optionArray.get(2));
		}else{
			FontHelper.normalfont.drawString(OFFSET_X + FontHelper.normalfont.getWidth(MENU_OPTIONS_ARRAY[2]) + OFFSET_SPACE, Pong.S_resY/2 + 40, "off", optionArray.get(2));	
		}
		if(Pong.S_Debug == true){
			FontHelper.normalfont.drawString(OFFSET_X, Pong.S_resY/2 + 60, MENU_OPTIONS_ARRAY[3],optionArray.get(5));
			FontHelper.normalfont.drawString(OFFSET_X + FontHelper.normalfont.getWidth(MENU_OPTIONS_ARRAY[3]) + OFFSET_SPACE, Pong.S_resY/2 + 60, Boolean.toString(Game.S_Debug_AI),optionArray.get(5));
		}
		
		FontHelper.normalfont.drawString(OFFSET_X, Pong.S_resY/2 + 90, MENU_OPTIONS_ARRAY[4],optionArray.get(3));
		FontHelper.normalfont.drawString(OFFSET_X + FontHelper.normalfont.getWidth(MENU_OPTIONS_ARRAY[4]) + 40, Pong.S_resY/2 + 90, MENU_OPTIONS_ARRAY[5],optionArray.get(4));

		if(savebool == true && savetimer <= 2000){
				FontHelper.normalfont.drawString(Pong.S_resX/2 - FontHelper.normalfont.getWidth("Options saved!")/2,Pong.S_resY/2 + Pong.S_resY/4 + FontHelper.normalfont.getHeight("Options saved!"),"Options saved!");		
		}else{
			savebool = false;
		}

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		//Timer for savemessage
		if(savebool == true){
			savetimer += delta;
		}else{
			savetimer = 0;
		}
		
		if(input.isKeyPressed(Input.KEY_UP) && configselection > 0){
			if(configselection == 4 && Pong.S_Debug == false){
				configselection -= 2;
			}else{
				configselection -= 1;
			}
		}else if(input.isKeyPressed(Input.KEY_DOWN) && configselection < MENU_OPTIONS_ARRAY.length){
			if(configselection == 2 && Pong.S_Debug == false){
				configselection += 2;
			}else{
				configselection += 1;
			}
		}
		
		if(configselection == 0){
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
		
		}else if(configselection == 1){
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
		
		}else if(configselection == 2){
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
		
		}else if(configselection == 3){
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
		
		}else if(configselection == 4){
			if(Pong.S_Debug == false){
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
					Pong.S_Prophelper.saveProperty("resX", Integer.toString(Pong.S_resX));
					Pong.S_Prophelper.saveProperty("resY", Integer.toString(Pong.S_resY));
					Pong.S_Prophelper.saveProperty("volume", Float.toString((int)(container.getMusicVolume()*100)/100.0f));
					Pong.S_Prophelper.saveProperty("vol_on", Boolean.toString(container.isMusicOn()));
					Pong.S_Prophelper.savePropertiesFile();
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
		}else if(configselection == 5){
			if(input.isKeyPressed(Input.KEY_ENTER)){
				//currentmenustate = MenuState.Main;	
			}
			for(int e = 0;e < optionArray.size();e++){
				optionArray.set(e, Color.gray);
			}
			optionArray.set(4, Color.white);
		}
	}
	

	@Override
	public int getID() {
		return ID;
	}

}
