package Set_GUI;

import connectionManager.ConnectionManager;

public class SetClientMain {

  /**
   * 
   * @author Alejandro Acosta <acosta317@gmail.com>
   * @since 2014-03-21
   */
  
  public static void main(String[] args) {
    String serverIp = "127.0.0.1";
    int serverPort = 20000;
    SetClientProtocol protocol = new SetClientProtocol(serverIp, serverPort);
    ConnectionManager setClientManager = new ConnectionManager(protocol);
    setClientManager.runManager();
    System.out.println("Exiting...");
  }
}
