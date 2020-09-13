 
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

	private ArrayList<water> Water = new ArrayList<water>();
	private ArrayList<water> Water2 = new ArrayList<water>();
	waterPainter waterPobj = new waterPainter();
	water waterObj;
	water waterObj2;
	int check = 0;
	String fileName = "medsample_in.txt";
	int[][] depthArray = waterPobj.waterDepArray();
	int tempDepth;

	
	public void readData(){
		land.readData(fileName);
	}
	
	public void addWater(water W){
		W.setWaterDepth();
		Water.add(W);
		
		this.repaint();
	}
	
	
	
	public void flow() {

	
		startFlow = 1;
		waterObj = Water.get(0);
			x = waterObj.getX();
			y = waterObj.getY();
	
			for(int i = 0; i<100;i++){
	
				if(check == 0){
					depthArray[x][y] = waterObj.getWaterDepth();
					System.out.println(depthArray[x][y]+" "+x+" "+y);
					newPos = waterPobj.compare(x, y,fileName );
					//System.out.println(y+ " "+x);
					check = 1;
				}
				else{
					try{
					newPos = waterPobj.compare(newPos[0], newPos[1],fileName );}
					catch(Exception e){
						;
					}
				}
				waterObj2 = new water(newPos[0],newPos[1]);
				Water2.add(waterObj2);
			
				this.repaint();
							}
	
		}
	
		
	
	@Override 
	public void paint(Graphics g){
		if (startFlow == 0){
		g.drawImage(land.getImage(), 0, 0, null);
		for (water waterObject: Water){
			waterObject.draw(g);
		}}
		else{
			
			g.drawImage(land.getImage(), 0, 0, null);
			for (water waterObject1: Water2){
				waterObject1.drawFlow(g);
				
			}
		}
	}
	
	public void run() {	
		// display loop here
		// to do: this should be controlled by the GUI		// to allow stopping and starting
		flow();

	    repaint();
	}


	
}