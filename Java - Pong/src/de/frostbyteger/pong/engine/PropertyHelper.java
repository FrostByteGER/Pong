package de.frostbyteger.pong.engine;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyHelper {

	protected Properties properties;
	protected String configpath = "data/config.properties";
	protected FileInputStream fis;
	
	public PropertyHelper(){
		properties = new Properties();
	}
	
	public PropertyHelper(String path){
		properties = new Properties();
		configpath = path;
	}
	
	public void loadPropertiesFile(){
		try {
			fis = new FileInputStream(configpath);
			properties.load(fis);
			if (fis != null) {
			    fis.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String loadProperty(String key){
		return properties.getProperty(key);
	}
		
	public static void main(String[] args) {
		PropertyHelper ph = new PropertyHelper();
		ph.loadPropertiesFile();
		System.out.println(ph.loadProperty("resX"));
	}

}
