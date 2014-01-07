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
public class GameWrapper {
    StringConverter sc = new StringConverter();
    Board board;
    
    public String InitializeGame(){
        board = new Board(); //Initialize Board with 12 cards
        board.DealUntilSet(); 
        return sc.EncodeBoardToString(board, "S");
    }
    
    public String CheckSetAndUpdate(String message){
        Set set = sc.DecodeSetFromString(message);
        if(board.TestAndRemoveSet(set) == 1){
            if(board.DealUntilSet())
                return sc.EncodeBoardToString(board, "Y");
            else
                return sc.EncodeBoardToString(board, "F"); // Game over
        }
        else if(board.TestAndRemoveSet(set) == 0){
            return sc.EncodeBoardToString(board, "N");
        }
        else{
            // Fatal error
            throw new IllegalArgumentException("Requested set not in board");
        }
    }
    
}
