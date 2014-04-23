package connectionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.net.Socket;
import java.util.concurrent.ConcurrentMap;

/*
 * Will spawn and run each time a new client connects
 */
public class Connection extends Thread {
  final int connectedID;
  Boolean isrunning;
  final BlockingQueue<Message> incomingMessages;
  final private BufferedReader incomingStream;
  final ConcurrentMap<Integer, Socket> sockets;
  Protocol protocol;
  
  public Connection(int connectedID,
                    Boolean isrunning,
                    BlockingQueue<Message> incomingMessages,
                    BufferedReader incomingStream,
                    ConcurrentMap<Integer, Socket> sockets,
                    Protocol protocol) {
    super("Connection: " + connectedID);
    this.connectedID = connectedID;
    this.isrunning = isrunning;
    this.incomingMessages= incomingMessages;
    this.incomingStream = incomingStream;
    this.sockets = sockets;
    this.protocol = protocol;
  }
  
  @Override
  public void run() {  
    System.out.println("Connected to client with ID: " + connectedID);
    String incomingMessage;
    while (isrunning) {
      try {
        incomingMessage = incomingStream.readLine();
        if (incomingMessage != null) {
          try {
            incomingMessages.put(new Message(connectedID, incomingMessage));
          } catch (InterruptedException except) {}
        } else {
          System.out.println(connectedID + " disconnected!");
          sockets.remove(connectedID);
          protocol.handleDisconnection(connectedID);
          break;
        }
      } catch (IOException except) {
        System.out.println("Couldn't get message from: " + connectedID);
        //shouldn't ever happen
      }
    }
  }
}