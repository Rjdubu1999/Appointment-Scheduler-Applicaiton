package Controller;

import Data_Access_Object.AppointmentDAO;
import Data_Access_Object.ContactDAO;
import Data_Access_Object.CustomerDAO;
import Data_Access_Object.USERDAO;
import Model.*;
import Utilities.DataBaseConnection;
import com.example.wilkinson_c195.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import static Utilities.Time.convertTOUTC;


/**
 * @Author Ryan Wilkinson
 * C195 - Software II
 *
 * Controller for the Appointment Model and DAO of Appointment
 * Which enables the user to fill in the appointment fields and add it
 * the Main appointment table
 */


public class AddAppointmentController   {

    @FXML private TextField CustomerID;
    @FXML private DatePicker EndDate;
    @FXML private TextField UserID;
    @FXML private ComboBox<String> Contact;
    @FXML private TextField Type;
    @FXML private TextField Location;
    @FXML private TextField Description;
    @FXML private ComboBox<String> EndField;
    @FXML private TextField Title;
    @FXML private TextField AptID;
    @FXML private ComboBox<String> StartTime;
    @FXML private DatePicker StartDate;
    @FXML private ComboBox ContactCombo;


    /**
     * @throws SQLException Initiializing the start and end time dropdown boxes and filling in the contact combo box as
     * well so that the user can add them to the main Appointment table
     */
    @FXML
    public void initialize() throws SQLException {

            ObservableList<Model.Contact> contactObservableList = ContactDAO.getAllContacts();
            ObservableList<String> allContacts = FXCollections.observableArrayList();

            contactObservableList.forEach(contact -> allContacts.add(contact.getContactName()));
            ObservableList<String> aptTimes = FXCollections.observableArrayList();
            LocalTime beginningApt = LocalTime.MIN.plusHours(8);
            LocalTime endingApt = LocalTime.MAX.minusHours(1).minusMinutes(45);
            if (!beginningApt.equals(0) || !endingApt.equals(0)) {
                while (beginningApt.isBefore(endingApt)) {
                    aptTimes.add(String.valueOf(beginningApt));
                    beginningApt = beginningApt.plusMinutes(15);
                }
            }
            StartTime.setItems(aptTimes);
            EndField.setItems(aptTimes);
            Contact.setItems(allContacts);


    }


    /**
     * @param actionEvent This method allows for a user to click save and then load the information they have added in the fields
     *                    to be added into the Main appointment table as well as load all of the input information into
     *                    the MySQL database
     * @throws IOException
     */
    public void onActionSave(ActionEvent actionEvent) throws IOException {
        try {

            Connection connection = DataBaseConnection.openConnection();

            if (!Title.getText().isEmpty() && !Description.getText().isEmpty() && !Location.getText().isEmpty() && !Type.getText().isEmpty() && StartDate.getValue() != null && EndDate.getValue() != null && !StartTime.getValue().isEmpty() && !EndField.getValue().isEmpty() && !CustomerID.getText().isEmpty()) {

                ObservableList<Customer> getAllCustomers = CustomerDAO.getAllCustomers(connection);
                ObservableList<Integer> storeCustomerIDs = FXCollections.observableArrayList();
                ObservableList<USERDAO> getAllUsers = USERDAO.getAllUser();
                ObservableList<Integer> storeUserIDs = FXCollections.observableArrayList();
                ObservableList<Appointment> getAllAppointments = AppointmentDAO.getAllAppointment();


                getAllCustomers.stream().map(Customer::getCustomerID).forEach(storeCustomerIDs::add);
                getAllUsers.stream().map(User::getUserID).forEach(storeUserIDs::add);

                LocalDate ldStart = StartDate.getValue();
                LocalDate ldEnd = EndDate.getValue();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                String startDateFormat = StartDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String appointmentStartTime = StartTime.getValue();
                String endDateFormat = EndDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String appointmentEndTime = EndField.getValue();
               // System.out.println("thisDate + thisStart " + startDateFormat + " " + appointmentStartTime + ":00");
                String startUTC = convertTOUTC(startDateFormat + " " + appointmentStartTime + ":00");
                String endUTC = convertTOUTC(endDateFormat + " " + appointmentEndTime + ":00");
                LocalTime ltStart = LocalTime.parse(StartTime.getValue(), dateTimeFormatter);
                LocalTime ltEnd = LocalTime.parse(EndField.getValue(), dateTimeFormatter);
                LocalDateTime dtStart = LocalDateTime.of(ldStart, ltStart);
                LocalDateTime dtEnd = LocalDateTime.of(ldEnd, ltEnd);
                ZonedDateTime zdtStart = ZonedDateTime.of(dtStart, ZoneId.systemDefault());
                ZonedDateTime zdtEnd = ZonedDateTime.of(dtEnd, ZoneId.systemDefault());
                ZonedDateTime estConversionStart = zdtStart.withZoneSameInstant(ZoneId.of("America/New_York"));
                ZonedDateTime estConversionEnd = zdtEnd.withZoneSameInstant(ZoneId.of("America/New_York"));

                LocalTime ltCheckStart = estConversionStart.toLocalTime();
                LocalTime ltCheckEnd = estConversionEnd.toLocalTime();

                DayOfWeek startAppointmentDayToCheck = estConversionStart.toLocalDate().getDayOfWeek();
                DayOfWeek endAppointmentDayToCheck = estConversionEnd.toLocalDate().getDayOfWeek();

                int startAppointmentDayToCheckInt = startAppointmentDayToCheck.getValue();
                int endAppointmentDayToCheckInt = endAppointmentDayToCheck.getValue();

                int startOfWorkWeek = DayOfWeek.MONDAY.getValue();
                int endOfWorkWeek = DayOfWeek.FRIDAY.getValue();

                LocalTime businessHours = LocalTime.of(8, 0, 0);
                LocalTime closedHours = LocalTime.of(22, 0, 0);
                /**
                 * Adding logical errors so that correct information can be input into the main appointment table.
                 */
                if (startAppointmentDayToCheckInt < startOfWorkWeek || startAppointmentDayToCheckInt > endOfWorkWeek || endAppointmentDayToCheckInt < startOfWorkWeek || endAppointmentDayToCheckInt > endOfWorkWeek) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Day is outside of business operations (Monday-Friday)");
                    Optional<ButtonType> error = alert.showAndWait();
                    System.out.println("day is outside of business hours");
                    return;
                }

                if (ltCheckStart.isBefore(businessHours) || ltCheckEnd.isAfter(closedHours) || ltCheckEnd.isBefore(businessHours) || ltCheckEnd.isAfter(closedHours))
                {
                    System.out.println("The selected time is outside of our hours of operation");
                    Alert alert = new Alert(Alert.AlertType.ERROR, "The selected time is outside of our hours of operation (8am-10pm EST): " + ltCheckStart + " - " + ltCheckEnd + " EST");
                    Optional<ButtonType> error = alert.showAndWait();
                    return;
                }

                int newAppointmentID = Integer.parseInt(String.valueOf((int) (Math.random() * 250)));
                int customerID = Integer.parseInt(CustomerID.getText());

                if (dtStart.isAfter(dtEnd)) {
                    System.out.println("Appointment start time is after the end time, please check times.");
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment start time is after end time.");
                    Optional<ButtonType> error = alert.showAndWait();
                    return;
                }

                if (dtStart.isEqual(dtEnd)) {
                    System.out.println("Appointment start time and end time are the same.");
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Same end and start time, please check times.");
                    Optional<ButtonType> error = alert.showAndWait();
                    return;
                }
                for (Appointment appointment: getAllAppointments)
                {
                    LocalDateTime checkStart = appointment.getStart();
                    LocalDateTime checkEnd = appointment.getEnd();

                    if ((customerID == appointment.getCustomerID()) && (newAppointmentID != appointment.getAppointmentID()) &&
                            (dtStart.isBefore(checkStart)) && (dtEnd.isAfter(checkEnd))) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment overlaps with existing appointment.");
                        Optional<ButtonType> error = alert.showAndWait();
                        System.out.println("Appointment overlaps with existing appointment.");
                        return;
                    }

                    if ((customerID == appointment.getCustomerID()) && (newAppointmentID != appointment.getAppointmentID()) &&

                            (dtStart.isAfter(checkStart)) && (dtStart.isBefore(checkEnd))) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Start time overlaps with existing appointment.");
                        Optional<ButtonType> error = alert.showAndWait();
                        System.out.println("Start time overlaps with existing appointment.");
                        return;
                    }



                    if (customerID == appointment.getCustomerID() && (newAppointmentID != appointment.getAppointmentID()) &&

                            (dtEnd.isAfter(checkStart)) && (dtEnd.isBefore(checkEnd))) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "End time overlaps with existing appointment.");
                        Optional<ButtonType> error = alert.showAndWait();
                        System.out.println("End time overlaps with existing appointment.");
                        return;
                    }
                }
/**
 * SQL query that inputs the appointment information from all of the fields into the MySQL Database
 */
                String query = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                DataBaseConnection.setPreparedStatement(DataBaseConnection.getConnection(), query);
                PreparedStatement preparedStatement = DataBaseConnection.getPreparedStatement();
                preparedStatement.setInt(1, newAppointmentID);
                preparedStatement.setString(2, Title.getText());
                preparedStatement.setString(3, Description.getText());
                preparedStatement.setString(4, Location.getText());
                preparedStatement.setString(5, Type.getText());
                preparedStatement.setTimestamp(6, Timestamp.valueOf(startUTC));
                preparedStatement.setTimestamp(7, Timestamp.valueOf(endUTC));
                preparedStatement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setString(9, "admin");
                preparedStatement.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setInt(11, 1);
                preparedStatement.setInt(12, Integer.parseInt(CustomerID.getText()));
                preparedStatement.setInt(13, Integer.parseInt(ContactDAO.locateContact(Contact.getValue())));
                preparedStatement.setInt(14, Integer.parseInt(ContactDAO.locateContact(UserID.getText())));

                preparedStatement.execute();
            }

            Parent root = FXMLLoader.load(Main.class.getResource("MainAppointment.fxml"));
            Scene scene = new Scene(root);
            Stage MainScreenReturn = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            MainScreenReturn.setScene(scene);
            MainScreenReturn.show();

        } catch (SQLException sqlException) {
           System.out.println("Exception:" + sqlException.getMessage());
        }

    }


    /**
     * @param actionEvent This method hides the current window and brings you back to the MainAppointment.fxml screen
     */
    public void onActionCancel(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();

    }
}
