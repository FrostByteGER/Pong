package de.frostbyteger.pong.engine;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 * @author Kevin
 *
 */
public class Pad{

	protected float velocity;
	
	protected int points;
	protected int keynr;
	protected int spinspeed;
	
	protected Rectangle pad;
	
	private final int WIDTH = 10;
	private final int HEIGHT = 100;
	
	
	public Pad(int x, int y, float velocity, int keynr){
		this.velocity = velocity;
		this.keynr = keynr;
		this.pad = new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	public void addPoint(){
		this.points += 1;
	}
	
	public void addPoints(int points){
		this.points += points;
	}

	public Rectangle getShape(){
		return pad;	
	}
	
	public Rectangle getPad() {
		return pad;
	}

	public void setPad(Rectangle pad) {
		this.pad = pad;
	}

	/**
	 * @return the speed
	 */
	public float getVelocity() {
		return velocity;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}

	/**
	 * @return the keynr
	 */
	public int getKeynr() {
		return keynr;
	}

	/**
	 * @param keynr the keynr to set
	 */
	public void setKeynr(int keynr) {
		this.keynr = keynr;
	}

	/**
	 * @return the spinspeed
	 */
	public int getSpinspeed() {
		return spinspeed;
	}

	/**
	 * @param spinspeed the spinspeed to set
	 */
	public void setSpinspeed(int spinspeed) {
		this.spinspeed = spinspeed;
	}

	/**
	 * @return the WIDTH
	 */
	public int getWIDTH() {
		return WIDTH;
	}

	/**
	 * @return the HEIGHT
	 */
	public int getHEIGHT() {
		return HEIGHT;
	}
	
    public boolean intersects(Shape shape) {
        return this.pad.intersects(shape);

  }

	public void draw(Graphics g){
		g.fill(pad);
	}

}
