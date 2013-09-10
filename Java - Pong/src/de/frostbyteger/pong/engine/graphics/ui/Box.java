package de.frostbyteger.pong.engine.graphics.ui;

import java.util.ArrayList;

import org.newdawn.slick.UnicodeFont;

/**
 * @author Kevin
 *
 */
public class Box{
	
	private UnicodeFont font;
	private ArrayList<ArrayList<Cell>> cells;

	/**
	 * 
	 */
	public Box() {
		cells = new ArrayList<ArrayList<Cell>>();
		for(;;){
			
		}
	}
	
	public void render(){
		
	}
	
	public void update(){
		
	}
	
	
	public static int showOptionBox(String message, BoxOptionSelection boxOS){
		if(boxOS == BoxOptionSelection.YES_NO_CANCEL_BOX){
			
		}else if(boxOS == BoxOptionSelection.YES_NO_BOX){
			
		}
		return 0;
	}
	
	public static int showMessageBox(String message, BoxOptionSelection boxOS){
		if(boxOS == BoxOptionSelection.OK_CANCEL_BOX){
			
		}else if(boxOS == BoxOptionSelection.OK_BOX){
			
		}
		
		return 0;
	}

}
