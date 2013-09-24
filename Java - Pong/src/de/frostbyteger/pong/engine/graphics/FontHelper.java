/**
 * 
 */
package de.frostbyteger.pong.engine.graphics;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

/**
 * @author Kevin
 *
 */
public class FontHelper {
	
	@SuppressWarnings("unchecked")
	public static UnicodeFont newFont(String font,int fontsize, boolean bold, boolean italic) throws SlickException{
		UnicodeFont unifont = new UnicodeFont(font,fontsize , bold, italic);
		unifont.addAsciiGlyphs();
		unifont.getEffects().add(new ColorEffect());
		unifont.loadGlyphs();
		return unifont;
	}

}
