package de.frostbyteger.pong.engine;

public enum Difficulty {
	EASY(2.5f), MEDIUM(5.0f), HARD(10.0f), UNBEATABLE(15.0f);
	
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
