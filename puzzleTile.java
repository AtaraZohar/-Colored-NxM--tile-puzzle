import java.io.PrintWriter;
import java.util.*;

public class puzzleTile extends algorithem{

	public static void main(String[] args) throws Exception{
		point point1 = new point ("1", "Green");
		point point2 = new point ("2", "Green");
		point point3 = new point ("3", "Green");
		point point4 = new point ("4", "Green");
		point point5 = new point ("5", "Green");
		point point6 = new point ("6", "Green");
		point point7 = new point ("7", "Green");
		point point8 = new point ("8", "Green");
		point point9 = new point ("9", "Green");
		point point10 = new point ("10", "Green");
		point point11 = new point ("11", "Red");
		point point12 = new point ("_", "Green");
		
		point point111 = new point ("1", "Green");
		point point22 = new point ("2", "Green");
		point point33 = new point ("3", "Green");
		point point44 = new point ("4", "Green");
		point point55 = new point ("5", "Green");
		point point66 = new point ("6", "Green");
		point point77 = new point ("7", "Green");
		point point88 = new point ("8", "Green");
		point point99 = new point ("9", "Green");
		point point100 = new point ("10", "Green");
		point point1111 = new point ("11", "Red");
		point point122 = new point ("_", "Green");
		point [][] start = {{point1,point2,point3,point4},
				{point5,point6,point11,point7},
				{point9,point10,point8,point12}};

		point [][] goal = {{point111,point22,point33,point44},
				{point55,point66,point77,point88},
				{point99,point100,point1111,point122}};
		
//		point point1 = new point ("_", "Green");
//		point point2 = new point ("7", "Green");
//		point point3 = new point ("2", "Green");
//		point point4 = new point ("1", "Green");
//		point point5 = new point ("5", "Green");
//		point point6 = new point ("3", "Green");
//		point point7 = new point ("4", "Green");
//		point point8 = new point ("8", "Green");
//		point point9 = new point ("6", "Green");
//		
//		point [][] start = {{point1,point2,point3},
//				{point4,point5,point6},
//				{point7,point8,point9}};
//
//		point [][] goal = {{point4,point3,point6},
//				{point7,point5,point9},
//				{point2,point8,point1}};
		
		 BFS bfs= new BFS();
		 DFID dfid =new DFID();
		 AStar astar =new AStar();
		 IDAStar idastar= new IDAStar();
		 DFBnB dfbnb= new DFBnB();
		 myNode ans = new myNode (start,"", "",0,0);
		 ans = dfid.DFID(start, goal);
		 PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
		 writer.println(ans.getPath());
		 writer.println(ans.getCost());
		 writer.println(dfid.nodeNum);
		 writer.close();
	} 

}