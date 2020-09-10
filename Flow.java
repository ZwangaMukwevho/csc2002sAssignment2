import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
//import java.awt.event.*;  

public class Flow  {
	static long startTime = 0;
	static int frameX;
	static int frameY;
	static FlowPanel fp;

	// start timer
	private static void tick(){
		startTime = System.currentTimeMillis();
	}
	
	// stop timer, return time elapsed in seconds
	private static float tock(){
		return (System.currentTimeMillis() - startTime) / 1000.0f; 
	}
	
	public static void setupGUI(int frameX,int frameY,Terrain landdata)  {
		
		Dimension fsize = new Dimension(800, 800);
		JFrame frame = new JFrame("Waterflow"); 
		
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		//frame.getContentPane().addMouseListener(new MouseClick());
    	
		  JPanel g = new JPanel();
		//g.addMouseListener(new MouseClick());
		System.out.println(landdata.getDimX()+ " land_data_x");
		System.out.println(landdata.getDimY()+ " land_data_y");
        g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS)); 
		fp = new FlowPanel(landdata);
		fp.addMouseListener(new MouseClick(fp));
		fp.setPreferredSize(new Dimension(frameX,frameY));
		g.add(fp);
	    
		// to do: add a MouseListener, buttons and ActionListeners on those buttons
	   	
		JPanel b = new JPanel();
		b.setLayout(new BoxLayout(b, BoxLayout.LINE_AXIS));
		
		//Creating buttons

		JButton endB = new JButton("End");;
		JButton resetB = new JButton("Reset");;
		JButton pauseB = new JButton("Pause");;
		JButton playB = new JButton("Play");;

		// add the listener to the jbutton to handle the "pressed" event
		endB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				// to do ask threads to stop
				frame.dispose();
			}
		});

		// Editing the button layout and color
		b.add(Box.createHorizontalGlue()); 
		endB.setBackground(Color.ORANGE); 
		 endB.setPreferredSize(new Dimension(70, 27));
		b.add(endB);
		
		b.add(Box.createHorizontalGlue());  
		resetB.setBackground(Color.ORANGE); 
		resetB.setPreferredSize(new Dimension(70, 27));
		b.add(resetB);

		b.add(Box.createHorizontalGlue()); 
		pauseB.setBackground(Color.ORANGE); 
		pauseB.setPreferredSize(new Dimension(70, 27));
		b.add(pauseB);

		b.add(Box.createHorizontalGlue());   
		playB.setBackground(Color.ORANGE); 
		playB.setPreferredSize(new Dimension(70, 27)); 
		b.add(playB);

		b.add(Box.createHorizontalGlue());  
		g.add(b);
    	
		frame.setSize(frameX, frameY+50);	// a little extra space at the bottom for buttons
      	frame.setLocationRelativeTo(null);  // center window on screen
      	frame.add(g); //add contents to window
        frame.setContentPane(g);
        frame.setVisible(true);
        Thread fpt = new Thread(fp);
        fpt.start();
	}
	
		
	public static void main(String[] args) {
		Terrain landdata = new Terrain();
		
		// check that number of command line arguments is correct
		if(args.length != 1)
		{
			System.out.println("Incorrect number of command line arguments. Should have form: java -jar flow.java intputfilename");
			//System.exit(0);
		}
				
		// landscape information from file supplied as argument
		// 
		landdata.readData("medsample_in.txt");
		
		frameX = landdata.getDimX();
        frameY = landdata.getDimY();
		SwingUtilities.invokeLater(()->setupGUI(frameX, frameY, landdata));
		
		// to do: initialise and start simulation
	}
}