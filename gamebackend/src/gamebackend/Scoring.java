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
    List<Player> players;
    
    //Only Supports 2 players for now
    //Add more constructor overloads for more
    
    public Scoring(int id1, int id2){
        players = new ArrayList<>();
        players.add(new Player(id1,0));
        players.add(new Player(id2,0));
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
}
