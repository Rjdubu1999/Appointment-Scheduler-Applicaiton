package Data_Access_Object;

import Model.User;
import Utilities.DataBaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * @Author Ryan Wilkinson
 * C195 - Software II
 */

/**
 * Creating a data access object that extends the User model class
 */
public class USERDAO extends User {
    /**
     * @param usedID super constructor for the USERDAO class
     * @param userName
     * @param password
     */
    public USERDAO(int usedID, String userName, String password){
        super();
    }

    public static int userValidation(String username, String password){
        try{
            String query = "SELECT * FROM users WHERE User_Name='" + username + "' AND password='" + password + "'";
            PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.getString("User_Name").equals(username)){
                if(resultSet.getString("Password").equals(password))
                    return resultSet.getInt("User_ID");
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        } return -1;
    }

    /**
     * gets all user data from the user table in the MYSQL DATABASE
     * @return
     * @throws SQLException
     */
    public static ObservableList<USERDAO> getAllUser()throws SQLException{
        ObservableList<USERDAO> userdaoObservableList = FXCollections.observableArrayList();
        String query  = "SELECT * from users";
        PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            int userID = resultSet.getInt("User_ID");
            String username = resultSet.getString("User_Name");
            String password = resultSet.getString("Password");
            USERDAO user = new USERDAO(userID,username,password);
            userdaoObservableList.add(user);
        }
        return userdaoObservableList;
    }
}
