public class Piece {
	// ********** GLOBAL VARIABLES **********
	private int value, maxValue;
	
	// ********** CONSTRUCTORS **********
	public Piece() { // Default constructor
		this(0, 2);
	}
	public Piece(int value, int maxValue) {
		this.value = value;
		this.maxValue = maxValue - 1;
	}
	
	// ********** GETTERS/SETTERS **********
	public void setValue(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}
	public int getMaxValue() {
		return maxValue;
	}
	
	// ********** METHODS **********
	public void incremement() {
		if (value == maxValue) {  value = 0;  }
		else {  value++;  }
	}
	@Override
	public String toString() {
		return Integer.toString(value);
	}
}