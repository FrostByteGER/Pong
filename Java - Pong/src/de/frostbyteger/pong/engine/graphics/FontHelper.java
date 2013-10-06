/**
 * 
 */
package de.frostbyteger.pong.engine.graphics;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

/**
 * @author Kevin Kuegler
 * @version 1.00
 *
 */
public class FontHelper {
	
	/**
	 * Creates a new font with the given parameters.
	 * @param fontPath the path to the font you want to create
	 * @param fontSize the fontsize the font should have
	 * @param bold indicates wether the font should be in bold or not
	 * @param italics indicates wether the font should be in italics or not
	 * @return the font returns the new created font
	 * @throws SlickException 
	 */
	@SuppressWarnings("unchecked")
	public static UnicodeFont newFont(String fontPath, int fontSize, boolean bold, boolean italics) throws SlickException{
		UnicodeFont unifont = new UnicodeFont(fontPath, fontSize, bold, italics);
		unifont.addAsciiGlyphs();
		unifont.getEffects().add(new ColorEffect());
		unifont.loadGlyphs();
		return unifont;
	}

}
