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

@XmlRootElement(name = "Config")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"configName", "configPath", "options"})
public class ConfigHelper {
	
	private LinkedHashMap<String, String> options = new LinkedHashMap<>();
	
	private String configPath; 
	private String configName = "config";
	
	@XmlTransient
	private String fileExtension = ".xml";
	
	/**
	 * Used by JAXB to instantiate an object of the class when unmarshalling.
	 */
	public ConfigHelper(){
	}
	
	/**
	 * 
	 * @param path
	 * @param name
	 */
	public ConfigHelper(String path, String name){
		this.configPath = path + name + fileExtension;
		this.configName = name;
	}
	
	/**
	 * 
	 * @param path
	 * @param name
	 * @param extension
	 */
	public ConfigHelper(String path, String name, String extension){
		this.configPath = path + name + extension;
		this.configName = name;
		this.fileExtension = extension;
	}
	
	/**
	 * 
	 * @return
	 * @throws JAXBException 
	 */
	public void createConfigFile() throws JAXBException{
		JAXBContext context = JAXBContext.newInstance(this.getClass());
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(this, new File(configPath));
	}
	
	public ConfigHelper loadConfigFile() throws FileNotFoundException, JAXBException{
		JAXBContext context = JAXBContext.newInstance(this.getClass());
		//Specified class acces to prevent confusion with other classes
		javax.xml.bind.Unmarshaller um = context.createUnmarshaller(); 
		return (ConfigHelper) um.unmarshal(new FileReader(configPath));
	}

	/**
	 * @return the options
	 */
	public LinkedHashMap<String, String> getOptions() {
		return options;
	}

	/**
	 * @param options the options to set
	 */
	public void setOptions(LinkedHashMap<String, String> options) {
		this.options = options;
	}

	/**
	 * @return the configPath
	 */
	public String getConfigpath() {
		return configPath;
	}

	/**
	 * @param configPath the configPath to set
	 */
	public void setConfigpath(String configpath) {
		this.configPath = configpath + configName + fileExtension;
	}

	/**
	 * @return the configName
	 */
	public String getConfigname() {
		return configName;
	}

	/**
	 * @param configName the configName to set
	 */
	public void setConfigname(String configname) {
		this.configName = configname;
	}

	/**
	 * @return the fileextension
	 */
	public String getFileextension() {
		return fileExtension;
	}

	/**
	 * @param fileextension the fileextension to set
	 */
	public void setFileextension(String fileextension) {
		this.fileExtension = fileextension;
	}

}
