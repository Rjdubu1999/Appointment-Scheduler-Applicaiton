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
            alert.setTitle(loginErrorTitle);
            alert.setHeaderText(loginErrorHeader);
            alert.setContentText(loginErrorText);
            alert.showAndWait();
        }
    }
    public static Locale getCurrentLocale(){
        return Locale.getDefault();
    }


   // Locale[] applicationLocales = {
     //       Locale.ENGLISH,
     //       Locale.FRENCH
  //  };


  /**  public void setLoginInfo(ResourceBundle languages) {
        Locale locale = getCurrentLocale();
         languages = ResourceBundle.getBundle("Languages/Language", locale);
        UsernameLabel.setText(languages.getString("username"));
        PasswordLabel.setText(languages.getString("password"));
        LogInButton.setText(languages.getString("login"));
        AnchorPaneMessage.setText(languages.getString("message"));
        AnchorPaneLanguage.setText(languages.getString("language"));
        loginErrorTitle = languages.getString("errortitle");
        loginErrorHeader = languages.getString("errorheader");
        loginErrorText = languages.getString("errortext");

    } **/
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
    /**try{
        setLoginInfo(languages);


    }catch (Exception e){
        System.out.println("Error " + e.getMessage());
    }
**/
    }



}
