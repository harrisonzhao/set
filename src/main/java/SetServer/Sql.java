/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SetServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 *
 * @author David
 */
public class Sql {
  static String ConnectionString ="jdbc:mysql://199.98.20.118:3306/software";
  static String Username = "root";
  static String Password = "cooperee";
  
  public void dropTableUsers(){
    String query = "drop table Users;";
    try{
      Class.forName("com.mysql.jdbc.Driver");
      Connection dbConnection = DriverManager.getConnection(
              ConnectionString,Username,Password);
      Statement stmt = dbConnection.createStatement();
      stmt.executeUpdate(query);
      dbConnection.close();
      
    }catch(SQLException | ClassNotFoundException e){
      System.out.println(e.getMessage());
    }
  }
  
  public void createTableUsers(){
    String query = "CREATE Table Users(\n" +
      "	id INT NOT NULL auto_increment,\n" +
      "	username VARCHAR(40) UNIQUE NOT NULL,\n" +
      "	password VARCHAR(40) NOT NULL,\n" +
      "	rating FLOAT DEFAULT 1200,\n" +
      "	PRIMARY KEY(id)\n" +
      ");";
    try{
      Class.forName("com.mysql.jdbc.Driver");
      Connection dbConnection = DriverManager.getConnection(
              ConnectionString,Username,Password);
      Statement stmt = dbConnection.createStatement();
      stmt.execute(query);
      dbConnection.close();
      System.out.println("done w/ sql");
    }catch(SQLException | ClassNotFoundException e){
      System.out.println(e.getMessage());
    }
  }
  
  public User getUserFromUsername(String username){
    String query = "Select * from Users where username = ?;";
    User su;
    try{
      Class.forName("com.mysql.jdbc.Driver");
      Connection dbConnection = DriverManager.getConnection(
              ConnectionString,Username,Password);
      PreparedStatement stmt = dbConnection.prepareStatement(query);
      stmt.setString(1, username);
      ResultSet rs = stmt.executeQuery();
      if(rs.next()){
        su = new User(rs.getInt("id"), rs.getString("username"),
                rs.getString("password"), rs.getDouble("rating"),-1);
      }
      else{
        su = new User();
      }
      dbConnection.close();      
    }catch(SQLException | ClassNotFoundException e){
      System.out.println(e.getMessage());
      su = new User();
    }
    return su;
  }
  
  public boolean updateUser(User su){
    String query = "UPDATE Users\n" +
      "SET id=?,username=?,password=?,rating=?\n" +
      "WHERE id = ?;";
    boolean result;
    try{
      Class.forName("com.mysql.jdbc.Driver");
      Connection dbConnection = DriverManager.getConnection(
              ConnectionString,Username,Password);
      PreparedStatement stmt = dbConnection.prepareStatement(query);
      stmt.setInt(1, su.id);
      stmt.setString(2, su.username);
      stmt.setString(3, su.password);
      stmt.setDouble(4, su.rating);
      stmt.setInt(5, su.id);
      result = stmt.execute();
      dbConnection.close();      
    }catch(SQLException | ClassNotFoundException e){
      System.out.println(e.getMessage());
      result = false;
    }
    return result;
  }
  
  public boolean addUser(String username, String password){
    String query = "INSERT INTO Users(username,password) Values(?,?);";
    try{
      Class.forName("com.mysql.jdbc.Driver");
      Connection dbConnection = DriverManager.getConnection(
              ConnectionString,Username,Password);
      PreparedStatement stmt = dbConnection.prepareStatement(query);
      stmt.setString(1, username);
      stmt.setString(2, password);
      stmt.execute();
      dbConnection.close();
      
    }catch(SQLException | ClassNotFoundException e){
      System.out.println(e.getMessage());
      return false;
    }
    return true;
  }
  
  public void connectTest(){
    try{
      Class.forName("com.mysql.jdbc.Driver");
      Connection dbConnection = DriverManager.getConnection(
              ConnectionString,Username,Password);
      dbConnection.close();
      System.out.println("No exceptions");
    }catch(SQLException | ClassNotFoundException e){
      System.out.println(e.getMessage());
    }
  }
  
  public static void main(String [] args) throws SQLException
	{
    Sql sql = new Sql();
    //sql.connectTest();
    //sql.addUser("david", "securepassword");
    User su = sql.getUserFromUsername("david");
    System.out.println(su.id + " " + su.username + " " + su.password + " " + su.rating);
    su.rating = 1500;
    sql.updateUser(su);            
    su = sql.getUserFromUsername("david");
    System.out.println(su.id + " " + su.username + " " + su.password + " " + su.rating);
    //sql.createTableUsers();
    //sql.dropTableUsers();
	}
}
