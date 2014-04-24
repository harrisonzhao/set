package Set_GUI;

import javax.swing.*;

import java.net.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;


// We'll see how this works:
//board is good, write display and send functions,
//and get the event listeners up and working, as well as the scoreboard!

@SuppressWarnings("serial")
public class Game extends JPanel {

	static JPanel cardPane, leftside, bottomLeft, rightside;
	static JTextField typedText;
	static JTextArea enteredText;
	boolean gameOn = false; // flag to signal start of game
	public JButton submitbutton; // changed to local declaration, to change text on the fly
	private String myUsername;

	JPanel mainframe;
	SetClientProtocol callingObj;

	ArrayList<String> cardSelection = new ArrayList<String>();
	HashMap<JToggleButton, String> cards = new HashMap<JToggleButton, String>();
	String[] playerScores = null;
	String[] playerNames = null;

	private Lobby lobby_panel;
	private Login login_panel;

	Game(Login login_panell, Lobby lobby_panel){
		this.lobby_panel = lobby_panel;
		this.login_panel = login_panel;
		//createAndShowGUI();
	}

	public void setClient(SetClientProtocol callingObj, String username) {
		this.callingObj = callingObj;
		this.myUsername = username;
	}

	public void displayBoard(String[] srvr_string){
		for (int i = 1; i < srvr_string.length; i++){
			System.out.println(srvr_string[i]);
		}

		///so now we need to handle all the things:
		switch (srvr_string[1].charAt(0)){
			case 'U':
				System.out.println("so i got a score msg...i'll deal with that later");
				break;

			case 'F':
				System.out.println("Game ovah!");
				//end game
				break;
			default:
				//either board(B), new scored (Y or N), or start(s)
				//so board...and then if you need to worry about scores...
				System.out.println("Board received:");
				System.out.println(srvr_string[2]);
				
				if (srvr_string[1].charAt(0) == 'S'){
				} else {
					System.out.println("clearing board:");
					cards.clear(); //clears held list of cards
					cardPane.removeAll(); //clears board
				}
				

				// parse the card list from the server and display the cards
				String cardString = srvr_string[2]; // get card list

				String cardsToShow[] = cardString.split(" "); // break down string of cards
				//then serve all the cards up:
				System.out.println("Received " + cardsToShow.length + " cards:");
				for (int i = 0; i < cardsToShow.length; i++){

					System.out.println("Creating card for " + cardsToShow[i]);

					JToggleButton setCard = new JToggleButton(new ImageIcon("src/main/resources/set images/" + cardsToShow[i] + ".gif"));

					setCard.setPreferredSize(new Dimension(100, 85));
					setCard.setBackground(new Color(255, 255, 255));
					setCard.addItemListener(new Selector());

					cards.put(setCard, cardsToShow[i]); //add to hashmap to get back later...
					cardPane.add(setCard); //display the card obviously

				}

				if ((srvr_string[1].charAt(0) == 'Y') || (srvr_string[1].charAt(0) == 'N')){ //if scores need to be updated
					System.out.println("updating scores:");
					String scoreStr = srvr_string[3]; // parse score segments
					String scores[] = scoreStr.split(" "); //get individual scores
					System.out.println("We have only " + scores.length + " score");
					//for (int j = 0; j < scores.length; j++){
					//	playerScores[j] = scores[j]; //build score list
					//}
				}
				break;


		}
		cardPane.updateUI();
		System.out.println("Done showing cards");
		/*
		cards.clear(); //clears board

		// parse the card list from the server and display the cards
		String cardString = srvr_string[2]; // get card list
		String cardsToShow[] = cardString.split(" "); // break down string of cards
		//then serve all the cards up:
		for (int i = 0; i < cardsToShow.length; i++){
			//String cardname = "card" + cardsToShow[i];

			JToggleButton setCard = new JToggleButton(new ImageIcon("resources/imgs/" + cardsToShow[i] + ".gif"));

			setCard.setPreferredSize(new Dimension(100, 85));
			setCard.setBackground(new Color(255, 255, 255));
			setCard.addItemListener(new Selector());
			//setCards[i] = setCard; //idk
			cards.put(setCard, cardsToShow[i]); //add to hashmap to get back later...
			cardPane.add(setCard);
		}

		*/
	}

	// adds to my enteredText window...or, chatUpdate from server needs to send to it
	//Chat message button listener
	class TextSend implements ActionListener{
		public void actionPerformed(ActionEvent e){
			String chattext = typedText.getText();
			callingObj.sendMessageToServer("T~" + chattext);
			//enteredText.append(myUsername + ": " + chattext + "\n");
			typedText.setText("");
		}
	}

	//Exit game button listener
	class GameExit implements ActionListener{
		public void actionPerformed(ActionEvent e){
			callingObj.sendMessageToServer("E");  // self explanatory
		}
	}


	// item listener, so selections are undoable...
	// and the cardSelection list is an array list to make things easier overall
	class Selector implements ItemListener{
		public void itemStateChanged(ItemEvent i){
			JToggleButton selectedCard = (JToggleButton) i.getSource(); //return button object that got pressed
			if(i.getStateChange()==ItemEvent.SELECTED){ //if the card was actually selected
				cardSelection.add(cards.get(selectedCard)); // return matching card number and add to array
				System.out.println("Selected card " + cards.get(selectedCard));
			} else { //remove the card
				cardSelection.remove(cards.get(selectedCard));
				System.out.println("Un-selected card " + cards.get(selectedCard));
			}
		}
	}


	class Submitter implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if (gameOn == false) { //if first press to start game;
				gameOn = true;
				System.out.println("the game has begun");
				submitbutton.setText("Submit Set!"); //change text
				System.out.println("button text changed");
				callingObj.sendMessageToServer("G"); //self explanatory
				System.out.println("told the server to start game:");
			} else{
				System.out.println("there are this many cards seen as selected: " + cardSelection.size());
				if (cardSelection.size() != 3){
					System.out.println("Invalid set submission!");
				}
				else {
					String setSubmission = "S~";
					for (int i = 0; i < 3; i++){
						if (i != 2) {
							setSubmission = setSubmission + cardSelection.get(i) + "~";
						}
						else {
							setSubmission = setSubmission + cardSelection.get(i);
						}
					}
					callingObj.sendMessageToServer(setSubmission);
					cardSelection.clear();
				}
			}
		}
	}

	public void updateScores(String srvr_message){
		//count number of players
		//
	}




	public void createAndShowGUI() {

		/*JPanel*/
		mainframe = new JPanel();

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


		submitbutton = new JButton("Ready To Play!");
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

		//bar to hold textField and send button
		JPanel chatbar = new JPanel();
		chatbar.setPreferredSize(new Dimension(275, 26));
		chatpanel.setBackground(new Color(100, 250, 220));

		//setup for fields
		enteredText = new JTextArea(10, 24);
	    	typedText   = new JTextField(15);

	    	//setup button
	    	JButton chatbutton = new JButton("Send");
	    	chatbutton.setPreferredSize(new Dimension(70, 20));
	    	chatbutton.addActionListener(new TextSend());

		enteredText.setVisible(true);
		enteredText.setEditable(false);
		//enteredText.setBackground(new Color(100, 240, 200)); //set random colors for testing
		typedText.setVisible(true);
		typedText.setEditable(true);

		//ExitButton
		JButton exitbutton = new JButton("Exit Game");
		exitbutton.setPreferredSize(new Dimension(120, 25));
		exitbutton.addActionListener(new GameExit());
		rightside.add(exitbutton, BorderLayout.PAGE_START);

		//add the two fields to chatpanel...
	    	chatpanel.add(enteredText, BorderLayout.NORTH);
	    	chatbar.add(typedText, BorderLayout.WEST);
	    	chatbar.add(chatbutton, BorderLayout.EAST);
	    	chatpanel.add(chatbar, BorderLayout.SOUTH);

	    //add chatpanel to rightside
		rightside.add(chatpanel, BorderLayout.SOUTH); //I have tried EVERYTHING to put this thing at the bottom, to no avail

		//finalize mainframe
		mainframe.add(leftside, BorderLayout.WEST);
		mainframe.add(rightside, BorderLayout.EAST);
		//mainframe.getContentPane().add(leftside, BorderLayout.WEST);
		//mainframe.getContentPane().add(rightside, BorderLayout.EAST);

		//mainframe.pack();
        mainframe.setVisible(true);

        add(mainframe);
	}
}
