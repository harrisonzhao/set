/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SetServer;

/**
 *
 * @author Harrison
 */
public class Client {
  public String username;
  public int currentGameRoom;
  public Client(String username, int currentGameRoom) {
    this.username = username;
    this.currentGameRoom = currentGameRoom;
  }
}
