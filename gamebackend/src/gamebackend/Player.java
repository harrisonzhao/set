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
    int id;
    int score;
    
    public Player(int Id, int Score){
        id = Id;
        score = Score;
    }
    
    public void AddToScore(int points){
        score = score + points;
    }
}
