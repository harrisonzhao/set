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
    public String EncodeBoardToString(Board b, String flags){
        String request = "G";
        request += "~";
        request += flags;
        request += "~";
        if(flags.equals("S")){
            request += b.BoardToString();
        }
          
        return request;
    }
    
    public Set DecodeSetFromString(String s){
        String [] cards = s.split("\\s+");
        return new Set(new Card(Integer.parseInt(cards[0])),
                new Card(Integer.parseInt(cards[1])),
                new Card(Integer.parseInt(cards[2])));
    }
    
}
