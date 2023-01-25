package Controller;

import Data_Access_Object.AppointmentDAO;
import Model.Appointment;
import Model.DatabaseUser;
import Model.User;
import com.example.wilkinson_c195.Main;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
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
    public Label UsernameLabel;
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
    public static User getUser() {
        return user;
    }


    /**
     * @param event Method which the user types in a username and password into the fields where the information will be
     *              validated or invalidated through a boolean allowing or not allowing the user. If the information entered
     *              is correct then the login screen will go away and bring up the MainScreen.fxml where the user may use
     *              the application
     * @throws IOException
     */
    public void OnActionLogin(ActionEvent event) throws SQLException, IOException {
        try {

            String username = UsernameTextField.getText();
            String password = PasswordTextField.getText();
            boolean validation = DatabaseUser.login(username, password);
            if (validation) {
                ((Node) event.getSource()).getScene().getWindow().hide();
                Stage stage = new Stage();
                Parent login = FXMLLoader.load(Main.class.getResource("MainScreen.fxml"));
                Scene scene = new Scene(login);
                stage.setScene(scene);
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(loginErrorTitle);
                alert.setHeaderText(loginErrorHeader);
                alert.setContentText(loginErrorText);
                alert.showAndWait();
            }


            ObservableList<Appointment> appointmentObservableList = AppointmentDAO.getAllAppointment();
            LocalDateTime plus15 = LocalDateTime.now().plusMinutes(15);
            LocalDateTime minus15 = LocalDateTime.now().minusMinutes(15);
            LocalDateTime start;
            int appointmentID = 0;
            LocalDateTime localDateTime = null;
            boolean fifteenMinuteAlert = false;

            for (Appointment appointment : appointmentObservableList) {
                start = appointment.getStart();
                if ((start.isAfter(minus15) || start.isEqual(plus15)) && (start.isBefore(plus15) || (start.isEqual(plus15)))) {
                    appointmentID = appointment.getAppointmentID();
                    localDateTime = start;
                    fifteenMinuteAlert = true;
                }
            }if(validation == true) {
                if (fifteenMinuteAlert != false) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You have an appoint within 15 minutes: " + appointmentID + " the appoint starts at " + localDateTime);
                    Optional<ButtonType> info = alert.showAndWait();
                    System.out.println("You have an appointment! Don't be late!");
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "There are no upcoming appointments.");
                    Optional<ButtonType> info = alert.showAndWait();
                    System.out.println("There are not any appointments within 15 minutes.");
                }

            }
        }catch (IOException e){

        }
    }

    /**
     * @return gets the locale of the user
     */
    public static Locale getCurrentLocale() {
        return Locale.getDefault();
    }


    /**
     * @param url       Initializes the class and uses the resource bundle of languages to change the language
     *                  of the login menu text to english or french depending on the system default of the users computer
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

    /**
     * did not function on mouse clicked so I put it inside of the validation method and it worked fine
     * this method will show an alert for appointment that are going to happen within fifteen minutes and will also
     * display an alert if there are no upcoming appointments
     * @param mouseEvent
     * @throws SQLException
     * @throws IOException
     */
    public void AppointmentAlert(MouseEvent mouseEvent) throws SQLException, IOException {
        try {
            ObservableList<Appointment> appointmentObservableList = AppointmentDAO.getAllAppointment();
            LocalDateTime plus15 = LocalDateTime.now().plusMinutes(15);
            LocalDateTime minus15 = LocalDateTime.now().minusMinutes(15);
            LocalDateTime start;
            int appointmentID = 0;
            LocalDateTime localDateTime = null;
            boolean fifteenMinuteAlert = false;

            for (Appointment appointment : appointmentObservableList) {
                start = appointment.getStart();
                if ((start.isAfter(minus15) || start.isEqual(plus15)) && (start.isBefore(plus15) || (start.isEqual(plus15)))) {
                    appointmentID = appointment.getAppointmentID();
                    localDateTime = start;
                    fifteenMinuteAlert = true;
                }
            }
            if (fifteenMinuteAlert != false) {
                Alert alert = new Alert (Alert.AlertType.CONFIRMATION, "You have an appoint within 15 minutes: " + appointmentID + " the appoint starts at " + localDateTime)
                ;
                Optional<ButtonType> info = alert.showAndWait();
                System.out.println("You have an appointment! Don't be late!");
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "There are no upcoming appointments.");
                Optional<ButtonType> info = alert.showAndWait();
                System.out.println("There are appointments within 15 minutes.");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
