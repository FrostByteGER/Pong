package de.frostbyteger.pong.engine;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 * @author Kevin
 *
 */
public class Pad{

	private float velocity  = 0.0f;
	private float spinSpeed = 0.0f;
	private int width       = 10;
	private int height      = 100;
	
	private boolean collided = false;
		
	private Rectangle pad;
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public Pad(int x, int y){
		this.pad = new Rectangle(x, y, width, height);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param velocity
	 */
	public Pad(int x, int y, float velocity){
		this.pad = new Rectangle(x, y, width, height);
		this.velocity = velocity;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Pad(int x, int y, int width, int height) {
		this.pad = new Rectangle(x, y, width, height);
		this.width = width;
		this.height = height;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param velocity
	 */
	public Pad(int x, int y, int width, int height, boolean collided) {
		this.pad = new Rectangle(x, y, width, height);
		this.width = width;
		this.height = height;
		this.collided = collided;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param velocity
	 */
	public Pad(int x, int y, int width, int height, float velocity) {
		this.pad = new Rectangle(x, y, width, height);
		this.velocity = velocity;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param velocity
	 */
	public Pad(int x, int y, int width, int height, float velocity, float spinSpeed) {
		this.pad = new Rectangle(x, y, width, height);
		this.velocity = velocity;
		this.spinSpeed = 0.0f;
		this.width = width;
		this.height = height;
	}
	
	public float getPadX(){
		return pad.getX();
	}
	
	public void setPadX(float x){
		pad.setX(x);
	}
	
	public float getPadY(){
		return pad.getY();
	}
	
	public void setPadY(float y){
		pad.setY(y);
	}
	
	public float getPadCenterX(){
		return pad.getCenterX();
	}
	
	public void setPadCenterX(float centerX){
		pad.setCenterX(centerX);
	}
	
	public float getPadCenterY(){
		return pad.getCenterY();
	}
	
	public void setPadCenterY(float centerY){
		pad.setCenterY(centerY);
	}
	
	public float getPadMaxX(){
		return pad.getMaxX();
	}
	
	public float getPadMaxY(){
		return pad.getMaxY();
	}
	
	public float getPadMinX(){
		return pad.getMinX();
	}
	
	public float getPadMinY(){
		return pad.getMinY();
	}

	public int getPadWidth() {
		return width;
	}

	public void setPadWidth(int width) {
		this.width = width;
	}

	public int getPadHeight() {
		return height;
	}

	public void setPadHeight(int height) {
		this.height = height;
	}

	public boolean isCollided() {
		return collided;
	}

	public void setCollided(boolean collided) {
		this.collided = collided;
	}
	
	public Rectangle getPad() {
		return pad;
	}

	public void setPad(Rectangle pad) {
		this.pad = pad;
	}

	public float getPadVelocity() {
		return velocity;
	}

	public void setPadVelocity(float velocity) {
		this.velocity = velocity;
	}

	public float getSpinSpeed() {
		return spinSpeed;
	}

	public void setSpinSpeed(float speed) {
		this.spinSpeed = speed;
	}
	
	public void addSpinSpeed(float speed){
		this.spinSpeed += speed;
	}
	
	public void reduceSpinSpeed(float speed){
		this.spinSpeed -= speed;
	}
	
	public void resetSpinSpeed(){
		this.spinSpeed = 0.0f;
	}
	
    public boolean intersects(Shape shape) {
        return pad.intersects(shape);
    }

	public void draw(Graphics g){
		g.fill(pad);
	}

}
