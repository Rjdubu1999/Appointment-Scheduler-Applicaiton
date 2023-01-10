package Model;

import Utilities.DataBaseConnection;
import Utilities.Logger;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class DatabaseUser {

    private static User activeUser;

    public static User getActiveUser() {
        return activeUser;
    }

    public static Boolean login(String username, String password) {
        try {
            Statement statement = DataBaseConnection.getConnection().createStatement();
            String query = "SELECT * FROM user WHERE userName='" + username + "AND password='" + password + "'";
            ResultSet result = statement.executeQuery(query);
            if (result.next()) {
                activeUser = new User();
                activeUser.setUsername(result.getString("userName"));
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


