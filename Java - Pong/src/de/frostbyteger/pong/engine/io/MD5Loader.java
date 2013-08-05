package de.frostbyteger.pong.engine.io;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * @author Kevin
 * @version 1.00
 */
public class MD5Loader {

	private String filename;
	private String result;

	private byte[] buffer;


	/**
	 * 
	 */
	public MD5Loader(String filename) {
		this.filename = filename;
		this.result = null;
		this.buffer = new byte[1024];
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void createChecksum() throws Exception {
		InputStream fis =  new FileInputStream(filename);

		buffer = new byte[1024];
		MessageDigest complete = MessageDigest.getInstance("MD5");
		int numRead;

		do {
			numRead = fis.read(buffer);
			if (numRead > 0) {
				complete.update(buffer, 0, numRead);
			}
		} while (numRead != -1);

		fis.close();
		buffer = complete.digest();
	}

	/**
	 * 
	 * @return
	 */
	public String getChecksum() {
		result = "";

		for (int i=0; i < buffer.length; i++) {
			result += Integer.toString( ( buffer[i] & 0xff ) + 0x100, 16).substring( 1 );
		}
		return result;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the buffer
	 */
	public byte[] getBuffer() {
		return buffer;
	}

	/**
	 * @param buffer the buffer to set
	 */
	public void setBuffer(byte[] buffer) {
		this.buffer = buffer;
	}

}
