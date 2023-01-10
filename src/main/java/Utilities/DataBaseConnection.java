package Utilities;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Author Ryan Wilkinson
 * SoftWare II
 */

/**
 * Creating class to connect MySQL database to project
 */
public class DataBaseConnection {

    private static final String DataBaseName = "JavaConnection";
    private static final String DataBaseURL = "jdbc:mysql://localhost:3306/mysql";
    private static final String DataBaseUsername = "root@localhost";
    private static final String DataBasePassword = "********!";
    private static final String DataBaseDriver = "com.mysql.cj.jdbc.Driver";

    private static Connection connection;
     public DataBaseConnection(){}


public static void makeConnection() {
         try{
             Class.forName(DataBaseDriver);
             connection = DriverManager.getConnection(DataBaseURL,DataBaseUsername,DataBasePassword);
             System.out.println("Connected to DataBase");

         }catch (ClassNotFoundException classNotFoundException){
             System.out.println("Class not found" + classNotFoundException.getMessage());
         }catch (SQLException sqlException){
             System.out.println("SQL Exception " + sqlException.getMessage());
             System.out.println("SQL State " + sqlException.getMessage() );
             System.out.println("Error Code : " + sqlException.getErrorCode());
         }
}
public static java.sql.Connection getConnection(){
    return connection;
}

public static void closeConnection(){
     try{
     connection.close();
     System.out.println("Connection to Database is closed");

     }catch (SQLException sqlException){
         System.out.println("SQL Exception: " + sqlException.getMessage());
     }
}
}
