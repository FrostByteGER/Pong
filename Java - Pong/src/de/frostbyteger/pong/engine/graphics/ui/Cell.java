package de.frostbyteger.pong.engine.graphics.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;

public class Cell{
	
	// Objects and Paths
	private UnicodeFont cellFont   = null;
	private Image cellImage        = null;
	private Object parentComponent = null;
	private String fontPath;
	private String imagePath;

	
	// Font options
	private boolean centered = true;
	private boolean left     = false;
	private boolean right    = false;
	private boolean bold     = false;
	private boolean italic   = false;
	private int size;
	
	// Cell options
	private boolean autoAdjust = false;
	private boolean active = true;
	private boolean selected = false;
	private boolean highlighted = false;
	private Color backgroundColor = Color.black;
	private int width;
	private int height;
	private int cellX;
	private int cellY;
	
	public Cell() {
	}

	/**
	 * @param width
	 * @param height
	 */
	public Cell(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * @param parentComponent
	 * @param width
	 * @param height
	 */
	public Cell(Object parentComponent, int width, int height) {
		this.parentComponent = parentComponent;
		this.width = width;
		this.height = height;
	}
	/**
	 * 
	 * @param parentComponent
	 * @param width
	 * @param height
	 * @param cellX
	 * @param cellY
	 */
	public Cell(Object parentComponent, int width, 
			int height, int cellX, int cellY) {
		this.parentComponent = parentComponent;
		this.width = width;
		this.height = height;
		this.cellX = cellX;
		this.cellY = cellY;
	}

	/**
	 * 
	 * @param imagePath
	 * @param parentComponent
	 * @param width
	 * @param height
	 * @param cellX
	 * @param cellY
	 * @throws SlickException
	 */
	public Cell(String imagePath, Object parentComponent, 
			int width, int height, int cellX,
			int cellY) throws SlickException {
		this.cellImage = new Image(imagePath, false);
		this.imagePath = imagePath;
		this.parentComponent = parentComponent;
		this.width = width;
		this.height = height;
		this.cellX = cellX;
		this.cellY = cellY;
	}

	/**
	 * 
	 * @param fontPath
	 * @param imagePath
	 * @param size
	 * @param autoAdjust
	 * @param width
	 * @param height
	 * @throws SlickException
	 */
	public Cell(String fontPath, String imagePath, int size,
			boolean autoAdjust, int width, int height) throws SlickException {
		this.cellFont = new UnicodeFont(fontPath, size, bold, italic);
		this.fontPath = fontPath;
		this.cellImage = new Image(imagePath, false);
		this.imagePath = imagePath;
		this.size = size;
		this.autoAdjust = autoAdjust;
		this.width = width;
		this.height = height;
	}

	/**
	 * 
	 * @param fontPath
	 * @param imagePath
	 * @param bold
	 * @param italic
	 * @param size
	 * @param width
	 * @param height
	 * @throws SlickException
	 */
	public Cell(String fontPath, String imagePath, boolean bold,
			boolean italic, int size, int width, int height) throws SlickException {
		this.cellFont = new UnicodeFont(fontPath, size, bold, italic);
		this.fontPath = fontPath;
		this.cellImage = new Image(imagePath, false);
		this.imagePath = imagePath;
		this.bold = bold;
		this.italic = italic;
		this.size = size;
		this.width = width;
		this.height = height;
	}

	/**
	 * 
	 * @param fontPath
	 * @param imagePath
	 * @param parentComponent
	 * @param bold
	 * @param italic
	 * @param size
	 * @param autoAdjust
	 * @param width
	 * @param height
	 * @throws SlickException
	 */
	public Cell(String fontPath, String imagePath, Object parentComponent,
			boolean bold, boolean italic, int size, boolean autoAdjust,
			int width, int height) throws SlickException {
		this.cellFont = new UnicodeFont(fontPath, size, bold, italic);
		this.fontPath = fontPath;
		this.cellImage = new Image(imagePath, false);
		this.imagePath = imagePath;
		this.parentComponent = parentComponent;
		this.bold = bold;
		this.italic = italic;
		this.size = size;
		this.autoAdjust = autoAdjust;
		this.width = width;
		this.height = height;
	}

	/**
	 * 
	 * @param fontPath
	 * @param parentComponent
	 * @param centered
	 * @param left
	 * @param right
	 * @param bold
	 * @param italic
	 * @param size
	 * @param autoAdjust
	 * @param width
	 * @param height
	 * @param cellX
	 * @param cellY
	 * @throws SlickException
	 */
	public Cell(String fontPath, Object parentComponent, boolean centered,
			boolean left, boolean right, boolean bold, boolean italic,
			int size, boolean autoAdjust, int width, int height, int cellX,
			int cellY) throws SlickException {
		this.cellFont = new UnicodeFont(fontPath, size, bold, italic);
		this.fontPath = fontPath;
		this.parentComponent = parentComponent;
		this.centered = centered;
		this.left = left;
		this.right = right;
		this.bold = bold;
		this.italic = italic;
		this.size = size;
		this.autoAdjust = autoAdjust;
		this.width = width;
		this.height = height;
		this.cellX = cellX;
		this.cellY = cellY;
	}
	
	/**
	 * 
	 * @param fontPath
	 * @param imagePath
	 * @param parentComponent
	 * @param centered
	 * @param left
	 * @param right
	 * @param bold
	 * @param italic
	 * @param size
	 * @param autoAdjust
	 * @param width
	 * @param height
	 * @param cellX
	 * @param cellY
	 * @throws SlickException
	 */
	public Cell(String fontPath, String imagePath, Object parentComponent,
			boolean centered, boolean left, boolean right, boolean bold,
			boolean italic, int size, boolean autoAdjust, int width,
			int height, int cellX, int cellY) throws SlickException {
		this.cellFont = new UnicodeFont(fontPath, size, bold, italic);
		this.fontPath = fontPath;
		this.cellImage = new Image(imagePath, false);
		this.imagePath = imagePath;
		this.parentComponent = parentComponent;
		this.centered = centered;
		this.left = left;
		this.right = right;
		this.bold = bold;
		this.italic = italic;
		this.size = size;
		this.autoAdjust = autoAdjust;
		this.width = width;
		this.height = height;
		this.cellX = cellX;
		this.cellY = cellY;
	}
	
	/**
	 * Use this method if you want to change the actual fontsettings
	 * like size, bold and italics.
	 * The fontclass has no setter for these settings so you have
	 * to overwrite the old font with a new one.
	 * @throws SlickException
	 */
	public void createNewFont() throws SlickException{
		cellFont = new UnicodeFont(fontPath, size, bold, italic);
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

}
