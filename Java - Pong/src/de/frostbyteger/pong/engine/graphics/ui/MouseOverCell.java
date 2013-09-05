package de.frostbyteger.pong.engine.graphics.ui;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 * Original Class by kevglass from Slick Devteam.
 * Reused and changed by Kevin Kuegler
 * 
 * Creates a MouseOverCellArea in which the mouse listener reacts to.
 * Either the area lights up if the mouse is in the area and flashes also up
 * if the mouse listener returns a mouseclick
 * The area is fully customizable.
 * 
 * @author Kevin Kuegler
 *
 */
public class MouseOverCell extends de.frostbyteger.pong.engine.graphics.ui.AbstractComponent{
	
	// Input constants
	private static final int MOUSE_NONE = 1;
	private static final int MOUSE_DOWN = 2;
	private static final int MOUSE_OVER = 3;
	
	// Input booleans
	private boolean mouseOver;
	private boolean mouseDown;
	private boolean mouseUp;
	
	// Area options
	private Shape area;
	private boolean areaFilled = false;
	private int state = MOUSE_NONE;

	// Additional audio-visual effects
	private Color normalColor = Color.white;
	private Color mouseOverColor = Color.white;
	private Color mouseDownColor = Color.white;
	private Color currentColor = Color.white;
	private Sound mouseOverSound;
	private Sound mouseDownSound;
	
	// Animation options
	private Animation overAnimation;
	private boolean animationVisible    = true;
	private boolean animationCentered   = true; //TODO: Add functionality in render method for centered/left/right
	private boolean animationLeft       = false;
	private boolean animationRight      = false;
	private boolean animationAutoAdjust = false;
	private int animationX;
	private int animationY;
	private float animationWidth;
	private float animationHeight;


	/**
	 * 
	 * @param container
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param listener
	 */
	public MouseOverCell(GameContainer container, int x, int y, float width, float height, ComponentListener listener) {
		super(container);
		this.area = new Rectangle(x, y, width, height);
		this.addListener(listener);
		
		state = MOUSE_NONE;
		Input input = container.getInput();
		mouseOver = area.contains(input.getMouseX(), input.getMouseY());
		mouseDown = input.isMouseButtonDown(0);
		this.updateArea();
	}
	
	
	/**
	 * 
	 * @param container
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param animation
	 * @param durations
	 * @param autoUpdate
	 * @param listener
	 */
	public MouseOverCell(GameContainer container, int x, int y, float width, float height, Image[] animation, int[] durations, boolean autoUpdate, ComponentListener listener) {
		super(container);
		this.area = new Rectangle(x, y, width, height);
		this.overAnimation = new Animation(animation, durations, autoUpdate);
		this.animationX = x;
		this.animationY = y;
		this.animationWidth = this.overAnimation.getWidth();
		this.animationHeight = this.overAnimation.getHeight();
		this.addListener(listener);
		
		state = MOUSE_NONE;
		Input input = container.getInput();
		mouseOver = area.contains(input.getMouseX(), input.getMouseY());
		mouseDown = input.isMouseButtonDown(0);
		this.updateArea();
		
	}
	
	/**
	 * 
	 * @param container
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param animation
	 * @param duration
	 * @param autoUpdate
	 * @param listener
	 */
	public MouseOverCell(GameContainer container, int x, int y, float width, float height, SpriteSheet animation, int duration, boolean autoUpdate, ComponentListener listener) {
		super(container);
		this.area = new Rectangle(x, y, width, height);
		this.overAnimation = new Animation(animation, duration);
		this.overAnimation.setAutoUpdate(autoUpdate);
		this.animationX = x;
		this.animationY = y;
		this.animationWidth = this.overAnimation.getWidth();
		this.animationHeight = this.overAnimation.getHeight();
		this.addListener(listener);
		
		state = MOUSE_NONE;
		Input input = container.getInput();
		mouseOver = area.contains(input.getMouseX(), input.getMouseY());
		mouseDown = input.isMouseButtonDown(0);
		this.updateArea();
		
	}
	
	/**
	 * 
	 * @param container
	 * @param g
	 */
	public void render(GameContainer container, Graphics g){
		if(areaFilled == true){
			g.setColor(currentColor);
			g.fill(area);
		}else{
			g.draw(area);
		}
		if(overAnimation != null){

			if(animationVisible == true){
				if(animationAutoAdjust == true){
					overAnimation.draw(area.getX(), area.getY(), area.getWidth(), area.getHeight());
				}else{
					overAnimation.draw(animationX, animationY, animationWidth, animationHeight);
				}
			}
		}
		updateArea();
	}

	/**
	 * 
	 */
	public void updateArea() {
		if (!mouseOver) {
			currentColor = normalColor;
			state = MOUSE_NONE;
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
		state = MOUSE_NONE;
	}
	
	/**
	 * 
	 */
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		mouseOver = area.contains(newx, newy);
	}
	
	/**
	 * 
	 */
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		mouseMoved(oldx, oldy, newx, newy);
	}
	
	/**
	 * 
	 */
	public void mousePressed(int button, int mx, int my) {
		mouseOver = area.contains(mx, my);
		if (button == 0) {
			mouseDown = true; 
		}
	}
	
	/**
	 * 
	 */
	public void mouseReleased(int button, int mx, int my) {
		mouseOver = area.contains(mx, my);
		if (button == 0) {
			mouseDown = false; 
		}
	}
	
	/**
	 * @return the area
	 */
	public Shape getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(Shape area) {
		this.area = area;
	}

	/**
	 * @return the areaWidth
	 */
	public float getAreaWidth() {
		return area.getWidth();
	}

	/**
	 * @return the areaHeight
	 */
	public float getAreaHeight() {
		return area.getHeight();
	}

	/**
	 * @return the areaFilled
	 */
	public boolean isAreaFilled() {
		return areaFilled;
	}

	/**
	 * @param areaFilled the areaFilled to set
	 */
	public void setAreaFilled(boolean areaFilled) {
		this.areaFilled = areaFilled;
	}
	
	/**
	 * 
	 * @return the x
	 */
	public float getX() {
		return area.getX();
	}

	/**
	 * 
	 * @param x the x to set
	 */
	public void setX(float x) {
		area.setX(x);
	}
	
	/**
	 * 
	 * @return the y
	 */
	public float getY() {
		return area.getY();
	}
	
	/**
	 * 
	 * @param y the y to set
	 */
	public void setY(float y) {
		area.setY(y);
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * @return the overAnimation
	 */
	public Animation getOverAnimation() {
		return overAnimation;
	}

	/**
	 * @param overAnimation the overAnimation to set
	 */
	public void setOverAnimation(Animation overAnimation) {
		this.overAnimation = overAnimation;
	}

	/**
	 * @return the animationCentered
	 */
	public boolean isAnimationCentered() {
		return animationCentered;
	}

	/**
	 * @param animationCentered the animationCentered to set
	 */
	public void setAnimationCentered(boolean animationCentered) {
		this.animationCentered = animationCentered;
	}

	/**
	 * @return the animationLeft
	 */
	public boolean isAnimationLeft() {
		return animationLeft;
	}

	/**
	 * @param animationLeft the animationLeft to set
	 */
	public void setAnimationLeft(boolean animationLeft) {
		this.animationLeft = animationLeft;
	}

	/**
	 * @return the animationRight
	 */
	public boolean isAnimationRight() {
		return animationRight;
	}

	/**
	 * @param animationRight the animationRight to set
	 */
	public void setAnimationRight(boolean animationRight) {
		this.animationRight = animationRight;
	}

	/**
	 * @return the animationVisible
	 */
	public boolean isAnimationVisible() {
		return animationVisible;
	}

	/**
	 * @param animationVisible the animationVisible to set
	 */
	public void setAnimationVisible(boolean animationVisible) {
		this.animationVisible = animationVisible;
	}

	/**
	 * @return the animationAutoAdjust
	 */
	public boolean isAnimationAutoAdjust() {
		return animationAutoAdjust;
	}

	/**
	 * @param animationAutoAdjust the animationAutoAdjust to set
	 */
	public void setAnimationAutoAdjust(boolean animationAutoAdjust) {
		this.animationAutoAdjust = animationAutoAdjust;
	}

	/**
	 * @return the animationX
	 */
	public int getAnimationX() {
		return animationX;
	}

	/**
	 * @param animationX the animationX to set
	 */
	public void setAnimationX(int animationX) {
		this.animationX = animationX;
	}

	/**
	 * @return the animationY
	 */
	public int getAnimationY() {
		return animationY;
	}

	/**
	 * @param animationY the animationY to set
	 */
	public void setAnimationY(int animationY) {
		this.animationY = animationY;
	}

	/**
	 * @return the animationWidth
	 */
	public float getAnimationWidth() {
		return animationWidth;
	}

	/**
	 * @param animationWidth the animationWidth to set
	 */
	public void setAnimationWidth(float animationWidth) {
		this.animationWidth = animationWidth;
	}

	/**
	 * @return the animationHeight
	 */
	public float getAnimationHeight() {
		return animationHeight;
	}

	/**
	 * @param animationHeight the animationHeight to set
	 */
	public void setAnimationHeight(float animationHeight) {
		this.animationHeight = animationHeight;
	}

	/**
	 * @return the normalColor
	 */
	public Color getNormalColor() {
		return normalColor;
	}

	/**
	 * @param normalColor the normalColor to set
	 */
	public void setNormalColor(Color normalColor) {
		this.normalColor = normalColor;
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
	 * @return the mouseOverColor
	 */
	public Color getMouseOverColor() {
		return mouseOverColor;
	}

	/**
	 * @param mouseOverColor the mouseOverColor to set
	 */
	public void setMouseOverColor(Color mouseOverColor) {
		this.mouseOverColor = mouseOverColor;
	}

	/**
	 * @return the mouseDownColor
	 */
	public Color getMouseDownColor() {
		return mouseDownColor;
	}

	/**
	 * @param mouseDownColor the mouseDownColor to set
	 */
	public void setMouseDownColor(Color mouseDownColor) {
		this.mouseDownColor = mouseDownColor;
	}

	/**
	 * @return the mouseOver
	 */
	public boolean isMouseOver() {
		return mouseOver;
	}

	/**
	 * @param mouseOver the mouseOver to set
	 */
	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	/**
	 * @return the mouseDown
	 */
	public boolean isMouseDown() {
		return mouseDown;
	}

	/**
	 * @param mouseDown the mouseDown to set
	 */
	public void setMouseDown(boolean mouseDown) {
		this.mouseDown = mouseDown;
	}

	/**
	 * @return the mouseUp
	 */
	public boolean isMouseUp() {
		return mouseUp;
	}

	/**
	 * @param mouseUp the mouseUp to set
	 */
	public void setMouseUp(boolean mouseUp) {
		this.mouseUp = mouseUp;
	}

	/**
	 * @return the mouseOverSound
	 */
	public Sound getMouseOverSound() {
		return mouseOverSound;
	}

	/**
	 * @param mouseOverSound the mouseOverSound to set
	 */
	public void setMouseOverSound(Sound mouseOverSound) {
		this.mouseOverSound = mouseOverSound;
	}

	/**
	 * @return the mouseDownSound
	 */
	public Sound getMouseDownSound() {
		return mouseDownSound;
	}

	/**
	 * @param mouseDownSound the mouseDownSound to set
	 */
	public void setMouseDownSound(Sound mouseDownSound) {
		this.mouseDownSound = mouseDownSound;
	}

}
