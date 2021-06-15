import java.util.Hashtable;
import java.util.PriorityQueue;

import javax.swing.text.LayeredHighlighter;

public class AStar extends algorithem{
	
	// in this class we implement the AStar algorithm on the tile puzzle game
	
	static int nodeNum=1;
	static boolean withOpen= false;
	static int [] Blank = new int [2];
	static PriorityQueue<myNode> openListQueue = new PriorityQueue<myNode>();
	static Hashtable<String, myNode> openListHash = new Hashtable<String, myNode>();
	static Hashtable<String, myNode> closedListHash = new Hashtable<String, myNode>();

	static myNode AStar (point[][] start ,point [][] goal) {
		if (matrixToString(start).equals(matrixToString(goal))){//check if the start puzzle is my goal puzzle
			return new myNode (start,"", "no path",0,0);
		}
		if (noSolution(start,goal) == true) {//check if there is no solution to the puzzle
			return new myNode (start,"", "no path",0,0);
		}
		myNode startNode= new myNode (start,"","",0, heuristicFunction(start, goal));
		int round=1;
		
		openListQueue.add(startNode);
		
		openListHash.put(matrixToString(start),startNode);

		while(!openListQueue.isEmpty()) {
			if (withOpen == true) {//print the list if nodes we need to check
				System.out.println("round: "+ round);
				round++;
				for(myNode s : openListQueue) { 
					  print(s.getMatrix()); 
					  System.out.println("****");
					}
			}
			point[][]n= openListQueue.poll().getMatrix();
			myNode current= openListHash.remove(matrixToString(n));
			if(matrixToString(n).equals(matrixToString(goal))) {//if we find the goal
				return current;
			}
			else {
				closedListHash.put(matrixToString(n), current);
			}

			Blank= findBlank(n);

			if(Blank[1] < start[0].length-1 ) {//for left
				String opOperator= n[Blank[0]][Blank[1]+1].getValue()+"R";//the opposite operator for left
				if(!(n[Blank[0]][Blank[1]+1].getColor().equals("Black")) && !(current.father.equals(opOperator))) {// check we don't do a operator and the opposite
					helpOperatot(n,"L",Blank[0],Blank[1]+1,current,goal);
				} 
			}
			if(Blank[0] < start.length-1) {//for up
				String opOperator= n[Blank[0]+1][Blank[1]].getValue()+"D";//the opposite operator for up
				if(!(n[Blank[0]+1][Blank[1]].getColor().equals("Black"))&& !(current.father.equals(opOperator))) {// check we don't do a operator and the opposite
					helpOperatot(n,"U",Blank[0]+1,Blank[1],current,goal);
				} 
			}
			if(Blank[1] > 0) {//for right
				String opOperator= n[Blank[0]][Blank[1]-1].getValue()+"L";//the opposite operator for right
				if(!(n[Blank[0]][Blank[1]-1].getColor().equals("Black")) && !(current.father.equals(opOperator))) {// check we don't do a operator and the opposite
					helpOperatot(n,"R",Blank[0],Blank[1]-1,current,goal);
				} 
			}
			if(Blank[0] > 0) {//for down
				String opOperator= n[Blank[0]-1][Blank[1]].getValue()+"U";//the opposite operator for down
				if(!(n[Blank[0]-1][Blank[1]].getColor().equals("Black")) && !(current.father.equals(opOperator))) {// check we don't do a operator and the opposite
					helpOperatot(n,"D",Blank[0]-1,Blank[1],current,goal);
				} 
			}
			
		}
		return new myNode (start,"", "no path",0,0);// we didnt find a solution
	}
	
	static void helpOperatot(point[][] matrix,String operator, int upi, int upj, myNode currentNode,point[][] goal) {
		//this function calculate the new operator
		
		nodeNum++;//count how nodes we make
		
		point [][] newMat= new point [goal.length][goal[0].length];
		newMat= updateMatrix (matrix,Blank[0],Blank[1],upi,upj);// make new matrix
		String newPath=currentNode.path+"-"+newMat[Blank[0]][Blank[1]].getValue()+operator;
		String father=newMat[Blank[0]][Blank[1]].getValue()+operator;
		int newCost=currentNode.getCost()+updateCost(newMat[Blank[0]][Blank[1]]);
		int newHeuristic= heuristicFunction(newMat, goal);
		myNode newNode= new myNode(newMat,father, newPath, newCost, newHeuristic);//create a new node with the new operator
		
		if ((!closedListHash.containsKey(matrixToString(newMat))) && (!openListHash.containsKey(matrixToString(newMat)))) {//check if the matrix is in closedListHash or openListHash
			openListQueue.add(newNode);
			openListHash.put(matrixToString(newMat), newNode);
		}
		else if ((openListHash.containsKey(matrixToString(newMat)))){//check if the matrix is in openListHash
			if (openListHash.get(matrixToString(newMat)).getHeuristic() > newHeuristic){
				myNode remove=openListHash.remove(matrixToString(newMat));
				openListQueue.remove(remove);
				openListQueue.add(newNode);
				openListHash.put(matrixToString(newMat), newNode);
			}
		}
	}

}
