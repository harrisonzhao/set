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
public class StringConverter {
    public String EncodeBoardToString(Board b, Scoring s, String flags){
        String request = "G";
        request += "~";
        request += flags;
        request += "~";
        request += b.BoardToString();
        request += "~";
        request += s.ScoresToString();
        return request;
    }
    
    // Parses 3 integers in a string into 3 cards.
    // Assumes that string is already parsed in that this is understood
    // To be a game request and only the relevent portion is passed
    public Set DecodeSetFromString(String s){
        String [] cards = s.split("\\s+");
        return new Set(new Card(Integer.parseInt(cards[0])),
                new Card(Integer.parseInt(cards[1])),
                new Card(Integer.parseInt(cards[2])));
    }
    
}
