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
public class GameRoom {
    StringConverter sc = new StringConverter();
    Board board;
    Scoring score;
    
    public String InitializeGame(int pid1, int pid2){
        board = new Board(); //Initialize Board with 12 cards
        board.DealUntilSetOrTwelve();
        score = new Scoring(pid1, pid2);
        return sc.EncodeBoardToString(board, "S");
    }
    
    public String CheckSetAndUpdate(String message, int pid){
        Set set = sc.DecodeSetFromString(message);
        if(board.TestAndRemoveSet(set) == 1){
            if(board.DealUntilSetOrTwelve()){
                score.AddToScore(pid, 3);
                return sc.EncodeBoardToString(board, "Y");
            }
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
