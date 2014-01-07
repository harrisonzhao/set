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
 * Will be spawned each time a new client connects
 * @author Harrison
 */
public class ClientConnection extends Thread {
  int clientID;
  Boolean isrunning;
  Socket socket;
  BlockingQueue<Message> incomingMessages;
  BufferedReader incomingStream;
  
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
      System.err.println("Problem getting input from client id: " + clientID);
    }
  }
  
  public void run() {  
    System.out.println("Connected to client with ID: " + clientID);
    String incomingMessage;
    while (isrunning) {
      try {
        incomingStream.readLine();
      } catch (IOException except) {
        System.out.println("Client with id: " + clientID + " disconnected");
        try {
          //WHAT MESSAGE?????
          incomingMessages.put(new Message("message!?!?!?!", clientID));       
        } catch (InterruptedException ex) {
          System.err.println("Error sending disconnection message!");
        }
        return;
      }
    }
  }
}
