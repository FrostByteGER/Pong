package de.frostbyteger.pong.engine.graphics.ui;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;

import de.frostbyteger.pong.engine.graphics.FontHelper;

/**
 * @author Kevin
 * TODO: Add all functionalities
 *
 */
public class Box{
	
	private GameContainer parentContainer;
	
	private UnicodeFont boxFont;
	private ArrayList<ArrayList<Cell>> cells;

	/**
	 * TODO: finish constructor
	 */
	public Box(int boxCount, int boxWidth, int boxHeight, String boxFontPath, int boxFontSize, int cellX, int cellY, float cellWidth, float cellHeight, GameContainer container) {
		parentContainer = container;
		try {
			boxFont = FontHelper.newFont(boxFontPath, boxFontSize, false, false);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		cells = new ArrayList<ArrayList<Cell>>();
		ArrayList<Cell> tempCell = new ArrayList<Cell>();
		for(int i = 0;i < boxWidth;i++){
			for(int j = 0; j < boxHeight;j++){
				tempCell.add(new Cell(cellX, cellY, cellWidth, cellHeight, container));
				cellY += cellHeight;
			}
			cells.add(tempCell);
			tempCell = new ArrayList<Cell>();
			cellX += cellWidth;
			cellY -= cellHeight*boxHeight;
		}
		for(int k = 0; k < cells.size();k++){
			for(int l = 0; l < cells.get(k).size();l++){
				Cell temp = cells.get(k).get(l);
				temp.setFontPath("data/Alexis.ttf");
				temp.setSize(50);
				try {
					temp.createNewFont();
				} catch (SlickException e) {
					e.printStackTrace();
					//TODO
				}
				temp.setCellText("This is a test!");
			}
		}
	}
	
	public void render(){
		for(int i = 0; i < cells.size();i++){
			for(int j = 0; j < cells.get(i).size();j++){
				try {
					cells.get(i).get(j).drawCell();
				} catch (SlickException e) {
					e.printStackTrace();
					//TODO
				};
			}
		}
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

	/**
	 * @return the cells
	 */
	public ArrayList<ArrayList<Cell>> getCells() {
		return cells;
	}

	/**
	 * @param cells the cells to set
	 */
	public void setCells(ArrayList<ArrayList<Cell>> cells) {
		this.cells = cells;
	}

}
