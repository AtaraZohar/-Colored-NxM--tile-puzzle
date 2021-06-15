import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

public class BFS extends algorithem {
	
	// in this class we implement the BFS algorithm on the tile puzzle game
	
	static int nodeNum=1;
	static boolean withOpen= false;
	static int [] Blank= new int [2];
	static Queue<point [][]> openList = new LinkedList<>();
	static Hashtable <String, myNode> openListHash =  new Hashtable<String, myNode>();
	static Hashtable <String, myNode> closedList =  new Hashtable<String, myNode>();

	static myNode BFS (point [][] start, point [][] goal) {
		if (matrixToString(start).equals(matrixToString(goal))){//check if the start puzzle is my goal puzzle
			return new myNode (start,"no path", 0);
		}
		if (noSolution(start,goal) == true) {//check if there is no solution to the puzzle
			return new myNode (start,"no path", 0);
		}
		myNode startnode = new myNode (start,"", 0);
		int round =1;

		openList.add(start);
		openListHash.put(matrixToString(start), startnode);
		while (!openList.isEmpty()) {
			if (withOpen == true) {
				System.out.println("round: "+ round);
				round++;
				for(point[][] s : openList) { 
					  print(s); 
					  System.out.println("****");
					}
			}
			point [][] n = openList.remove();
			myNode current= openListHash.remove(matrixToString(n));
			closedList.put(matrixToString(n),current);
			Blank= findBlank(n);
			if(Blank[1] < start[0].length-1 ) {//for left
				String opOperator= n[Blank[0]][Blank[1]+1].getValue()+"R";//the opposite operator for left
				if(!(n[Blank[0]][Blank[1]+1].getColor().equals("Black")) && !(current.father.equals(opOperator))) {// check we don't do a operator and the opposite
					myNode ans= helpOperatot(n,"L", Blank[0], Blank[1]+1, current,goal);
					if (!ans.path.equals("")) {//if we found the goal
						return ans;
					}
				}
			}
			if(Blank[0] < start.length-1) {//for up
				String opOperator= n[Blank[0]+1][Blank[1]].getValue()+"D";//the opposite operator for up
				if(!(n[Blank[0]+1][Blank[1]].getColor().equals("Black"))&& !(current.father.equals(opOperator))) {// check we don't do a operator and the opposite
					myNode ans= helpOperatot(n, "U",Blank[0]+1, Blank[1], current,goal);
					if (!ans.path.equals("")) {
						return ans;
					}
				}
			}
			if(Blank[1] > 0) {//for right
				String opOperator= n[Blank[0]][Blank[1]-1].getValue()+"L";//the opposite operator for right
				if(!(n[Blank[0]][Blank[1]-1].getColor().equals("Black")) && !(current.father.equals(opOperator))) {// check we don't do a operator and the opposite
					myNode ans= helpOperatot(n,"R", Blank[0], Blank[1]-1, current,goal);//if we found the goal
					if (!ans.path.equals("")) {
						return ans;
					}
				}
			}
			if(Blank[0] > 0) {//for down
				String opOperator= n[Blank[0]-1][Blank[1]].getValue()+"U";//the opposite operator for down
				if(!(n[Blank[0]-1][Blank[1]].getColor().equals("Black")) && !(current.father.equals(opOperator))) {// check we don't do a operator and the opposite
					myNode ans= helpOperatot(n,"D", Blank[0]-1, Blank[1], current,goal);//if we found the goal
					if (!ans.path.equals("")) {
						return ans;
					}
				}
			}
		}
		return new myNode (start,"no path", 0);//if we didn't found any solution for the board
	}

	static myNode helpOperatot(point[][] matrix,String operator, int upi, int upj, myNode currentNode, point[][] goal) {
		//this function calculate the new operator
		point [][] newMat = new point [goal.length][goal[0].length];
		newMat= updateMatrix (matrix,Blank[0],Blank[1],upi,upj); // make new matrix
		nodeNum++;//count how nodes we make
		if((!closedList.containsKey(matrixToString(newMat))) && (!openListHash.containsKey(matrixToString(newMat))))//check if the matrix is in C or LHash 
		{
			myNode upt= new myNode (newMat,newMat[Blank[0]][Blank[1]].getValue() +operator, currentNode.path+"-"+newMat[Blank[0]][Blank[1]].getValue() +operator,currentNode.getCost()+updateCost(newMat[Blank[0]][Blank[1]]));
			if (matrixToString(newMat).equals(matrixToString(goal))) {//check if its goal if is return the cost and path
				return upt;
			}
			openList.add(newMat);
			openListHash.put(matrixToString(newMat),upt);// put the matrix in L with cost and path
		}
		return new myNode (matrix,"",0);
	}
}
