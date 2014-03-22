package Set_GUI;

// how should leaving the lobby be handled?
/**
*
* @author Harrison
* edited by Alejandro Acosta
*/
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.SwingUtilities;

public class SetClient {
  boolean listening;
  final Socket socket;
  final BufferedReader istream;
  final DataOutputStream ostream;
  
  public SetClient() throws IOException {
    listening = true;
    //System.out.println("hi");
    socket = new Socket("127.0.0.1", 10000);
    istream = new BufferedReader(
            new InputStreamReader(socket.getInputStream()));
    ostream = new DataOutputStream(socket.getOutputStream());
  }
  
  public void runClient() throws IOException {
    /*
     * Opening the Login Screen
     */
    
    final SetClient runObj = this;
    
    SwingUtilities.invokeLater(new Runnable() {
      
      public void run() {
        Login log = new Login(runObj);
        log.setVisible(true);
      }
    });
    
    while(listening) {
      //System.out.println("Hello from Client");
      String incomingMessage = istream.readLine();
      String [] messagePieces = incomingMessage.split("~");
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
  }

  /**
   * @author Alejandro Acosta
   * 
   * sends the message to the server
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
    try {
      System.out.println("testing");
      ostream.writeChars(message + "\n");
      System.out.println("success, sent:" + message);
    } catch (IOException except) {
      System.err.println("Unable to print message:" + message);
      System.out.println("whoops");
    }
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
