package Set_GUI;

// how should leaving the lobby be handled?
/**
* Protocol for SetClient that uses the ConnectionManager class
* @author Harrison
* @author Alejandro Acosta
*/
import connectionManager.Connection;
import connectionManager.Message;
import connectionManager.Protocol;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.SwingUtilities;

public class SetClientProtocol extends Protocol {

  final int serverId;
  String serverIp;
  int serverPort;
  
  /**
   * Constructor, modify arguments passed to it in SetClientMain
   * @param serverIp
   * @param serverPort 
   */
  public SetClientProtocol(String serverIp, int serverPort) {
    super();
    serverId = 0;
    this.serverIp = serverIp;
    this.serverPort = serverPort;
  }
  
  /**
   * attempts to connect to set server, will be called automatically
   * after constructors finish
   * also calls the showInterface function (not sure if supposed to be here)
   * @author Harrison
   */
  @Override
  public void connect() {
    System.out.println("Attempting to connect to set server");
    Socket masterSocket;
    try {
      masterSocket = new Socket(serverIp, serverPort);
      BufferedReader masterStream = new BufferedReader(
              new InputStreamReader(masterSocket.getInputStream()));
      sockets.put(serverId, masterSocket);
      Connection connection= new Connection(serverId,
                                            isrunning,
                                            incomingMessages,
                                            masterStream,
                                            this);
      connection.start();
    } catch (IOException ex) {
      System.err.println("Couldn't connect to master!");
      System.exit(1);
    }
    
    showInterface();
  }
  
  /**
   * if server disconnects, shutdown everything
   * @param connectedID 
   */
  @Override
  public void handleDisconnection(int connectedID) {
    System.err.println("The server went offline! exiting...");
    isrunning = false;
  }
  
  /**
   * Sends message to set server
   * automatically appends a newline to end of message
   * @param message 
   */
  public void sendMessageToServer(String message) {
    sendMessage(serverId, message);
  }
  
  /**
   * @author Alejandro Acosta
   */
  public void showInterface() {
    /*
     * Opening the Login Screen
     */
    
    final SetClientProtocol runObj = this;
    
    SwingUtilities.invokeLater(new Runnable() {
      
      @Override
      public void run() {
        Login log = new Login(runObj);
        log.setVisible(true);
      }
    });
  }
  
  /**
   * the processing function for message that the client receives from server
   * @param message 
   */
  @Override
  public void processManagerMessages(Message message) {
    String [] messagePieces = message.message.split("~");
    switch(messagePieces[0].charAt(0)) {
      case 'X':
        // parse (errorMSG): Login/Register error
        break;
      case 'G':
/*        G~S start?
            G~Y yes set made
            G~F game over
            G~N no set wasn't made
            G~R reset ready button (shouldn't be able to press if already pressed)
            G~U~[game room userlist string] update names+scores
                 whenever a name is added or removed
               :Update GameRoom in game*/
        break;
      case 'E':
        // exited GameRoom
        break;
      case 'J':
        /* J~I :Could not join, game in progress
        J~F :Game Room is full*/
        break;
      case 'R':
        // reset gameroom
        break;
      case 'C':
        //[sender's username] (message)
        // send to lobby chat
        break;
      case 'T':
        /* Username~(message): game chat
         * ~(message): system message
         */
        break;
      case 'P':
        /* P~A~name :update players in lobby table of users
     P~R~name: removes name from lobby table of users*/
        break;
      case 'U':
        /*
         * U~A~[room number]~[room name]~[current numPlayers]~[max players]~[status]
         * U~R~[room number]
         */
        break;
    }
  }

  /**
   * @author Alejandro Acosta
   * 
   * sends the message to the server
   * SEE USE SEND MESSAGETOSERVER FUNCTION INSTEAD OF CREATING AN OUTPUTSTREAM
   */
  public void sendMessage(String message) {
    /*
     * L~username~password          :Login
     * R~username~password          :Registration
     * D                            :Disconnection
     * N~[room name]~maxNumPlayers  :Create Game
     * J~[room number]              :Join Game
     * G                            :Start Game (ready button pressed)
     * S~card1~card2~card3          :Set request
     * E                            :Exit Game
     * C~Message                    :Lobby Chat
     * T~Message                    :Game Chatindex
     *
     */
    
    System.out.println("testing");
    sendMessageToServer(message);
    System.out.println("success, sent:" + message);
    /*String [] MessagePieces = message.split("~");
    switch(MessagePieces[0].charAt(0)) {
    case 'L':
      // login
      break;
    case 'R':
      // register
      break;
    case 'D':
      //disconnect
      break;
    case 'N':
      // Create Game
      break;
    case 'J':
      // Join Game
      break;
    case 'G':
      // start game
      break;
    case 'S':
      // set request
      break;
    case 'E':
      //Exit game
      break;
    case 'C':
      //lobby chat
      break;
    case 'T':
      // game chat
      break;
    }*/
    
  }
}
