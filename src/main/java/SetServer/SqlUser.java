/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SetServer;

/**
 *
 * @author David
 */
public class SqlUser {
  int Id;
  String Name;
  String Password;
  double Rating;
  
  SqlUser(int id, String name, String password, double rating){
    Id = id;
    Name = name;
    Password = password;
    Rating = rating;
  }
  
  SqlUser(){
    Id = -1;
    Name = "";
    Password = "";
    Rating = -1;
  }
}
