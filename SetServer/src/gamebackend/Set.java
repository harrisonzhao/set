/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SetServer.src.gamebackend;

/**
 *
 * @author David
 */
public class Set {
    Card[] cards = new Card[3];
    
    Set(Card c0, Card c1, Card c2){
        cards[0] = c0;
        cards[1] = c1;
        cards[2] = c2;
    }
    
    // Checks if the given set is valid
    // Returns true if valid, else returns false
    public boolean CheckIfSetValid(){
        for(int i=0; i<4; i++){
            if(!CheckIfPropertyValid(i))
                return false;
        }
        return true;
    }
    
    public Card GetCardNumber(int num){
        return cards[num];
    }
    
    // Checks if a property of a set (0-3) is valid across the 3 cards
    // Returns true if valid, else returns false
    private boolean CheckIfPropertyValid(int p){
        int[] v = new int[3];
        for(int i=0; i<3; i++){
            v[i] = cards[i].GetPropertyValue(p);
        }
        return ((v[0] != v[1]) || (v[1] == v[2]))
                && ((v[1] != v[2]) || (v[2] == v[0]))
                && ((v[2] != v[0]) || (v[0] == v[1]));
    }
    
    @Override
    public String toString(){
      return cards[0] + "," + cards[1] + "," + cards[2];
    }
}
