package Controller;

import Model.User;
import Utilities.DataBaseConnection;
import com.example.wilkinson_c195.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class LoginController implements Initializable {

    @FXML
    private TextField PasswordTextField;
    @FXML
    public Label UsernameLabel ;
    @FXML
    private Label PasswordLabel;
    @FXML
    private TextField UsernameTextField;
    @FXML
    private Button LogInButton;
    @FXML
    private Button EnglishLabel;
    @FXML
    private Button FrenchLabel;

    private static User user;
    public static User getUser(){
        return user;
    }


    public void OnActionLogin(ActionEvent event) throws IOException {
        String username = UsernameTextField.getText();
        String password = PasswordTextField.getText();
        boolean validation = login(username, password);
        if(validation){
            ((Node) event.getSource()).getScene().getWindow();
            Stage stage = new Stage();
            Parent login = FXMLLoader.load(Main.class.getResource("MainScreen.fxml"));
            Scene scene = new Scene(login);
            stage.setScene(scene);
            stage.show();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Login Error");
            alert.setContentText("Username or Password incorrect");
            alert.showAndWait();
        }
    }

    /**
     * get Locales
     * @return
     */
    public static Locale getLocale(){
        return Locale.getDefault();
    }

    Locale[] programLocales = {
            Locale.ENGLISH,
            Locale.FRENCH
    };

    //set up locale later
    /**
    public void setLanguageLabels(ResourceBundle language){
        Locale location = getLocale();
        //Setting English locale
        language = ResourceBundle.getBundle("")
    }
    **/


    public static boolean login(String username, String password) {
        try{
            Statement statement = DataBaseConnection.getConnection().createStatement();
            String query = "Select * FROM user WHERE userName= '" + username + "AND password = '" +password + "'";
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()){
                user = new User();
                user.setUsername();resultSet.getString("UserName");
                statement.close();
                Utilities.Logger.log(username, true);
                return true;
            }else {
                Utilities.Logger.log(username,false);
                return false;
            }
        }catch (SQLException q){
            System.out.println("Error with database :" + q.getMessage());
            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }


}
