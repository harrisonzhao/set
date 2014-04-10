/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.SetServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author David
 */
public class Sql {
  static String ConnectionString ="jdbc:mysql://199.98.20.118:3306/software";
  static String Username = "root";
  static String Password = "cooperee";
  public void connectTest(){
    try{
      Class.forName("com.mysql.jdbc.Driver");
      
      Connection dbConnection = DriverManager.getConnection(
              ConnectionString,Username,Password);
      //Statement stmt = dbConnection.createStatement();
      //stmt.executeUpdate("CREATE DATABASE Set");
      dbConnection.close();
      
      System.out.println("No exceptions");
    }catch(SQLException | ClassNotFoundException e){
      System.out.println(e.getMessage());
    }
  }
  
  public static void main(String [] args)
	{
    Sql sql = new Sql();
    sql.connectTest();
	}
}
