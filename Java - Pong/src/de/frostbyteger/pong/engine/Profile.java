package de.frostbyteger.pong.engine;

import java.io.FileNotFoundException;
import java.util.LinkedHashMap;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlTransient;

import de.frostbyteger.pong.engine.io.ProfileHelper;

@XmlTransient
public class Profile extends ProfileHelper{
	
	public Profile() {
		super();
	}
	
	/**
	 * @param path
	 * @param name
	 */
	public Profile(String path, String name) {
		super(path, name);
	}
	
	/**
	 * @param path
	 * @param name
	 * @param data
	 */
	public Profile(String path, String name, LinkedHashMap<String, String> data) {
		super(path, name, data);
	}
	
	/**
	 * @param path
	 * @param name
	 * @param data
	 * @param achievements
	 */
	public Profile(String path, String name, LinkedHashMap<String, String> data, LinkedHashMap<String, Achievement> achievements) {
		super(path, name, data, achievements);
	}
	
	public void create() throws JAXBException{
		this.createProfile(true);
	}
	
	public void load() throws FileNotFoundException, JAXBException{
		ProfileHelper helper = this.loadProfile();
		this.setProfileName(helper.getProfileName());
		this.setProfileInfos(helper.getProfileData());
		this.setProfileAchievements(helper.getProfileAchievements());
	}
	
	public void delete(){
		this.deleteProfile();
	}

}
