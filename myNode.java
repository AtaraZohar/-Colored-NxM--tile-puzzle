
public class myNode implements Comparable<myNode>{
	
	//this class represent node of the tile puzzle

	public point [][] matrix;//the puzzle board
	String father;//the previous action
	public String path;//the path until this node
	public int cost;//the cost
	public int heuristic;//the value of the heuristic function
	public boolean out;//is the node was visited befor
	
	public myNode (point [][] mat,  String p, int c) {//costructor
		copyNode(mat);
		this.father="";
		this.path=p;
		this.cost=c;
		this.heuristic=0;
		this.out=false;
	}
	
	public myNode (point [][] mat, String f,  String p, int c) {//costructor
		copyNode(mat);
		this.father=f;
		this.path=p;
		this.cost=c;
		this.heuristic=0;
		this.out=false;
	}
	
	public myNode (point [][] mat, String f,  String p, int c, int h) {//costructor
		copyNode(mat);
		this.father=f;
		this.path=p;
		this.cost=c;
		this.heuristic=h;
		this.out=false;
	}
	
	
	public myNode (point [][] mat,String f, String p, int c, int h, boolean o) {//costructor
		copyNode(mat);
		this.father=f;
		this.path=p;
		this.cost=c;
		this.heuristic=h;
		this.out=o;
	}
	
	
	public void setPath (String p) {
		this.path=path+p;
	}
	
	public void setFather (String f) {
		this.father=f;
	}
	
	public void setOut (boolean o) {
		this.out=o;
	}
	
	public void setCost (int c) {
		this.cost=cost+c;
	}
	
	public void setHeuristic (int h) {
		this.heuristic=heuristic+h;
	}
	
	public int getCost() {
		return this.cost;	
	}
	
	public boolean getOut() {
		return this.out;	
	}
	
	public int getHeuristic() {
		return this.heuristic;	
	}
	
	public String getPath() {
		return this.path.substring(1, this.path.length());
	}
	
	public point [][] getMatrix() {
		return this.matrix;
	}
	
	public int getEvaluation () {
		return this.cost+this.heuristic;
	}
	
	
	@Override
	public int compareTo(myNode other) {
		// TODO Auto-generated method stub
		return this.getEvaluation()-other.getEvaluation();
	}
	
	void copyNode(point [][] newNode) {
		this.matrix = new point [newNode.length][newNode[0].length] ;
		for(int i=0;i<newNode.length;i++)
		{
			for(int j=0;j<newNode[0].length;j++) {
				this.matrix[i][j]= new point("","");
				this.matrix[i][j]= newNode[i][j];
			}
		}
	}
	
	
}
