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
public class User {
  public String username;
  public int currentGameRoom;
  public int rating;
  public User(String username, int currentGameRoom, int rating) {
    this.username = username;
    this.currentGameRoom = currentGameRoom;
    this.rating = rating;
  }
}
