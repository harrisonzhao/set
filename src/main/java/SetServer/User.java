/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src.main.java.SetServer;

/**
 *
 * @author Harrison
 */
public class User {
  int id;
  public String username;
  public String password;
  public int currentGameRoom;
  public double rating;
  public User(int id, String username, String password,
          double rating, int currentGameRoom) {
    this.username = username;
    this.currentGameRoom = currentGameRoom;
    this.rating = rating;
    this.id = id;
    this.password = password;
  }
  
  public User(){
    id = -1;
    username = "";
    password = "";
    rating = -1;
    currentGameRoom = -1;
  }
}
