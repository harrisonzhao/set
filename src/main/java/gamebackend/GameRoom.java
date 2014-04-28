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
  final int maxNumPlayers;
  String name;
    
  public GameRoom(String name, int maxNumPlayers){
    players = new ArrayList<>();
    state = 0;
    numReady = 0;
    this.maxNumPlayers = maxNumPlayers;
  }
  
  public String InitializeGame() {
    board = new Board(); //Initialize Board with 12 cards
    board.DealUntilSetOrTwelve();
    score = new Scoring(players);
    state = 1;
    return encodeBoardToString(board, score, "S");
  }
  
  public void resetRoom() {
    state = 0;
    numReady = 0;
    score.resetScores();
  }
  
  public String encodeNamesToString() {
    String ret = "G~U";
    for (Player p : players)
      ret += ("~" + p.username + "~" + p.score);
    return ret;
  }
  
  public String encodeBoardToString(Board b, Scoring s, String flags) {
    b.PrintSet();

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
    return new Set(new Card(Integer.parseInt(c1)-1),
            new Card(Integer.parseInt(c2)-1),
            new Card(Integer.parseInt(c3)-1));
  }
  
  public String CheckSetAndUpdate(int pid, String c1, String c2, String c3) {
    Set set = decodeSetFromString(c1, c2, c3);
    if(board.TestAndRemoveSet(set) == 1){
      score.addToScore(pid, 3);
      if(board.DealUntilSetOrTwelve()){
        return encodeBoardToString(board, score, "Y");
      }
      else{
        state = 2;
        return encodeBoardToString(board, score, "F"); // Game over
      }
    }
    else if(board.TestAndRemoveSet(set) == 0){
      //minus 1 point for wrong set
      score.subtractFromScore(pid, 1);
      return encodeBoardToString(board, score, "N");
    }
    else{
      //The board state changed after this request was sent
      //Shouldn't penailize user not sure how to handle this
      return null;
    }
  }
  public String getName() {
    return name;
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
  
  public int getMaxNumPlayers() {
    return maxNumPlayers;
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
  
  //default score is 0
  public void addPlayer(int id, String username) {
    players.add(new Player(id, username, 0));
  }
  
  public void removePlayer(int id) {
    for (int i = 0; i != players.size(); i++) {
      if (players.get(i).id == id) {
        players.remove(i);
        return;
      }
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
  
  public void getWinners(List<Integer> winners, List<Integer> losers) {
    int maxscore = -1000000;
    for (int i = 0; i != players.size(); ++i)
      if (players.get(i).score > maxscore)
        maxscore = players.get(i).score;
    for (int i = 0; i != players.size(); ++i) {
      if (players.get(i).score == maxscore)
        winners.add(players.get(i).id);
      else losers.add(players.get(i).id);
    }
  }
  
  // This is essentially a giant unit test of the game portion's functionality.
  // This DOES NOT currently test any of the communications.
  // I will talk to the client people make sure their following the same scheme
  // Then we shall test the commuincations.
  public static void main(String [] args){
    GameRoom gr = new GameRoom("TestRoom", 2);
    gr.addPlayer(1, "WingXhaoMoonslayerSucksAtSet");
    gr.addPlayer(2, "DonkeyKongTheMaster");
    gr.incNumReady();
    gr.incNumReady();
    gr.InitializeGame();
    
    //gr.board.PrintActiveCards();
    
    int size = gr.board.active.size();
    if(gr.board.TestAndRemoveSet(
            new Set(new Card(0),new Card(1), new Card(2))) != -1){
      System.out.println("Test and remove set for -1 possibly broken");
    }
    if(gr.board.TestAndRemoveSet(
            new Set(gr.board.active.get(0),
                    gr.board.active.get(1),
                    gr.board.active.get(2))) != 0){
      System.out.println("Test and remove set for 0 possibly broken");
    }
    if(gr.board.TestAndRemoveSet(gr.board.ValidSet) != 1){
      System.out.println("Valid Set or TestAndRemove correct broken");
    }
    if(size != gr.board.active.size() + 3){
      System.out.println("Something broken with removing elements");
    }
    
    gr.score.addToScore(1, 3);
    gr.board.DealUntilSetOrTwelve();
    
    while(gr.board.DealUntilSetOrTwelve()){
      gr.board.TestAndRemoveSet(gr.board.ValidSet);
      gr.score.addToScore(2, 3);
    }
    
    gr.board.PrintActiveCards();
    
    //gr.board.TestAndRemoveSet(gr.board.ValidSet);
    //System.out.println(gr.board.ValidSet);
    //gr.board.PrintActiveCards();
    //gr.score.addToScore(1, 3);
    
    

  }
}
