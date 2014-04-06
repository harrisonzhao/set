package gamestuff;

import javax.swing.*;
import java.net.*;
import java.awt.*;
import java.awt.event.ActionListener;


// We'll see how this works:


public class wipGUI extends JFrame {
	
	
	//I'm not good at sockets yet, so I haven't implemented a working version of the connection yet
	//I took out everything else but what should work, which is commented out for now
	//The layout is that we have the cards on the left, supposed to be checkboxes, but working on fixing that...
	
	//the top right should be a table showing player names and scores
	//the bottom right should eventually be a functional chat widget
	
	//so far, i'm still working on formatting; things have been weird for two weeks; sorry it's behind...
	//since i have nlp nearly done i've been focusing on getting the sockets and event handling to work, but to no avail.
	//i know its not much, sorry again and I'll be working on it all weekend, as I did last weekend.
	
	//aside from the obvious, let me know what else you want/need implemented
	
	
	/*
	private Socket socket;
    private Out out;
    private In in;

	
	public connectToServ(String acctname, String hostName) {
		   
		        try {
		            socket = new Socket(hostName, 5122);
		            out    = new Out(socket);
		            in     = new In(socket);
		        }
		        catch (Exception ex) { ex.printStackTrace(); }
	}
	*/

	
	private static void createAndShowGUI() {
		JFrame mainframe = new JFrame("Let's Play Set!");
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainframe.setPreferredSize(new Dimension(700, 600));
		//add two frames to this:
		
		//leftside shall have craploads of image buttons...(SET game cards)
		JPanel leftside = new JPanel();
		leftside.setBackground(new Color(248, 213, 131));
		leftside.setPreferredSize(new Dimension(400, 600));
		leftside.setVisible(true);
		
		//Right side will hold scorechart and chat
		JPanel rightside = new JPanel();
		rightside.setBackground(new Color(150, 200, 200));
		rightside.setPreferredSize(new Dimension(300, 600));
		rightside.setVisible(true);
		
		//create imageicons for set card checkboxes
		ImageIcon set1 = new ImageIcon("resources/imgs/66.gif");
		ImageIcon set2 = new ImageIcon("resources/imgs/53.gif");
		ImageIcon set3 = new ImageIcon("resources/imgs/21.gif");
		
		//create checkboxes from set images
		JCheckBox setcard1 = new JCheckBox(set1);
		JCheckBox setcard2 = new JCheckBox(set2);
		JCheckBox setcard3 = new JCheckBox(set3);
		
		//organizes layout
		//todo: get more rows to shop up on some condition?
		setcard1.setPreferredSize(new Dimension(100, 100));
		setcard1.setLocation(0, 0);
		setcard2.setPreferredSize(new Dimension(100, 100));
		setcard2.setLocation(125, 0);
		setcard3.setPreferredSize(new Dimension(100, 100));
		setcard3.setLocation(250, 0);
		
		//add event handling...for some reason it gave me problems
		/*
		setcard1.addActionListener(this);
		setcard2.addActionListener(this);
		setcard3.addActionListener(this);
		 */
		
		//adds to frame
		leftside.add(setcard1, BorderLayout.EAST);
		leftside.add(setcard2, BorderLayout.NORTH);
		leftside.add(setcard3, BorderLayout.WEST);
		
		//TABLE STUFF
		//Table stuff will depend on database setup...I'll work on that
		
		//JTable scores = new JTable(data, {"Players", "Score"});
		//rightside.add(scores, BorderLayout.NORTH);
		
		
		
		//CHAT STUFF
		//This panel will be at the bottom of the right side, holding the "Player Chat" label, and the input and log windows
		
		//panel and label setup
		JPanel chatpanel = new JPanel();
		chatpanel.setPreferredSize(new Dimension(275, 230));
		chatpanel.setBackground(new Color(100, 250, 150));
		JLabel chatlabel = new JLabel("Player Chat");
		chatpanel.add(chatlabel);
		
		//setup for fields
		JTextArea  enteredText = new JTextArea(10, 150);
	    JTextField typedText   = new JTextField(150);
	    
		enteredText.setVisible(true);
		//enteredText.setBackground(new Color(100, 240, 200)); //set random colors for testing
		typedText.setVisible(true);
		typedText.setEditable(true);
		//typedText.setBackground(new Color(200, 20, 50)); //set random colors for testing
		
		//add event handling to send text to server
		//typedText.addActionListener(this);
		
		
		
		//add the two fields to chatpanel...
	    chatpanel.add(enteredText, BorderLayout.CENTER);
	    chatpanel.add(typedText, BorderLayout.SOUTH);
	    
	    //add chatpanel to rightside
		rightside.add(chatpanel, BorderLayout.PAGE_END); //I have tried EVERYTHING to put this thing at the bottom, to no avail
	    
		//finalize mainframe
		mainframe.getContentPane().add(leftside, BorderLayout.WEST);
		mainframe.getContentPane().add(rightside, BorderLayout.EAST);
		
		mainframe.pack();
        mainframe.setVisible(true);	
		
	}

	
	public static void main(String[] args) {
		// This does the work at the end...
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

	}

}
