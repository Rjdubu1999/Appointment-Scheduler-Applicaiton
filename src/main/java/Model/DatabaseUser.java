package Model;

import Utilities.DataBaseConnection;
import Utilities.Logger;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * @Author Ryan Wilkinson
 * C195 - Software II
 */

/**
 * creating a database user class that has a login validation method
 */
public class DatabaseUser {

    private static User activeUser;

    public static User getActiveUser() {
        return activeUser;
    }

    /**
     * @param username this boolean checks the information input into the login controller to validate the
     *                 user
     * @param password
     * @return
     */
    public static Boolean login(String username, String password) {
        try {
            Statement statement = DataBaseConnection.getConnection().createStatement();
            String query = "SELECT * FROM users WHERE User_Name='" + username + "' AND Password='" + password + "'";
            ResultSet result = statement.executeQuery(query);
            if (result.next()) {
                activeUser = new User();
                activeUser.setUsername(result.getString("User_Name"));
                statement.close();
                Logger.log(username, true);
                return true;
            } else {
                Logger.log(username, false);
                return false;
            }

        } catch (SQLException sqlException) {
            System.out.println("SQLException: " + sqlException.getMessage());
            return false;
        }
    }
}


