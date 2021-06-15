
public class node {
	
	public String path;
	public int cost;
	
	
	public node(String p, int c) {
		// TODO Auto-generated constructor stub
		this.path=p;
		this.cost=c;
	}

	public void setPath (String p) {
		this.path=path+p;
	}
	
	public void setCost (int c) {
		this.cost=cost+c;
	}
	
	public int getCost() {
		return this.cost;	
	}
	
	public String getPath() {
		return this.path.substring(1, this.path.length());
	}
	
	

}
