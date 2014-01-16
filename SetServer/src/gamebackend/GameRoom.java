/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gamebackend;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David
 */
public class GameRoom {
  
  final List<Player> players;
  int numReady;
  Board board;
  Scoring score;
  int state; // 0 inactive, 1 active, 2 complete
    
  public GameRoom(){
    state = 0;
    players = new ArrayList<>();
  }
  
  public String InitializeGame() {
    board = new Board(); //Initialize Board with 12 cards
    board.DealUntilSetOrTwelve();
    score = new Scoring(players);
    state = 1;
    return encodeBoardToString(board, score, "S");
  }
  
  public String encodeBoardToString(Board b, Scoring s, String flags) {
    String request = "G";
    request += "~";
    request += flags;
    request += "~";
    request += b.BoardToString();
    request += "~";
    request += s.ScoresToString();
    return request;
  }
  
  public Set decodeSetFromString(String c1, String c2, String c3) {
    return new Set(new Card(Integer.parseInt(c1)),
            new Card(Integer.parseInt(c2)),
            new Card(Integer.parseInt(c3)));
  }
  
  public String CheckSetAndUpdate(int pid, String c1, String c2, String c3) {
    Set set = decodeSetFromString(c1, c2, c3);
    if(board.TestAndRemoveSet(set) == 1){
      score.AddToScore(pid, 3);
      if(board.DealUntilSetOrTwelve()){
        return encodeBoardToString(board, score, "Y");
      }
      else{
        state = 2;
        return encodeBoardToString(board, score, "F"); // Game over
      }
    }
    else if(board.TestAndRemoveSet(set) == 0){
      return encodeBoardToString(board, score, "N");
    }
    else{
      // There is a bug somewhere. This shouldn't be possible.
      throw new IllegalArgumentException("Requested set not in board");
    }
  }
  
  public String getGameUpdate() {
    return encodeBoardToString(board, score, "U");
  }
  
  public void resetNumReady() {
    numReady = 0;
  }
  
  public int getNumReady() {
    return numReady;
  }
  
  public void incNumReady() {
    ++numReady;
  }
  
  public void decNumReady() {
    --numReady;
  }
  
  public List<Integer> getPlayerIds() {
    List<Integer> pids = new ArrayList<>();
    for (Player p : players)
      pids.add(p.id);
    return pids;
  }
  
  public int getNumPlayers() {
    return players.size();
  }
  
  public boolean isRoomEmpty() {
    return players.isEmpty();
  }
  
  //default score is 1000?
  public void addPlayer(int id) {
    players.add(new Player(id, 1000));
  }
  
  public void removePlayer(int id) {
    for (int i = 0; i != players.size(); ++i) {
      if (players.get(i).id == id)
        players.remove(i);
    }
  }
  
  public boolean isInactive() {
    return state == 0;
  }
     
  public boolean isPlaying() {
    return state == 1;
  }
  
  public void setPlaying() {
    state = 1;
  }
  
  public boolean isCompleted() {
    return state == 2;
  }
  
  public void setCompleted() {
    state = 2;
  }
}
