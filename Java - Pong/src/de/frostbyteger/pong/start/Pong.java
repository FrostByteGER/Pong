package de.frostbyteger.pong.start;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.StateBasedGame;

import de.frostbyteger.pong.core.CorePong;
import de.frostbyteger.pong.core.Game;
import de.frostbyteger.pong.core.Lan;
import de.frostbyteger.pong.core.MainMenu;
import de.frostbyteger.pong.core.Options;
import de.frostbyteger.pong.core.Profile;
import de.frostbyteger.pong.engine.FontHelper;
import de.frostbyteger.pong.engine.io.PropertyHelper;

public class Pong extends StateBasedGame{
	
	// Options
	public static int S_resX = 800;
	public static int S_resY = 600;
	public static final int FPS = 60;
	
	// Version info
	public static final String TITLE = "Pong";
	public static final String VERSION = "v1.12";
	public static final String VERSION_STATUS = "BETA";
	
	public static AppGameContainer S_Container;
	public static PropertyHelper S_Prophelper;
	
	public static boolean S_Debug = true;

	public Pong(String name) {
		super(name);
		S_Prophelper = new PropertyHelper();
	}
	
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new MainMenu());
		addState(new Options());
		addState(new Lan());
		addState(new Game());
		addState(new Profile());
		
	}

	public static void main(String[] args) throws SlickException {
		S_Container = new AppGameContainer(new Pong(TITLE + " " + VERSION));
		S_Container.setDisplayMode(S_resX, S_resY, false);
		S_Container.setTargetFrameRate(FPS);
		S_Container.setShowFPS(false); //TODO: Delete this
		S_Container.start();


	}
}
