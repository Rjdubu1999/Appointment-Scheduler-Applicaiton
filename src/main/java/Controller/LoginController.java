package Controller;

import Model.DatabaseUser;
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
    private Label EnglishLabel;
    @FXML
    private Label FrenchLabel;

    private static User user;
    public static User getUser(){
        return user;
    }


    public void OnActionLogin(ActionEvent event) throws IOException {
        String username = UsernameTextField.getText();
        String password = PasswordTextField.getText();
        boolean validation = DatabaseUser.login(username, password);
        if(validation){
            ((Node) event.getSource()).getScene().getWindow().hide();
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





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
   // Locale locale = Locale.getDefault();
    //resourceBundle = ResourceBundle.getBundle("Languages/login",locale);
    //UsernameLabel.setText(resourceBundle.getString("username"));
    //PasswordLabel.setText(resourceBundle.getString("password"));
    //LogInButton.setText(resourceBundle.getString("login"));


    }



}
