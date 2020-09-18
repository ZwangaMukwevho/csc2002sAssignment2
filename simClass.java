
import java.awt.Color;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;



public class simClass extends java.lang.Thread  {

	/**
	 *
	 */
	
	Terrain land;
	int high;
	int low;
	waterPainter waterPobj = new waterPainter();
	
	volatile boolean check = true;
	volatile boolean stop = false;

	String fileName = "medsample_in.txt";
	float[][] depthArray = waterPobj.waterDepArray();
	int tempDepth;

	int x1;
	int y1;

	//Constructor for terrain takes in a land terrain
	simClass(Terrain t,int l,int h,int xClick, int yClick){
		land = t;
		low = l;
		high = h;
		x1 = yClick;
		y1 = yClick;
	}

	

		
	public void flow(int lo, int hi) {
		//int size = water.get
	
		int xPos;
		int yPos;
		int yWidth = land.dimx -1;
		int xWidth = land.dimy - 1;
		
		int [] newPos = new int[2];
		int [] prevCurr = new int[2];
		int tempPos [] = new int[2];
		
		//Getting size of water objectsd

		//Defining variables that will be used in the loop
		int size = land.dim();
		
		float waterDepth;

		// current water node
		water currentWater;
		water lowestWater;

		for(int i = lo; i<hi;i++){

				// Obtaining random position to permute
				land.locate(i, tempPos);
				
				// Getting x and y position of the temporay chosen position
				xPos = tempPos[0];
				yPos = tempPos[1];
				
				// Checking of that current position has water
				currentWater = land.waterItems[xPos][yPos];

				synchronized(currentWater){
				waterDepth = currentWater.getWaterDepth();
				
				if(!(xPos == 0 || yPos == 0 || xPos == xWidth || yPos == yWidth)){
			
				//Checking if the current pixel has water
			 		if(waterDepth!=0){
					
					//Finding the lowest closest position
					
					
						newPos = waterPobj.compare(xPos, yPos,fileName,depthArray );

					// This means the water has reached a basin
						if(newPos[0]==0 && newPos[1]==0){
							;
						}
						else{
							
							lowestWater = land.waterItems[newPos[0]][newPos[1]];
							synchronized(lowestWater){
							//Colouring the new position
							land.blueColor(newPos[0], newPos[1]);

							//Increasing and decreasing the waterDepth to indicate transfering of water
							waterPobj.inreamentDepth(depthArray, newPos);
							waterPobj.decrementDepth(depthArray, tempPos);

							// Increasing and decreasing the waterdepth for current and lowest water object
							currentWater.decreamenetDepth();
							lowestWater.increamentDepth();

						//Checking the current water object has water
						if(currentWater.getWaterDepth() == 0){
							land.normalColor(xPos, yPos);
							}
						else{
							//reshade the current pixel
							land.blueColor(xPos, yPos);
							}

					
						prevCurr[0] = tempPos[0];
						prevCurr[1] = tempPos[1];}
						}// End of lowestWater synch
				 
					}//End of second if
				}// End of first if
				
			else if((xPos == 0 || yPos == 0 || xPos == xWidth || yPos == yWidth)){
				land.waterItems[xPos][yPos].resetDepth();
			}
		}
				 Flow.fp.repaint();	
						}// End of for loop
			}
	
	public void fillWater(int x, int y) {
				x1 = x;
				y1 = y;
				land.img.setRGB(x,y,Color.BLUE.getRGB());
				land.waterItems[x][y].setWaterDepth();
				depthArray[x][y] = (float)(0.04);
		
				for(int i = x-3; i<=x+4;i++){
					for(int j = y-3;j<=y+3;j++){
						depthArray[x][y] = (float)(0.04);
						land.img.setRGB(i,j,Color.BLUE.getRGB());
						land.waterItems[i][j].setWaterDepth();}
				  }
				Flow.fp.repaint();
		}

	@Override
	public void run() {
		fillWater(x1, y1);
		while(true){
		flow(low,high);}
		
	}
}
