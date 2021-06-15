import java.util.Hashtable;
import java.util.Stack;

public class IDAStar extends algorithem {
	
	// in this class we implement the IDAStar algorithm on the tile puzzle game

	static int nodeNum=1;
	static boolean withOpen= false;
	static Stack<point [][]> Lstack = new Stack<point [][]>();
	static Hashtable<String, myNode> loopAvoidance = new Hashtable<String, myNode>();
	static int minF=0;
	static int t=0;
	static int [] Blank =new int [2];

	static myNode IDAStar (point[][] start, point [][] goal) {
		if (matrixToString(start).equals(matrixToString(goal))){//check if the start puzzle is my goal puzzle
			return new myNode (start,"", "no path",0,0);
		}
		if (noSolution(start,goal) == true) {//check if there is no solution to the puzzle
			return new myNode (start,"", "no path",0,0);
		}
		t=heuristicFunction(start, goal);
		int round=1;
		myNode startNode = new myNode (start, "","", 0, heuristicFunction(start, goal), false);
		while (t != Integer.MAX_VALUE) {
			minF=Integer.MAX_VALUE;	
			Lstack.push(start);
			loopAvoidance.put(matrixToString(start), startNode);
			while (!Lstack.isEmpty()) {
				if (withOpen == true) {//print the list if nodes we need to check
					System.out.println("round: "+ round);
					round++;
					for(point[][] s : Lstack) { 
						  print(s); 
						  System.out.println("****");
						}
				}
				point [][] n = Lstack.pop();
				myNode currentNode= loopAvoidance.get(matrixToString(n));
				if (loopAvoidance.get(matrixToString(n)).getOut()== true) {
					loopAvoidance.remove(matrixToString(n));
				}
				else {
					loopAvoidance.get(matrixToString(n)).setOut(true);
					Lstack.push(n);

					Blank= findBlank(n);

					if(Blank[1] < start[0].length-1 ) {//for left
						String opOperator= n[Blank[0]][Blank[1]+1].getValue()+"R";//the opposite operator for left
						if(!(n[Blank[0]][Blank[1]+1].getColor().equals("Black")) && !(currentNode.father.equals(opOperator))) {// check we don't do a operator and the opposite
							myNode result = operatorHelp(n, currentNode, goal, "L", Blank[0], Blank[1]+1);
							if (!result.path.equals("")) {//if we found the goal
								return result;
							}
						}
					}
					if(Blank[0] < start.length-1) {//for up
						String opOperator= n[Blank[0]+1][Blank[1]].getValue()+"D";//the opposite operator for up
						if(!(n[Blank[0]+1][Blank[1]].getColor().equals("Black"))&& !(currentNode.father.equals(opOperator))) {// check we don't do a operator and the opposite
							myNode result = operatorHelp(n, currentNode, goal, "U", Blank[0]+1, Blank[1]);
							if (!result.path.equals("")) {//if we found the goal
								return result;
							}
						}
					}
					if(Blank[1] > 0) {//for right
						String opOperator= n[Blank[0]][Blank[1]-1].getValue()+"L";//the opposite operator for right
						if(!(n[Blank[0]][Blank[1]-1].getColor().equals("Black")) && !(currentNode.father.equals(opOperator))) {// check we don't do a operator and the opposite
							myNode result = operatorHelp(n, currentNode, goal, "R", Blank[0], Blank[1]-1);
							if (!result.path.equals("")) {//if we found the goal
								return result;
							}
						}
					}
					if(Blank[0] > 0) {//for down
						String opOperator= n[Blank[0]-1][Blank[1]].getValue()+"U";//the opposite operator for down
						if(!(n[Blank[0]-1][Blank[1]].getColor().equals("Black")) && !(currentNode.father.equals(opOperator))) {// check we don't do a operator and the opposite
							myNode result = operatorHelp(n, currentNode, goal, "D", Blank[0]-1, Blank[1]);
							if (!result.path.equals("")) {//if we found the goal
								return result;
							}
						}
					}
				}
			}
			t=minF;//update the treshold
			startNode.setOut(false);
		}
		return new myNode (start,"", "no path",0,0);//we didnt find a solution
	}


	static myNode operatorHelp (point[][] matrix, myNode current, point[][] goal, String oprator, int updatI, int updatJ) {
		//this function calculate the new operator
		point [][] newMat= new point [goal.length][goal[0].length];
		newMat= updateMatrix (matrix,Blank[0],Blank[1],updatI,updatJ); // make new matrix
		String newPath=current.path+"-"+newMat[Blank[0]][Blank[1]].getValue()+oprator;
		int newCost=current.getCost()+updateCost(newMat[Blank[0]][Blank[1]]);
		int newHeuristic= heuristicFunction(newMat, goal);
		String father=newMat[Blank[0]][Blank[1]].getValue()+oprator;
		myNode newNode= new myNode(newMat,father, newPath, newCost, newHeuristic,false);//create a new node with the new operator
		nodeNum++;//count how nodes we make
		if (newNode.getEvaluation() > t) {// if the evaluation of the new node is bigger then the treshold
			minF=Math.min(minF, newNode.getEvaluation());
			return new myNode(matrix,"","",0,0);//Continue to the next operator
		}
		if (loopAvoidance.contains(matrixToString(newMat)) && loopAvoidance.get(matrixToString(newMat)).out==true) {//check if the matrix is in C or LHash and mark as out
			return new myNode(matrix,"","",0,0);//Continue to the next operator
		}
		if (loopAvoidance.contains(matrixToString(newMat)) && loopAvoidance.get(matrixToString(newMat)).out==false) {//check if the matrix is in C or LHash and dont mark as out
			if (loopAvoidance.get(matrixToString(newMat)).getEvaluation() > newNode.getEvaluation() ) {
				Lstack.remove(newMat);
				loopAvoidance.remove(matrixToString(newMat));
			}
			else {
				return new myNode(matrix,"","",0,0);//Continue to the next operator
			}
		}
		if (matrixToString(newMat).equals(matrixToString(goal))) {//we find the goal
			return newNode;
		}
		else {
			Lstack.push(newMat);
			loopAvoidance.put(matrixToString(newMat), newNode);
		}
		return new myNode(matrix,"","",0,0);
	}
}

