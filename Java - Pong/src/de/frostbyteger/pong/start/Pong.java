package de.frostbyteger.pong.start;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;

import javax.swing.JOptionPane;
import javax.xml.bind.JAXBException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.frostbyteger.pong.core.Game;
import de.frostbyteger.pong.core.Lan;
import de.frostbyteger.pong.core.MainMenu;
import de.frostbyteger.pong.core.Options;
import de.frostbyteger.pong.core.Profiles;
import de.frostbyteger.pong.engine.Achievement;
import de.frostbyteger.pong.engine.Profile;
import de.frostbyteger.pong.engine.io.ConfigHelper;
import de.frostbyteger.pong.engine.io.MD5Loader;

public class Pong extends StateBasedGame{
	
	// Options
	public static int S_resX        = 800;
	public static int S_resY        = 600;
	public static boolean S_debug   = true;
	public static boolean S_showFPS = true;
	public static final int FPS     = 60;
	
	// Profiles Data
	public static final String PROFILE_PATH = "profiles/";
	public static String S_activeProfile = "standard";
	public static LinkedHashMap<String, Profile> S_profiles = new LinkedHashMap<String,Profile>();
	public static LinkedHashMap<String, Integer> S_statisticsData = new LinkedHashMap<String, Integer>(12);
	public static LinkedHashMap<String, String> S_achievementData = new LinkedHashMap<String, String>(6);
	public static final String[] STATISTICS_KEYS = {"timePlayedOverall","timePlayedCPU","timePlayedLAN",
											 		"timePlayedChallenge","matchesPlayedOverall",
											 		"matchesPlayedCPU","matchesPlayedLAN",
											 		"matchesPlayedChallenge","matchesWonOverall",
											 		"matchesWonCPU","matchesWonLAN","matchesWonChallenge"};
	
	// Statistics Data
	public static int S_timePlayedOverall      = 0;
	public static int S_timePlayedCPU          = 0;	
	public static int S_timePlayedLAN          = 0;	
	public static int S_timePlayedChallenge    = 0;	
	public static int S_matchesPlayedOverall   = 0;	
	public static int S_matchesPlayedCPU       = 0;
	public static int S_matchesPlayedLAN       = 0;
	public static int S_matchesPlayedChallenge = 0;
	public static int S_matchesWonOverall      = 0;
	public static int S_matchesWonCPU          = 0;
	public static int S_matchesWonLAN          = 0;
	public static int S_matchesWonChallenge    = 0;
	
	//Achievement Data
	public static Achievement test;
	
	// Version info
	public static final String TITLE          = "Pong";
	public static final String VERSION        = "v1.34";
	public static final String VERSION_STATUS = "INTERNAL";
	
	// MD5 checksums
	private static final String MD5_FONT  = "d060b8b0afa1753bf21d5fa3d3b14493";
	//private static final String MD5_LEFT  = "42a88f1b4fa5de64c17bb8f8ca300234";
	//private static final String MD5_RIGHT = "1c5d1ecec440191de3b71f080f93eb51";
	
	// Additional
	public static final String FONT = "data/alexis.ttf";
	
	public static AppGameContainer S_container;
	public static ConfigHelper S_configHelper = new ConfigHelper("data/", "config",".xml");
	

	public Pong(String name) {
		super(name);
	}
	
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new MainMenu());
		addState(new Options());
		addState(new Lan());
		addState(new Game());
		addState(new Profiles());		
	}
	
	//TODO: Find better try/catch and if algorithm
	/**
	 * Inits games subroutines. Checks if all
	 * files are existing and loads config-file
	 * and the profiles. It also creates a standard-configfile
	 * and/or standard-profile if the config file does not exist
	 * and/or no valid profile is found.
	 * @return returns an errorcode if something went wrong
	 */
	private int initGameSubRoutines(){
		MD5Loader md5 = new MD5Loader("data/Alexis.ttf");
		try{
			md5.createChecksum();
			if(!md5.getChecksum().equals(MD5_FONT)){
				JOptionPane.showMessageDialog(null,"File" + " data/Alexis.ttf" + " is corrupt. \n\nGame exits!");
				return -1;
			}
			/*
			md5.setFilename("data/arrow_left.png");
			md5.createChecksum();
			if(md5.getChecksum().equals(MD5_LEFT)){
			}else{
				JOptionPane.showMessageDialog(null,"File" + " data/arrow_left.png" + " is corrupt. \n\nGame exits!");
				return -1;
			}
			md5.setFilename("data/arrow_right.png");
			md5.createChecksum();
			if(md5.getChecksum().equals(MD5_RIGHT)){
			}else{
				JOptionPane.showMessageDialog(null,"File" + " data/arrow_right.png" + " is corrupt. \n\nGame exits!");
				return -1;
			}
			*/
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,e.toString() + "\n\nGame exits!");
			return -1;
		}
		
		try{
			ConfigHelper ch2 = S_configHelper.loadConfigFile();
			Pong.S_resX = Integer.parseInt(ch2.getOptions().get("resX"));
			Pong.S_resY = Integer.parseInt(ch2.getOptions().get("resY"));
			S_container.setMusicVolume(Float.parseFloat(ch2.getOptions().get("volume")));
			S_container.setMusicOn(Boolean.parseBoolean(ch2.getOptions().get("vol_on")));
			Pong.S_debug = Boolean.parseBoolean(ch2.getOptions().get("debug"));
			Pong.S_showFPS = Boolean.parseBoolean(ch2.getOptions().get("show_fps"));
			S_activeProfile = ch2.getOptions().get("lastActiveProfile");
		}catch(FileNotFoundException | JAXBException e){
			//Creating a config-file with standard values
			LinkedHashMap<String, String> options = new LinkedHashMap<>();
			options.put("resX", Integer.toString(Pong.S_resX));
			options.put("resY", Integer.toString(Pong.S_resY));
			options.put("volume", Float.toString(1.0f));
			options.put("vol_on", Boolean.toString(true));
			options.put("show_fps", Boolean.toString(false));
			options.put("lastActiveProfile", S_activeProfile);
			S_configHelper.setOptions(options);
			try {
				S_configHelper.createConfigFile();
			} catch (JAXBException jaxbe) {
				JOptionPane.showMessageDialog(null,jaxbe.toString() + "\n\nThe game encountered a serious Error. Game exits!");
				return -1;
			}
		}		
		S_statisticsData.put(STATISTICS_KEYS[0], S_timePlayedOverall);
		S_statisticsData.put(STATISTICS_KEYS[1], S_timePlayedCPU);
		S_statisticsData.put(STATISTICS_KEYS[2], S_timePlayedLAN);
		S_statisticsData.put(STATISTICS_KEYS[3], S_timePlayedChallenge);
		S_statisticsData.put(STATISTICS_KEYS[4], S_matchesPlayedOverall);
		S_statisticsData.put(STATISTICS_KEYS[5], S_matchesPlayedCPU);
		S_statisticsData.put(STATISTICS_KEYS[6], S_matchesPlayedLAN);
		S_statisticsData.put(STATISTICS_KEYS[7], S_matchesPlayedChallenge);
		S_statisticsData.put(STATISTICS_KEYS[8], S_matchesWonOverall);
		S_statisticsData.put(STATISTICS_KEYS[9], S_matchesWonCPU);
		S_statisticsData.put(STATISTICS_KEYS[10], S_matchesWonLAN);
		S_statisticsData.put(STATISTICS_KEYS[11], S_matchesWonChallenge);
		int validProfiles = 0;
		try{ 
			File f = new File("profiles");
			if(!f.exists()){
				f.mkdir();
			}
			if(f.list().length == 0){
				createStandardProfile();
			}else{
				File[] temp = f.listFiles();
				for(int i = 0;i < temp.length;i++){
					String name = temp[i].getName();
					int pos = name.lastIndexOf(".");
					if (pos > 0) {
					    name = name.substring(0, pos);
					}
					Profile tempP = new Profile(PROFILE_PATH, name);
					try {
						tempP.load();
						S_profiles.put(tempP.getProfileName().toLowerCase(), tempP);
						validProfiles += 1;
					} catch (JAXBException e) {
						JOptionPane.showMessageDialog(null,e.toString() + "\n\nIt seems that one or more profiles are corrupted!");
					}
				}
			}
			if(validProfiles == 0){
				createStandardProfile();
			}

		}catch(FileNotFoundException fnfe){
			JOptionPane.showMessageDialog(null,fnfe.toString() + "\n\nGame exits!");
			return -1;
		}
		try{
			Profile profile = S_profiles.get(S_activeProfile);
			for(int i = 0;i < S_statisticsData.size();i++){
				S_statisticsData.put(STATISTICS_KEYS[i], Integer.parseInt(profile.getProfileData().get(STATISTICS_KEYS[i])));
			}
		}catch(NullPointerException npe){
			JOptionPane.showMessageDialog(null,npe.toString() + "\n\nLast loaded profile not found, loading standardprofile");
			if(S_profiles.get("standard") == null){
				createStandardProfile();
			}else{
				S_activeProfile = "standard";
			}
			Profile profile = S_profiles.get(S_activeProfile);
			for(int i = 0;i < S_statisticsData.size();i++){
				S_statisticsData.put(STATISTICS_KEYS[i], Integer.parseInt(profile.getProfileData().get(STATISTICS_KEYS[i])));
			}
		}

		return 0;
	}
	
	private void createStandardProfile(){
		Profile standard = new Profile();
		standard.setProfilePath(PROFILE_PATH);
		LinkedHashMap<String, String> temp = new LinkedHashMap<>(12);
		for(int i = 0;i < S_statisticsData.size();i++){
			temp.put(STATISTICS_KEYS[i], Integer.toString(S_statisticsData.get(STATISTICS_KEYS[i])));
		}
		standard.setProfileInfos(temp);
		try {
			standard.createProfile(true);
		} catch (JAXBException e) {
			JOptionPane.showMessageDialog(null,e.toString() + "\n\nCan't create standardprofile, please create a new profile!");
			e.printStackTrace(); //TODO: If exception is thrown here, change start to create new Profile.
		}
		temp = null;
		S_profiles.put("standard", standard);
		S_activeProfile = "standard";
	}

	public static void main(String[] args) throws SlickException {
		Pong pong = new Pong(TITLE + " " + VERSION);
		S_container = new AppGameContainer(pong);
		int errorcode = pong.initGameSubRoutines();
		if(errorcode == 0){
			S_container.setDisplayMode(S_resX, S_resY, false);
			S_container.setTargetFrameRate(FPS);
			S_container.setShowFPS(S_showFPS);
			S_container.start();
		}else{
			S_container.exit();
		}


	}
}
