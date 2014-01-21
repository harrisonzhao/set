/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SetClient;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author Harrison
 */
public class SetClient {
  
  boolean listening;
  final Socket socket;
  final BufferedReader istream;
  final DataOutputStream ostream;
  
  
  public SetClient() throws IOException {
    //replace with actual server connection
    listening = true;
    socket = new Socket("127.0.0.1", 1111);
    istream = new BufferedReader(
            new InputStreamReader(socket.getInputStream()));
    ostream = new DataOutputStream(socket.getOutputStream());
  }
  
  /*
   * X :Login/Register error
   * G~S start?
     G~Y yes set made
     G~F game over
     G~N no set wasn't made
     G~R reset ready button (shouldn't be able to press if already pressed)
     G~U~[game room userlist string] update names+scores
          (whenever a name is added or removed)
        :Update GameRoom in game
   * E :Exited GameRoom
   * J~I :Could not join, game in progress
     J~F :Game Room is full
   * R :Reset GameRoom
   * C :Lobby Chat
   * T~Username~message
     T~message   //system message
        :Game Chat
   * P~A~name :update players in lobby table/ 
     P~R~name: removes name from lobby table
   * U~A~[room number]~[room name]~[current numPlayers]~[max players]~[status]
   * U~R~[room number]
   */
  public void runClient() throws IOException {
    while (listening) {
      String incomingMessage = istream.readLine();
      String [] messagePieces = incomingMessage.split("~");
      switch (messagePieces[0].charAt(0)) {
        //cases to switch on
      }
    }
  }
  
}
