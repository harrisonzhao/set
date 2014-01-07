/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SetServer;

/**
 *
 * @author Harrison
 */
public class Client {
  public String username;
  public int currentTable;
  public Client(String username,
                int currentTable) {
    this.username = username;
    this.currentTable = currentTable;
  }
}
