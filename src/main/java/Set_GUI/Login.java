package Set_GUI;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.util.*;

/* Written by Alejandro Acosta
 * 
 */
@SuppressWarnings("serial")

/**
 * 
 * @author Alejandro Acosta <acosta317@gmail.com>
 * @since 2014-02-21
 */
public class Login extends JFrame implements ActionListener {
  // username for this client instance
  public String myUsername;
  
  // master panel that will contain all windows
  private JPanel master;
  
  // String constants to represent the different windows
  private final static String LOGIN = "Set Login Screen";
  private final static String LOBBY = "Set Lobby Screen";
  private final static String GAME = "Set Game Room";
  
  // Card Layout for changing the window focus. Needs to be changed within another class
  private CardLayout cl; 
  private Lobby lobby_Panel;
  private Game game_Panel;
  
  private SetClientProtocol callingObj;
  
  // master panel for the login window.
  private JPanel panel = new JPanel(new BorderLayout());
  
  /* panels for each of the areas on the login screen.
   * Each has its own method to create it 
   */
  private JPanel top = new JPanel();
  private JPanel bottom = new JPanel();
  private JPanel right = new JPanel();
  private JPanel center = new JPanel();
  
  // fields used for password entry. Needed here for use in action listener
  private JTextField inputUsername = new JTextField(10);
  private JPasswordField inputPassword = new JPasswordField(10);
  
  // field for error text if you do something you shouldn't
  private JLabel jErrorText = new JLabel();
  
  // login button
  private JButton logButton;
  
  /**
   * Indicates if this client is logged in
   */
  public boolean isLoggedIn;
  
  /** Constructor: Creates a card layout for screen switching and sets up the main login page 
   * <p>
   * Each card is a different screen: Login, Lobby, and the Game itself. The other screens have their GUIs created in Lobby.java
   * and Set_Game.java respectively. 
   * <p>
   * Sets up the JComponent structure for everything. It sets up a refrence to the
   * calling object (SetClientProtocol) and sets up the GUI panel references for the
   * Client.
   */
  public Login(SetClientProtocol callingObj) {
    this.callingObj = callingObj;

    master = new JPanel(new CardLayout());
    lobby_Panel = new Lobby(this);
    game_Panel = new Game(lobby_Panel);

    callingObj.grabPanels(this, lobby_Panel, game_Panel);
    cl = (CardLayout)(master.getLayout());
    master.add(panel, LOGIN);
    master.add(lobby_Panel, LOBBY);
    master.add(game_Panel, GAME);
    
    isLoggedIn = false;
    
    cl.show(master, LOGIN);

    createGUI();
  }
  
   /** Creates the Login page GUI.
    * <p> 
    * Uses a Border Layout Manager to place all components in their proper places.
    * <p>
    * It uses a Border Layout Manager with the project header at the top,
    * an area for error messages at the right,
    * the login fields in the center,
    * and the register button at the bottom.
    * NOTE: may have to rearrange this to allow online registration and just login on the client.
    */
  public final void createGUI() {
    lobby_Panel.createGUI();
    makeTop();
    makeBottom();
    makeRight();
    makeCenter();
    
    panel.add(top, BorderLayout.NORTH);
    panel.add(bottom, BorderLayout.SOUTH);
    panel.add(right, BorderLayout.EAST);
    panel.add(center, BorderLayout.CENTER);
    add(master);
    
    this.getRootPane().setDefaultButton(logButton);
    setTitle(LOGIN);
    
    setSize(400,400);

    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          windowClose();
        }
      });
        
    setLocationRelativeTo(null);
  }
  
  /** Creates the project header
   * Still a WIP
   */
  public void makeTop() {
    // Image for header
    BufferedImage header;
    Boolean image_succeed = true;
    JLabel headerLabel;
    try {
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

    
    // text for header
    top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
  
    // Create the header text
    // We can come up with a better name later
    String headerText = "The Trembling Triumvirate's Terrific Set";
    JLabel jHeaderText = new JLabel(headerText);
    
    top.add(Box.createRigidArea(new Dimension(0, 5)));
    top.add(headerLabel);
    top.add(jHeaderText);
    
    // Center Aligning the components to the top panel
    headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    jHeaderText.setAlignmentX(Component.CENTER_ALIGNMENT);
  }
  
  /** Adds the register button and launches the register window when clicked
   * Still a WIP.
   */
  public void makeBottom() {
    
    JButton registerButton = new JButton("Register");
    registerButton.addActionListener(this);
    registerButton.setActionCommand("Register");

    bottom.add(registerButton);
    
  }
  
  /** Area for login error message
   * Invisible until a message needs to be displayed
   * Needs listener to communicate with server about that.
   */
  public void makeRight() {
    right.setLayout(new BoxLayout(right, BoxLayout.X_AXIS));
    right.add(new JSeparator(SwingConstants.VERTICAL)); // creates a vertical line
    
    // setting font style for the error text
    jErrorText.setForeground(Color.RED);
    
    right.add(Box.createRigidArea(new Dimension(10, 0)));
    right.add(jErrorText);
    right.add(Box.createRigidArea(new Dimension(10, 0))); // why doesn't this seem to create blank space?
    
    right.setVisible(false); 
  }
  
  /** 
   * Makes the section of the screen that is the actual login.
   * <p>
   * A login indicator and below fields to enter your login information.
   */
  public void makeCenter() {
    // Setting up center panel to have components organized vertically
    center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
    // top portion of center. Login Indicator
    JLabel logLabel = new JLabel("Login");
    logLabel.setFont(new Font("Serif", Font.BOLD, 16));
    logLabel.setAlignmentX(CENTER_ALIGNMENT);
    
    // username/password input field
    JPanel credentials = new JPanel();
    
    
    // username input field
    JPanel username = new JPanel();
    username.add(new JLabel("Username"));
    username.add(inputUsername);
    username.setAlignmentX(Component.CENTER_ALIGNMENT);
    
    // password input field
    JPanel password = new JPanel();
    password.add(new JLabel("Password"));
    password.add(inputPassword);
    password.setAlignmentX(Component.CENTER_ALIGNMENT);
    
    credentials.add(username);
    credentials.add(password);
    
    // login button
    logButton = new JButton("Login");
    logButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    logButton.addActionListener(this);
    logButton.setActionCommand("Login");
    
    center.add(Box.createVerticalGlue());
    center.add(logLabel);
    center.add(credentials);
    center.add(Box.createRigidArea(new Dimension(0, 5)));
    center.add(logButton);
    center.add(Box.createVerticalGlue());
    
  }
  
  /** 
   * The action performed when the Login or Register button is pressed.
   * Sends message for registration and for login
   */
  public void actionPerformed(ActionEvent e) {

    // sets username
    String yourUsername = inputUsername.getText();
    char[] yourPassword = inputPassword.getPassword();
    myUsername = yourUsername;
    
    /*
     * Action Listener for Login/Register Button. 
     * Queries the database and determines if username/password combination is correct
     */
    if("Login".equals(e.getActionCommand())) {      
      // send message to server
      callingObj.sendMessageToServer("L~" + yourUsername + "~" + new String(yourPassword));
    }
    
    /*
     * Action Listener for Registration Button. Determines if username is valid then sends the 
     * new registration information to the database.
     */
    else if("Register".equals(e.getActionCommand())) {
      // server connection
      callingObj.sendMessageToServer("R~" + yourUsername + "~" + new String(yourPassword));
    }
    
    /*
     * Error code. This shouldn't ever run.
     */
    else {
      System.err.println("We shouldn't see this");
    }

    inputUsername.setText("");
    inputPassword.setText("");
    
  }


  /**
   *  Exits the user from the game Lobby.
   *  <p>
   *  Sets the window size to appropriate dimensions, loads the Game page card, and sets the default button for that window.  
   */
  public void logout() {
    this.getRootPane().setDefaultButton(logButton);
    setSize(400,400);
    isLoggedIn = false;
    cl.show(master, LOGIN);
  }
  
  /**
   * Enters the game room from the Game lobby.
   * <p>
   * Sets the window size to appropriate dimensions, loads the Game page card, and sets the default button for that window.
   */
  public void enterGame() {
    this.getRootPane().setDefaultButton(logButton); // change this to appropriate button
    setSize(400,400); // figure out appropriate size
    cl.show(master, GAME);
  }
  /**
   * Exits the user from the game itself.
   * <p>
   * Sets the window size to the lobby dimensions and shows the Lobby page card.
   */
  public void exitGame() {
    this.getRootPane().setDefaultButton(logButton);
    setSize(400,400);
    cl.show(master, LOBBY);
  }
  
  /**
   *  Set the error text visible in the right portion of the screen. 
   */
  public void setErrorText(String errorMessage) {
    jErrorText.setText(errorMessage);
    right.setVisible(true);
  }  
  
  private void windowClose() {
    callingObj.sendMessageToServer("D");
    dispose();
    System.exit(0);
  }
  
  /**
   * Logs the player in if the call to the server was successful
   */
  public void login(String username) {
    isLoggedIn = true;
    myUsername = username;
    System.out.println("I just tried to login");
    setSize(1000,450);
    lobby_Panel.enterLobby(username, callingObj);
    right.setVisible(false);
    setTitle(LOBBY);
    cl.show(master, LOBBY);
  }
}
