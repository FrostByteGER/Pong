package de.frostbyteger.pong.engine.graphics.ui.gui;


/**
 * Original Class by kevglass from Slick Devteam.
 * Reused and changed by Kevin Kuegler
 * 
 * A description of a class responding to events occuring on a GUI component
 * 
 * @author Kevin Kuegler
 */
public interface ComponentListener {

	/**
	 * Notification that a component has been activated (button clicked,
	 * text field entered, etc)
	 * 
	 * @param source The source of the event
	 */
	public void componentActivated(AbstractComponent source);
}
