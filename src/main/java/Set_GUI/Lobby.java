package Set_GUI;

import java.io.*;
import java.util.HashMap;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.event.KeyEvent.*;
import java.awt.Color.*;
import java.awt.Robot.*;
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
  
  // Error text
  private JLabel errorText = new JLabel();
  private String gameFullError = "Error: The game you tried to join is full.";
  private String gameInProgressError = "Error: The game you tried to enter is in progress.";

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
  private JScrollPane chatScroll;
  private JTextArea chatLog;
  private JTextField messageInput;
  private JButton sendMessage;
  
  // active user list and open game list
  public DefaultListModel<String> currentUsers, currentGames;
  private JList<String> userList, gameList;
    
  // join other games
  private JPopupMenu joinMenu;
  private JMenuItem menuJoin;
  
  // flag for empty game list
  private String emptyGameList = "There are no open games";
  
  // hash table for game rooms
  private HashMap<Integer,GameRoomData> gameRoomList = new HashMap<Integer,GameRoomData>();

  // stuff for creating a game
  private JPopupMenu gameCreate; 
  
  private JLabel gameName;
  private JLabel maxPlayers;
  
  private final JTextField gameNameField = new JTextField(10);
  private final JTextField maxPlayerField = new JTextField(2);

  private JPanel namePanel;
  private JPanel playerPanel;
  
  private JButton submit;

  private JButton cancelButton = new JButton("Cancel");
  private boolean exitCreate = false;
  private JLabel createErrorText = new JLabel("");

  final JComponent[] createGameInputs = new JComponent[] {
    createErrorText,
    new JLabel("Enter name:"),
    gameNameField,
    new JLabel("Enter max # players (limit 4):"),
    maxPlayerField,
    cancelButton
  };

  public Lobby(Login login_Frame) {
    this.login_Frame = login_Frame;

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
        
    welcome.setText("Welcome " + username);
    setEnterButton();
  }

  public void setEnterButton() {
    this.getRootPane().setDefaultButton(sendMessage);
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
        
    // join game popup menu
    joinMenu = new JPopupMenu();
    menuJoin = new JMenuItem("Join Game");
    joinMenu.add(menuJoin);
    menuJoin.addActionListener(new JoinGameListener());
    add(panel);
    
    // create game popup menu
    gameCreate = new JPopupMenu();
    gameCreate.setLayout(new BoxLayout(gameCreate, BoxLayout.Y_AXIS));
    
    gameName = new JLabel("Game Name: ");
    maxPlayers = new JLabel("Max Number of Players: ");
    
    namePanel = new JPanel();
    playerPanel = new JPanel();
    
    namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
    playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.X_AXIS));
  }

  /**
   *  The heading for the lobby page
   */
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
      //String dirtest = System.getProperty("user.dir");
      //System.out.println("Current working directory = " + dirtest);
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
    top.add(errorText);

    headerText.setVisible(true);
    //top.add(welcome);
    //top.add(Box.createRigidArea(new Dimension(200,0)));
    //top.add(Logout);
  }
  
  /**
   * Creates a list of players logged in.
   */
  public void makeLeft() {
    currentUsers = new DefaultListModel<String>();
    userList = new JList<String>(currentUsers);
    
    JScrollPane userPane = new JScrollPane(userList);
    userPane.setAlignmentX(LEFT_ALIGNMENT);
    
    userList.setPreferredSize(new Dimension(100, 100));

    JLabel titlePanel = new JLabel("Users in Lobby");
    JPanel subPanel = new JPanel();
    subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
    subPanel.add(titlePanel);
    subPanel.add(userPane);
    
    left.setLayout(new BoxLayout(left, BoxLayout.X_AXIS));
    left.add(subPanel);
    left.add(Box.createRigidArea(new Dimension(50,0)));
  }

  // The lobby chat 
  public void makeRight() {
    right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
    
    // the header for the chat area
    chatHeader = new JLabel(CHAT_HEADER);
    chatHeader.setAlignmentX(CENTER_ALIGNMENT);
    
    // creating and organizing the component that stores the chatlog
    //chatScroll = new JScrollPane();
    chatLog = new JTextArea("", 15,30);
    chatScroll = new JScrollPane(chatLog);
    //, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    chatLog.setEditable(false);
    chatLog.setLineWrap(true);
    //chatScroll.add(chatLog);
    
    // the field where you type your messages to the chat
    messageInput = new JTextField(15);
    
    // submit button. Invisible since enter activates it
    sendMessage = new JButton("Send");
    sendMessage.addActionListener(new ChatButtonListener());
    sendMessage.setVisible(false);
    
    // Adding all the components to the right side of the screen
    right.add(chatHeader);
    right.add(chatScroll);
    right.add(messageInput);
    right.add(sendMessage);
  }

  /** 
   *  Creates the center portion of the game window
   *  Has a create game button as well as a list of all active games.
   *  Clicking on an active game will open a pop-up menu giving the option to 
   *  join that game. 
   *  As far as I know, games should be removed from the list once they begin.
   *  
   */
  public void makeCenter() {
    //gameCreate.setLayout(new BoxLayout(gameCreate, BoxLayout.Y_AXIS));

    JButton game_Request = new JButton("Join Game");
    game_Request.setVisible(false);
    JButton create_game = new JButton("Create Game");
    
    create_game.addActionListener(new CreationListener());
    
    currentGames = new DefaultListModel<String>();
    
    gameList = new JList<String>(currentGames);
    
    JScrollPane gamePane = new JScrollPane(gameList);
    gamePane.setAlignmentX(CENTER_ALIGNMENT);
    
    gameList.addMouseListener(new JoinListener());
    gameList.setPreferredSize(new Dimension(200, 300));
    
    // setting error text font color to be red
    errorText.setForeground(new Color(0xff0000));
    errorText.setVisible(false);

    JPanel subPanel = new JPanel();
    subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
    subPanel.add(errorText);
    subPanel.add(create_game);
    subPanel.add(Box.createRigidArea(new Dimension(0,5)));
    subPanel.add(gamePane);
    
    center.setLayout(new BoxLayout(center, BoxLayout.X_AXIS));
    //center.add(game_Request); // need message "N~[room name]~maxNumPlayers" around here
    center.add(subPanel);
    center.add(Box.createRigidArea(new Dimension(50,0)));
    
    cancelButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent evt) {
      exitCreate = true;
    }
  });
    createErrorText.setForeground(Color.red);

  }


  /**
   * blahblahblah
   * @author alejandro
   *
   */
  public class CreationListener implements ActionListener {
    public void actionPerformed(ActionEvent evt) {
      errorText.setVisible(false);
      int maxPlayers;
      boolean redo = false;
      exitCreate = false;
      do {
        JOptionPane.showMessageDialog(login_Frame, createGameInputs, "Test Dialog", JOptionPane.PLAIN_MESSAGE);
        createErrorText.setText("");
        // checking that max player field contains an integer
        try {
          maxPlayers = Integer.parseInt(maxPlayerField.getText());
          redo = maxPlayers>4 || maxPlayers<1;
          if(redo) {
            createErrorText.setText("The max player field value must be between 1 and 4.");
          }
        }
        catch(NumberFormatException ex) {
          redo = true;
          createErrorText.setText("The max player field must contain a valid number.");
        }

        // checking that game name field is of proper format
        if(gameNameField.getText().contains("~")) {
          redo = true;
          createErrorText.setText("The game field must not contain a '~'.");
        }
        if(gameNameField.getText().equals("")) {
          redo = true;
          createErrorText.setText("Please enter a name for your game room.");
        }

        System.out.println("exit create = " + exitCreate);
        if(exitCreate) {
          break;
        }
      } while(redo);
      
      if(!exitCreate) {
        callingObj.sendMessageToServer("N~"+gameNameField.getText()+"~"+maxPlayerField.getText()); 
        login_Frame.enterGame();
      }
    }
  }

  private class JoinListener extends MouseAdapter {
    public void mouseClicked(MouseEvent evt) {
      String joined = gameList.getSelectedValue();
      if(!joined.equals(emptyGameList)) {
        // set up the code for what to do
        joinMenu.show((Component) gameList,0,0);
      }
    }
  }

  private class JoinGameListener implements ActionListener {
    public void actionPerformed(ActionEvent evt) {
      errorText.setVisible(false);
      String roomInfo = gameList.getSelectedValue();

      // parse roomInfo to grab room number
      String [] roombits = roomInfo.split(" ");

      // removing colon in room number
      System.out.println(roombits[0] + "is the integer I'm trying to parse for game room");
      int roomNumber = Integer.parseInt
          (roombits[0].substring(0,roombits[0].length()-1));

      callingObj.sendMessageToServer("J~"+roomNumber); // join the game. Server will presumeably send back U~X~[room number]
      // this line cannot be here in the end
    }
  } 

  private class ChatButtonListener implements ActionListener {    
    public void actionPerformed(ActionEvent event) {
      errorText.setVisible(false);
      if(messageInput.isFocusOwner()) {
        //System.out.println("test button press\n");
        String message = messageInput.getText();
        if(!message.equals("")) {           
          messageInput.setText("");
          callingObj.sendMessageToServer("C~"+message);
        }
      }
      else { }; // do nothing
    }
  }

  // contains the game room data.
  private class GameRoomData {
    String roomName;
    int currentNumPlayers;
    int maxNumPlayers;
    boolean playingStatus; // true if playing
    boolean full; // true if game room is full
    
    String gameRoomInfo;
    
    public GameRoomData(String roomName, int currentNumPlayers, 
        int maxNumPlayers, boolean status) {
      this.roomName = roomName;
      this.maxNumPlayers = maxNumPlayers;
      this.currentNumPlayers = currentNumPlayers;
      this.playingStatus = status;
      
      //this.full = currentNumPlayers == maxNumPlayers;
      if(maxNumPlayers == 1){
        this.full = true;
      } else { 
        this.full = false;
      }

      // debugging statement
      System.out.println("room name: " + roomName + " current number players: " + currentNumPlayers 
          + " Max number Players: " + maxNumPlayers + " Status: " + status);
    }

    // gets the reference to the game room being updated
    // gets the index of that element in the game list by searching for the game info screen
    // sets the new updated string
    // updates the current game list
    public void updateGameListing(int roomNum) {
      //GameRoomData gameRoom = GameRoomList.get(roomNum);
      int index = currentGames.indexOf(this.getListString());
      this.setListString(roomNum);
      String newString = this.getListString();
      //System.out.println("the current index is" + index);
      currentGames.set(index, newString);
    }
    
    public void setListString(int roomNumber) {
      String statusWord = playingStatus == true ? "Playing" : "Open";
      if(statusWord.equals("Open") && full) {
        statusWord = "Full";
      }
      //statusWord = "testing";
      this.gameRoomInfo = roomNumber + ": " + roomName + " " + 
        currentNumPlayers + "/" + maxNumPlayers + " " + statusWord;
    }
    
    public String getListString() {
      return gameRoomInfo;
    }
  
    public boolean testFull() {
      //full = currentNumPlayers == maxNumPlayers ? true : false;
      full = currentNumPlayers == maxNumPlayers;
      return full;
    }
    
    public void gameStart() {
      playingStatus = true;
    }
    
    public void setInactive() {
      playingStatus = false;
    }
    
    public void setPlaying() {
      playingStatus = true;
    }
    
    public void addPlayer() {
      if(!full) {
        ++currentNumPlayers;
      }
    }
    
    public void removePlayer() {
      if(currentNumPlayers > 0) {
        --currentNumPlayers;
      }
    }
    
  }

  /**
   * Creates a new game room from the parameters specified in the game protocol
   */
  public void addGameRoom(int roomNumber, String roomName, 
      int curNumPlayer, int maxNumPlayer, boolean status) {
    GameRoomData newRoom = new GameRoomData(roomName, curNumPlayer, 
        maxNumPlayer, status);
    gameRoomList.put(roomNumber, newRoom);
    newRoom.setListString(roomNumber);
    String gameRoomInfo = newRoom.getListString();
    //String statusWord = status == true ? "Playing" : "Open";
    //String gameRoomInfo = roomNumber + ": " + roomName + " " + 
    //    curNumPlayer + "/" + maxNumPlayer + " " + statusWord;
    currentGames.addElement(gameRoomInfo);
    
  }
  
  /**
   * Removes the specified game from the window and removes it from the master 
   * hash table of games.
   */
  public void removeGameRoom(int roomNumber) {
    GameRoomData deadRoom = gameRoomList.get(roomNumber);
    String gameRoomInfo = deadRoom.getListString();
    currentGames.removeElement(gameRoomInfo);
    gameRoomList.remove(roomNumber);
  }
  
  public void setInactive(int roomNum) {
    GameRoomData gameRoom = gameRoomList.get(roomNum);
    gameRoom.setInactive();
    gameRoom.updateGameListing(roomNum);
  }

  public void setPlaying(int roomNum) {
    GameRoomData gameRoom = gameRoomList.get(roomNum);
    gameRoom.setPlaying();
    gameRoom.updateGameListing(roomNum);
  }
  
  public void increasePlayers(int roomNum) {
    GameRoomData gameRoom = gameRoomList.get(roomNum);
    gameRoom.addPlayer();
    gameRoom.testFull();
    gameRoom.updateGameListing(roomNum);
  }
  
  public void decreasePlayers(int roomNum) {
    GameRoomData gameRoom = gameRoomList.get(roomNum);
    gameRoom.removePlayer();
    gameRoom.testFull();
    gameRoom.updateGameListing(roomNum);
  }
  
  /**
   * Will update the chat log as messages are sent
   * @param username: the username of the user who sent the message 
   * @param message: the message sent
   */
  public void updateChat(String username, String message) {
    chatLog.append(username + ": " + message + "\n");
    JScrollBar vertical = chatScroll.getVerticalScrollBar();
    vertical.setValue(vertical.getMaximum());
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

  /**
  *  Will clear the contents of the text fields in the lobby so that the user can start 
  *  from a clean slate when logging back in after logging out.
  */
  public void clearContents() {
    chatLog.setText("");
    currentUsers.clear();
  }

  /**
  *  Handle the joining of a game
  */
  public void handleJoin(char mode) {

    if(mode == 'J') {
      login_Frame.enterGame();      
      errorText.setVisible(false);
    }
    else if(mode == 'I') {
      errorText.setText(gameInProgressError);
      errorText.setVisible(true);
    }
    else if (mode == 'F') {
      errorText.setText(gameFullError);
      errorText.setVisible(true);
    }
    else {
      // this shouldn't ever run
      System.err.println("There is a mode error in Lobby.handleJoin()");
    }
  }
}
