import java.util.Hashtable;
import java.util.Set;

public class DFID extends algorithem{
	
	// in this class we implement the DFID algorithm on the tile puzzle game

	static int nodeNum = 1;
	static boolean withOpen= false;
	static int round=1;
	
	static myNode DFID (point [][] start, point [][] goal) {
		if (matrixToString(start).equals(matrixToString(goal))){//check if the start puzzle is my goal puzzle
			return new myNode (start,"","no path",0,0);
		}
		if (noSolution(start,goal) == true) {//check if there is no solution to the puzzle
			return new myNode (start,"","no path",0,0);
		}
		for (int depth=1; depth<Integer.MAX_VALUE; depth++) {
			myNode result = new myNode (start,"","", 0,0);
			myNode startNode = new myNode (start,"","", 0,0);
			Hashtable <String, myNode> loopAvoidance =  new Hashtable<String, myNode>();
			result = Limited_DFS(startNode,goal,depth,loopAvoidance);
			if (!result.path.contains("cutoff")) {
				return result;
			}
		}
		return new myNode (start,"","no path",0,0);// we didnt find solution
	}

	static myNode Limited_DFS (myNode currentnode, point [][] goal , int limit, Hashtable <String, myNode> Htable) {
		myNode result = new myNode (currentnode.getMatrix(),"","", 0,0);
		int [] Blank= new int [2];
		point [][] newMat = new point [currentnode.getMatrix().length][currentnode.getMatrix()[0].length];
		boolean isCutoff=true;
		if (matrixToString(currentnode.getMatrix()).equals(matrixToString(goal))) {//check if its goal and return the cost and path
			return currentnode;
		}
		if (limit == 0) {
			result.path= "cutoff";
			return result;
		}
		else {
			Htable.put(matrixToString(currentnode.getMatrix()),currentnode);
			isCutoff=false;
			Blank= findBlank(currentnode.getMatrix());
			if(Blank[1] < currentnode.getMatrix()[0].length-1 ) {//for left
				String opOperator= currentnode.getMatrix()[Blank[0]][Blank[1]+1].getValue()+"R";//the opposite operator for left
				if(!(currentnode.getMatrix()[Blank[0]][Blank[1]+1].getColor().equals("Black")) && !(currentnode.father.equals(opOperator))) {// check we don't do a operator and the opposite
					newMat= updateMatrix (currentnode.getMatrix(),Blank[0],Blank[1],Blank[0],Blank[1]+1); // make new matrix
					nodeNum++;//count how nodes we make
					if((!Htable.containsKey(matrixToString(newMat))))//check if the matrix is in C or LHash 
					{
						myNode newNode= new myNode (newMat,newMat[Blank[0]][Blank[1]].getValue() +"L",currentnode.path+"-"+newMat[Blank[0]][Blank[1]].getValue() +"L", currentnode.getCost()+updateCost(newMat[Blank[0]][Blank[1]]),0);// update the new operator
						result = Limited_DFS(newNode,goal,limit-1,Htable);
						if (result.path.contains("cutoff")) {// if we need to keep searching
							isCutoff=true;
						}
						else if (!result.path.contains("fail")) {//if we found a path
							return result;
						}
					}
				}
			}
			if(Blank[0] < currentnode.getMatrix().length-1) {//for up
				String opOperator= currentnode.getMatrix()[Blank[0]+1][Blank[1]].getValue()+"D";//the opposite operator for up
				if(!(currentnode.getMatrix()[Blank[0]+1][Blank[1]].getColor().equals("Black"))&& !(currentnode.father.equals(opOperator))) {// check we don't do a operator and the opposite
					newMat =updateMatrix (currentnode.getMatrix(),Blank[0],Blank[1],Blank[0]+1,Blank[1]);// make new matrix
					nodeNum++;//count how nodes we make
					if((!Htable.containsKey(matrixToString(newMat))))//check if the matrix is in C or LHash 
					{
						myNode newNode= new myNode (newMat,newMat[Blank[0]][Blank[1]].getValue()+"U",currentnode.path+"-"+newMat[Blank[0]][Blank[1]].getValue()+"U", currentnode.getCost()+updateCost(newMat[Blank[0]][Blank[1]]),0);// update the new operator
						result = Limited_DFS(newNode,goal,limit-1,Htable);
						if (result.path.contains("cutoff")) {// if we need to keep searching
							isCutoff=true;
						}
						else if (!result.path.contains("fail")) { //if we found a path
							return result;
						}
					}
				}
			}
			if(Blank[1] > 0) {//for right
				String opOperator= currentnode.getMatrix()[Blank[0]][Blank[1]-1].getValue()+"L";//the opposite operator for right
				if(!(currentnode.getMatrix()[Blank[0]][Blank[1]-1].getColor().equals("Black")) && !(currentnode.father.equals(opOperator))) {// check we don't do a operator and the opposite
					newMat= updateMatrix (currentnode.getMatrix(),Blank[0],Blank[1],Blank[0],Blank[1]-1);// make new matrix
					nodeNum++;//count how nodes we make
					if((!Htable.containsKey(matrixToString(newMat))))//check if the matrix is in C or LHash 
					{
						myNode newNode= new myNode (newMat,newMat[Blank[0]][Blank[1]].getValue() +"R",currentnode.path+"-"+newMat[Blank[0]][Blank[1]].getValue() +"R", currentnode.getCost()+updateCost(newMat[Blank[0]][Blank[1]]),0);// update the new operator
						result = Limited_DFS(newNode,goal,limit-1,Htable);
						if (result.path.contains("cutoff")) {// if we need to keep searching
							isCutoff=true;
						}
						else if (!result.path.contains("fail")) {//if we found a path
							return result;
						}
					}
				}
			}
			if(Blank[0] > 0) {//for down
				String opOperator= currentnode.getMatrix()[Blank[0]-1][Blank[1]].getValue()+"U";//the opposite operator for down
				if(!(currentnode.getMatrix()[Blank[0]-1][Blank[1]].getColor().equals("Black")) && !(currentnode.father.equals(opOperator))) {// check we don't do a operator and the opposite
					newMat= updateMatrix (currentnode.getMatrix(),Blank[0],Blank[1],Blank[0]-1,Blank[1]);// make new matrix
					nodeNum++;//count how nodes we make
					if((!Htable.containsKey(matrixToString(newMat))))//check if the matrix is in C or LHash 
					{
						myNode newNode= new myNode (newMat,newMat[Blank[0]][Blank[1]].getValue() +"D",currentnode.path+"-"+newMat[Blank[0]][Blank[1]].getValue() +"D", currentnode.getCost()+updateCost(newMat[Blank[0]][Blank[1]]),0);// update the new operator
						result = Limited_DFS(newNode,goal,limit-1,Htable);
						if (result.path.contains("cutoff")) {// if we need to keep searching
							isCutoff=true;
						}
						else if (!result.path.contains("fail")) {//if we found a path
							return result;
						}
					}
				}
			}
			if (withOpen == true) {//print the list if nodes we need to check
				System.out.println("round: "+ round);
				round++;
				Set<String> myKeySet= Htable.keySet();
				for(String key: myKeySet){
					print(Htable.get(key).matrix);
					System.out.println("****");
		        }
			}
			Htable.remove(matrixToString(currentnode.getMatrix()));
			if (isCutoff == true) {
				result.path="cutoff";
				return result;
			}
			else {
				result.setPath("fail");//if we didnt found a path
				return result;
			}
		}

	}
}
