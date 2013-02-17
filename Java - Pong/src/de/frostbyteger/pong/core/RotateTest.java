package de.frostbyteger.pong.core;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

public class RotateTest extends BasicGame{
	
	protected Shape dummy;
	protected float rotate = 0.045f;
	protected int radius = 15;
	int i;
	
	public RotateTest(String title) {
		super(title);
	}
	
	@Override
	public void init(GameContainer arg0) throws SlickException {
		dummy = new Circle(620/2 - radius *2,480/2 - radius *2,radius);

		
	}

	@Override
	public void render(GameContainer arg0, Graphics g) throws SlickException {
		g.fill(dummy);
		g.drawString(Float.toString(dummy.getCenterX()) + "|" +  Float.toString(dummy.getCenterY()), 10, 10);
		g.drawString(Float.toString(rotate), 10, 25);
		dummy = dummy.transform(Transform.createRotateTransform( rotate, 620/2,480/2));
		
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		rotate += 0.0002;
	}
	
	
	public static void main(String[] args) throws SlickException {

		AppGameContainer container = new AppGameContainer(new RotateTest("ROTATE TEST"));
		container.setDisplayMode(640, 480, false);
		container.setTargetFrameRate(60);
		container.setShowFPS(false);
		container.start();

	}
}
