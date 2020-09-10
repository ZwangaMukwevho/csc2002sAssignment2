 
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
	
	FlowPanel(Terrain terrain) {
		land=terrain;
	}
		
	// responsible for painting the terrain and water
	// as images
	@Override
    protected void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		System.out.println(width);
		System.out.println(height);
		  
		super.paintComponent(g);
		g.setColor(Color.BLUE);
		

		// draw the landscape in greyscale as an image
		if (land.getImage() != null){
			g.drawImage(land.getImage(), 0, 0, null);
			//g.drawLine( 400, 400,500,500 );
		}
	}

	private List<water> Water = new LinkedList<water>();

	public void addWater(water W){
		Water.add(W);
		//this.repaint();
	}

	@Override 
	public void paint(Graphics g){
		for (water waterObject: Water){
			waterObject.draw(g);
		}
	}
	
	public void run() {	
		// display loop here
		// to do: this should be controlled by the GUI		// to allow stopping and starting
	    repaint();
	}


	
}