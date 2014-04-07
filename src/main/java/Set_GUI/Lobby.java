package Set_GUI;

import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.event.*;
import SetServer.*;
import gamebackend.*;

// NEED TO MAKE SURE LOGGING OUT AND THEN BACK IN AGAIN DOESN'T CAUSE DUPLICATION OF THE PAGE!

@SuppressWarnings("serial")
public class Lobby extends JPanel {
  
  // master panel containing everything this screen contains
  private JPanel panel = new JPanel(new BorderLayout()); 
  
  // all the components of this screen
  private JPanel top = new JPanel();
  private JPanel left = new JPanel();
  private JPanel right = new JPanel();
  private JPanel center = new JPanel();
  
  // subcomponents of the above
  //private JPanel headertext = new JPanel();
  
  // reference to the Login class
  private Login login_Frame;
  
  // The player's username
  private String username;
  
  // welcome message to the user
  private JLabel welcome;
  
  // calling Client object
  private SetClientProtocol callingObj;
  
  // chat box
  private String CHAT_HEADER = "Lobby Group Chat";
  private JLabel chatHeader;
  private JTextArea chatLog;
  private JTextField messageInput;
  private JButton sendMessage;
  
  // active user list and open game list
  public DefaultListModel<String> currentUsers, currentGames;
  private JList<String> userList, gameList;
  
  // challenge other players
  private JPopupMenu challengeMenu;
  private JMenuItem menuChallenge;
  
  // flag for empty game list
  private String emptyGameList = "There are no open games";
  
  public Lobby(Login login_Frame) {
    this.login_Frame = login_Frame;

  }
  
  /**
   * Creates the GUI for the Lobby page
   * <p>
   * Uses a Border Layout Manager to place all components in separate parts of the screen. 
   * 
   */
  public final void createGUI() {
    makeTop();
    makeLeft();
    makeCenter();
    makeRight();

    panel.add(top, BorderLayout.NORTH);
    panel.add(left, BorderLayout.WEST);
    panel.add(center, BorderLayout.CENTER);
    panel.add(right, BorderLayout.EAST);
    
    // challenge popup menu
    challengeMenu = new JPopupMenu();
    menuChallenge = new JMenuItem("Challenge");
    challengeMenu.add(menuChallenge);
    menuChallenge.addActionListener(new IssueChallengeListener());
    
    add(panel);
  }

  private class IssueChallengeListener implements ActionListener {
    public void actionPerformed(ActionEvent evt) {
      // send challenge request to the server which will handle it.
    }
  }
  
  /**
   * Enters the lobby with your specified username.
   * <p>
   * Sets the default button for the lobby window and adds the user's username to the server's list of active users. Also sets the welcome
   * text
   * <p>
   * @param username Identifies the name of the user using this instance of the game lobby.
   */
  public void enterLobby (String username, SetClientProtocol callingObj) {
    this.username = username;
    this.callingObj = callingObj;
    
    // probably won't need this in the end.
    currentUsers.addElement(username);
    
    // or send the new username to the server to update the list (probably that)
    
    welcome.setText("Welcome " + username);
    this.getRootPane().setDefaultButton(sendMessage);
  }
  
  // The heading for the lobby page
  public void makeTop() {
    
    //image for header
    //BufferedImage header = null;
	BufferedImage header;
	Boolean image_succeed = true;
	JLabel headerLabel;
    try {
      // Here's code for getting the current working directory, but i'm not sure
      // if we need that. the hard coded one works fine for now. It may be an issue
      // later though depending on how we're running the client code.
      String dirtest = System.getProperty("user.dir");
      System.out.println("Current working directory = " + dirtest);
      header = ImageIO.read(new File("src/main/resources/set_card.png"));
    }
    catch (IOException ex) {
      // handle exception
      image_succeed = false;
      header = null;
    }
    if(!image_succeed) {
      headerLabel = new JLabel("Image Not Found");
    }
    else {
      headerLabel = new JLabel(new ImageIcon(header));
      headerLabel.setAlignmentX(CENTER_ALIGNMENT);
    }
    welcome = new JLabel();
    welcome.setAlignmentX(LEFT_ALIGNMENT);

    JButton Logout = new JButton("Logout");
    Logout.setAlignmentX(RIGHT_ALIGNMENT);
    Logout.addActionListener(new ActionListener(){
      /**
       * Calls the logout method of the Login class.
       */
      public void actionPerformed(ActionEvent evt) {
        login_Frame.logout();
      }
    });
    
    JPanel headerText = new JPanel();
    headerText.add(welcome);
    headerText.add(Box.createRigidArea(new Dimension(400,0)));
    headerText.add(Logout);
    headerText.setVisible(true);
    
    top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
    
    //top.setAlignmentY(Component.CENTER_ALIGNMENT);
    top.add(headerLabel);
    //top.add(Box.createRigidArea(new Dimension(0,10)));
    top.add(headerText);
    headerText.setVisible(true);
    //top.add(welcome);
    //top.add(Box.createRigidArea(new Dimension(200,0)));
    //top.add(Logout);
  }
  
  /* List of players requesting games.
   * Needs a list populated by the server with available games
   * and an action listepaper marioner to send accepted games.
   */
  public void makeLeft() {
    currentUsers = new DefaultListModel<String>();
    
    userList = new JList<String>(currentUsers);
    
    JScrollPane userPane = new JScrollPane(userList);
    userPane.setAlignmentX(LEFT_ALIGNMENT);
    
    //userList.addListSelectionListener(new ChallengeListener2());
    userList.addMouseListener(new ChallengeListener());
    userList.setPreferredSize(new Dimension(150, 300));
    currentUsers.addElement(" ");
    // dummy user for testing purposes
    currentUsers.addElement("ArtificalBob");

    left.add(userPane);
  }

  /* mouse event listener for list
   * 
   */
  private class ChallengeListener extends MouseAdapter {
   public void mouseClicked(MouseEvent evt) {
    String challenged = userList.getSelectedValue();
    String challenger = username;
    if(challenged != " " && challenged != username) {
      int userIndex = userList.getSelectedIndex();
      challengeMenu.show((Component) userList,0,0);
    }
   }
  }
  
  // Need a listener to notice when the server sends a message to update the list of users.
  /* private class UpdateUserList implements *Listener {
   *   public void serverMessageListener(ServerMessageEvent message) {
   *     currentUsers.clear();
   *     for username in message {
   *       currentUsers.addElement(username);
   *     }
   *   }
   * }
   */
  /** Creates the center portion of the game window
   *  Has a create game button as well as a list of all active games.
   *  Clicking on an active game will open a pop-up menu giving the option to 
   *  join that game. 
   *  As far as I know, games should be removed from the list once they begin.
   *  
   */
  public void makeCenter() {
    JButton game_Request = new JButton("Join Game");
    game_Request.setVisible(false);
    JButton create_game = new JButton("Create Game");
    
    currentGames = new DefaultListModel<String>();
    
    gameList = new JList<String>(currentGames);
    
    JScrollPane gamePane = new JScrollPane(gameList);
    gamePane.setAlignmentX(CENTER_ALIGNMENT);
    
    gameList.addMouseListener(new JoinListener());
    gameList.setPreferredSize(new Dimension(150, 300));
    // need this following line of code in a conditional that runs whenever gameList
    // gets updated. make it part of the function that listens from server
    //currentGames.addElement(emptyGameList);
    currentGames.addElement(" ");
    
    center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
    //center.add(game_Request); // need message "N~[room name]~maxNumPlayers" around here
    center.add(create_game);
    center.add(Box.createRigidArea(new Dimension(0,5)));
    center.add(gamePane);
  }
  
  private class JoinListener extends MouseAdapter {
    public void mouseClicked(MouseEvent evt) {
      String joined = gameList.getSelectedValue();
      if(!joined.equals(emptyGameList)) {
        // set up the code for what to do
      }
    }
  }

  // The lobby chat 
  public void makeRight() {
    right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
    
    // the header for the chat area
    chatHeader = new JLabel(CHAT_HEADER);
    chatHeader.setAlignmentX(CENTER_ALIGNMENT);
    
    // creating and organizing the component that stores the chatlog
    chatLog = new JTextArea(15,30);
    chatLog.setEditable(false);
    chatLog.setLineWrap(true);
    
    // the field where you type your messages to the chat
    messageInput = new JTextField(15);
    
    // submit button. Invisible since enter activates it
    sendMessage = new JButton("Send");
    sendMessage.addActionListener(new ChatButtonListener());
    sendMessage.setVisible(false);
    
    // Adding all the components to the right side of the screen
    right.add(chatHeader);
    right.add(chatLog);
    right.add(messageInput);
    right.add(sendMessage);
  }
  
  private class ChatButtonListener implements ActionListener {    
    public void actionPerformed(ActionEvent event) {
      if(messageInput.isFocusOwner()) {
        //System.out.println("test button press\n");
        String message = messageInput.getText();
        if(!message.equals("")) { 
          // this line is temporary
          chatLog.append(username + ": " + message + "\n");
          
          messageInput.setText("");
          callingObj.sendMessageToServer("C~"+message);
        }
      }
      else { }; // do nothing
    }
  }
  
  /**
   * Will update the chat log as messages are sent
   * @param username: the username of the user who sent the message 
   * @param message: the message sent
   */
  public void updateChat(String username, String message) {
    chatLog.append(username + ": " + message + "\n");
  }
  
  /**
   *  Will update the userlist
   * @param mode: the mode of the change. "A" represents adding to the userlist.
   * "R" represents removing from the userlist.
   * @param username: The username of the user who sent the message
   */
  public void updateUserList(String mode, String username) {
    if (mode.equals("A")) {
      currentUsers.addElement(username);
    }
    else if (mode.equals("R")) {
      currentUsers.removeElement(username);
    }
    else {
      // shouldn't run
      System.err.println("Error. That is an invalid userlist update command");
    }
  }
}
