package de.frostbyteger.pong.engine.graphics.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;

/**
 * @author Kevin
 *
 */
public class MouseOverCell extends AbstractComponent{
	
	private static final int NORMAL = 1;

	private static final int MOUSE_DOWN = 2;

	private static final int MOUSE_OVER = 3;

	private Image normalImage;
	private Image mouseOverImage;
	private Image mouseDownImage;

	private Color normalColor = Color.white;
	private Color mouseOverColor = Color.white;
	private Color mouseDownColor = Color.white;

	private Sound mouseOverSound;
	private Sound mouseDownSound;

	private Shape area;

	private Image currentImage;

	private Color currentColor;

	private boolean over;
	private boolean mouseDown;
	private boolean mouseUp;
	
	private int state = NORMAL;


	
	/**
	 * 
	 */
	public MouseOverCell(GameContainer container) {
		super(container);
	}


	/*public MouseOverArea(GUIContext container, Image image, int x, int y,
			             int width, int height, ComponentListener listener) {
		this(container,image,x,y,width,height);
		addListener(listener);
	}*/
	
	/*public MouseOverArea(GUIContext container, Image image, Shape shape) {
		super(container);

		area = shape;
		normalImage = image;
		currentImage = image;
		mouseOverImage = image;
		mouseDownImage = image;

		currentColor = normalColor;

		state = NORMAL;
		Input input = container.getInput();
		over = area.contains(input.getMouseX(), input.getMouseY());
		mouseDown = input.isMouseButtonDown(0);
		updateImage();
	}*/

	/**
	 * Moves the component.
	 * 
	 * @param x X coordinate
	 * @param y Y coordinate
	 */
	public void setLocation(float x, float y) {
		if (area != null) {
			area.setX(x);
			area.setY(y);
		}
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

	/**
	 * Set the normal image used on the image in the default state
	 * 
	 * @param image
	 *            The image to be used
	 */
	public void setNormalImage(Image image) {
		normalImage = image;
	}

	/**
	 * Set the image to be used when the mouse is over the area
	 * 
	 * @param image
	 *            The image to be used when the mouse is over the area
	 */
	public void setMouseOverImage(Image image) {
		mouseOverImage = image;
	}

	/**
	 * Set the image to be used when the mouse is down the area
	 * 
	 * @param image
	 *            The image to be used when the mouse is down the area
	 */
	public void setMouseDownImage(Image image) {
		mouseDownImage = image;
	}

	/**
	 * @see org.newdawn.slick.gui.AbstractComponent#render(org.newdawn.slick.gui.GUIContext,
	 *      org.newdawn.slick.Graphics)
	 */
	public void render(GUIContext container, Graphics g) {
		if (currentImage != null) {
			
			int xp = (int) (area.getX() + ((getWidth() - currentImage.getWidth()) / 2));
			int yp = (int) (area.getY() + ((getHeight() - currentImage.getHeight()) / 2));

			currentImage.draw(xp, yp, currentColor);
		} else {
			g.setColor(currentColor);
			g.fill(area);
		}
		updateImage();
	}

	/**
	 * Update the current normalImage based on the mouse state
	 */
	private void updateImage() {
		if (!over) {
			currentImage = normalImage;
			currentColor = normalColor;
			state = NORMAL;
			mouseUp = false;
		} else {
			if (mouseDown) {
				if ((state != MOUSE_DOWN) && (mouseUp)) {
					if (mouseDownSound != null) {
						mouseDownSound.play();
					}
					currentImage = mouseDownImage;
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
					currentImage = mouseOverImage;
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
	public int getHeight() {
		return (int) (area.getMaxY() - area.getY());
	}

	/**
	 * @see org.newdawn.slick.gui.AbstractComponent#getWidth()
	 */
	public int getWidth() {
		return (int) (area.getMaxX() - area.getX());
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
	 * Set the location of this area
	 * 
	 * @param x The x coordinate of this area
	 * @param y The y coordiante of this area
	 */
	public void setLocation(int x, int y) {
		setLocation((float) x,(float) y);
	}

}
