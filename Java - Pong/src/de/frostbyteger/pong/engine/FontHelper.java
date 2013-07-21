/**
 * 
 */
package de.frostbyteger.pong.engine;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

/**
 * @author Kevin
 *
 */
public class FontHelper {

	public static final String FONT = "data/alexis.ttf";
	public static UnicodeFont smallfont;
	public static UnicodeFont normalfont;
	public static UnicodeFont mediumfont;
	public static UnicodeFont bigfont;
	
	public FontHelper() {
	}
	
	public static UnicodeFont newFont(String font,int fontsize, boolean bold, boolean italic) throws SlickException{
		UnicodeFont unifont = new UnicodeFont(font,fontsize , bold, italic);
		unifont.addAsciiGlyphs();
		unifont.getEffects().add(new ColorEffect());
		unifont.loadGlyphs();
		return unifont;
	}

}
