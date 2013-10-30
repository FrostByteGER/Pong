package de.frostbyteger.pong.engine;

public enum Difficulty {
	Easy(2.5f), Medium(5.0f), Hard(10.0f), Unbeatable(15.0f);
	
	private float difficulty;
	
	private Difficulty(float difficulty){
		this.difficulty = difficulty;
	}

	/**
	 * @return the difficulty
	 */
	public float getDifficulty() {
		return difficulty;
	}
	
}
