/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gamebackend;

/**
 *
 * @author David
 */
public class Player {
  public int id;
  String username;
  public int score;
    
  public Player(int id, String username, int score){
    this.id = id;
    this.username = username;
    this.score = score;
  }
    
  public void AddToScore(int points){
    score = score + points;
  }
    
  public void SubtractFromScore(int points) {
    score -= points;
  }
  
  public void resetScore() {
    score = 0;
  }
}
