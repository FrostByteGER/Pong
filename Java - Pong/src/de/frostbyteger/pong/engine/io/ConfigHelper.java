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
@XmlType(propOrder = {"configname", "configpath", "options"})
public class ConfigHelper {
	
	private LinkedHashMap<String, String> options = new LinkedHashMap<>();
	
	private String configpath; 
	private String configname = "config";
	
	@XmlTransient
	private String fileextension = ".xml";
	
	/**
	 * Used by JAXB to instantiate an object of the class when unmarshalling.
	 */
	@SuppressWarnings("unused")
	private ConfigHelper(){
	}
	
	/**
	 * 
	 * @param path
	 * @param name
	 */
	public ConfigHelper(String path, String name){
		this.configpath = path + name + fileextension;
		this.configname = name;
	}
	
	/**
	 * 
	 * @param path
	 * @param name
	 * @param extension
	 */
	public ConfigHelper(String path, String name, String extension){
		this.configpath = path + name + extension;
		this.configname = name;
		this.fileextension = extension;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean createConfigFile(){
		try {
			JAXBContext context = JAXBContext.newInstance(this.getClass());
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(this, new File(configpath));
			return true;
		} catch (JAXBException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public ConfigHelper loadConfigFile() throws FileNotFoundException{
		try {
			JAXBContext context = JAXBContext.newInstance(this.getClass());
			//Specified class acces to prevent confusion with other classes
			javax.xml.bind.Unmarshaller um = context.createUnmarshaller(); 
			return (ConfigHelper) um.unmarshal(new FileReader(configpath));
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
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
	 * @return the configpath
	 */
	public String getConfigpath() {
		return configpath;
	}

	/**
	 * @param configpath the configpath to set
	 */
	public void setConfigpath(String configpath) {
		this.configpath = configpath;
	}

	/**
	 * @return the configname
	 */
	public String getConfigname() {
		return configname;
	}

	/**
	 * @param configname the configname to set
	 */
	public void setConfigname(String configname) {
		this.configname = configname;
	}

	/**
	 * @return the fileextension
	 */
	public String getFileextension() {
		return fileextension;
	}

	/**
	 * @param fileextension the fileextension to set
	 */
	public void setFileextension(String fileextension) {
		this.fileextension = fileextension;
	}

}
