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
	static simClass[] sc;
	static volatile boolean checkStart = false;

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
    	
		  JPanel g = new JPanel();
		
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
				//sc.stopEx();
				for(int i = 0;i<4;i++){
					sc[i].stopEx();;
				}
				fp.stop();
				frame.dispose();
				}
			});

		// Adds action listener to the play button that starts the simulation
		playB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				// to do ask threads to play
				
				int x = fp.getXclick();
				int y = fp.getYclick();
				if(!(checkStart)){
				sc = new simClass[4];
				//sc.start();

				int size = landdata.dim();
					int quarter = size/4;
					int tempLo ;
					int tempHi;
					// Creating the four threads
					for(int i = 0;i<4;i++){
						tempLo = i*quarter;
						tempHi = i*quarter + quarter;
						sc[i] = new simClass(landdata,tempLo,tempHi,x,y);
						sc[i].start();
					}
				checkStart = true;
								}
				else{
					for(int i = 0;i<4;i++){
						sc[i].unpause();
					}
					//sc.unpause();
					fp.unpause();}
					}

				
				
					
			});
		
		// Adds action listener to the play button that pauses the simulation
		pauseB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				// to do ask threads to pause
				for(int i = 0;i<4;i++){
					sc[i].pause();;
				}	
				//sc.pause();
				fp.pause();
								}
				});
		
		// Adds action listener to the play button that pauses the simulation
		resetB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				// to do ask threads to reset	
				for(int i = 0;i<4;i++){
					sc[i].pause();}
				for(int i = 0;i<4;i++){
					sc[i].restart();}
				
				fp.pause();
				fp.restart();
				
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
