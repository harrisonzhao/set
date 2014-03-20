/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SetServer.src.gamebackend;
import java.util.*;

/**
 *
 * @author David
 */
public class Board{
    List<Card> deck;    // All unused cards
    List<Card> active;  // All cards currently on the table
    List<Card> found;   // All cards who have already been found in a set
    
    Set ValidSet; // A valid set.  Will be null until checkforsets runs
    
    public Board(){
        active = new ArrayList<>();
        found = new ArrayList<>();
        deck = new ArrayList<>();
        FillAndShuffle(deck);
        Deal(12);
    }
    
    // Deals cards 3 at a time until there is a set
    // Returns false if no sets are possible after emptying deck (game over)
    public boolean DealUntilSetOrTwelve(){
        while(!CheckForSets()){
            if(!Deal(3))
                return false;
        }
        if(active.size() < 12){
            Deal(12-active.size());
        }
        return true;
    }
    
    // Returns -1 if one or more of the cards in the set not found in active
    // Return of -1 indicates a bug, and should never happen under normal cond.
    // Return 1 if set is valid, and board must be updated
    // Return 0 if set is invalid, and board need not be changed.
    public int TestAndRemoveSet(Set s){
        if(s.CheckIfSetValid()){
            for(int i=0; i<3; i++){
                Card c = s.GetCardNumber(i);
                int addr = FindCardAddr(c);
                if(addr == -1)
                    return -1;
                found.add(active.remove(addr));
            }
            return 1;
        }
        return 0;
    }
    
    //
    public String BoardToString(){
        String outp = "";
        for(int i=0; i<active.size(); i++){
            Card c = active.get(i);
            outp += c.GetSingleDigit();
            outp += " ";
        }
        return outp.trim();
    }
    
    public void PrintActiveCards(){
      for(Card c : active){
        System.out.println(c.toString());
      }
    }
    
    private int FindCardAddr(Card c){
        for(int i=0; i<active.size(); i++){
            if((active.get(i)).equals(c))
                return i;
        }
        return -1;
    }
    
    // Returns true if there is a set in active cards else false
    private boolean CheckForSets(){
        for(int i=0; i<active.size(); i++){
            for(int j=(i+1); j<active.size(); j++){
                for(int k=(j+1); k<active.size(); k++){
                    Set s = new Set(active.get(i), active.get(j), active.get(k));
                    if(s.CheckIfSetValid()){
                      ValidSet = s;
                      return true;
                    }
                }
            }
        }
        return false;
    }
    
    // Attempts to deal "num" cards from deck to active.
    // Returns true if successful. Returns false if deck is empty.
    private boolean Deal(int num){
        if(deck.size() >= num){
            for(int i=0; i<num; i++){
                active.add(deck.remove(0));
            }
            return true;
        }
        return false;
    }
    
    // Fills an empty list with all possible cards and randomizes
    private void FillAndShuffle(List<Card> deck){
        for(int i=0; i<81; i++){
            deck.add(new Card(i));
        }
        Collections.shuffle(deck);
    }
}
