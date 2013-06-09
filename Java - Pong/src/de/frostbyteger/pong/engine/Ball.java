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
	protected float velocity;
	protected Vector2f vector;
	protected int radius;
	protected int spin; // TODO
	protected Random rndm;
	protected float estimatedY;
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param radius
	 */
	public Ball(int x, int y, int radius) {
		rndm = new Random();
		this.ball = new Circle(x, y, radius);
		this.vector = new Vector2f();
		this.velocity = 0.02f;
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
	 * @param ball the ball to set
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
	 * @param velocity the velocity to set
	 */
	public void setVelocity(float velocity) {
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
	 * @param radius the radius to set
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	public void draw(Graphics g) {
		//TODO: Set Ballcolor if it hits the pads
		g.fill(ball);
	}
	
	public float getRoundedEtimatedY(){
		return Math.round(this.estimatedY);
	}

	/**
	 * 
	 */
	public void calcDirection() {
		float speedX = 1.5f;
		float speedY = (float) rndm.nextDouble();
		if(speedY < 0.5){
			speedY += 5.0;
		}
		this.vector.set(-speedX, speedY = 0.0f);
/*
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
*/
	}
	
	/**
	 * 
	 * @param vector2f
	 * @param x
	 * @param y
	 */
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

	/**
	 * 
	 * @param acceleration
	 * @param delta
	 * @param lastcollision
	 */
	public void addVelocity(double acceleration, int delta, Border lastcollision) {
		float hip = (float) (acceleration * delta + velocity) / 100;
		if(velocity < 3.5){
			if (vector.getX() <= 5 && lastcollision == Border.LEFT || vector.getX() <= 5  && lastcollision == Border.RIGHT) {
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
	
	/**
	 * 
	 * @param acceleration
	 * @param delta
	 * @param lastcollision
	 */
	public void addVelocityGravity(double acceleration, int delta, Border lastcollision) {
		velocity += (float) (acceleration * (delta/100));
		if(velocity < 3.5){
			if (vector.getX() <= 5) {
				if (vector.getX() < 0) {
					vector.set(vector.getX() - (velocity * (delta/100)), vector.getY());
				} else {
					vector.set(vector.getX() + (velocity * (delta/100)), vector.getY());
				}
				if (vector.getY() < 0) {
					vector.set(vector.getX(), vector.getY() + (velocity * (delta/10)));
				} else {
					vector.set(vector.getX(), vector.getY() + (velocity * (delta/10)));
				}
	
			}
		}
	}
	
	/**
	 * Adds the given acceleration to the balls speed. Negative values decrease the speed.
	 * @param acceleration
	 * @param delta
	 */
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
	
	/**
	 * 
	 * @param acceleration
	 */
	public void addSpin(float acceleration){
		if (vector.getX() < 0) {
			if(rndm.nextBoolean()){
				vector.set(vector.getX() - acceleration, vector.getY());
			}else{
				vector.set(vector.getX() + acceleration, vector.getY() * 0.05f);
			}
		} else {
			if(rndm.nextBoolean()){
				vector.set(vector.getX() + acceleration, vector.getY());
			}else{
				vector.set(vector.getX() - acceleration, vector.getY() * 0.05f);
			}
		}
		
		if (vector.getY() < 0) {
			if(rndm.nextBoolean()){
				vector.set(vector.getX(), vector.getY() - acceleration);
			}else{
				vector.set(vector.getX(), vector.getY() + acceleration * 0.05f);
			}
		} else {
			if(rndm.nextBoolean()){
				vector.set(vector.getX(), vector.getY() + acceleration);
			}else{
				vector.set(vector.getX(), vector.getY() - acceleration * 0.05f);
			}
		}
	}
}
