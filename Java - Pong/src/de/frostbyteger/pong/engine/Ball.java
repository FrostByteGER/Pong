/**
 * 
 */
package de.frostbyteger.pong.engine;

import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

import de.frostbyteger.pong.start.Pong;

/**
 * @author Kevin
 * 
 */
public class Ball {

	private Circle ball;
	private float velocity;
	private float estimatedY;
	private int radius;
	private int spin;	
	private Vector2f vector;
	private Random rndm;
	
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
	 * 
	 * @param x
	 * @param y
	 * @param radius
	 * @param lastcollision
	 */
	public Ball(int x, int y, int radius, Border lastcollision) {
		rndm = new Random();
		this.ball = new Circle(x, y, radius);
		this.vector = new Vector2f();
		this.velocity = 0.02f;
		this.radius = radius;
		calcDirection();
	}

	public Circle getBall() {
		return ball;
	}

	public void setBall(Circle ball) {
		this.ball = ball;
	}
	
	public float getBallVelocity() {
		return velocity;
	}

	public void setBallVelocity(float velocity) {
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

	public int getBallSpin() {
		return spin;
	}

	public void setBallSpin(int spin) {
		this.spin = spin;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public void draw(Graphics g) {
		g.fill(ball);
	}
	
	public void drawColored(Graphics g){
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
	
	/**
	 * 
	 * @param vector2f
	 * @param x
	 * @param y
	 */
	public void calcTrajectory(Vector2f vector2f, float x, float y){
			x += vector2f.getX();
			y += vector2f.getY();
			//Combine the 2 if-statements
			if (y <= 0 || y >= Pong.S_resY) {
				vector2f.set(vector2f.getX(), -vector2f.getY());
			}
			
			if(x >= Pong.S_resX - 20){
				estimatedY = y;
				return;
			}else{
				calcTrajectory(vector2f, x, y);				
			}

	}

	/**
	 * 
	 * @param acceleration
	 * @param delta
	 * @param lastcollision
	 */
	public void addBallVelocity(double acceleration, int delta, Border lastcollision) {
		float hip = (float) (acceleration * delta + velocity) / 100.0f;
		if(velocity < 3.5){
			if (vector.getX() <= 5 && lastcollision == Border.Left || vector.getX() <= 5  && lastcollision == Border.Right) {
				velocity += 0.005f;
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
	 * Adds the given velocity to the ball
	 * @param acceleration
	 * @param delta
	 */
	public void addBallVelocity(double acceleration, int delta) {
		//velocity += (float) (acceleration * (delta/100));
		if (vector.getX() < 0) {
			vector.set(vector.getX() - (velocity * (delta/100.0f)), vector.getY());
		} else {
			vector.set(vector.getX() + (velocity * (delta/100.0f)), vector.getY());
		}
		if (vector.getY() < 0) {
			vector.set(vector.getX(), vector.getY() + (velocity * (delta/10.0f)));
		} else {
			vector.set(vector.getX(), vector.getY() + (velocity * (delta/10.0f)));
		}
	
	}
	
	/**
	 * 
	 * @param acceleration
	 */
	public void addBallSpin(float acceleration){
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
	
	/**
	 * TODO: Use this method
	 */
	public void moveBall(){
		ball.setCenterX(ball.getCenterX() + vector.getX());
		ball.setCenterY(ball.getCenterY() + vector.getY());
	}
	
	/*
	public Border collideBallListener(Pad pad1, Pad pad2){
		if (ball.getMinY() <= 0) {
			setVectorXY(vector.getX(), -vector.getY());
			lastcollision = Border.TOP;
			return lastcollision;
		}

		if (ball.getMaxY() >= Pong.S_resY) {
			setVectorXY(vector.getX(), -vector.getY());
			lastcollision = Border.BOTTOM;
			return lastcollision;
		}

		if (pad1.intersects(ball)) {
			if(pad1.getSpinSpeed() > 0.0f){
				addSpin(pad1.getSpinSpeed());
			}
			setVectorXY(-vector.getX(), vector.getY());
			//lastpadcollision = Border.LEFT;
			lastcollision = Border.LEFT;
			return lastcollision;
		}

		if (pad2.intersects(ball)) {
			//if(currentmenustate == MenuState.PvP || currentmenustate == MenuState.LAN ){
			//	if(pad2.getSpinspeed() > 0.0f){
			//		addSpin(-pad2.getSpinspeed());
			//	}
			//}
			setVectorXY(-vector.getX(), vector.getY());
			//lastpadcollision = Border.RIGHT;
			lastcollision = Border.RIGHT;
			return lastcollision;
		}else{
			return Border.NONE;
		}
	}
	*/
}
