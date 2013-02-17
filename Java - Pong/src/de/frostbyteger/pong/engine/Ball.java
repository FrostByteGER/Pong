/**
 * 
 */
package de.frostbyteger.pong.engine;

import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

import de.frostbyteger.pong.core.Pong;

/**
 * @author Kevin
 * 
 */
public class Ball {

	protected Circle ball;
	protected double velocity;
	protected Vector2f vector;
	protected int radius;
	protected int spin; // TODO
	protected Random rndm;
	protected float estimatedY;
	

	public Ball(int x, int y, int radius) {
		rndm = new Random();
		this.ball = new Circle(x, y, radius);
		this.vector = new Vector2f();
		this.velocity = 0.02;
		this.radius = radius;
		calcDirection();
	}

	/**
	 * @return the ball
	 */
	public Circle getBall() {
		return ball;
	}

	/**
	 * @param ball
	 *            the ball to set
	 */
	public void setBall(Circle ball) {
		this.ball = ball;
	}

	/**
	 * @return the velocity
	 */
	public double getVelocity() {
		return velocity;
	}

	/**
	 * @param velocity
	 *            the velocity to set
	 */
	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	public Vector2f getVector() {
		return vector;
	}

	public float getVectorX() {
		return vector.getX();
	}

	public float getVectorY() {
		return vector.getY();
	}

	public void setVector(Vector2f vector) {
		this.vector = vector;
	}

	public void setVectorXY(float x, float y) {
		this.vector.set(x, y);
	}

	public int getSpin() {
		return spin;
	}

	public void setSpin(int spin) {
		this.spin = spin;
	}

	public Circle getShape() {
		return ball;
	}

	/**
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * @param radius
	 *            the radius to set
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	public void draw(Graphics g) {
		//TODO: Set Ballcolor if it hits the pads
		g.fill(ball);
	}
	
	public float getEtimatedY(){
		return this.estimatedY;
	}

	public void calcDirection() {
		float speedX = 1.5f;
		float speedY = (float) rndm.nextDouble();
		if(speedY < 0.5){
			speedY += 5.0;
		}

		if (rndm.nextBoolean()) {
			if (rndm.nextBoolean()) {
				this.vector.set(-speedX, -speedY);

			} else {
				this.vector.set(-speedX, speedY);
			}

		} else {
			if (rndm.nextBoolean()) {
				this.vector.set(speedX, -speedY);
			} else {
				this.vector.set(speedX, speedY);
			}
		} 

	}
	
	public void calcTrajectory(Vector2f vector2f, float x, float y){
			x += vector2f.getX();
			y += vector2f.getY();
			if (y <= 0) {
				vector2f.set(vector2f.getX(), -vector2f.getY());
			}

			if (y >= Pong.resY) { 
				vector2f.set(vector2f.getX(), -vector2f.getY());
			}
			
			if(x >= Pong.resX - 20){
				estimatedY = y;
				return;
			}else{
				calcTrajectory(vector2f, x,y);				
			}

	}

	public void addVelocity(double acceleration, int delta, Border lastcollision) {
		float hip = (float) (acceleration * delta + velocity) / 100;
		if(velocity < 3.5){
			if (vector.getX() <= 5 && lastcollision == Border.LEFT ||vector.getX() <= 5  && lastcollision == Border.RIGHT) { //TODO Static Declaration
				velocity += 0.005;
				if (vector.getX() < 0) {
					vector.set(vector.getX() - hip, vector.getY());
				} else {
					vector.set(vector.getX() + hip, vector.getY());
				}
				if (vector.getY() < 0) {
					vector.set(vector.getX(), vector.getY() - hip);
				} else {
					vector.set(vector.getX(), vector.getY() + hip);
				}
	
			}
		}
	}
	
	public void addDebugVelocity(float acceleration, int delta){
		float hip = (float) (acceleration * delta + velocity) / 100;
		if (vector.getX() < 0) {
			vector.set(vector.getX() - hip, vector.getY());
		} else {
			vector.set(vector.getX() + hip, vector.getY());
		}
		if (vector.getY() < 0) {
			vector.set(vector.getX(), vector.getY() - hip);
		} else {
			vector.set(vector.getX(), vector.getY() + hip);
		}
	}
}
