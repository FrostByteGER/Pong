/**
 * 
 */
package de.frostbyteger.pong.engine;

/**
 * @author Kevin
 *
 */
public class Achievement {

	private String achievementName;
	private String achievementDescription;
	private boolean unlocked = false;
	private boolean active   = true;
	
	
	/**
	 * 
	 */
	public Achievement() {
		this.achievementName = null;
		this.achievementDescription = null;
	}
	
	/**
	 * @param name
	 */
	public Achievement(String name) {
		this.achievementName = name;
	}

	/**
	 * @param name
	 * @param description
	 */
	public Achievement(String name, String description) {
		this.achievementName = name;
		this.achievementDescription = description;
	}

	/**
	 * @param name
	 * @param description
	 * @param unlocked
	 */
	public Achievement(String name, String description, boolean unlocked) {
		this.achievementName = name;
		this.achievementDescription = description;
		this.unlocked = unlocked;
	}

	/**
	 * @param name
	 * @param description
	 * @param unlocked
	 * @param active
	 */
	public Achievement(String name, String description, boolean unlocked, boolean active) {
		this.achievementName = name;
		this.achievementDescription = description;
		this.unlocked = unlocked;
		this.active = active;
	}

	/**
	 * @return the achievementName
	 */
	public String getAchievementName() {
		return achievementName;
	}

	/**
	 * @param name the name to set
	 */
	public void setAchievementName(String name) {
		this.achievementName = name;
	}

	/**
	 * @return the achievementDescription
	 */
	public String getAchievementDescription() {
		return achievementDescription;
	}

	/**
	 * @param description the description to set
	 */
	public void setAchievementDescription(String description) {
		this.achievementDescription = description;
	}

	/**
	 * @return the unlocked
	 */
	public boolean isUnlocked() {
		return unlocked;
	}

	/**
	 * @param unlocked the unlocked to set
	 */
	public void setUnlocked(boolean unlocked) {
		this.unlocked = unlocked;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	

}
