 
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
	int x;
	int y;

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
	

	int check = 0;
	String fileName = "medsample_in.txt";
	float[][] depthArray = waterPobj.waterDepArray();
	int tempDepth;
	int tempPos [] = new int[2];

	
	public void readData(){
		land.readData(fileName);
	}
	
	public void addWater(water W){
		W.setWaterDepth();
		depthArray[W.getX()][W.getY()] = waterPobj.convertWater(W.getWaterDepth());
		WaterList.add(W);
		
		this.repaint();
	}
	
	
	
	public void flow() {
		//int size = water.get
		startFlow = 1;
		int count = 0;
		int xPos;
		int yPos;
		
		waterObj = WaterList.get(0);

		//Getting size of water objectsd
		int size = WaterList.size();
		boolean check;

		while(size!=0){

			for(int i = 0; i<size;i++){

				//getting node that is being currently processed
				waterObj = WaterList.get(i);

				x = WaterList.get(i).getX();
				y = WaterList.get(i).getY();
				newPos = waterPobj.compare(x, y,fileName,depthArray );

				//Checking if there's a basin
				if(!(newPos[0]==0 || newPos[0]==0)){

					//Decreasing depth
					waterObj.decreamenetDepth();
					waterPobj.decrementDepth(depthArray, newPos);
					

					//Checking if there new position is already initialised with a water object
					check =  waterPobj.check(WaterList,newPos);
					//System.out.println(depthArray[newPos[0]][newPos[1]]);
					if(check){
						waterObj2 = new water(newPos[0],newPos[1]);
						waterObj2.increamentDepth();
						waterPobj.inreamentDepth(depthArray, newPos);
						WaterList.add(waterObj2);
						//System.out.println(waterObj2.getWaterDepth());
					}
					else{
						waterObj2 = waterPobj.find(WaterList,newPos[0],newPos[1]);
						
						waterObj2.increamentDepth();
						waterPobj.inreamentDepth(depthArray, newPos);
						//System.out.println(depthArray[newPos[0]][newPos[1]]);
					}				
					
					//removing waterObj that has zero length
					if(waterObj.getWaterDepth()<=0){
						
						Iterator<water> iter = WaterList.iterator();
						while(iter.hasNext()){
							waterObj3 = iter.next();
							xPos = waterObj3.getX();
							yPos = waterObj3.getY();
				
							if(xPos == x && yPos == y){
								//wO = waterObject;
								iter.remove();
							}
							count++;
						}

						
						count = 0;
					}

						}
					
					
				size = WaterList.size();
				//System.out.println(size);
				this.repaint();
			}// End of for loop
			
		}//End of while loop
		
						}

		
	
		
	
	@Override 
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
	}
	
	public  void run() {	
		// display loop here
		// to do: this should be controlled by the GUI		// to allow stopping and starting
		flow();

	    repaint();
	}


	
}