package de.frostbyteger.pong.start;

import java.io.FileNotFoundException;
import java.util.LinkedHashMap;

import javax.swing.JOptionPane;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.frostbyteger.pong.core.Game;
import de.frostbyteger.pong.core.Lan;
import de.frostbyteger.pong.core.MainMenu;
import de.frostbyteger.pong.core.Options;
import de.frostbyteger.pong.core.Profile;
import de.frostbyteger.pong.engine.io.ConfigHelper;
import de.frostbyteger.pong.engine.io.MD5Loader;

public class Pong extends StateBasedGame{
	
	// Options
	public static int S_resX        = 800;
	public static int S_resY        = 600;
	public static boolean S_debug   = true;
	public static boolean S_showFPS = false;
	public static final int FPS     = 60;
	
	// Version info
	public static final String TITLE          = "Pong";
	public static final String VERSION        = "v1.20";
	public static final String VERSION_STATUS = "INTERNAL";
	
	// MD5 checksums
	public static final String MD5_FONT  = "d060b8b0afa1753bf21d5fa3d3b14493";
	public static final String MD5_LEFT  = "42a88f1b4fa5de64c17bb8f8ca300234";
	public static final String MD5_RIGHT = "1c5d1ecec440191de3b71f080f93eb51";
	
	public static AppGameContainer S_container;
	public static ConfigHelper ch = new ConfigHelper("data//", "config",".xml");

	public Pong(String name) {
		super(name);
	}
	
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new MainMenu());
		addState(new Options());
		addState(new Lan());
		addState(new Game());
		addState(new Profile());
		
	}
	
	//TODO: Find better try/catch and if algorithm
	public int initGameSubRoutines(){
		MD5Loader md5 = new MD5Loader("data/Alexis.ttf");
		try{

			md5.createChecksum();
			if(md5.getChecksum().equals("d060b8b0afa1753bf21d5fa3d3b14493")){
				System.out.println("CHECK 1");
			}else{
				JOptionPane.showMessageDialog(null,"File" + " data/Alexis.ttf" + " is corrupt. \n\nGame exits!");
				return -1;
			}
			md5.setFilename("data/arrow_left.png");
			md5.createChecksum();
			if(md5.getChecksum().equals("42a88f1b4fa5de64c17bb8f8ca300234")){
				System.out.println("CHECK 2");
			}else{
				JOptionPane.showMessageDialog(null,"File" + " data/arrow_left.png" + " is corrupt. \n\nGame exits!");
				return -1;
			}
			md5.setFilename("data/arrow_right.png");
			md5.createChecksum();
			if(md5.getChecksum().equals("1c5d1ecec440191de3b71f080f93eb51")){
				System.out.println("CHECK 3");
			}else{
				JOptionPane.showMessageDialog(null,"File" + " data/arrow_right.png" + " is corrupt. \n\nGame exits!");
				return -1;
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,e.toString() + "\n\nGame exits!");
			return -1;
		}
		
		try{
			ConfigHelper ch2 = ch.loadConfigFile();
			Pong.S_resX = Integer.parseInt(ch2.getOptions().get("resX"));
			Pong.S_resY = Integer.parseInt(ch2.getOptions().get("resY"));
			S_container.setMusicVolume(Float.parseFloat(ch2.getOptions().get("volume")));
			S_container.setMusicOn(Boolean.parseBoolean(ch2.getOptions().get("vol_on")));
			Pong.S_debug = Boolean.parseBoolean(ch2.getOptions().get("debug"));
			System.out.println(Pong.S_debug);
		}catch(FileNotFoundException fnfe){
			//Creating a config-file with standard values
			LinkedHashMap<String, String> options = new LinkedHashMap<>();
			options.put("resX", Integer.toString(Pong.S_resX));
			options.put("resY", Integer.toString(Pong.S_resY));
			options.put("volume", Float.toString(1.0f));
			options.put("vol_on", Boolean.toString(true));
			ch.setOptions(options);
			ch.createConfigFile();
		}
		return 1;
	}

	public static void main(String[] args) throws SlickException {
		Pong pong = new Pong(TITLE + " " + VERSION);
		S_container = new AppGameContainer(pong);
		int errorcode = pong.initGameSubRoutines();
		if(errorcode == 1){
			S_container.setDisplayMode(S_resX, S_resY, false);
			S_container.setTargetFrameRate(FPS);
			S_container.setShowFPS(S_showFPS);
			S_container.start();
		}else{
			S_container.exit();
		}


	}
}
