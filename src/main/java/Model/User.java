package Model;

import Utilities.DataBaseConnection;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class User {
    public int userID;
    public String username;
    public String password;



    public String getUsername(){
        return username;
    }
   // public void setUsername(String userName){
   //     this.username = username;
  //  }

    public User(){ /**
        this.userID = userID;
        this.username = username;
        this.password = password;   **/
    }
    public int getUserID(){
        return userID;
    }

    public String getPassword(){
        return password;
    }


























}
