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
public class Message {
  public String message;
  public int clientID;
  public Message(String message, int clientID) {
    this.message = message;
    this.clientID = clientID;
  }
}
