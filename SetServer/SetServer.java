/*
 *                    SetServer
 *                    /   |   \
 *       ServerMessenger  |   ServerLogic (done by SetServer's functions)
 *                 ConnectionAcceptor
 *                        |
 * pawns multiple threads (one for each client connection)
 *
 *
 *
 */

package SetServer;

import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SetServer {
  
  Boolean running;
  Connection dbConnection = null;
  final Map<Object, Client> clients;
  final Map<Object, GameRoom> gameRooms;
  final ConcurrentMap<Object, Socket> sockets;
  final BlockingQueue<Message> incomingMessages;
  final BlockingQueue<Message> outgoingMessages;
  ServerConnectionAcceptor scManager;
  ServerMessenger sMessenger;
 
  public SetServer() {
    running = true;
    clients = new HashMap<>();
    gameRooms = new HashMap<>();
    sockets = new ConcurrentHashMap<>();
    incomingMessages = new LinkedBlockingQueue<>();
    outgoingMessages = new LinkedBlockingQueue<>();
  }
    
}
