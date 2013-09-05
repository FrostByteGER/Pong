package de.frostbyteger.pong.engine.graphics.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 * @author Kevin
 *
 */
public class MouseOverCell extends de.frostbyteger.pong.engine.graphics.ui.AbstractComponent{
	
	private static final int NORMAL     = 1;
	private static final int MOUSE_DOWN = 2;
	private static final int MOUSE_OVER = 3;
	
	private float height;
	private float width;

	private Color normalColor = Color.white;
	private Color mouseOverColor = Color.white;
	private Color mouseDownColor = Color.white;

	private Sound mouseOverSound;
	private Sound mouseDownSound;

	private Shape area;

	private Color currentColor = Color.white;

	private boolean over;
	private boolean mouseDown;
	private boolean mouseUp;
	
	private boolean filled = false;
	
	private int state = NORMAL;


	
	/**
	 * 
	 */
	public MouseOverCell(GameContainer container, int x, int y, float width, float height, ComponentListener listener) {
		super(container);
		this.width = width;
		this.height = height;
		this.area = new Rectangle(x, y, width, height);
		addListener(listener);
		
		state = NORMAL;
		Input input = container.getInput();
		over = area.contains(input.getMouseX(), input.getMouseY());
		mouseDown = input.isMouseButtonDown(0);
		updateArea();
	}

	/**
	 * Set the x coordinate of this area
	 * 
	 * @param x The new x coordinate of this area
	 */
	public void setX(float x) {
		area.setX(x);
	}
	
	/**
	 * Set the y coordinate of this area
	 * 
	 * @param y The new y coordinate of this area
	 */
	public void setY(float y) {
		area.setY(y);
	}
	
	/**
	 * Returns the position in the X coordinate
	 * 
	 * @return x
	 */
	public int getX() {
		return (int) area.getX();
	}

	/**
	 * Returns the position in the Y coordinate
	 * 
	 * @return y
	 */
	public int getY() {
		return (int) area.getY();
	}
	
	/**
	 * Set the normal color used on the image in the default state
	 * 
	 * @param color
	 *            The color to be used
	 */
	public void setNormalColor(Color color) {
		normalColor = color;
	}

	/**
	 * Set the color to be used when the mouse is over the area
	 * 
	 * @param color
	 *            The color to be used when the mouse is over the area
	 */
	public void setMouseOverColor(Color color) {
		mouseOverColor = color;
	}

	/**
	 * Set the color to be used when the mouse is down the area
	 * 
	 * @param color
	 *            The color to be used when the mouse is down the area
	 */
	public void setMouseDownColor(Color color) {
		mouseDownColor = color;
	}
	
	public void render(GameContainer container, Graphics g){
		if(filled == true){
			g.setColor(currentColor);
			g.fill(area);
		}else{
			g.draw(area);
		}
		updateArea();
	}

	public void updateArea() {
		if (!over) {
			currentColor = normalColor;
			state = NORMAL;
			mouseUp = false;
		} else {
			if (mouseDown) {
				if ((state != MOUSE_DOWN) && (mouseUp)) {
					if (mouseDownSound != null) {
						mouseDownSound.play();
					}
					currentColor = mouseDownColor;
					state = MOUSE_DOWN;
					
					notifyListeners();
					mouseUp = false;
				}
				
				return;
			} else {
				mouseUp = true;
				if (state != MOUSE_OVER) {
					if (mouseOverSound != null) {
						mouseOverSound.play();
					}
					currentColor = mouseOverColor;
					state = MOUSE_OVER;
				}
			}
		}

		mouseDown = false;
		state = NORMAL;
	}

	/**
	 * Set the mouse over sound effect
	 * 
	 * @param sound
	 *            The mouse over sound effect
	 */
	public void setMouseOverSound(Sound sound) {
		mouseOverSound = sound;
	}

	/**
	 * Set the mouse down sound effect
	 * 
	 * @param sound
	 *            The mouse down sound effect
	 */
	public void setMouseDownSound(Sound sound) {
		mouseDownSound = sound;
	}

	/**
	 * @see org.newdawn.slick.util.InputAdapter#mouseMoved(int, int, int, int)
	 */
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		over = area.contains(newx, newy);
	}
	
	/**
	 * @see org.newdawn.slick.util.InputAdapter#mouseDragged(int, int, int, int)
	 */
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		mouseMoved(oldx, oldy, newx, newy);
	}

	/**
	 * @see org.newdawn.slick.util.InputAdapter#mousePressed(int, int, int)
	 */
	public void mousePressed(int button, int mx, int my) {
		over = area.contains(mx, my);
		if (button == 0) {
			mouseDown = true; 
		}
	}
	
	/**
	 * @see org.newdawn.slick.util.InputAdapter#mouseReleased(int, int, int)
	 */
	public void mouseReleased(int button, int mx, int my) {
		over = area.contains(mx, my);
		if (button == 0) {
			mouseDown = false; 
		}
	}

	/**
	 * @see org.newdawn.slick.gui.AbstractComponent#getHeight()
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * @see org.newdawn.slick.gui.AbstractComponent#getWidth()
	 */
	public float getWidth() {
		return width;
	}
	
	/**
	 * Check if the mouse is over this area
	 * 
	 * @return True if the mouse is over this area
	 */
	public boolean isMouseOver() {
		return over;
	}

	/**
	 * @return the currentColor
	 */
	public Color getCurrentColor() {
		return currentColor;
	}

	/**
	 * @param currentColor the currentColor to set
	 */
	public void setCurrentColor(Color currentColor) {
		this.currentColor = currentColor;
	}

	/**
	 * @return the filled
	 */
	public boolean isFilled() {
		return filled;
	}

	/**
	 * @param filled the filled to set
	 */
	public void setFilled(boolean filled) {
		this.filled = filled;
	}

}
