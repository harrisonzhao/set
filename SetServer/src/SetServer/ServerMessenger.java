package SetServer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;

/**
 * Singleton that handles messaging all connections
 * @author Harrison
 */
public class ServerMessenger extends Thread {
  Boolean isrunning;
  final ConcurrentMap<Integer, Socket> sockets;
  final BlockingQueue<Message> outgoingMessages;
  
  public ServerMessenger(Boolean isrunning,
                         ConcurrentMap<Integer, Socket> sockets,
                         BlockingQueue<Message> outgoingMessages) {
    this.isrunning = isrunning;
    this.sockets = sockets;
    this.outgoingMessages = outgoingMessages;
  }
  void sendMessage(Socket s, Message m) {
    try {
      DataOutputStream ostream = new DataOutputStream(s.getOutputStream());
      ostream.writeBytes(m.message + '\n');
    } catch (IOException except) {
      System.err.println("Failed to create output stream for socket "
              + "or send message to client with ID: " + m.clientID);
    }
  }
  
  @Override
  public void run() {
    System.out.println("Outgoing Messages running");
    while (isrunning) {
      try {
        Message outgoingMessage = outgoingMessages.take();
        
        //value of -1 means send to everyone
        //any value >= 0 will be to a specific client
        if (outgoingMessage.clientID == -1) {
          for (Socket out : sockets.values()) {
            sendMessage(out, outgoingMessage);
          }
        } else {
          sendMessage(sockets.get(outgoingMessage.clientID), outgoingMessage);
        }
        
      } catch (InterruptedException ex) {
        System.err.println("Problem getting message from outgoingMessages!");
      }
    }
  }
}
