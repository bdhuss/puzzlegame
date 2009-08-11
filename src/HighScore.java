public class HighScore {
	// ********** GLOBAL VARIABLES ********** 
	private long time;
	private int moveCount, difficulty;
	private String name;
	
	// ********** CONSTRUCTORS ********** 
	public HighScore() { // Default constructor.
		this("Fake", 1000, 0, 2);
	}
	public HighScore(String name, long time, int moveCount, int difficulty) {
		this.name = name;
		this.time = time;
		this.moveCount = moveCount;
		this.difficulty = difficulty;
	}
	
	// ********** GETTERS/SETTERS********** 
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public long getTime() {
		return time;
	}
	public void setMoveCount(int moveCount) {
		this.moveCount = moveCount;
	}
	public int getMoveCount() {
		return moveCount;
	}
	// 2 = easy, 3 = hard
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	public int getDifficulty() {
		return difficulty;
	}
}