/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SetServer;

/**
 * let -1 be a message to everyone logged in
 * @author Harrison
 */
public class Message {
  public int clientID;
  public String message;
  public Message(int clientID, String message) {
    this.clientID = clientID;
    this.message = message;
  }
}
