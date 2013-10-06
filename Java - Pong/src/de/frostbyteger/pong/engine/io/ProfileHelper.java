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
@XmlType(propOrder = {"profilename", "profilepath", "profileData","profileAchievements"})
public class ProfileHelper{
	
	private LinkedHashMap<String, String> profileData        = new LinkedHashMap<>();
	private LinkedHashMap<String, String> profileAchievements = new LinkedHashMap<>();
	
	private String profilepath; 
	private String profilename = "standard";
	private String fileextension = ".xml";
	
	/**
	 * Used by JAXB to instantiate an object of the class when unmarshalling.
	 */
	@SuppressWarnings("unused")
	private ProfileHelper(){
	}
	
	/**
	 * Creates a new ProfileHelper object with a specified filepath and name but not data.
	 * @param path the path of the xml file
	 * @param name the name of the xml file
	 */
	public ProfileHelper(String path, String name) {
		this.profilepath = path;
		this.profilename = name;
	}
	
	/**
	 * Creates a new ProfileHelper object with a specified filepath, name and data.
	 * @param path the path of the xml file
	 * @param name the name of the xml file
	 * @param data the LinkedHashMap with profiledata
	 */
	public ProfileHelper(String path, String name, LinkedHashMap<String, String> data) {
		this.profilepath = path;
		this.profilename = name;
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
		this.profilepath = path;
		this.profilename = name;
		this.profileData = data;
		this.profileAchievements = achievements;
	}
	
	/**
	 * This method creates a new profile.xml with the given path. It only creates a new file, 
	 * if there's no file with the same name. If so, the program returns false.
	 * 
	 * @return returns true if file was created successfully. 
	 * If false, the file already existed or the user had no write permission
	 */
	public boolean createProfile(){
		File checkfile = new File(profilepath);
		if(checkfile.exists()){
			return false;
		}else{
			try {
				JAXBContext context = JAXBContext.newInstance(this.getClass());
				Marshaller m = context.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				m.marshal(this, checkfile);
			} catch (JAXBException e) {
				e.printStackTrace();
			}
			return true;
		}
	}
	
	/**
	 * This method creates a new profile.xml with the given path. It only creates a new file, 
	 * if there's no file with the same name. If so, the program returns false.
	 * You can also override the filecheck by setting the override boolean to true. 
	 * The method will not check if there's already a file with the same name or the file is locked by the OS
	 * or any other program.
	 * 
	 * @param override If set to true, the method will not check if the file already existed
	 * @return returns true if file was created successfully. If false, the file already existed or the 
	 * user had no write permission
	 */
	public boolean createProfile(boolean override){
		File checkfile = new File(profilepath);
		if(override == false && checkfile.exists() == true){
			return false;
		}else{
			try {
				JAXBContext context = JAXBContext.newInstance(this.getClass());
				Marshaller m = context.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				m.marshal(this, checkfile);
			} catch (JAXBException e) {
				e.printStackTrace();
			}
			return true;
		}
	}
	
	/**
	 * 
	 * This method creates a new profile.xml with the given path. It only creates a new file, 
	 * if there's no file with the same name. If so, the program returns false.
	 * <p>The filename is structured like the following<p>
	 * Structure: profilepath + profilename + .xml<p>
	 * Example:   D:\\saves\  + standard    + .xml
	 * 
	 * @param profilepath a specified profilepath instead of class field. 
	 * @param profilename a specified profilename instead of class field. The file will be 
	 * named after this parameter 
	 * @return returns true if file was created successfully. If false, the file already 
	 * existed or the user had no write permission
	 */
	public boolean createProfile(String profilepath, String profilename){
		File checkfile = new File(profilepath + profilename + fileextension);
		if(checkfile.exists()){
			return false;
		}else{
			try {
				JAXBContext context = JAXBContext.newInstance(this.getClass());
				Marshaller m = context.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				m.marshal(this, checkfile);
			} catch (JAXBException e) {
				e.printStackTrace();
			}
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
	 * Structure: profilepath + profilename + .xml<p>
	 * Example:   D:\\saves\  + standard    + .xml
	 * 
	 * @param profilepath a specified profilepath instead of class field. 
	 * @param profilename a specified profilename instead of class field.
	 * @param override If set to true, the method will not check if the file already existed
	 * @return returns true if file was created successfully. 
	 * If false, the file already existed or the user had no write permission
	 */
	public boolean createProfile(String profilepath, String profilename, boolean override){
		File checkfile = new File(profilepath + profilename + fileextension);
		if(override == false && checkfile.exists()){
			return false;
		}else{
			try {
				JAXBContext context = JAXBContext.newInstance(this.getClass());
				Marshaller m = context.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				m.marshal(this, checkfile);
			} catch (JAXBException e) {
				e.printStackTrace();
			}
			return true;
		}
	}

	/**
	 * This method loads a specific profile and returns the loaded object.
	 * @return Returns the stored instance of a ProfileHelper object. 
	 * If the file was not found or any other interruptions occured, 
	 * the method will return null
	 */
	public ProfileHelper loadProfile(){
		try {
			JAXBContext context = JAXBContext.newInstance(this.getClass());
			//Specified class acces to prevent confusion with other classes
			javax.xml.bind.Unmarshaller um = context.createUnmarshaller(); 
			return (ProfileHelper) um.unmarshal(new FileReader(profilepath));
		} catch (JAXBException | FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	/**
	 * This method loads a specific profile and returns the loaded object.
	 * @param profilepath a specified profilepath instead of class field. 
	 * @param profilename a specified profilename instead of class field.
	 * @return Returns the stored instance of a ProfileHelper object. 
	 * If the file was not found or any other interruptions occured, 
	 * the method will return null
	 */
	public ProfileHelper loadProfile(String profilepath, String profilename){
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(this.getClass());
			//Specified class access to prevent confusion with other classes
			javax.xml.bind.Unmarshaller um = context.createUnmarshaller(); 
			return (ProfileHelper) um.unmarshal(new FileReader(profilepath + profilename + fileextension));
		} catch (JAXBException | FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	/**
	 * Deletes the actual profile 
	 * @return true if the file has been successfully deleted,
	 * otherwise it will return false
	 */
	public boolean deleteProfile(){
		File file = new File(profilepath + profilename + fileextension);
		boolean check = file.delete();
		if(check){
			return true;			
		}else{
			return false;
		}

	}
	
	/**
	 * Deletes a specific profile
	 * @param profilePath the profilepath to the profile
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
	public void setProfileinfos(LinkedHashMap<String, String> profileData) {
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
	 * @return the profilepath
	 */
	public String getProfilepath() {
		return profilepath;
	}

	/**
	 * @param profilepath the profilepath to set
	 */
	public void setProfilepath(String profilepath) {
		this.profilepath = profilepath;
	}

	/**
	 * @return the profilename
	 */
	public String getProfilename() {
		return profilename;
	}

	/**
	 * @param profilename the profilename to set
	 */
	public void setProfilename(String profilename) {
		this.profilename = profilename;
	}

	/**
	 * @return the fileextension
	 */
	public String getFileExtension() {
		return fileextension;
	}

	/**
	 * @param fileextension the fileextension to set
	 */
	public void setFileExtension(String filextension) {
		this.fileextension = filextension;
	}

}
