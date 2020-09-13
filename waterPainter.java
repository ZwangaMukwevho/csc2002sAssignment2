
import java.util.ArrayList;
public class waterPainter {
	Terrain land = new Terrain() ;


	public static void main(String[] args){
		Terrain land = new Terrain() ;

		int x = 368;
		int y = 455;
		//Array that returns lowest position
		int lowest_pos [] = new int[2];

		//Array to hold x and y cooridinates of the grid position read in from the method
		int [] pos = new int[2];

		//Reading in position from mouse and converting it to height
		land.readData("medsample_in.txt");

		//Array that hold indexes 
		int matrix[][] = land.positions;

		// Bounds of the grid
		int xWidth = land.getDimX();
		int yWidth = land.getDimY();

		//List to be permuted
		ArrayList<Integer> list = land.permute;	



		float [][] landHeight = land.height;
		int index = x*xWidth + y;
		float height = 0;
		float temp_height = 0;
		int count = 0;


		
		for(int j = 0; j<list.size();j++){
			if(list.get(j) == index){
				land.getPermute(j,pos);
				height = landHeight[pos[0]][pos[1]];
				//System.out.println(pos[0]+" "+pos[1]);
			}
		}
		System.out.println(lowest_pos[0]+" "+lowest_pos[1]);
		int int_pos [] = new int[8];
		createIndexes(int_pos,matrix,pos[0],pos[1]);
	
		//Loop for comparing result
		for(int i = 0;i<8;i++){
			
			if(count != 0){
				index = int_pos[i];

				for(int j = 0; j<list.size();j++){
					if(list.get(j) == index){
						land.getPermute(j,pos);
						temp_height = landHeight[pos[0]][pos[1]];
						//System.out.println(pos[0]+" "+pos[1]);
					}
				}

				if(temp_height<height){
					height = temp_height;
					lowest_pos[0] = pos[0];
					lowest_pos[1] = pos[1];
				}

			}
			else{
				index = int_pos[i];
				count = 1;
				
				for(int j = 0; j<list.size();j++){
					if(list.get(j) == index){
						land.getPermute(j,pos);
						temp_height = landHeight[pos[0]][pos[1]];
					
					}
				}

				if(temp_height<height){
					height = temp_height;
					lowest_pos[0] = pos[0];
					lowest_pos[1] = pos[1];
					System.out.println(lowest_pos[0]+" "+lowest_pos[1]);
				}
				//System.out.println(pos[0]+" "+pos[1]);
			}

		}

		// Updating lowest 
		//System.out.println(height);
		System.out.println(lowest_pos[0]+" "+lowest_pos[1]);
	
	}

	public int [][] waterDepArray(){
		land.readData("medsample_in.txt");

		int [][] depthArray = new int[land.dimx][land.dimy];
		return depthArray;
	}
	
	
	public int[] compare (int x,int y,String fileName){
		//Array that returns lowest position
		int lowest_pos [] = new int[2];

		//Array to hold x and y cooridinates of the grid position read in from the method
		int [] pos = new int[2];

		//Reading in position from mouse and converting it to height
		//land.readData(fileName);

		//Array that hold indexes 
		int matrix[][] = land.positions;

		// Bounds of the grid
		int xWidth = land.getDimX();
		//int yWidth = land.getDimY();

		//List to be permuted
		ArrayList<Integer> list = land.permute;	



		float [][] landHeight = land.height;
		int index = x*xWidth + y;
		float height = 0;
		float temp_height = 0;
		int count = 0;


		
		for(int j = 0; j<list.size();j++){
			if(list.get(j) == index){
				land.getPermute(j,pos);
				height = landHeight[pos[0]][pos[1]];
			}
		}
		
		int int_pos [] = new int[8];
		createIndexes(int_pos,matrix,pos[0],pos[1]);
	
		//Loop for comparing result
		for(int i = 0;i<8;i++){
			
			if(count != 0){
				index = int_pos[i];


				for(int j = 0; j<list.size();j++){
					if(list.get(j) == index){
						land.getPermute(j,pos);
						temp_height = landHeight[pos[0]][pos[1]];
						
					}
				}

				if(temp_height<height){
					height = temp_height;
					lowest_pos[0] = pos[0];
					lowest_pos[1] = pos[1];
				}

			}
			else{
				index = int_pos[i];
				count = 1;
				
				for(int j = 0; j<list.size();j++){
					if(list.get(j) == index){
						land.getPermute(j,pos);
						temp_height = landHeight[pos[0]][pos[1]];
						
					}
				}

				if(temp_height<height){
					height = temp_height;
					lowest_pos[0] = pos[0];
					lowest_pos[1] = pos[1];
				}

			}

		}
		if(lowest_pos[0] == 0 || lowest_pos[1]==0){
			return null;
		}
		return lowest_pos;
	}

	//public int [] findCoord(ArrayList<Integer> list, float [][] landheight, int index,int [] pos){
	//	for(int j = 0; j<list.size();j++){
	//		if(list.get(j) == index){
	//			land.getPermute(j,pos);	
	//		}
	//	}
	//	return pos;
	//}

	public static int[] createIndexes (int []  int_pos ,int [][] matrix, int tempRow, int tempCol){
				int_pos[0] = matrix[tempRow][tempCol-1];
				int_pos[1] = matrix[tempRow][tempCol+1];
				int_pos[2] = matrix[tempRow-1][tempCol];
				int_pos[3] = matrix[tempRow+1][tempCol];
				int_pos[4] = matrix[tempRow-1][tempCol-1];
				int_pos[5] = matrix[tempRow+1][tempCol-1];
				int_pos[6] = matrix[tempRow-1][tempCol+1];
				int_pos[7] = matrix[tempRow+1][tempCol+1];
				
				return int_pos;
}}