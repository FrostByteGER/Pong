package de.frostbyteger.pong.engine.graphics.ui;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.newdawn.slick.Input;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.util.InputAdapter;

/**
 * Original Class by kevglass from Slick Devteam.
 * Reused and changed by Kevin Kuegler
 * 
 * The utility class to handle all the input related gubbins for basic GUI
 * components
 * 
 * @author Kevin Kuegler
 */
public abstract class AbstractComponent extends InputAdapter {
	/** The component that currently has focus */
	private static AbstractComponent currentFocus = null;
	
	/** The game container */
	protected GUIContext container;

	/** Listeners for the component to notify */
	@SuppressWarnings("rawtypes")
	protected Set listeners;

	/** True if this component currently has focus */
	private boolean focus = false;

	/** The input we're responding to */
	protected Input input;
	
	private String actionCommand = null;
	
	/**
	 * Create a new component
	 * 
	 * @param container
	 *            The container displaying this component
	 */
	@SuppressWarnings("rawtypes")
	public AbstractComponent(GUIContext container) {
		this.container = container;
		this.listeners = new HashSet();

		this.input = container.getInput();
		this.input.addPrimaryListener(this);

	}
	
	/**
	 * Add a component listener to be informed when the component sees fit.
	 * 
	 * It will ignore listeners already added.
	 * 
	 * @param listener
	 *            listener
	 */
	@SuppressWarnings("unchecked")
	public void addListener(ComponentListener listener) {
		listeners.add(listener);
	}

	/**
	 * Remove a component listener.
	 * 
	 * It will ignore if the listener wasn't added.
	 * 
	 * @param listener
	 *            listener
	 */
	public void removeListener(ComponentListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Notify all the listeners.
	 */
	@SuppressWarnings("rawtypes")
	public void notifyListeners() {
		Iterator it = listeners.iterator();
		while (it.hasNext()) {
			((ComponentListener) it.next()).componentActivated(this);
		}
	}

	/**
	 * Indicate whether this component should be focused or not
	 * 
	 * @param focus
	 *            if the component should be focused
	 */
	public void setFocus(boolean focus) {
		if (focus) {
			if (currentFocus != null) {
				currentFocus.setFocus(false);
			}
			currentFocus = this;
		} else {
			if (currentFocus == this) {
				currentFocus = null;
			}
		}
		this.focus = focus;
	}

	/**
	 * Check if this component currently has focus
	 * 
	 * @return if this field currently has focus
	 */
	public boolean hasFocus() {
		return focus;
	}

	/**
	 * Consume the event currently being processed
	 */
	public void consumeEvent() {
		input.consumeEvent();
	}

	/**
	 * @return the actionCommand
	 */
	public String getActionCommand() {
		return actionCommand;
	}

	/**
	 * @param actionCommand the actionCommand to set
	 */
	public void setActionCommand(String actionCommand) {
		this.actionCommand = actionCommand;
	}
	
}