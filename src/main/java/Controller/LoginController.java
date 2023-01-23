package Controller;

import Model.DatabaseUser;
import Model.User;
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
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * @Author Ryan Wilkinson
 * C195 - Software II
 * Creating a controller class which enables a User to login to the application by inputing the correct information into the
 * MySQL database. If the incorrect information is entered into the log in fields then the login will deny them access.
 */
public class LoginController implements Initializable {
    @FXML
    private Label AnchorPaneLanguage;
    @FXML
    private Label AnchorPaneMessage;
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

    private String loginErrorTitle;
    private String loginErrorHeader;
    private String loginErrorText;

    private static User user;

    /**
     * @return getter for user class
     */
    public static User getUser(){
        return user;
    }


    /**
     * @param event Method which the user types in a username and password into the fields where the information will be
     *              validated or invalidated through a boolean allowing or not allowing the user. If the information entered
     *              is correct then the login screen will go away and bring up the MainScreen.fxml where the user may use
     *              the application
     * @throws IOException
     */
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
            alert.setTitle(loginErrorTitle);
            alert.setHeaderText(loginErrorHeader);
            alert.setContentText(loginErrorText);
            alert.showAndWait();
        }
    }

    /**
     * @return gets the locale of the user
     */
    public static Locale getCurrentLocale(){
        return Locale.getDefault();
    }


    /**
     * @param url Initializes the class and uses the resource bundle of languages to change the language
     *            of the login menu text to english or french depending on the system default of the users computer
     * @param languages
     */
    @Override
    public void initialize(URL url, ResourceBundle languages) {
        Locale locale = Locale.getDefault();
        languages = ResourceBundle.getBundle("Languages/Language", locale);
        UsernameLabel.setText(languages.getString("username"));
        PasswordLabel.setText(languages.getString("password"));
        LogInButton.setText(languages.getString("login"));
        AnchorPaneMessage.setText(languages.getString("message"));
        AnchorPaneLanguage.setText(languages.getString("language"));
        loginErrorTitle = languages.getString("errortitle");
        loginErrorHeader = languages.getString("errorheader");
        loginErrorText = languages.getString("errortext");

    }



}
