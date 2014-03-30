/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SetServer;

import connectionManager.ConnectionManager;

/**
 * @author Harrison
 */
public class SetServerMain {
  
  public static void main() {
    int serverPort = 20000;
    SetServerProtocol protocol = new SetServerProtocol();
    ConnectionManager setserver;
    setserver = new ConnectionManager(serverPort, protocol);
    setserver.runManager();
  }
}