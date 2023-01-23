package Utilities;

import java.sql.*;

/**
 * @Author Ryan Wilkinson
 * SoftWare II
 */

/**
 * Creating class to connect MySQL database to project
 */
public class DataBaseConnection {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static String password = "Passw0rd!"; // Password
    public static Connection connection;  // Connection Interface

    /**
     * @return This method opens a connection to the MYSQL database and outputs a success text when it has done
     * so in the console
     */
    public static Connection openConnection() {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return connection;
    }

    /**
     * This method ends the connection to the MYSQL database and will output to the console when it no longer has connection
     * to the data base
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * @return getter for the connection utility
     */
    public static java.sql.Connection getConnection(){
        return connection;
    }

    private static PreparedStatement preparedStatement;

    /**
     * @param connection sets prepared statement that is used for queries throughout this program
     * @param query
     * @throws SQLException
     */
    public static void setPreparedStatement(Connection connection, String query) throws SQLException {
        preparedStatement = connection.prepareStatement(query);
    }

    /**
     * @return gets the prepared statement
     */
    public static PreparedStatement getPreparedStatement(){
        return preparedStatement;
    }


}


