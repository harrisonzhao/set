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
    
    //Only Supports 2 players for now
    //Add more constructor overloads for more
    
    public Scoring(List<Player> players){
      this.players = players;
    }
    
    public void AddToScore(int id, int amount){
        for(int i=0; i<players.size(); i++){
            Player p = players.get(i);
            if(p.id == id){
                p.AddToScore(amount);
                return;
            }
        }          
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
