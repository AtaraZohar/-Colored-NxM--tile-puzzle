import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Stack;

import javax.swing.plaf.synth.SynthSpinnerUI;

public class DFBnB extends algorithem {
	
	// in this class we implement the DFBnB algorithm on the tile puzzle game
	
	static int nodeNum=1;
	static boolean withOpen= false;
	static Stack<point [][]> Lstack = new Stack<point [][]>();
	static Hashtable<String, myNode> loopAvoidance = new Hashtable<String, myNode>();
	static int [] Blank =new int [2];
	
	static myNode DFBnB(point[][] start, point[][]goal) {
		if (matrixToString(start).equals(matrixToString(goal))){//check if the start puzzle is my goal puzzle
			new myNode (start, "","no path", 0, 0);
		}
		if (noSolution(start,goal) == true) {//check if there is no solution to the puzzle
			 new myNode (start, "","no path", 0, 0);
		}
		int round=1;
		myNode startNode = new myNode (start, "","", 0, heuristicFunction(start, goal), false);
		Lstack.push(start);
		loopAvoidance.put(matrixToString(start), startNode);
		
		myNode result= new myNode (start, "","no path", 0, heuristicFunction(start, goal), false);
		
		int t= Math.min(Integer.MAX_VALUE, factorial(numOfBriks(goal)));
		
		while (!Lstack.isEmpty()) {
			if (withOpen == true) {//print the list if nodes we need to check
				System.out.println("round: "+ round);
				round++;
				for(point[][] s : Lstack) { 
					  print(s); 
					  System.out.println("****");
					}
			}
			point [][] n= Lstack.pop();
			myNode currentNode= loopAvoidance.get(matrixToString(n));
			if (loopAvoidance.get(matrixToString(n)).getOut()== true) {//check if the matrix is in loopAvoidance or LHash and mark as out
				loopAvoidance.remove(matrixToString(n));
			}
			else {
				loopAvoidance.get(matrixToString(n)).setOut(true);
				Lstack.push(n);
				
				ArrayList<myNode> operators = new ArrayList<myNode>();// a arrayList of all the valid operator i can create
				
				Blank= findBlank(n);

				if(Blank[1] < start[0].length-1 ) {//for left
					String opOperator= n[Blank[0]][Blank[1]+1].getValue()+"R";//the opposite operator for left
					if(!(n[Blank[0]][Blank[1]+1].getColor().equals("Black")) && !(currentNode.father.equals(opOperator))) {// check we don't do a operator and the opposite
						myNode newNode = operatorHelp(n, currentNode, goal, "L", Blank[0], Blank[1]+1);
						operators.add(newNode);
					}
				}
				if(Blank[0] < start.length-1) {//for up
					String opOperator= n[Blank[0]+1][Blank[1]].getValue()+"D";//the opposite operator for up
					if(!(n[Blank[0]+1][Blank[1]].getColor().equals("Black"))&& !(currentNode.father.equals(opOperator))) {// check we don't do a operator and the opposite
						myNode newNode = operatorHelp(n, currentNode, goal, "U", Blank[0]+1, Blank[1]);
						operators.add(newNode);
					}
				}
				if(Blank[1] > 0) {//for right
					String opOperator= n[Blank[0]][Blank[1]-1].getValue()+"L";//the opposite operator for right
					if(!(n[Blank[0]][Blank[1]-1].getColor().equals("Black")) && !(currentNode.father.equals(opOperator))) {// check we don't do a operator and the opposite
						myNode newNode = operatorHelp(n, currentNode, goal, "R", Blank[0], Blank[1]-1);
						operators.add(newNode);
					}
				}
				if(Blank[0] > 0) {//for down
					String opOperator= n[Blank[0]-1][Blank[1]].getValue()+"U";//the opposite operator for down
					if(!(n[Blank[0]-1][Blank[1]].getColor().equals("Black")) && !(currentNode.father.equals(opOperator))) {// check we don't do a operator and the opposite
						myNode newNode = operatorHelp(n, currentNode, goal, "D", Blank[0]-1, Blank[1]);
						operators.add(newNode);
					}
				}
				Collections.sort(operators);
				for (int i=0; i< operators.size();i++) {
					if (operators.get(i).getEvaluation() >= t) {//if the evaluation function of the new node is bigger then the treshold
						int end = operators.size();
						for (int j=i; j< end; j++) {// remove the rest of the arrayList
							operators.remove(0);
						}
						
					}
						else if (loopAvoidance.contains(matrixToString(operators.get(i).matrix)) && loopAvoidance.get(matrixToString(operators.get(i).matrix)).out==true ) {//check if the matrix is in loopAvoidance and mark as out
							operators.remove(i);
					}
						else if(loopAvoidance.contains(matrixToString(operators.get(i).matrix)) && loopAvoidance.get(matrixToString(operators.get(i).matrix)).out==false ) {//check if the matrix is in loopAvoidance and dont mark as out
							if (loopAvoidance.get(matrixToString(operators.get(i).matrix)).getEvaluation() <= operators.get(i).getEvaluation() ) {
								operators.remove(i);
							}
							else {
								Lstack.remove(operators.get(i).matrix);
								loopAvoidance.remove(matrixToString(operators.get(i).matrix));
							}
						}
						else if (matrixToString(operators.get(i).matrix).equals(matrixToString(goal))) {// if we find the goal
							t=operators.get(i).getEvaluation();
							result= operators.get(i);
							int end= operators.size();
							for (int j=i; j<end; j++) {//remove the rest of the arrayList
								operators.remove(0);
							}
						}
				}
				Collections.reverse(operators);
				for (int i=0; i< operators.size();i++) {//the rest of the arrayList put in the stack
					Lstack.push(operators.get(i).matrix);
					loopAvoidance.put(matrixToString(operators.get(i).matrix), operators.get(i));
				}
				
			}
		}
		return result;
		
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
		return newNode;
	}
}

