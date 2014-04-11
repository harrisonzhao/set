/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gamebackend;
import java.util.*;
/**
 *
 * @author David
 */
public class Scoring {
    public List<Player> players;
        
    public Scoring(List<Player> players){
      this.players = players;
      for (Player p : players)
        p.score = 0;
    }
    
    public void addToScore(int id, int amount){
      for (int i=0; i<players.size(); i++){
        Player p = players.get(i);
        if (p.id == id) {
          p.AddToScore(amount);
        }
      }          
    }
    
    public void subtractFromScore(int id, int amount) {
      for (int i = 0; i < players.size(); i++) {
        Player p = players.get(i);
        if (p.id == id) {
          p.SubtractFromScore(amount);
          return;
        }
      }
    }
    
    public void resetScores() {
      for (Player p: players)
        p.resetScore();
    }
    
    // Returns the scores of the players in the order of the list
    public String ScoresToString(){
        String s = "";
        for(int i=0; i<players.size(); i++){
            s += (players.get(i)).score;
            s += " ";
        }
        s.trim();
        return s;
    }
    
}
