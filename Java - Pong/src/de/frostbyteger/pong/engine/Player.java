package de.frostbyteger.pong.engine;

/**
 * @author Kevin
 *
 */
public class Player {
	
	private Pad playerPad;
	private int points;
	private boolean cpuControlled;
	
	/**
	 * @param playerPad
	 * @param points
	 */
	public Player(Pad playerPad, int points) {
		this.playerPad = playerPad;
		this.points = points;
		cpuControlled = false;
	}
	
	/**
	 * 
	 * @param playerPad
	 * @param points
	 * @param isCPUControlled
	 */
	public Player(Pad playerPad, int points, boolean isCPUControlled) {
		this.playerPad = playerPad;
		this.points = points;
		this.cpuControlled = isCPUControlled;
	}
	
	/**
	 * @return the playerPad
	 */
	public Pad getPlayerPad() {
		return playerPad;
	}

	/**
	 * @param playerPad the playerPad to set
	 */
	public void setPlayerPad(Pad playerPad) {
		this.playerPad = playerPad;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getPoints() {
		return points;
	}
	
	/**
	 * 
	 * @param points
	 */
	public void setPoints(int points) {
		this.points = points;
	}
	
	/**
	 * 
	 */
	public void addPoint(){
		this.points += 1;
	}
	
	/**
	 * 
	 */
	public void removePoint(){
		this.points -= 1;
	}
	
	/**
	 * 
	 * @param points
	 */
	public void addPoints(int points){
		this.points += points;
	}
	
	/**
	 * 
	 * @param points
	 */
	public void removePoints(int points){
		this.points -= points;
	}

	/**
	 * @return the cpuControlled
	 */
	public boolean isCpuControlled() {
		return cpuControlled;
	}

	/**
	 * @param cpuControlled the cpuControlled to set
	 */
	public void setCpuControlled(boolean cpuControlled) {
		this.cpuControlled = cpuControlled;
	}
	
	

}
