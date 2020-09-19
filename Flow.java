import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
//import java.awt.event.*;  

public class Flow {
	static long startTime = 0;
	static int frameX;
	static int frameY;
	static FlowPanel fp;
	static simClass[] sc;
	static JLabel timerLabel;
	static volatile boolean checkStart = false;
	static volatile boolean timeStart = false;
	static volatile boolean restart = false;
	static volatile boolean step = true;
	static volatile int count = 0;
	static dummy dummyObj = new dummy();

	// start timer
	private static void tick() {
		startTime = System.currentTimeMillis();
	}

	/*
	 * public static void setTimer(){ float currentTime; String currentTimeString;
	 * tick(); while(true){ currentTime = tock(); currentTimeString =
	 * String.valueOf(currentTime); timer.setText(currentTimeString);
	 * 
	 * } }
	 */

	// stop timer, return time elapsed in seconds
	private static float tock() {
		return (System.currentTimeMillis() - startTime) / 1000.0f;
	}

	public static void setupGUI(int frameX, int frameY, Terrain landdata) {

		Dimension fsize = new Dimension(800, 800);
		JFrame frame = new JFrame("Waterflow");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());

		JPanel g = new JPanel();

		g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS));
		fp = new FlowPanel(landdata);
		fp.addMouseListener(new MouseClick(fp));
		fp.setPreferredSize(new Dimension(frameX, frameY));
		g.add(fp);

		// to do: add a MouseListener, buttons and ActionListeners on those buttons

		JPanel b = new JPanel();
		b.setLayout(new BoxLayout(b, BoxLayout.LINE_AXIS));

		// Creating buttons

		JButton endB = new JButton("End");
		;
		JButton resetB = new JButton("Reset");
		;
		JButton pauseB = new JButton("Pause");
		;
		JButton playB = new JButton("Play");
		;

		// add the listener to the jbutton to handle the "pressed" event
		endB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// to do ask threads to stop
				// sc.stopEx();
				for (int i = 0; i < 4; i++) {
					sc[i].stopEx();
					;
				}
				fp.stop();
				frame.dispose();
			}
		});

		// Adds action listener to the play button that starts the simulation
		playB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// to do ask threads to play

				int x = fp.getXclick();
				int y = fp.getYclick();
				if (!(checkStart)) {
					sc = new simClass[4];
					// sc.start();

					int size = landdata.dim();
					int quarter = size / 4;
					int tempLo;
					int tempHi;
					// Creating the four threads
					for (int i = 0; i < 4; i++) {
						tempLo = i * quarter;
						tempHi = i * quarter + quarter;
						sc[i] = new simClass(landdata, tempLo, tempHi, x, y, dummyObj);
						sc[i].start();
					}
					tock();
					checkStart = true;
					timeStart = true;
				} else {
					timeStart = true;
					for (int i = 0; i < 4; i++) {
						sc[i].unpause();
					}
					// sc.unpause();
					fp.unpause();
				}

			}

		});

		// Adds action listener to the play button that pauses the simulation
		pauseB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// to do ask threads to pause
				timeStart = false;
				for (int i = 0; i < 4; i++) {
					sc[i].pause();
					;
				}
				// sc.pause();
				fp.pause();
			}
		});

		// Adds action listener to the play button that pauses the simulation
		resetB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// to do ask threads to reset
				for (int i = 0; i < 4; i++) {
					sc[i].pause();
				}
				for (int i = 0; i < 4; i++) {
					sc[i].restart();
				}

				fp.pause();
				fp.restart();

			}
		});

		// timerClass t = new timerClass();
		// playB.addActionListener(t);

		// Creating labels
		float currentTime;

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

		timerLabel = new JLabel("0:00");
		if (checkStart == true) {
			timerLabel.setText("zwanga");
		}
		
		b.add(timerLabel);
		g.add(b);

		frame.setSize(frameX, frameY + 50); // a little extra space at the bottom for buttons
		frame.setLocationRelativeTo(null); // center window on screen
		frame.add(g); // add contents to window
		frame.setContentPane(g);
		frame.setVisible(true);
		Thread fpt = new Thread(fp);
		fpt.start();

		String currentTimeString;
		while (checkStart) {
			currentTime = tock();
			currentTimeString = String.valueOf(currentTime);
			// searchArea.setText(currentTimeString);
		}
	}

	public static void setFalse (){
		step = false;
	}

	public static void setTrue(){
		step = true;
	}

	public static void main(String[] args) {
		Terrain landdata = new Terrain();

		// check that number of command line arguments is correct
		if (args.length != 1) {
			System.out.println(
					"Incorrect number of command line arguments. Should have form: java -jar flow.java intputfilename");
			// System.exit(0);
		}

		// landscape information from file supplied as argument
		//
		landdata.readData("medsample_in.txt");

		frameX = landdata.getDimX();
		frameY = landdata.getDimY();
		SwingUtilities.invokeLater(() -> setupGUI(frameX, frameY, landdata));
		int counter = 0;
		setTrue();
		while (step) {
			
			if (checkStart) {
				if (sc[0].getflag() == true || sc[1].getflag() == true || sc[2].getflag() == true
						|| sc[3].getflag() == true) {
							counter++;
							System.out.println(counter);

					try {
						sc[0].join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						sc[1].join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						sc[2].join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						sc[3].join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
							
				}
			setFalse();
			}}

		// to do: initialise and start simulation
		/*while (true) {s
			// sc[1].getState();
			// sc[2].getState();
			// sc[3].getState();
			// sc[4].getState();
			if (checkStart) {

				// System.out.println(sc[0].getState()+" "+sc[1].getState()+"
				// "+sc[2].getState()+" "+sc[2].getState());

				if (sc[0].getflag()) {
					synchronized (sc[0]) {
						try {
							sc[0].wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
								}
					}
				if (sc[1].getflag()) {
					synchronized (sc[1]) {
						try {
							sc[1].wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
								}
					}
				
					if (sc[2].getflag()) {
						synchronized (sc[2]) {
							try {
								sc[2].wait();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
									}
						}
						if (sc[3].getflag()) {
							synchronized (sc[3]) {
								try {
									sc[3].wait();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
										}
							}
						
							synchronized(sc[0]){
								sc[0].notify();
							}

							synchronized(sc[1]){
								sc[1].notify();
							}

							synchronized(sc[2]){
								sc[2].notify();
							}

							synchronized(sc[3]){
								sc[3].notify();
							}
			
		}//While loop
	}*/

	//}

	/*public static class timerClass implements ActionListener{
		static Timer timer;
		public void actionPerformed(ActionEvent e){
			int count = 0;
			timerLabel.setText(String.valueOf("0"));
			
			//Making time class
			TimeClass tc = new TimeClass(count);
			timer = new Timer(100, tc);
			timer.start();
			
		}
		public static void stopTimer() {
			timer.stop();
		}

		public static void restart(){
			timer.restart();
		}

		public static void startTimer(){
			timer.start();
		}

		
	}

	public static class TimeClass implements ActionListener {
		int counter;
		int previousCounter = 0;

		public TimeClass(int counter) {
			this.counter = counter;
		}

		public void actionPerformed(ActionEvent tc) {
			if(timeStart){
				timerClass.startTimer();
				counter = counter + previousCounter;
				counter++;
				tick();
			
				if (counter >= 1) {
					timerLabel.setText(String.valueOf(counter));}}
			
			else {
				//timerLabel.setText("0");
				previousCounter = counter;
				timerClass.stopTimer();
				//Toolkit.getDefaultToolkit().beep();
			}
		}
	}*/
}
