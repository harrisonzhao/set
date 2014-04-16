package Set_GUI;

import javax.swing.*;

import java.net.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;


// We'll see how this works:
//board is good, write display and send functions, 
//and get the event listeners up and working, as well as the scoreboard!

@SuppressWarnings("serial")
public class Game extends JPanel {
	
	static JPanel cardPane, leftside, bottomLeft, rightside;
	String cardSelection[] = null;
	HashMap<JToggleButton, String> cards = null;
	
	private Lobby lobby_panel;

	Game(Lobby lobby_panel){
		this.lobby_panel = lobby_panel;
		//createAndShowGUI();
	}

	
	public void displayBoard(String srvr_string){
		//----------------------------
		//CALLED FROM SERVER
		//----------------------------
		cards.clear(); //clears board
		//JToggleButton setCards[] = null;
		// parse the message from the server and display the cards
		String cardString = srvr_string.substring(4); // get rid of teh first 4 characters
		String cardsToShow[] = cardString.split(" "); // break down string of cards
		//then serve all the cards up:
		for (int i = 0; i < cardsToShow.length; i++){
			//String cardname = "card" + cardsToShow[i]; 
			
			JToggleButton setCard = new JToggleButton(new ImageIcon("resources/imgs/" + cardsToShow[i] + ".gif"));

			setCard.setPreferredSize(new Dimension(100, 85));
			setCard.setBackground(new Color(255, 255, 255));
			setCard.addActionListener(new Selector());
			//setCards[i] = setCard; //idk
			cards.put(setCard, cardsToShow[i]); //add to hashmap to get back later...
			cardPane.add(setCard);
				
		}
		
				
	}
	
	class Selector implements ActionListener{
		public void actionPerformed(ActionEvent e){
			JToggleButton selectedCard = (JToggleButton) e.getSource(); //return button object that got pressed
			cardSelection[cardSelection.length] = cards.get(selectedCard); // return matching card number and add to array
			
		}
		
	}
	
	/*
	public void actionPerformed(ActionEvent e){
		JToggleButton selectedCard = (JToggleButton) e.getSource(); //return button object that got pressed
		cardSelection[cardSelection.length] = cards.get(selectedCard); // return matching card number and add to array
		
	}
	*/
	
	class Submitter implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if (cardSelection.length != 3){
				//tell the user they done fucked up
			} else {
				String setSubmission = "S~";
				for (int i = 0; i < 3; i++){
					setSubmission = setSubmission + cardSelection[i] + " ";
				}
				//sendMessageToServer(setSubmission);
				
			}
		}
	}
	
	public static void submitSet(String selectedCards[]) {
		if (selectedCards.length != 3){
			//tell the user they done fucked up
		} else {
			String setSubmission = "S~";
			for (int i = 0; i < 3; i++){
				setSubmission = setSubmission + selectedCards[i] + " ";
			}
			//sendMessageToServer(setSubmission);
			
		}	
		
	}
	
	public void updateScores(String srvr_message){
		//count number of players
		//
	}
	
	
	
	public void createAndShowGUI() {
		//JFrame mainframe = new JFrame("Let's Play Set!");
		//mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//mainframe.setPreferredSize(new Dimension(650, 700));
		//add two frames to this:

		JPanel mainframe = new JPanel();
		
		//leftside shall have craploads of image toggle buttons...(SET game cards)
		leftside = new JPanel(new BorderLayout());
		leftside.setBackground(new Color(95, 145, 150));
		leftside.setPreferredSize(new Dimension(350, 700));
		leftside.setVisible(true);
		
		cardPane = new JPanel(new FlowLayout());
		cardPane.setBackground(new Color(95, 145, 150));
		cardPane.setPreferredSize(new Dimension(350, 600));
		
		//holds button underneath cards
		bottomLeft = new JPanel();
		bottomLeft.setBackground(new Color(95, 145, 150));
		bottomLeft.setPreferredSize(new Dimension(350, 50));
		
		//Right side will hold scorechart and chat
		rightside = new JPanel(new BorderLayout());
		rightside.setBackground(new Color(150, 200, 200));
		rightside.setPreferredSize(new Dimension(300, 700));
		rightside.setVisible(true);
		
		
		//the following examples is just display testing:
		
		/*
		String num = "44";
		//create imageicons for set card checkboxes
		ImageIcon set1 = new ImageIcon("resources/imgs/" + num + ".gif"); //that works! :)
		ImageIcon set2 = new ImageIcon("resources/imgs/53.gif");
		ImageIcon set3 = new ImageIcon("resources/imgs/21.gif");
		
		//create checkboxes from set images
		JToggleButton setcard1 = new JToggleButton(set1);
		JToggleButton setcard2 = new JToggleButton(set2);
		JToggleButton setcard3 = new JToggleButton(set3);
		
		//organizes layout
		setcard1.setPreferredSize(new Dimension(100, 85));
		setcard2.setPreferredSize(new Dimension(100, 85));
		setcard3.setPreferredSize(new Dimension(100, 85));		
		
		//add event handling...for some reason it gave me problems
		/*
		setcard1.addActionListener(this);
		setcard2.addActionListener(this);
		setcard3.addActionListener(this);
		 */
		
		/*
		//adds to frame
		cardPane.add(setcard1);
		cardPane.add(setcard2);
		cardPane.add(setcard3);
		*/
		
		//lets try this...
		
		
		JButton submitbutton = new JButton("Submit Set!");
		submitbutton.addActionListener(new Submitter());
		bottomLeft.add(submitbutton, BorderLayout.CENTER);
		
		
		
		leftside.add(cardPane, BorderLayout.NORTH);
		leftside.add(bottomLeft, BorderLayout.SOUTH);
		
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
	    chatpanel.add(enteredText, BorderLayout.NORTH);
	    chatpanel.add(typedText, BorderLayout.SOUTH);
	    
	    //add chatpanel to rightside
		rightside.add(chatpanel, BorderLayout.PAGE_END); //I have tried EVERYTHING to put this thing at the bottom, to no avail
	    
	    
		//finalize mainframe
		mainframe.add(leftside, BorderLayout.WEST);
		mainframe.add(rightside, BorderLayout.EAST);
		//mainframe.getContentPane().add(leftside, BorderLayout.WEST);
		//mainframe.getContentPane().add(rightside, BorderLayout.EAST);
		
		//mainframe.pack();
        mainframe.setVisible(true);	
		
	}

	
	public static void main(String[] args) {
		// This does the work at the end...
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                wipGUI thegui = new wipGUI();
            }
        });

	}

}
