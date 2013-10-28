package de.frostbyteger.pong.engine.graphics.ui.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Rectangle;

import de.frostbyteger.pong.engine.graphics.FontHelper;

/**
 * This class creates a fully customizable cell which
 * is similar to a table cell. You can change almost everything.
 * @author Kevin Kuegler
 *
 */
public class Cell extends CellListener{
	
	// Objects and Paths
	private Rectangle cell          = null;
	private Rectangle cellBorder    = null;
	private UnicodeFont cellFont    = null;
	private Image cellImage         = null;
	private Object parentComponent  = null;
	private GameContainer container = null;
	private String cellText         = "";
	private String fontPath         = "";
	private String imagePath        = "";
	
	
	// Font options
	private Color fontColor  = Color.white;
	private boolean centered = true;
	private boolean left     = false;
	private boolean right    = false;
	private boolean bold     = false;
	private boolean italics  = false;
	private int fontsize;
	
	// Image options
	private float imageScale           = 1.0f;
	private float cellImageDrawOffsetX = 1.0f;
	private float cellImageDrawOffset  = 1.0f;
	private boolean autoAdjustImage    = true;
	
	// Cell options
	private boolean active        = true;
	private boolean visible	      = true;
	private boolean autoAdjust    = true;
	private boolean clickable     = true;
	private boolean selected      = false;
	private boolean highlighted   = false;
	private boolean edging        = false;
	private Color backgroundColor = Color.transparent;
	private Color borderColor     = Color.white;
	private float cellScale       = 1.0f;
	private int cellX;
	private int cellY;
	private int cellEdgeWidth     = 1;
	private int cellDrawOffsetX   = 20;
	private int cellDrawOffsetY   = 10;
	
	// Additional variables
	private int areaOffset = 1;
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param container
	 */
	public Cell(int x, int y, float width, float height, GameContainer container) {
		super(container, x + 1, y, width - (float)1, height - (float)1, false);
		super.setAreaFilled(true);
		super.setNormalColor(new Color(1,1,1,0.0f));
		super.setMouseOverColor(new Color(1,1,1,0.7f));
		this.cell = new Rectangle(x,y, width, height);
		this.cellBorder = new Rectangle(x, y, width, height);
		this.cellX = x;
		this.cellY = y;
		this.container = container;

	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Cell(int x, int y, float width, float height, float scale, GameContainer container) {
		super(container, x + 1, y, width * scale - (float)1, height * scale - (float)1, false);
		super.setAreaFilled(true);
		super.setNormalColor(new Color(1,1,1,0.0f));
		super.setMouseOverColor(new Color(1,1,1,0.7f));
		this.cell = new Rectangle(x,y, width * scale, height * scale);
		this.cellBorder = new Rectangle(x, y, width * scale, height * scale);
		this.cellX = x;
		this.cellY = y;
		this.cellScale = scale;
		this.container = container;
	}

	/**
	 * 
	 * @param parentComponent
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Cell(Object parentComponent, int x, int y, float width, float height, GameContainer container) {
		super(container, x + 1, y, width - (float)1, height - (float)1, false);
		super.setAreaFilled(true);
		super.setNormalColor(new Color(1,1,1,0.0f));
		super.setMouseOverColor(new Color(1,1,1,0.7f));
		this.cell = new Rectangle(x,y, width, height);
		this.cellBorder = new Rectangle(x, y, width, height);
		this.parentComponent = parentComponent;
		this.cellX = x;
		this.cellY = y;
		this.container = container;
	}
	
	/**
	 * 
	 * @param fontPath
	 * @param fontSize
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @throws SlickException
	 */
	public Cell(String fontPath, int fontSize, int x, int y, float width, float height, GameContainer container) throws SlickException {
		super(container, x + 1, y, width - (float)1, height - (float)1, false);
		super.setAreaFilled(true);
		super.setNormalColor(new Color(1,1,1,0.0f));
		super.setMouseOverColor(new Color(1,1,1,0.7f));
		this.fontPath = fontPath;
		this.cell = new Rectangle(x,y, width, height);
		this.cellBorder = new Rectangle(x, y, width, height);
		this.cellFont = FontHelper.newFont(fontPath, fontSize, false, false);
		this.fontsize = fontSize;
		this.cellX = x;
		this.cellY = y;
		this.container = container;
	}
	
	/**
	 * 
	 * @param fontPath
	 * @param fontSize
	 * @param imagePath
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @throws SlickException
	 */
	public Cell(String fontPath, int fontSize, String imagePath, int x, int y, float width, float height, GameContainer container) throws SlickException {
		super(container, x + 1, y, width - (float)1, height - (float)1, false);
		super.setAreaFilled(true);
		super.setNormalColor(new Color(1,1,1,0.0f));
		super.setMouseOverColor(new Color(1,1,1,0.7f));
		this.cell = new Rectangle(x,y, width, height);
		this.cellBorder = new Rectangle(x, y, width, height);
		this.cellFont = FontHelper.newFont(fontPath, fontSize, false, false);
		this.cellImage = new Image(imagePath);
		this.fontPath = fontPath;
		this.imagePath = imagePath;
		this.fontsize = fontSize;
		this.cellX = x;
		this.cellY = y;
		this.container = container;

	}
	
	/**
	 * 
	 * @param fontPath
	 * @param fontSize
	 * @param bold
	 * @param italics
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @throws SlickException
	 */
	public Cell(String fontPath, int fontSize, boolean bold, boolean italics, int x, int y, float width, float height, GameContainer container) throws SlickException {
		super(container, x + 1, y, width - (float)1, height - (float)1, false);
		super.setAreaFilled(true);
		super.setNormalColor(new Color(1,1,1,0.0f));
		super.setMouseOverColor(new Color(1,1,1,0.7f));
		this.fontPath = fontPath;
		this.cell = new Rectangle(x,y, width, height);
		this.cellBorder = new Rectangle(x, y, width, height);
		this.cellFont = FontHelper.newFont(fontPath, fontSize, bold, italics);
		this.fontsize = fontSize;
		this.cellX = x;
		this.cellY = y;
		this.container = container;
	}
	
	/**
	 * Draws the cell while taking account of the set celloptions
	 * like auto adjustment or visibility.
	 * However, the autoadjust is not possible for the image due to
	 * missing methods in the imageclass. 
	 * @throws SlickException
	 */
	public void drawCell() throws SlickException{
		if(active == true){
			if(visible == true){
				if(edging == true){
					if(areaOffset == 0){
						areaOffset = 1;
						this.setArea(new Rectangle(this.getX() + areaOffset, this.getY() + areaOffset, this.getAreaWidth() - (float)areaOffset*2, this.getAreaHeight() - (float)areaOffset*2));
					}
					container.getGraphics().setColor(borderColor);
					container.getGraphics().setLineWidth(cellEdgeWidth);
					container.getGraphics().draw(cellBorder);
				}else{
					if(areaOffset == 1){
						this.setArea(new Rectangle(this.getX() - areaOffset, this.getY() - areaOffset, this.getAreaWidth() + (float)areaOffset*2, this.getAreaHeight() + (float)areaOffset*2));
						areaOffset = 0;
					}
					container.getGraphics().setColor(backgroundColor);
					container.getGraphics().fill(cell);
				}
				if(edging == false && autoAdjustImage == true){
					cellImageDrawOffsetX = 0.0f;
					cellImageDrawOffset = 0.0f;
				}else if(edging == true && autoAdjustImage == false || edging == false && autoAdjustImage == false){
					cellImageDrawOffsetX = 1.0f;
					cellImageDrawOffset = 1.0f;
				}
				if(autoAdjust == true){ //TODO: Add algorithm for increasing fontsize 
					if(cellFont != null){
						if(cellFont.getWidth(cellText) >= cell.getWidth()) {
							float i = (float)cellFont.getWidth(cellText) / (float)fontsize;
							if(cell.getWidth() > cellDrawOffsetX){
								fontsize = (int) ((cell.getWidth() - cellDrawOffsetX) / i);			
							}else{
								fontsize = (int) (cell.getWidth() / i);			
							}
							cellFont = FontHelper.newFont(fontPath, fontsize, bold, italics);
						}else if(cellFont.getHeight(cellText) >= cell.getHeight()){
							float i = (float)cellFont.getHeight(cellText) / (float)fontsize;
							if(cell.getHeight() > cellDrawOffsetY){
								fontsize = (int) ((cell.getHeight() - cellDrawOffsetY) / i);							
							}else{
								fontsize = (int) (cell.getHeight() / i);							
							}
							cellFont = FontHelper.newFont(fontPath, fontsize, bold, italics);
						}
					}
				}
				if(cellImage != null){
					if(autoAdjustImage == true){
						cellImage.draw(cell.getMinX() + cellImageDrawOffsetX, cell.getMinY(), cell.getWidth() - cellImageDrawOffset, cell.getHeight() - cellImageDrawOffset);
					}else{
						cellImage.draw(cell.getMinX() + cellImageDrawOffsetX, cell.getMinY());

					}
				}
				if(clickable == true){
					if(highlighted == true && isFocused() == false){
						setFocused(true);
						highlighted = false;
					}else if(highlighted == false && isFocused() == true){
						setFocused(false);
					}
					this.render(container, container.getGraphics());
				}

				if(cellFont != null){
					if(left == true){
						cellFont.drawString(cell.getMinX() + 1.0f , cell.getCenterY() - cellFont.getHeight(cellText)/2.0f, cellText, fontColor);
					}else if(centered == true){
						cellFont.drawString(cell.getCenterX() - cellFont.getWidth(cellText)/2.0f, cell.getCenterY() - cellFont.getHeight(cellText)/2.0f, cellText, fontColor);
					}else{
						cellFont.drawString(cell.getMaxX() - cellFont.getWidth(cellText) - 1.0f , cell.getCenterY() - cellFont.getHeight(cellText)/2.0f, cellText, fontColor);
					}
				}
				
			}
			container.getGraphics().setColor(Color.white);
		}else{
			return;
		}
	}
	
	//TODO: Delete or add functionality
	public void updateCell(){
		/*if(this.getState() == MOUSE_OVER && highlighted == false){
			highlighted = true;
		}else if(this.getState() == MOUSE_DOWN && selected == false){
			selected = true;
		}else if(this.getState() == MOUSE_NONE && highlighted == true || this.getState() == MOUSE_NONE && selected == true){
			highlighted = false;
			selected = false;
		}*/
	}


	
	/**
	 * Use this method if you want to change the actual fontsettings
	 * like fontsize, bold and italics.
	 * The fontclass has no setter for these settings so you have
	 * to overwrite the old font with a new one.
	 * @throws SlickException
	 */
	public void createNewFont() throws SlickException{
		this.cellFont = FontHelper.newFont(fontPath, fontsize, bold, italics);
	}
	
	/**
	 * Use this method if you want to change the resource of the image.
	 * The imageclass has no setter for these setting so you have
	 * to overwrite the old image with a new one.
	 * @throws SlickException
	 */
	public void createNewImage() throws SlickException{
		this.cellImage = new Image(imagePath);
	}
	
	
	/**
	 * @return the cellFont
	 */
	public UnicodeFont getCellFont() {
		return cellFont;
	}

	/**
	 * @return the cellImage
	 */
	public Image getCellImage() {
		return cellImage;
	}

	/**
	 * @param cellImage the cellImage to set
	 */
	public void setCellImage(Image cellImage) {
		this.cellImage = cellImage;
	}

	/**
	 * @return the parentComponent
	 */
	public Object getParentComponent() {
		return parentComponent;
	}

	/**
	 * @param parentComponent the parentComponent to set
	 */
	public void setParentComponent(Object parentComponent) {
		this.parentComponent = parentComponent;
	}

	/**
	 * @return the centered
	 */
	public boolean isCentered() {
		return centered;
	}

	/**
	 * Sets the cells textlayout to centered. Note that you dont need a parameter. 
	 * Just call this method and all other textlayouts(right and left) will
	 * be deactivated. This prevents that you can have 2 different activated
	 * textlayouts. That would crash the application
	 */
	public void setCentered() {
		if(this.centered == false && left == true || this.centered == false && right == true){
			this.centered = true;
			this.right = false;
			this.left = false;
		}else{
			return;
		}
	}

	/**
	 * @return the left
	 */
	public boolean isLeft() {
		return left;
	}

	/**
	 * Sets the cells textlayout to left. Note that you dont need a parameter. 
	 * Just call this method and all other textlayouts(centered and right) will
	 * be deactivated. This prevents that you can have 2 different activated
	 * textlayouts. That would crash the application
	 */
	public void setLeft() {
		if(this.left == false && centered == true || this.left == false && right == true){
			this.centered = false;
			this.right = false;
			this.left = true;
		}else{
			return;
		}
	}

	/**
	 * @return the right
	 */
	public boolean isRight() {
		return right;
	}

	/**
	 * Sets the cells textlayout to right. Note that you dont need a parameter. 
	 * Just call this method and all other textlayouts(centered and left) will
	 * be deactivated. This prevents that you can have 2 different activated
	 * textlayouts. That would crash the application
	 */
	public void setRight() {
		if(this.right == false && left == true || this.right == false && centered == true){
			this.centered = false;
			this.right = true;
			this.left = false;
		}else{
			return;
		}
	}

	/**
	 * 
	 * @return fontsize the fontsize
	 */
	public int getFontsize() {
		return fontsize;
	}

	/**
	 * @param fontsize the fontsize to set
	 */
	public void setFontsize(int fontsize) {
		this.fontsize = fontsize;
	}

	/**
	 * @return the autoAdjust
	 */
	public boolean isAutoAdjust() {
		return autoAdjust;
	}

	/**
	 * @param autoAdjust the autoAdjust to set
	 */
	public void setAutoAdjust(boolean autoAdjust) {
		this.autoAdjust = autoAdjust;
	}

	/**
	 * @return the width
	 */
	public float getWidth() {
		return this.cell.getWidth();
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(float width) {
		this.cell.setWidth(width * this.cellScale);
		this.cellBorder.setWidth(width * this.cellScale + 1);
	}

	/**
	 * @return the height
	 */
	public float getHeight() {
		return this.cell.getHeight();
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(float height) {
		this.cell.setHeight(height * this.cellScale);
	}

	/**
	 * @return the cellX
	 */
	public int getCellX() {
		return cellX;
	}

	/**
	 * @param cellX the cellX to set
	 */
	public void setCellX(int cellX) {
		this.cellX = cellX;
		this.cell.setX(cellX);
	}

	/**
	 * @return the cellY
	 */
	public int getCellY() {
		return cellY;
	}

	/**
	 * @param cellY the cellY to set
	 */
	public void setCellY(int cellY) {
		this.cellY = cellY;
		this.cell.setY(cellY);
	}
	
	/**
	 * @param cellX the cellX to set
	 */
	public void setCellBorderX(int cellBorderX) {
		this.cellBorder.setX(cellBorderX);
	}
	
	public float getCellBorderX() {
		return this.cellBorder.getX();
	}
	

	public void setCellBorderY(int cellBorderY) {
		this.cellBorder.setY(cellBorderY);
	}
	
	public float getCellBorderY() {
		return this.cellBorder.getY();
	}
	
	public void setWholeCellX(int cellX){
		this.cellX = cellX;
		this.cell.setX(cellX);
		this.cellBorder.setX(cellX);
		this.setX(cellX + 1);
	}
	
	public void setWholeCellY(int cellY){
		this.cellY = cellY;
		this.cell.setY(cellY);
		this.cellBorder.setY(cellY);
		this.setY(cellY + 1);
	}

	/**
	 * @return the cellText
	 */
	public String getCellText() {
		return cellText;
	}

	/**
	 * @param cellText the cellText to set
	 */
	public void setCellText(String cellText) {
		this.cellText = cellText;
	}

	/**
	 * @return the fontPath
	 */
	public String getFontPath() {
		return fontPath;
	}

	/**
	 * @param fontPath the fontPath to set
	 */
	public void setFontPath(String fontPath) {
		this.fontPath = fontPath;
	}

	/**
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * @param imagePath the imagePath to set
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
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
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/**
	 * @return the highlighted
	 */
	public boolean isHighlighted() {
		return highlighted;
	}

	/**
	 * @param highlighted the highlighted to set
	 */
	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}

	/**
	 * @return the backgroundColor
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * @param backgroundColor the backgroundColor to set
	 */
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * @return the cell
	 */
	public Rectangle getCell() {
		return cell;
	}

	/**
	 * @param cell the cell to set
	 */
	public void setCell(Rectangle cell) {
		this.cell = cell;
	}

	/**
	 * @return the cellBorder
	 */
	public Rectangle getCellBorder() {
		return cellBorder;
	}

	/**
	 * @param cellBorder the cellBorder to set
	 */
	public void setCellBorder(Rectangle cellBorder) {
		this.cellBorder = cellBorder;
	}

	/**
	 * @return the edged
	 */
	public boolean isEdged() {
		return edging;
	}

	/**
	 * @param edging the edging to set
	 */
	public void setEdged(boolean edged) {
		this.edging = edged;
	}

	/**
	 * @return the borderColor
	 */
	public Color getBorderColor() {
		return borderColor;
	}

	/**
	 * @param borderColor the borderColor to set
	 */
	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	/**
	 * @return the imageScale
	 */
	public float getImageScale() {
		return imageScale;
	}

	/**
	 * Sets the imagescale to the given scale and rescales the image
	 * because of missing setScale option of the Slick Image class.
	 * @param imageScale the imageScale to set
	 */
	public void setImageScale(float imageScale) {
		this.imageScale = imageScale;
		this.cellImage.getScaledCopy(imageScale);
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
	 * @return the cellScale
	 */
	public float getCellScale() {
		return cellScale;
	}

	/**
	 * @param cellScale the cellScale to set
	 */
	public void setCellScale(float cellScale) {
		this.cellScale = cellScale;
	}

	/**
	 * @return the cellEdgeWidth
	 */
	public int getCellEdgeWidth() {
		return cellEdgeWidth;
	}

	/**
	 * @param cellEdgeWidth the cellEdgeWidth to set
	 */
	public void setCellEdgeWidth(int cellEdgeWidth) {
		this.cellEdgeWidth = cellEdgeWidth;
	}

	/**
	 * @return the cellDrawOffsetX
	 */
	public int getCellDrawOffsetX() {
		return cellDrawOffsetX;
	}

	/**
	 * @param cellDrawOffsetX the cellDrawOffsetX to set
	 */
	public void setCellDrawOffsetX(int cellDrawOffsetX) {
		this.cellDrawOffsetX = cellDrawOffsetX;
	}

	/**
	 * @return the cellDrawOffsetY
	 */
	public int getCellDrawOffsetY() {
		return cellDrawOffsetY;
	}

	/**
	 * @param cellDrawOffsetY the cellDrawOffsetY to set
	 */
	public void setCellDrawOffsetY(int cellDrawOffsetY) {
		this.cellDrawOffsetY = cellDrawOffsetY;
	}

	/**
	 * @return the fontColor
	 */
	public Color getFontColor() {
		return fontColor;
	}

	/**
	 * @param fontColor the fontColor to set
	 */
	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}

	/**
	 * @return the container
	 */
	public GameContainer getContainer() {
		return container;
	}

	/**
	 * @param container the container to set
	 */
	public void setContainer(GameContainer container) {
		this.container = container;
	}

	/**
	 * @return the autoAdjustImage
	 */
	public boolean isAutoAdjustImage() {
		return autoAdjustImage;
	}

	/**
	 * @param autoAdjustImage the autoAdjustImage to set
	 */
	public void setAutoAdjustImage(boolean autoAdjustImage) {
		this.autoAdjustImage = autoAdjustImage;
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
		setInputActive(clickable);
	}
	
	/**
	 * Returns the width of the current cell text.
	 * @return cell text width the cell text width
	 */
	public int getCellTextWidth(){
		return this.cellFont.getWidth(this.cellText);
		
	}
	
	/**
	 * Returns the height of the current cell text.
	 * @return cell text height the cell text height
	 */
	public int getCellTextHeight(){
		return this.cellFont.getHeight(this.cellText);
		
	}
	
	/**
	 * Returns the width and height of the current cell text.
	 * @return cell text dimensions the cell texts width and height
	 */
	public int[] getCellTextDimensions(){
		int[] wh = {this.cellFont.getWidth(this.cellText),this.cellFont.getHeight(this.cellText)};
		return wh;
		
	}
	
	/**
	 * @return the bold
	 */
	public boolean isBold() {
		return bold;
	}

	/**
	 * @param bold the bold to set
	 */
	public void setBold(boolean bold) {
		this.bold = bold;
	}

	/**
	 * @return the italics
	 */
	public boolean isItalics() {
		return italics;
	}

	/**
	 * @param italics the italics to set
	 */
	public void setItalics(boolean italics) {
		this.italics = italics;
	}
	
}
