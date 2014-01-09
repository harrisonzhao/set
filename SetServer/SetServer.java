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
    
  }
  
  void pCreateGame(int clientID, String [] messagePieces) {
    
  }
  
  void pJoinGame(int clientID, String [] messagePieces) {
    
  }
  
  void pStartGame(int clientID, String [] messagePieces) {
    
  }
  
  void pCheckForSets(int clientID, String [] messagePieces) {
    
  }
  
  void pSetMade(int clientID, String [] messagePieces) {
    
  }
  
  void pSetMistake(int clientID, String [] messagePieces) {
    
  }
      
  void pLeaveGame(int clientID, String [] messagePieces) {
    
  }
  
  void pGameChat(int clientID, String [] messagePieces) {
    
  }
  
  //user to user chat
  void pUserChat(int clientID, String [] messagePieces) {
    
  }  
}
