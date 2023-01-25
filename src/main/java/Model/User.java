package Model;

import Utilities.DataBaseConnection;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * @Author Ryan Wilkinson
 * C195 - Software II
 */

/**
 * creating model class user
 */
public class User {
    public int userID;
    public String username;
    public String password;
    public User(){
        this.userID = userID;
        this.username = username;
        this.password = password;
    }

    /**
     * @return gets username
     */
    public String getUsername(){
        return username;
    }

    /**
     * @param userName sets username
     */
    public void setUsername(String userName){
        this.username = username;
    }



    /**
     * @return gets user id
     */
    public int getUserID(){
        return userID;
    }

    /**
     * @return gets users password
     */
    public String getPassword(){
        return password;
    }


























}
