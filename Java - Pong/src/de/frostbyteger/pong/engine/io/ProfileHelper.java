package de.frostbyteger.pong.engine.io;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * This class handles profiles with xml.
 * It saves and loads an instance of the actual class and saves it
 * to the xml format. 
 * @author Kevin Kuegler
 * @version 1.2
 */
@XmlRootElement(name = "Profile")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"profileName", "profilePath", "profileData","profileAchievements"})
public class ProfileHelper{
	
	private LinkedHashMap<String, String> profileData         = new LinkedHashMap<>();
	private LinkedHashMap<String, String> profileAchievements = new LinkedHashMap<>();
	
	private String profilePath; 
	private String profileName = "standard";
	
	@XmlTransient
	private String fileExtension = ".xml";
	
	/**
	 * Used by JAXB to instantiate an object of the class when unmarshalling.
	 */
	public ProfileHelper(){
	}
	
	/**
	 * Creates a new ProfileHelper object with a specified filepath and name but not data.
	 * @param path the path of the xml file
	 * @param name the name of the xml file
	 */
	public ProfileHelper(String path, String name) {
		this.profilePath = path + name.toLowerCase() + fileExtension;
		this.profileName = name;
	}
	
	/**
	 * Creates a new ProfileHelper object with a specified filepath, name and data.
	 * @param path the path of the xml file
	 * @param name the name of the xml file
	 * @param data the LinkedHashMap with profiledata
	 */
	public ProfileHelper(String path, String name, LinkedHashMap<String, String> data) {
		this.profilePath = path + name.toLowerCase() + fileExtension;
		this.profileName = name;
		this.profileData = data;
	}
	
	/**
	 * Creates a new ProfileHelper object with a specified filepath, name and data.
	 * @param path the path of the xml file
	 * @param name the name of the xml file
	 * @param data the LinkedHashMap with profiledata
	 * @param achievements the LinkedHashMap with profileachievements
	 */
	public ProfileHelper(String path, String name, LinkedHashMap<String, String> data,LinkedHashMap<String, String> achievements) {
		this.profilePath = path + name.toLowerCase() + fileExtension;
		this.profileName = name;
		this.profileData = data;
		this.profileAchievements = achievements;
	}

	
	/**
	 * This method creates a new profile.xml with the given path. It only creates a new file, 
	 * if there's no file with the same name. If so, the program returns false.
	 * You can also override the filecheck by setting the override boolean to true. 
	 * The method will not check if there's already a file with the same name or the file is locked by the OS
	 * or any other program.
	 * 
	 * @param override If set to true, the method will not check if the file already existed
	 * @return returns true if file was created successfully or false if the file already existed and no
	 * override was set.
	 * @throws JAXBException 
	 */
	public boolean createProfile(boolean override) throws JAXBException{
		File checkfile = new File(profilePath);
		if(override == false && checkfile.exists() == true){
			return false;
		}else{
			JAXBContext context = JAXBContext.newInstance(this.getClass());
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(this, checkfile);
			return true;
		}
	}
	
	/**
	 * 
	 * This method creates a new profile.xml with the given path. It only creates a new file, 
	 * if there's no file with the same name. If so, the program returns false.
	 * You can also override the filecheck by setting the override boolean to true. 
	 * The method will not check if there's already a file with the same name or the file is locked by the OS 
	 * or any other program.
	 * 
	 * <p>The filename is structured like the following<p>
	 * Structure: profilePath + profileName + .xml<p>
	 * Example:   D:\\saves\  + standard    + .xml
	 * 
	 * @param profilePath a specified profilePath instead of class field. 
	 * @param profileName a specified profileName instead of class field.
	 * @param override If set to true, the method will not check if the file already existed
	 * @return returns true if file was created successfully or false if the file already existed and no
	 * override was set.
	 * @throws JAXBException 
	 */
	public boolean createProfile(String profilepath, String profilename, boolean override) throws JAXBException{
		File checkfile = new File(profilepath);
		if(override == false && checkfile.exists()){
			return false;
		}else{
			JAXBContext context = JAXBContext.newInstance(this.getClass());
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(this, checkfile);
			return true;
		}
	}

	/**
	 * This method loads a specific profile and returns the loaded object.
	 * @return Returns the stored instance of a ProfileHelper object. 
	 * If the file was not found or any other interruptions occured, 
	 * the method will return null
	 */
	public ProfileHelper loadProfile() throws JAXBException, FileNotFoundException{
		JAXBContext context = JAXBContext.newInstance(this.getClass());
		//Specified class acces to prevent confusion with other classes
		javax.xml.bind.Unmarshaller um = context.createUnmarshaller(); 
		return (ProfileHelper) um.unmarshal(new FileReader(profilePath));

	}
	
	/**
	 * This method loads a specific profile and returns the loaded object.
	 * @param profilePath a specified profilePath instead of class field. 
	 * @param profileName a specified profileName instead of class field.
	 * @return Returns the stored instance of a ProfileHelper object. 
	 * If the file was not found or any other interruptions occured, 
	 * the method will return null
	 */
	public ProfileHelper loadProfile(String profilepath, String profilename) throws JAXBException, FileNotFoundException{
		JAXBContext context;
		context = JAXBContext.newInstance(this.getClass());
		//Specified class access to prevent confusion with other classes
		javax.xml.bind.Unmarshaller um = context.createUnmarshaller(); 
		return (ProfileHelper) um.unmarshal(new FileReader(profilepath + profilename + fileExtension));


	}
	
	/**
	 * This method loads a specific profile and returns the loaded object.
	 * @param profilePath a specified profilePath instead of class field. 
	 * @return Returns the stored instance of a ProfileHelper object. 
	 * @throws JAXBException 
	 * @throws FileNotFoundException 
	 */
	public ProfileHelper loadProfile(String profilepath) throws JAXBException, FileNotFoundException{
		JAXBContext context;
		context = JAXBContext.newInstance(this.getClass());
		//Specified class access to prevent confusion with other classes
		javax.xml.bind.Unmarshaller um = context.createUnmarshaller(); 
		return (ProfileHelper) um.unmarshal(new FileReader(profilepath));

	}
	
	/**
	 * Deletes the actual profile 
	 * @return true if the file has been successfully deleted,
	 * otherwise it will return false
	 */
	public boolean deleteProfile(){
		File file = new File(profilePath + profileName + fileExtension);
		boolean check = file.delete();
		if(check){
			return true;			
		}else{
			return false;
		}

	}
	
	/**
	 * Deletes a specific profile
	 * @param profilePath the profilePath to the profile
	 * @return true if the file has been successfully deleted,
	 * otherwise it will return false
	 */
	public static boolean deleteProfile(String profilePath){
		File file = new File(profilePath);
		boolean check = file.delete();
		if(check){
			return true;			
		}else{
			return false;
		}
	}
	
	/**
	 * @return the profiledata
	 */
	public LinkedHashMap<String, String> getProfileData() {
		return profileData;
	}

	/**
	 * @param profileData the profiledata to set
	 */
	public void setProfileInfos(LinkedHashMap<String, String> profileData) {
		this.profileData = profileData;
	}

	/**
	 * @return the profileAchievements
	 */
	public LinkedHashMap<String, String> getProfileAchievements() {
		return profileAchievements;
	}

	/**
	 * @param profileAchievements the profileAchievements to set
	 */
	public void setProfileAchievements(
			LinkedHashMap<String, String> profileAchievements) {
		this.profileAchievements = profileAchievements;
	}

	/**
	 * @return the profilePath
	 */
	public String getProfilePath() {
		return profilePath;
	}

	/**
	 * @param profilePath the profilePath to set
	 */
	public void setProfilePath(String profilepath) {
		this.profilePath = profilepath + profileName + fileExtension;
	}

	/**
	 * @return the profileName
	 */
	public String getProfileName() {
		return profileName;
	}

	/**
	 * @param profileName the profileName to set
	 */
	public void setProfileName(String profilename) {
		this.profileName = profilename;
	}

	/**
	 * @return the fileExtension
	 */
	public String getFileExtension() {
		return fileExtension;
	}

	/**
	 * @param fileExtension the fileExtension to set
	 */
	public void setFileExtension(String filextension) {
		this.fileExtension = filextension;
	}

}
