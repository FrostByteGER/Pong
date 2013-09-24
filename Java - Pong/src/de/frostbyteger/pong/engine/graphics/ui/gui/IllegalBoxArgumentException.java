/**
 * 
 */
package de.frostbyteger.pong.engine.graphics.ui.gui;

/**
 * @author Kevin
 *
 */
public class IllegalBoxArgumentException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6674270486557085878L;

	/**
	 * 
	 */
	public IllegalBoxArgumentException() {
	}


	/**
	 * @param message
	 * @param cause
	 */
	public IllegalBoxArgumentException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public IllegalBoxArgumentException(String message) {
		super(message);
	}
	
	
}
