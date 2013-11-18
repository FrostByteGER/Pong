/**
 * 
 */
package de.frostbyteger.pong.engine;

/**
 * @author Kevin
 * @version 1.00
 */
public class Achievement {

	private String achievementName;
	private String achievementDescription;
	private int conditionCount  = 0;
	private boolean countActive = false;
	private boolean unlocked    = false;
	private boolean active      = true;
	
	
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
	 * @param achievementName
	 * @param achievementDescription
	 * @param conditionCount 
	 * @param unlocked
	 * @param active
	 */
	public Achievement(String achievementName, String achievementDescription, int conditionCount, boolean unlocked, boolean active) {
		this.achievementName = achievementName;
		this.achievementDescription = achievementDescription;
		this.conditionCount = conditionCount;
		this.countActive = true;
		this.unlocked = unlocked;
		this.active = active;
	}
	
	/**
	 * @param achievementName
	 * @param achievementDescription
	 * @param conditionCount
	 * @param countActive
	 * @param unlocked
	 * @param active
	 */
	public Achievement(String achievementName, String achievementDescription, int conditionCount, boolean countActive, boolean unlocked, boolean active) {
		this.achievementName = achievementName;
		this.achievementDescription = achievementDescription;
		this.conditionCount = conditionCount;
		this.countActive = countActive;
		this.unlocked = unlocked;
		this.active = active;
	}

	public void showUnlockMessage(){
		//TODO: Add functionality
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

	/**
	 * @return the conditionCount
	 */
	public int getConditionCount() {
		return conditionCount;
	}

	/**
	 * @param conditionCount the conditionCount to set
	 */
	public void setConditionCount(int conditionCount) {
		this.conditionCount = conditionCount;
	}

	/**
	 * @return the countActive
	 */
	public boolean isCountActive() {
		return countActive;
	}

	/**
	 * @param countActive the countActive to set
	 */
	public void setCountActive(boolean countActive) {
		this.countActive = countActive;
	}
	

}
