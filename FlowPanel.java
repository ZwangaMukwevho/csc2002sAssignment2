 
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Color;


//import java.awt.*;  
//mport java.awt.event.*;  

public class FlowPanel extends JPanel implements Runnable {
	Terrain land ;
	static int startFlow = 0;
	
	int h;
	int l;
	
	int x1;
	int y1;

	FlowPanel(Terrain terrain) {
		land=terrain;
	}

	FlowPanel(Terrain terrain,int lo, int hi){
		land=terrain;
		l = lo;
		h = hi;
	}
		
	// responsible for painting the terrain and water
	// as images
	@Override
    protected void paintComponent(Graphics g) {
		
		  
		super.paintComponent(g);
		g.setColor(Color.BLUE);
		

		// draw the landscape in greyscale as an image
		if (land.getImage() != null){
			g.drawImage(land.getImage(), 0, 0, null);
			//g.drawLine( 400, 400,500,500 );
		}
	}


	waterPainter waterPobj = new waterPainter();
	
	volatile boolean check = true;
	volatile boolean stop = false;
	

	
	String fileName = "medsample_in.txt";
	float[][] depthArray = waterPobj.waterDepArray();
	int tempDepth;
	

	
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
	
	public int getXclick(){
		return x1;
	}

	public int getYclick(){
		return y1;
	}
	
	
	//Boolean that is used to stop the simulation
	public void stop(){
		check = false;
	}

	// Boolean that is used to pause the simulation
	public void pause(){
		stop = true;
	}

	public void unpause(){
		stop = false;
	}

	// Reseting the thread
	public void restart(){
		int tempPos [] = new int[2];
		int size = land.dim();
		
		// Reset everything
		for(int i = 0; i<size;i++){
			land.locate(i, tempPos);
			land.normalColor(tempPos[0], tempPos[1]);
			land.waterItems[tempPos[0]][tempPos[1]] = new water(tempPos[0],tempPos[1]);
			depthArray[tempPos[0]][tempPos[1]] = (float)(0.00);
		}

		
	}

	boolean d = false;
	public void checking(){
		d = true;
		
	}
	

	//Boolean that is used
	public  void run() {	
		// display loop here
		// to do: this should be controlled by the GUI		// to allow stopping and starting
		
		
	}

	


	
}