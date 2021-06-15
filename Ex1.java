import java.awt.Color;
import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.PriorityQueue; 

public class Ex1 extends algorithem {
	
	// this class will read the text from the input file and will build the puzzle object

	public static void main(String[] args)throws Exception 
	{ 
		String myAlgo="";
		boolean withOpen=false;
		boolean withTime=false;
		ArrayList<String> myFile = new ArrayList<String>();
		int [] sizeMatrix=new int[2];

		// We need to provide file path as the parameter: 
		// double backquote is to avoid compiler interpret words 
		// like \test as \t (ie. as a escape sequence) 
		File file = new File("input.txt"); 

		BufferedReader br = new BufferedReader(new FileReader(file)); 

		String st; 
		while ((st = br.readLine()) != null) 
			myFile.add(st);

		myAlgo=myFile.get(0);
		if (myFile.get(1).equals("no time")) {
			withTime=false;
		}
		else if (myFile.get(1).equals("with time")) {
			withTime=true;
		}

		if (myFile.get(2).equals("no open")) {
			withOpen=false;
		}
		else if (myFile.get(2).equals("with open")) {
			withOpen=true;
		}

		sizeMatrix[0]=Integer.parseInt(myFile.get(3).split("x")[0]);
		sizeMatrix[1]=Integer.parseInt(myFile.get(3).split("x")[1]);

		String [] colors= new String [sizeMatrix[0]*sizeMatrix[1]];

		for (int i=0; i< colors.length; i++) {
			colors[i]="Green";
		}

		if (!myFile.get(4).substring(6).equals("")) {
			String [] Black=myFile.get(4).substring(7).split(",");
			for(int i=0; i< Black.length; i++) {
				if (!Black[i].equals("_")) {
					colors[Integer.parseInt(Black[i])-1]="Black";
				}
				else {
					colors[colors.length-1]="Black";
				}
			}
		}

		if (!myFile.get(5).substring(5).equals("")) {
			String [ ] Red=myFile.get(5).substring(5).split(",");
			for(int i=0; i< Red.length; i++) {
				if (!Red[i].equals("_")) {
					colors[Integer.parseInt(Red[i])-1]="Red";
				}
				else {
					colors[colors.length-1]="Red";
				}
			}
		}

		point [][] start= new point [sizeMatrix[0]][sizeMatrix[1]];

		for (int i=6; i<myFile.size(); i++) {
			String [] Row= myFile.get(i).split(",");
			for(int j=0; j< Row.length; j++) {
				if  (!Row[j].equals("_")) {
					start[i-6][j]=new point(Row[j], colors[Integer.parseInt(Row[j])-1]);
				}
				else {
					start[i-6][j]=new point(Row[j], colors[((i-6)+1)*(j+1)-1]);
				}
			}
		}

		point [][] goal= new point [sizeMatrix[0]][sizeMatrix[1]];
		int num=1;
		for (int i=0; i< goal.length; i++) {
			for (int j=0; j< goal[0].length; j++) {
				goal[i][j]=new point (Integer.toString(num), colors[num-1]);
				num++;
			}
		}
		goal[sizeMatrix[0]-1][sizeMatrix[1]-1]=new point("_",colors[(sizeMatrix[0]-1)*(sizeMatrix[1]-1)]);
		
		try {  
	        File myObj = new File("output.txt"); 
	        myObj.createNewFile();
	      } catch (IOException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();  
	      }
		
		FileWriter fileWriter = new FileWriter("output.txt");
	    PrintWriter writer = new PrintWriter(fileWriter);

		if (myAlgo.equals("BFS")) {
			BFS algo= new BFS();
			if (withOpen== true) {
				algo.withOpen=true;
			}
			myNode ans = new myNode (start,"", "",0,0);
			long startTime = System.currentTimeMillis();
			ans = algo.BFS(start, goal);
			long endTime = System.currentTimeMillis();
			if (ans.path.equals("no path")) {
				writer.println("no path");
				writer.println("Num: "+algo.nodeNum);
			}
			else {
				writer.println(ans.getPath());
				writer.println("Num: "+algo.nodeNum);
				writer.println("Cost: "+ans.getCost());
			}
			if (withTime==true) {
				//finding the time difference and converting it into seconds
				float sec = (endTime - startTime) / 1000F; 
				writer.println(sec + " seconds");
			}
		}

		if (myAlgo.equals("DFID")) {
			DFID algo= new DFID();
			if (withOpen== true) {
				algo.withOpen=true;
			}
			myNode ans = new myNode (start,"", "",0,0);
			long startTime = System.currentTimeMillis();
			ans = algo.DFID(start, goal);
			long endTime = System.currentTimeMillis();
			if (ans.path.equals("no path")) {
				writer.println("no path");
				writer.println("Num: "+algo.nodeNum);
			}
			else {
				writer.println(ans.getPath());
				writer.println("Num: "+algo.nodeNum);
				writer.println("Cost: "+ans.getCost());
			}
			if (withTime==true) {
				//finding the time difference and converting it into seconds
				float sec = (endTime - startTime) / 1000F; 
				writer.println(sec + " seconds");
			}
		}

		if (myAlgo.equals("A*")) {
			AStar algo= new AStar();
			if (withOpen== true) {
				algo.withOpen=true;
			}
			myNode ans = new myNode (start,"", "",0,0);
			long startTime = System.currentTimeMillis();
			ans = algo.AStar(start, goal);
			long endTime = System.currentTimeMillis();
			if (ans.path.equals("no path")) {
				writer.println("no path");
				writer.println("Num: "+algo.nodeNum);
			}
			else {
				writer.println(ans.getPath());
				writer.println("Num: "+algo.nodeNum);
				writer.println("Cost: "+ans.getCost());
			}
			if (withTime==true) {
				//finding the time difference and converting it into seconds
				float sec = (endTime - startTime) / 1000F; 
				writer.println(sec + " seconds");
			}
		}

		if (myAlgo.equals("IDA*")) {
			IDAStar algo= new IDAStar();
			if (withOpen== true) {
				algo.withOpen=true;
			}
			myNode ans = new myNode (start,"", "",0,0);
			long startTime = System.currentTimeMillis();
			ans = algo.IDAStar(start, goal);
			long endTime = System.currentTimeMillis();
			if (ans.path.equals("no path")) {
				writer.println("no path");
				writer.println("Num: "+algo.nodeNum);
			}
			else {
				writer.println(ans.getPath());
				writer.println("Num: "+algo.nodeNum);
				writer.println("Cost: "+ans.getCost());
			}
			if (withTime==true) {
				//finding the time difference and converting it into seconds
				float sec = (endTime - startTime) / 1000F; 
				writer.println(sec + " seconds");
			}
		}

		if (myAlgo.equals("DFBnB")) {
			DFBnB algo= new DFBnB();
			if (withOpen== true) {
				algo.withOpen=true;
			}
			myNode ans = new myNode (start,"", "",0,0);
			long startTime = System.currentTimeMillis();
			ans = algo.DFBnB(start, goal);
			long endTime = System.currentTimeMillis();
			if (ans.path.equals("no path")) {
				writer.println("no path");
				writer.println("Num: "+algo.nodeNum);
			}
			else {
				writer.println(ans.getPath());
				writer.println("Num: "+algo.nodeNum);
				writer.println("Cost: "+ans.getCost());
			}
			if (withTime==true) {
				//finding the time difference and converting it into seconds
				float sec = (endTime - startTime) / 1000F; 
				writer.println(sec + " seconds");
			}
		}

		writer.close();

	} 
} 

