
public class point {
	
	//this class represent a single tile of the puzzle

	public String value ;//the value of the current tile
	public String color;//the color of the tile with represent the cost
	
	public point (String num, String color) {
		this.value=num;
		this.color=color;
	}
	
	public String getValue (){
		return this.value;
	}
	
	public String getColor (){
		return this.color;
	}
	
}
