
public class algorithem {

	// this class is for all the algorithms that we use in each of the other classes.

	static int [] findBlank (point [][] mat) {
		// this function find the "_" cell in the puzzle so we know which step we can make
		for (int i = 0; i<mat.length; i++){
			for (int j = 0; j<mat[i].length; j++){
				if (mat[i][j].getValue().equals("_") && !(mat[i][j].getColor().equals("Black"))) {
					int [] ans = {i,j};
					return ans;
				}
			} 
		}
		int [] ans = {-1,-1};
		return ans;
	}

	static int updateCost (point p){
		// this function return the cost of a cell by color and return the cost so we can update the cost of the step
		if (p.getColor().equals("Red")) {
			return 30;
		}
		else if (p.getColor().equals("Green")) {
			return 1;
		}
		return 0;
	}


	static point [][] updateMatrix (point[][] mat, int xblank, int yblank, int xSwitch, int ySwitch ){
		// this function get a matrix and tow position to switch between the blank cell and the other number and return a new matrix
		// with the new puzzle board
		point [][] newMatrix = new point [mat.length][mat[0].length];
		for (int i = 0; i<mat.length; i++){
			for (int j = 0; j<mat[i].length; j++){
				newMatrix[i][j]= mat[i][j];
			}
		}
		newMatrix[xSwitch][ySwitch]= mat[xblank][yblank];
		newMatrix[xblank][yblank]= mat[xSwitch][ySwitch];
		return newMatrix;
	}

	static void print (point[][] start) {
		// this function get a puzzle board and print it to the screen
		for (int i = 0; i<start.length; i++){
			for (int j = 0; j<start[i].length; j++){
				System.out.print(start[i][j].value+ ",");
			}
			System.out.println();
		}
	}

	static String matrixToString (point[][] mat) {
		//// this function get a puzzle board and return a string of the board
		String ans = "";
		for (int i = 0; i<mat.length; i++){
			for (int j = 0; j<mat[i].length; j++){
				ans = ans + mat[i][j].value;
			}
		}
		return ans;
	}

	static int heuristicFunction (point [][] current, point [][]goal) {
		// this function calculate the heuristic Function the function calculate the manhathen distance multiply by the cost
		// also we ignore the distance of the blank cell
		int sum=0;
		for (int i = 0; i<current.length; i++){
			for (int j = 0; j<current[i].length; j++){
				String Value=current[i][j].getValue();
				for (int goali = 0; goali<goal.length; goali++){
					for (int goalj = 0; goalj<goal[i].length; goalj++){
						if(Value.equals(goal[goali][goalj].getValue()) && !goal[goali][goalj].getValue().equals("_")) {
							sum += (Math.abs(goali-i)+Math.abs(goalj-j))*updateCost(current[i][j]);
						}
					}
				}
			}
		}
		return sum;
	}

	static int numOfBriks (point [][] mat) {
		//this function calculate the number of number cell in the matrix 
		int sum=0;
		for (int i = 0; i<mat.length; i++){
			for (int j = 0; j<mat[i].length; j++){
				if (!mat[i][j].getColor().equals("Black")) {
					sum ++;
				}
			}
		}
		return sum;
	}

	public static int factorial(int number) {
		//this function calculate the factorial if a number
		int result = 1;

		for (int factor = 2; factor <= number; factor++) {
			result *= factor;
		}

		return result;
	}

	public static boolean noSolution (point[][] start, point[][]goal) {
		//function that check if there is no solution for the puzzle by checking if one tile is not in his goal position and also is marked black so we cant move it.
		for (int i = 0; i<start.length; i++){
			for (int j = 0; j<start[i].length; j++){
				point Value=start[i][j];
				if(Value.getColor().equals("Black") && !goal[i][j].getValue().equals(Value.getValue())) {
							 return true;
							}
				}
			}

		return false;
	}
}