package de.frostbyteger.pong.engine.graphics.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Rectangle;

import de.frostbyteger.pong.engine.graphics.FontHelper;

public class Cell{
	
	// Objects and Paths
	private Rectangle cell         = null;
	private Rectangle cellBorder   = null;
	private UnicodeFont cellFont   = null;
	private Image cellImage        = null;
	private Object parentComponent = null;
	private String cellText        = "";
	private String fontPath        = "";
	private String imagePath       = "";
	
	// Font options
	private boolean centered = true;
	private boolean left     = false;
	private boolean right    = false;
	private boolean bold     = false;
	private boolean italic   = false;
	private int size;
	
	// Cell options
	private boolean active               = true;
	private boolean autoAdjust           = true;
	private boolean autoAdjustCellWidth  = false;
	private boolean autoAdjustCellHeight = false;	
	private boolean selected             = false;
	private boolean highlighted          = false;
	private boolean edging               = true;
	private Color backgroundColor = Color.black;
	private Color borderColor     = Color.blue;
	private int width;
	private int height;
	private int cellX = 0;
	private int cellY = 0;
	private int cellEdgeWidth = 1;
	
	/**
	 * Default constructor.
	 */
	public Cell(){
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Cell(int x, int y, int width, int height) {
		this.cell = new Rectangle(x + 1,y, width - 1, height - 1);
		this.cellBorder = new Rectangle(x, y, width, height);
		this.cellX = x;
		this.cellY = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * 
	 * @param parentComponent
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Cell(Object parentComponent, int x, int y, int width, int height) {
		this.cell = new Rectangle(x + 1,y, width - 1, height - 1);
		this.cellBorder = new Rectangle(x, y, width, height);
		this.parentComponent = parentComponent;
		this.cellX = x;
		this.cellY = y;
		this.width = width;
		this.height = height;
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
	public Cell(String fontPath, int fontSize, int x, int y, int width, int height) throws SlickException {
		this.cell = new Rectangle(x + 1,y, width - 1, height - 1);
		this.cellBorder = new Rectangle(x, y, width, height);
		this.cellFont = FontHelper.newFont(fontPath, size, bold, italic);
		this.size = fontSize;
		this.cellX = x;
		this.cellY = y;
		this.width = width;
		this.height = height;
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
	public Cell(String fontPath, int fontSize, String imagePath, int x, int y, int width, int height) throws SlickException {
		this.cell = new Rectangle(x + 1,y, width - 1, height - 1);
		this.cellBorder = new Rectangle(x, y, width, height);
		this.cellFont = FontHelper.newFont(fontPath, size, bold, italic);
		this.cellImage = new Image(imagePath);
		this.imagePath = imagePath;
		this.size = fontSize;
		this.cellX = x;
		this.cellY = y;
		this.width = width;
		this.height = height;
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
	public Cell(String fontPath, int fontSize, boolean bold, boolean italics, int x, int y, int width, int height) throws SlickException {
		this.cell = new Rectangle(x + 1,y, width - 1, height - 1);
		this.cellBorder = new Rectangle(x, y, width, height);
		this.cellFont = FontHelper.newFont(fontPath, size, bold, italics);
		this.size = fontSize;
		this.bold = bold;
		this.italic = italics;
		this.cellX = x;
		this.cellY = y;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Draws the cell while taking account of the set celloptions
	 * like auto adjustment or visibility.
	 * @param g
	 * @throws SlickException
	 */
	public void drawCell(Graphics g) throws SlickException{
		System.out.println(cellFont.getWidth(cellText));
		if(active == true){
			if(autoAdjust == true){
				if(autoAdjustCellHeight == true){
					//TODO: Fill 
				}else{
					
				}
				
				if(autoAdjustCellWidth == true){
					
				}else{
					
				}

			}
			if(edging == true){
				g.setColor(borderColor);
				g.setLineWidth(cellEdgeWidth);
				g.draw(cellBorder);
				

			}else{
				g.setColor(backgroundColor);
				g.fill(cell);

			}
			if(autoAdjust == true){
				if(cellFont.getHeight(cellText) >= cell.getHeight() - 10 || cellFont.getWidth(cellText) >= cell.getWidth() - 20) {
					float i = (float)cellFont.getWidth(cellText) / (float)size;
					System.out.println(cellFont.getWidth(cellText));
					size = (int) ((float)width / i);
					cellFont = FontHelper.newFont(fontPath, size, bold, italic);
				}
			}
			if(left == true){
				cellFont.drawString(cell.getMinX() + 1 , cell.getCenterY() - cellFont.getHeight(cellText)/2, cellText);
			}else if(centered == true){
				cellFont.drawString(cell.getCenterX() - cellFont.getWidth(cellText)/2, cell.getCenterY() - cellFont.getHeight(cellText)/2, cellText);
			}else{
				cellFont.drawString(cell.getMaxX() - cellFont.getWidth(cellText) - 1 , cell.getCenterY() - cellFont.getHeight(cellText)/2, cellText);
			}
		}else{
			return;
		}
	}


	
	/**
	 * Use this method if you want to change the actual fontsettings
	 * like size, bold and italics.
	 * The fontclass has no setter for these settings so you have
	 * to overwrite the old font with a new one.
	 * @throws SlickException
	 */
	public void createNewFont() throws SlickException{
		this.cellFont = FontHelper.newFont(fontPath, size, bold, italic);
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
	 * @param cellFont the cellFont to set
	 */
	public void setCellFont(UnicodeFont cellFont) {
		this.cellFont = cellFont;
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
	 * @param centered the centered to set
	 */
	public void setCentered(boolean centered) {
		this.centered = centered;
	}

	/**
	 * @return the left
	 */
	public boolean isLeft() {
		return left;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(boolean left) {
		this.left = left;
	}

	/**
	 * @return the right
	 */
	public boolean isRight() {
		return right;
	}

	/**
	 * @param right the right to set
	 */
	public void setRight(boolean right) {
		this.right = right;
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
	 * @return the italic
	 */
	public boolean isItalic() {
		return italic;
	}

	/**
	 * @param italic the italic to set
	 */
	public void setItalic(boolean italic) {
		this.italic = italic;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
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
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
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
	 * @return the edging
	 */
	public boolean isEdging() {
		return edging;
	}

	/**
	 * @param edging the edging to set
	 */
	public void setEdging(boolean edging) {
		this.edging = edging;
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

}
