package de.frostbyteger.pong.engine.graphics.ui.gui;

import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Rectangle;

import de.frostbyteger.pong.engine.graphics.FontHelper;

/**
 * 
 * @author Kevin
 * @version 1.00
 */
public class Box{
	
	// Box objects
	private ArrayList<ArrayList<Cell>> cells;
	private GameContainer parentContainer;
	private UnicodeFont boxFont;
	private ArrayList<Cell> sources;
	private Cell header        = null;
	private Rectangle edgedBox = null;
	private int[] boxKeyCoordinates = new int[2];
	
	
	// Box options
	private int boxWidth;
	private int boxHeight;
	private int boxX;
	private int boxY;
	private int boxFontSize;
	private int boxHeaderHeight       = 50;
	private float cellWidth;
	private float cellHeight;
	private Color boxBorderColor      = Color.white;
	private boolean active            = true;
	private boolean visible           = true;
	private boolean clickable         = true;
	private boolean focused           = false;
	private boolean edged             = false;
	private boolean keyInput          = false;
	private boolean autoAdjustBox     = false;
	
	// Box image options
	private Image boxImage = null;
	private boolean imageVisible = false;



	/**
	 * 
	 * @param boxWidth Width of the box
	 * @param boxHeight Height of the box
	 * @param boxX The boxes starting coordinations
	 * @param boxY the boxes starting coordinations
	 * @param boxFontPath
	 * @param boxFontSize
	 * @param cellWidth
	 * @param cellHeight
	 * @param container
	 * @throws SlickException
	 */
	public Box(int boxWidth, int boxHeight, int boxX, int boxY, String boxFontPath, int boxFontSize, float cellWidth, float cellHeight, GameContainer container) throws SlickException {
		this.parentContainer = container;
		this.sources = new ArrayList<Cell>();
		
		if(boxWidth <= 0 || boxHeight <= 0){
			throw new IllegalBoxArgumentException("Boxes width or height is 0 or negative!");
		}
		if(cellWidth <= 0 || cellHeight <= 0){
			throw new IllegalCellArgumentException("Cell width or height is 0 or negative!");
		}
		
		this.boxWidth = boxWidth;
		this.boxHeight = boxHeight;
		this.boxX = boxX;
		this.boxY = boxY;
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
		int cellX = boxX;
		int cellY = boxY;
		this.boxFontSize = boxFontSize;
		this.boxFont = FontHelper.newFont(boxFontPath, boxFontSize, false, false);
		this.cells = new ArrayList<ArrayList<Cell>>();
		this.edgedBox = new Rectangle(cellX, cellY + this.boxHeaderHeight, cellWidth * boxWidth, cellHeight * boxHeight);
		this.header = new Cell(cellX, cellY, cellWidth * boxWidth, this.boxHeaderHeight, container);
		this.header.setFontPath(boxFontPath);
		this.header.setSize(boxFontSize);
		this.header.createNewFont();
		this.header.setEdging(true);
		this.header.setActive(false);
		this.header.setClickable(false);

		ArrayList<Cell> tempCell = new ArrayList<Cell>();
		
		for(int i = 0;i < boxWidth;i++){
			for(int j = 0; j < boxHeight;j++){
				tempCell.add(new Cell(cellX, cellY + this.boxHeaderHeight, cellWidth, cellHeight, container));
				cellY += cellHeight;
			}
			this.cells.add(tempCell);
			tempCell = new ArrayList<Cell>();
			cellX += cellWidth;
			cellY -= cellHeight * boxHeight;
		}
		for(int k = 0; k < this.cells.size();k++){
			for(int l = 0; l < this.cells.get(k).size();l++){
				Cell temp = this.cells.get(k).get(l);
				temp.setFontPath(boxFontPath);
				temp.setSize(boxFontSize);
				temp.createNewFont();
				this.sources.add(temp);
			}
		}
		
	}
	
	public void render() throws SlickException{

		for(int i = 0; i < cells.size();i++){
			for(int j = 0; j < cells.get(i).size();j++){
					cells.get(i).get(j).drawCell();
			}
		}
		if(edged){
			parentContainer.getGraphics().setColor(boxBorderColor);
			parentContainer.getGraphics().draw(edgedBox);			
		}

		
		if(header.isActive()){
			header.drawCell();
		}
		parentContainer.getGraphics().setColor(Color.white);
	}
	
	public void update(){
		if(focused == true && keyInput == true){
			if((boxKeyCoordinates[0] > 0 && boxKeyCoordinates[1] > 0) && cells.get(boxKeyCoordinates[0] - 1).get(boxKeyCoordinates[1] - 1).isHighlighted() == false){
				for(int i = 0;i < sources.size();i++){
					sources.get(i).setHighlighted(false);
				}
				cells.get(boxKeyCoordinates[0] - 1).get(boxKeyCoordinates[1] - 1).setHighlighted(true);
			}else if(boxKeyCoordinates[0] <= 0 || boxKeyCoordinates[1] <= 0){
				throw new BoxIndexOutOfBoundsException("BoxKeyCoordinates 0 or negative");
			}
		}else if(focused == false){
			for(int i = 0;i < sources.size();i++){
				sources.get(i).setHighlighted(false);
			}
		}

	}
	
	//TODO: Add functionality
	public static int showOptionBox(String message, BoxOptionSelection boxOS) throws SlickException{
		if(boxOS == BoxOptionSelection.YES_NO_BOX){
			
		}else if(boxOS == BoxOptionSelection.YES_NO_CANCEL_BOX){
			
		}
		return 0;
	}
	
	//TODO: Add functionality
	public static int showMessageBox( String message, BoxOptionSelection boxOS){
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

	/**
	 * @return the sources
	 */
	public ArrayList<Cell> getSources() {
		return sources;
	}

	/**
	 * @return the boxFont
	 */
	public UnicodeFont getBoxFont() {
		return boxFont;
	}

	/**
	 * @param boxFont the boxFont to set
	 */
	public void setBoxFont(UnicodeFont boxFont) {
		this.boxFont = boxFont;
	}

	/**
	 * @return the boxFontSize
	 */
	public int getBoxFontSize() {
		return boxFontSize;
	}

	/**
	 * @param boxFontSize the boxFontSize to set
	 */
	public void setBoxFontSize(int boxFontSize) {
		this.boxFontSize = boxFontSize;
	}

	/**
	 * @return the boxWidth
	 */
	public int getBoxWidth() {
		return boxWidth;
	}

	/**
	 * @param boxWidth the boxWidth to set
	 */
	public void setBoxWidth(int boxWidth) {
		this.boxWidth = boxWidth;
	}

	/**
	 * @return the boxHeight
	 */
	public int getBoxHeight() {
		return boxHeight;
	}

	/**
	 * @param boxHeight the boxHeight to set
	 */
	public void setBoxHeight(int boxHeight) {
		this.boxHeight = boxHeight;
	}

	/**
	 * @return the boxX
	 */
	public int getBoxX() {
		return boxX;
	}

	/**
	 * @param boxX the boxX to set
	 */
	public void setBoxX(int boxX) {
		this.boxX = boxX;
	}

	/**
	 * @return the boxY
	 */
	public int getBoxY() {
		return boxY;
	}

	/**
	 * @param boxY the boxY to set
	 */
	public void setBoxY(int boxY) {
		this.boxY = boxY;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @return the visible
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * @param visible the visible to set
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * @return the clickable
	 */
	public boolean isClickable() {
		return clickable;
	}

	/**
	 * @param clickable the clickable to set
	 */
	public void setClickable(boolean clickable) {
		this.clickable = clickable;
	}

	/**
	 * @return the edged
	 */
	public boolean isEdged() {
		return edged;
	}

	/**
	 * @param edged the edged to set
	 */
	public void setEdged(boolean edged) {
		this.edged = edged;
	}

	/**
	 * @return the parentContainer
	 */
	public GameContainer getParentContainer() {
		return parentContainer;
	}

	/**
	 * @param parentContainer the parentContainer to set
	 */
	public void setParentContainer(GameContainer parentContainer) {
		this.parentContainer = parentContainer;
	}

	/**
	 * @return the header
	 */
	public Cell getHeader() {
		return header;
	}

	/**
	 * @param header the header to set
	 */
	public void setHeader(Cell header) {
		this.header = header;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isHeaderActive(){
		return this.header.isActive();
	}
	
	/**
	 * 
	 * @param active
	 */
	public void setHeaderActive(boolean active){
		this.header.setActive(active);
	}
	
	/**
	 * 
	 * @return
	 */
	public String getHeaderTitle(){
		return this.header.getCellText();
	}
	
	/**
	 * 
	 * @param title
	 */
	public void setHeaderTitle(String title){
		this.header.setCellText(title);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isHeaderEdged(){
		return this.header.isEdging();
	}
	
	/**
	 * 
	 * @param edging
	 */
	public void setHeaderEdging(boolean edging){
		this.header.setEdging(edging);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isHeaderClickable(){
		return this.header.isClickable();
	}
	
	/**
	 * 
	 * @param clickable
	 */
	public void setHeaderClickable(boolean clickable){
		this.header.setClickable(clickable);
	}

	/**
	 * @return the boxKeyCoordinates
	 */
	public int[] getBoxKeyCoordinates() {
		return boxKeyCoordinates;
	}

	/**
	 * @param boxKeyCoordinates the boxKeyCoordinates to set
	 */
	public void setBoxKeyCoordinates(int[] boxKeyCoordinates) {
		this.boxKeyCoordinates = boxKeyCoordinates;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void setBoxKeyCoordinates(int x, int y){
		this.boxKeyCoordinates[0] = x;
		this.boxKeyCoordinates[1] = y;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getBoxKeyX(){
		return boxKeyCoordinates[0];
	}
	
	/**
	 * 
	 * @param x
	 */
	public void setBoxKeyX(int x){
		this.boxKeyCoordinates[0] = x;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getBoxKeyY(){
		return boxKeyCoordinates[1];
	}
	
	/**
	 * 
	 * @param y
	 */
	public void setBoxKeyY(int y){
		this.boxKeyCoordinates[1] = y;	
	}
	


	/**
	 * @return the focused
	 */
	public boolean isFocused() {
		return focused;
	}

	/**
	 * @param focused the focused to set
	 */
	public void setFocus(boolean focus) {
		this.focused = focus;
	}
	
	public void setKeyInput(boolean activated){
		for(int i = 0; i < this.getSources().size();i++){
			this.getSources().get(i).setKeysActive(activated);
		}
		this.keyInput = activated;
	}
	
	public boolean isKeyInputActivated(){
		return this.keyInput;
	}

	/**
	 * @return the boxImage
	 */
	public Image getBoxImage() {
		return boxImage;
	}

	/**
	 * @param boxImage the boxImage to set
	 */
	public void setBoxImage(Image boxImage) {
		this.boxImage = boxImage;
	}

	/**
	 * @return the imageVisible
	 */
	public boolean isImageVisible() {
		return imageVisible;
	}

	/**
	 * @param imageVisible the imageVisible to set
	 */
	public void setImageVisible(boolean imageVisible) {
		this.imageVisible = imageVisible;
	}

	/**
	 * @return the boxHeaderHeight
	 */
	public int getBoxHeaderHeight() {
		return boxHeaderHeight;
	}

	/**
	 * @param boxHeaderHeight the boxHeaderHeight to set
	 */
	public void setBoxHeaderHeight(int boxHeaderHeight) {
		this.boxHeaderHeight = boxHeaderHeight;
	}

	/**
	 * @return the boxBorderColor
	 */
	public Color getBoxBorderColor() {
		return boxBorderColor;
	}

	/**
	 * @param boxBorderColor the boxBorderColor to set
	 */
	public void setBoxBorderColor(Color boxBorderColor) {
		this.boxBorderColor = boxBorderColor;
	}
	
	/**
	 * Checks if ALL cells in the box are centered or not.
	 * If only ONE cell returns no, the return value will be false.
	 * @return true if whole box is centered, false if not
	 */
	public boolean isBoxCentered(){
		for(int i = 0;i < sources.size();i++){
			if(!sources.get(i).isCentered()){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Sets ALL cells of the box centered.
	 */
	public void setBoxCentered(){
		for(int i = 0;i < sources.size();i++){
			sources.get(i).setCentered();
		}
	}
	
	/**
	 * Checks if ALL cells in the box are left or not.
	 * If only ONE cell returns no, the return value will be false.
	 * @return true if whole box is left, false if not
	 */
	public boolean isBoxLeft(){
		for(int i = 0;i < sources.size();i++){
			if(!sources.get(i).isLeft()){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Sets ALL cells of the box left.
	 */
	public void setBoxLeft(){
		for(int i = 0;i < sources.size();i++){
			sources.get(i).setLeft();
		}
	}
	
	/**
	 * Checks if ALL cells in the box are right or not.
	 * If only ONE cell returns no, the return value will be false.
	 * @return true if whole box is right, false if not
	 */
	public boolean isBoxRight(){
		for(int i = 0;i < sources.size();i++){
			if(!sources.get(i).isRight()){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Sets ALL cells of the box right.
	 */
	public void setBoxRight(){
		for(int i = 0;i < sources.size();i++){
			sources.get(i).setRight();
		}
	}
	
	/**
	 * Sets the action commands of every cell in the box to
	 * the given list content.
	 * @param actionCommands the action commands you want to set
	 */
	public void setAllActionCommands(String[] actionCommands){
		for(int i = 0;i < actionCommands.length;i++){
			sources.get(i).setActionCommand(actionCommands[i]);
		}
	}
	
	/**
	 * Returns all action commands from the boxes cells.
	 * @return the action commands
	 */
	public String[] getAllActionCommands(){
		String[] actionCommands = new String[sources.size()];
		for(int i = 0;i < actionCommands.length;i++){
			actionCommands[i] = sources.get(i).getActionCommand();
		}
		return actionCommands;
	}
	
	/**
	 * Sets the titles of every cell in the box to
	 * the given list content.
	 * @param titles the titles you want to set
	 */
	public void setAllCellTitles(String[] titles){
		for(int i = 0;i < titles.length;i++){
			sources.get(i).setCellText(titles[i]);
		}
	}
	
	/**
	 * Returns all cell titles from the boxes cells.
	 * @return the cell titles
	 */
	public String[] getAllCellTitles(){
		String[] titles = new String[sources.size()];
		for(int i = 0;i < titles.length;i++){
			titles[i] = sources.get(i).getCellText();
		}
		return titles;
	}
	
	/**
	 * Adds listeners to every cell in the box from
	 * the given list content.
	 * @param listeners the listeners you want to add
	 */
	public void addAllListeners(ComponentListener[] listeners){
		for(int i = 0;i < listeners.length;i++){
			sources.get(i).addListener(listeners[i]);
		}
	}
	
	/**
	 * Adds a listener to every cell in the box.
	 * @param listener the listener you want to add
	 */
	public void addBoxListener(ComponentListener listener){
		for(int i = 0;i < sources.size();i++){
			sources.get(i).addListener(listener);
		}
	}
	
	/**
	 * Removes all listeners from the boxes cells.
	 * @param the listener to remove
	 */
	public void removeAllListeners(ComponentListener listener){
		for(int i = 0;i < sources.size();i++){
			sources.get(i).removeListener(listener);;
		}
	}
	
	/**
	 * Sets the auto adjust ability of every cell in the box to
	 * true or false.
	 * @param activated set auto ajust to true or false
	 */
	public void setAllAutoAdjust(boolean active){
		for(int i = 0;i < sources.size();i++){
			sources.get(i).setAutoAdjust(active);
		}
	}

	/**
	 * @return the autoAdjustBox
	 */
	public boolean isAutoAdjustBox() {
		return autoAdjustBox;
	}

	/**
	 * Sets the auto adjust ability of the box
	 * to true or false. This changes all cell widths
	 * and heights to the maximum string length/height
	 * from the boxes cells or reverts it back to its
	 * original length and height.
	 * @param autoAdjustBox the autoAdjustBox to set
	 */
	public void setAutoAdjustBox(boolean autoAdjustBox) {
		this.autoAdjustBox = autoAdjustBox;
		if(autoAdjustBox){
			int[] width = new int[sources.size()];
			int[] height = new int[sources.size()];
			
			for(int i = 0;i < sources.size();i++){
				width[i] = sources.get(i).getCellTextWidth();
			}
			Arrays.sort(width);
			for(int i = 0;i < sources.size();i++){
				sources.get(i).setWidth(width[width.length - 1] + sources.get(i).getCellDrawOffsetX());
				sources.get(i).setAreaWidth(width[width.length - 1] + sources.get(i).getCellDrawOffsetX());
			}
			/*// TODO: Fix bugs
			for(int i = 0;i < sources.size();i++){
				height[i] = sources.get(i).getCellTextHeight();
			}
			Arrays.sort(height);
			for(int i = 0;i < sources.size();i++){
				sources.get(i).setHeight(height[height.length - 1] + sources.get(i).getCellDrawOffsetY());
				System.out.println(width[i] + "|" + height[i]);
				sources.get(i).setAreaHeight(height[height.length - 1] + sources.get(i).getCellDrawOffsetY());
			}
			*/
			
		}else{
			for(int i = 0;i < sources.size();i++){
				sources.get(i).setWidth(cellWidth);
				sources.get(i).setAreaWidth(cellWidth);
				sources.get(i).setHeight(cellHeight);
				sources.get(i).setAreaHeight(cellHeight);
			}
		}
	}
	


}