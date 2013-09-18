package de.frostbyteger.pong.engine.graphics.ui;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.UnicodeFont;

import de.frostbyteger.pong.engine.graphics.FontHelper;

/**
 * @author Kevin
 *
 */
public class Box{
	
	private GameContainer parentContainer;
	
	private UnicodeFont boxFont;
	private ArrayList<ArrayList<Cell>> cells;

	/**
	 * 
	 */
	public Box(int boxCount, int boxWidth, int boxHeight, String boxFontPath, int boxFontSize, int cellX, int cellY, float cellWidth, float cellHeight, GameContainer container) {
		boxFont = FontHelper.newFont(boxFontPath, boxFontSize, false, false);
		cells = new ArrayList<ArrayList<Cell>>();
		ArrayList<Cell> tempCell = new ArrayList<Cell>();
		for(int i = 0;i <= boxWidth;i++){
			for(int j = 0; j <= boxHeight;j++){
				Cell cell = new Cell(cellX, cellY, cellWidth, cellHeight, parentContainer);
				cell.setCellFont(boxFont);
				tempCell.add(cell);
			}
			cells.add(tempCell);
			tempCell.clear();
		}
	}
	
	public void render(){
		
	}
	
	public void update(){
		
	}
	
	
	public static int showOptionBox(String message, BoxOptionSelection boxOS){
		//TODO: Pause parentContainer logic, put a gray filter on the screen, then render the Box
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
