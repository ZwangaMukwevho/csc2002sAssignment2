 
import java.awt.Graphics;
import javax.swing.JPanel;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
import java.awt.Color;
import java.util.*;

//import java.awt.*;  
//mport java.awt.event.*;  

public class FlowPanel extends JPanel implements Runnable {
	Terrain land ;
	static int startFlow = 0;
	int [] newPos = new int[2];
	int [] tempPrev = new int[2];
	int x1;
	int y1;

	FlowPanel(Terrain terrain) {
		land=terrain;
	}
		
	// responsible for painting the terrain and water
	// as images
	@Override
    protected void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		  
		super.paintComponent(g);
		g.setColor(Color.BLUE);
		

		// draw the landscape in greyscale as an image
		if (land.getImage() != null){
			g.drawImage(land.getImage(), 0, 0, null);
			//g.drawLine( 400, 400,500,500 );
		}
	}

	private ArrayList<water> WaterList = new ArrayList<water>();
	private ArrayList<water> Water2 = new ArrayList<water>();
	waterPainter waterPobj = new waterPainter();
	water waterObj;
	water waterObj2;
	water waterObj3;
	boolean check = true;
	

	
	String fileName = "medsample_in.txt";
	float[][] depthArray = waterPobj.waterDepArray();
	int tempDepth;
	int tempPos [] = new int[2];

	
	public void readData(){
		land.readData(fileName);
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
		this.repaint();
	}
	
	
	
	public void flow() {
		//int size = water.get
		
		int count = 0;
		int xPos;
		int yPos;
		
		
		
		//Getting size of water objectsd

		//Defining variables that will be used in the loop
		int size = land.dim();
		
		float waterDepth;
		int tempIndex = 0;
		
		float tempDepth;

		// current water node
		water currentWater;
		water lowestWater;

		for(int i = 0; i<size;i++){

				// Obtaining random position to permute
				land.locate(i, tempPos);
				
				// Getting x and y position of the temporay chosen position
				xPos = tempPos[0];
				yPos = tempPos[1];
				
				// Checking of that current position has water
				currentWater = land.waterItems[xPos][yPos];

				waterDepth = currentWater.getWaterDepth();
				
				if(xPos!=0){
					if(yPos !=0){
				//Checking if the current pixel has water
			 	if(waterDepth!=0){
					
					//Finding the lowest closest position
					
					
					newPos = waterPobj.compare(xPos, yPos,fileName,depthArray );
					if(newPos[0]==0 || newPos[1]==0){
						;


					}
					else{
					lowestWater = land.waterItems[newPos[0]][newPos[1]];

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
					tempPrev[0] = newPos[0];
					tempPrev[1] = newPos[1];}
					
				 }//End of waterDepth if
				}//End of second if
			}// End of first if
				 
				 this.repaint();
				
					
						}// End of for loop
				
				size = WaterList.size();
				//System.out.println(size);
				//System.out.println(size);
				//this.repaint();
			}
			
						

		
	
		
	
	/*@Override 
	public void paint(Graphics g){
		if (startFlow == 0){
		g.drawImage(land.getImage(), 0, 0, null);
		for (water waterObject: WaterList){
			waterObject.drawFlow(g);
		}}
		else{
			g.drawImage(land.getImage(), 0, 0, null);
			int counter = 0;
			for (water waterObject1: WaterList){
					
					waterObject1.drawFlow(g);
			}
			counter = 0;
		}
	}*/
	
	public void stop(){
		check = false;
	}
	public  void run() {	
		// display loop here
		// to do: this should be controlled by the GUI		// to allow stopping and starting
		fillWater(x1, y1);
		while(check){
		
		flow();
		repaint();}
	

	  
	}

	


	
}