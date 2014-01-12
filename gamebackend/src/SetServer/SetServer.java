/*
 *                    SetServer
 *                    /   |   \
 *       ServerMessenger  |   ServerLogic (done by SetServer's functions)
 *                 ConnectionAcceptor
 *                        |
 * spawns multiple threads (one for each client connection)
 *
 * clients - hashMap keying cliends by their ID, managed by SetServer functions
 * gameRooms - hashMap
 *
 *
 */

package SetServer;

import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import gamebackend.*;

public class SetServer {
  
  Boolean isrunning;
  Connection dbConnection = null;
  final Map<Object, Client> clients;
  final Map<Object, GameRoom> gameRooms;
  final ConcurrentMap<Object, Socket> sockets;
  final BlockingQueue<Message> incomingMessages;
  final BlockingQueue<Message> outgoingMessages;
  ServerConnectionAcceptor scAcceptor;
  ServerMessenger sMessenger;
  
  // Dave's package wrapper which stores all of the game information
  GameRoom gameRoom;
 
  public SetServer() {
    isrunning = true;
    clients = new HashMap<>();
    gameRooms = new HashMap<>();
    sockets = new ConcurrentHashMap<>();
    incomingMessages = new LinkedBlockingQueue<>();
    outgoingMessages = new LinkedBlockingQueue<>();
    scAcceptor = new ServerConnectionAcceptor(isrunning,
                                              sockets,
                                              incomingMessages);
    sMessenger = new ServerMessenger(isrunning,
                                     sockets,
                                     outgoingMessages);
    gameRoom = new GameRoom();
  }
  
  public void runServer() {
    scAcceptor.start();
    sMessenger.start();
    while (isrunning) {
      try {
        Message incomingMessage = incomingMessages.take();
        String [] messagePieces;
        messagePieces = incomingMessage.message.split("~");
        switch(messagePieces[0].charAt(0)) {
          //switch cases
        }
      } catch (InterruptedException except) {
        System.out.println("Interrupted");
      }
    }
  }
 
  //p stands for process
  //First three are functions that require SQL connection
  //have to connect and close after each query because of connection timeout
  //login message is L~username~password
  void pLogin (int clientID, String [] messagePieces) 
          throws SQLException, InterruptedException {
    
    if (messagePieces.length != 3) {
      System.err.println("Message error!");
      return;
    }
    try {
      dbConnection = DriverManager.getConnection("jdbc:mysql://IP:Port",
                                               "userName",
                                               "passWord");     
    } catch (SQLException except) {
      System.err.println("SQL database connection failed");
      outgoingMessages.put(new Message(clientID, "LOGIN_ERROR_MESSAGE"));
      return;
    }
    
    Statement stmt;
    stmt = dbConnection.createStatement();
    ResultSet rs = stmt.executeQuery("INSERT_QUERY_HERE");
    if (rs.next()) {
      outgoingMessages.put(new Message(clientID, "LOGIN_ERROR_MESSAGE"));
      System.out.println("Username not found!");      
    } else {
      
      //check if user is already online; if user is send error message to client
      for (Entry<Object, Client> entry : clients.entrySet()) {
        Client current = entry.getValue();
        if (messagePieces[1].equals(current.username)) {
          outgoingMessages.put(new Message(clientID, "ALREADY ONLINE ERROR"));
          return;
        }
      }
           
      //if not already online 
      //verify the password
      String password = rs.getString("password");
      if (messagePieces[2].equals(password));
      //modify other user fields????
    }
    stmt.close();
    dbConnection.close();
  }
  
  void pRegistration(int clientID, String [] messagePieces) 
          throws SQLException, InterruptedException {
    if (messagePieces.length != 3) {
      System.err.println("Message error!");
      return;
    }
    try {
      dbConnection = DriverManager.getConnection("jdbc:mysql://IP:Port",
                                               "userName",
                                               "passWord");     
    } catch (SQLException except) {
      System.err.println("SQL database connection failed");
      outgoingMessages.put(new Message(clientID, "REGISTER_ERROR_MESSAGE"));
      return;
    }
    
    Statement stmt;
    stmt = dbConnection.createStatement();
    ResultSet rs = stmt.executeQuery("INSERT_QUERY_HERE");
    
    if (rs.next()) {
      outgoingMessages.put(new Message(clientID, "REGISTER_ERROR_MESSAGE"));
      System.out.println("Username already exists!");      
    } else {
      
      stmt.executeUpdate("Insert into database");
      System.out.println("created new user: " + messagePieces[1]);
      //add the new client's name into clients
      clients.put(clientID, new Client(messagePieces[1], -1));
      //send a message to the client???
      
    }
    stmt.close();
    dbConnection.close();
  }
  
  void pDisconnection(int clientID, String [] messagePieces) {
    // Easiest solution to end game in an incomplete state
  }
  
  void pCreateGame(int clientID, String [] messagePieces) {
    // Creates the room? (No players inside yet)
  }
  
  void pJoinGame(int clientID, String [] messagePieces) {
    // Add a player to the room.
  }
  
  // Requires both players to be in room
  void pStartGame(int clientID, String [] messagePieces) {
    int pid1, pid2; // Needs to actually get these from somewhere...
    pid1 = 1;
    pid2 = 2;
    String message = gameRoom.InitializeGame(1, 2);
    // Send "message" globally to clients
    // Message of form G~S~board~scores
  }
  
  void pSetRequest(int clientID, String [] messagePieces){
    String s = "9 18 27"; //Extract string of only relevant portion
    String message = gameRoom.CheckSetAndUpdate(s, clientID);
    // Send "message" globally to clients
    // Message of form G~flag~board~scores
    
    if (gameRoom.state == 2){
      // Game is over (players received F in their messages so they know)
      // Send results to database
      // Decide what to do with game room
    }
  }
      
  void pLeaveGame(int clientID, String [] messagePieces) {
    // Probably just end the game
  }
  
  void pGameChat(int clientID, String [] messagePieces) {
    
  }
  
  //user to user chat
  void pUserChat(int clientID, String [] messagePieces) {
    
  }  
}
