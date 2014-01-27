/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SetServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

/*
 * Will spawn and run each time a new client connects
 * @author Harrison
 */
public class ClientConnection extends Thread {
  final int clientID;
  Boolean isrunning;
  final Socket socket;
  final BlockingQueue<Message> incomingMessages;
  private BufferedReader incomingStream;
  
  public ClientConnection(int clientID,
                          Boolean isrunning,
                          Socket socket,
                          BlockingQueue<Message> incomingMessages){
    super("ClientConnection: " + clientID);
    this.clientID = clientID;
    this.isrunning = isrunning;
    this.socket = socket;
    this.incomingMessages= incomingMessages;
    try {
      incomingStream = new BufferedReader(new InputStreamReader(
              this.socket.getInputStream()));
    } catch (IOException except) {
      System.err.println("Problem getting istream from client id: " + clientID);
    }
  }
  
  void handleDisconnection() {
    System.out.println("Client with id: " + clientID + " disconnected");
    try {
      incomingMessages.put(new Message(clientID, "D"));       
    } catch (InterruptedException ex) {
      System.err.println("Error sending disconnection message!");
    }
  }
  
  @Override
  public void run() {  
    System.out.println("Connected to client with ID: " + clientID);
    String incomingMessage;
    while (isrunning) {
      try {
        incomingMessage = incomingStream.readLine();
        if (incomingMessage != null) {
          try {
            incomingMessages.put(new Message(clientID, incomingMessage));
          } catch (InterruptedException except) {
          }
        } else {
          handleDisconnection();
        }
      } catch (IOException except) {
        handleDisconnection();
      }
    }
  }
}
