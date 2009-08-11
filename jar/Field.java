public class Field {
	// ********** GLOBAL VARIABLES **********
	private Piece[][] grid;
	
	// ********** CONSTRUCTORS **********
	public Field() {
		this(2);
	}
	public Field(int difficulty) {
		grid = new Piece[3][3];
		for (int x=0; x<grid.length; x++) {
			for (int y=0; y<grid.length; y++) {
				grid[x][y] = new Piece(0, difficulty);
			}
		}
		randomize();
	}
	
	// ********** METHODS **********
	public void piecePressed(int position) {
		switch (position) {
		case 0: {
			grid[0][0].incremement();
			grid[0][1].incremement();
			grid[1][0].incremement();
			break;
		}
		case 1: {
			grid[0][0].incremement();
			grid[0][1].incremement();
			grid[0][2].incremement();
			grid[1][1].incremement();
			break;
		}
		case 2: {
			grid[0][1].incremement();
			grid[0][2].incremement();
			grid[1][2].incremement();
			break;
		}
		case 3: {
			grid[0][0].incremement();
			grid[1][0].incremement();
			grid[1][1].incremement();
			grid[2][0].incremement();
			break;
		}
		case 4: {
			grid[0][1].incremement();
			grid[1][0].incremement();
			grid[1][1].incremement();
			grid[1][2].incremement();
			grid[2][1].incremement();
			break;
		}
		case 5: {
			grid[0][2].incremement();
			grid[1][1].incremement();
			grid[1][2].incremement();
			grid[2][2].incremement();
			break;
		}
		case 6: {
			grid[1][0].incremement();
			grid[2][0].incremement();
			grid[2][1].incremement();
			break;
		}
		case 7: {
			grid[1][1].incremement();
			grid[2][0].incremement();
			grid[2][1].incremement();
			grid[2][2].incremement();
			break;
		}
		case 8: {
			grid[1][2].incremement();
			grid[2][1].incremement();
			grid[2][2].incremement();
			break;
		}
		}
	}
	public void randomize() {
		java.util.Random random = new java.util.Random();
		for (int x=0; x<grid.length; x++) {
			for (int y=0; y<grid.length; y++) {
				grid[x][y].setValue(random.nextInt(grid[x][y].getMaxValue()+1));
			}
		}
		if (isComplete()) {  randomize();  }
	}
	public boolean isComplete() {
		int difference = 0;
		for (int x=0; x<grid.length; x++) {
			for (int y=0; y<grid.length; y++) {
				if (grid[0][0].getValue() != grid[x][y].getValue()) {
					difference++;
				}
			}
		}
		if (difference == 0) {  return true;  }
		else {  return false;  }
	}
	public Piece getPiece(int position) {
		Piece piece = new Piece();
		switch (position) {
		case 0: {  piece = grid[0][0]; break;  }
		case 1: {  piece = grid[0][1]; break;  }
		case 2: {  piece = grid[0][2]; break;  }
		case 3: {  piece = grid[1][0]; break;  }
		case 4: {  piece = grid[1][1]; break;  }
		case 5: {  piece = grid[1][2]; break;  }
		case 6: {  piece = grid[2][0]; break;  }
		case 7: {  piece = grid[2][1]; break;  }
		case 8: {  piece = grid[2][2]; break;  }
		}
		return piece;
	}
	@Override
	public String toString() {
		String str = new String();
		for (int x=0; x<grid.length; x++) {
			str+="[";
			for (int y=0; y<grid.length; y++) {
				str+=" "+grid[x][y].toString();
			}
			str+=" ]\n";
		}
		return str;
	}
}