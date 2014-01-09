package SetServer;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;

/**
 * Singleton that handles messaging all connections
 * @author Harrison
 */
public class ServerMessenger extends Thread {
  Boolean isrunning;
  final ConcurrentMap<Object, Socket> sockets;
  final BlockingQueue<Message> outgoingMessages;
  
  public ServerMessenger(Boolean isrunning,
                         ConcurrentMap<Object, Socket> sockets,
                         BlockingQueue<Message> outgoingMessages) {
    this.isrunning = isrunning;
    this.sockets = sockets;
    this.outgoingMessages = outgoingMessages;
  }
  
  @Override
  public void run() {
    System.out.println("Outgoing Messages running");
    while (isrunning) {
      try {
        Message outgoingMessage = outgoingMessages.take();
        //do stuff with the message!!!!!!!!!!!!
        //support send to every user as well as send to single user?
      } catch (InterruptedException ex) {
        System.err.println("Problem getting message from outgoingMessages!");
      }
    }
  }
}
