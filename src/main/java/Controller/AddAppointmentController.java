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
   // @FXML private ComboBox ContactCombo;





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

                LocalDate ldEnd = EndDate.getValue();
                LocalDate LdStart = StartDate.getValue();
                //formatting time for hours and minutes
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

                LocalTime ltStart = LocalTime.parse(StartTime.getValue(), timeFormatter);
                LocalTime ltEnd = LocalTime.parse(EndField.getValue(), timeFormatter);

                LocalDateTime dtStart = LocalDateTime.of(LdStart, ltStart);
                LocalDateTime dtEnd = LocalDateTime.of(ldEnd, ltEnd);

                ZonedDateTime zonedDateTimeStart = ZonedDateTime.of(dtStart, ZoneId.systemDefault());
                ZonedDateTime zonedDateTimeEnd = ZonedDateTime.of(dtEnd, ZoneId.systemDefault());

                ZonedDateTime StartESTConversion = zonedDateTimeStart.withZoneSameInstant(ZoneId.of("America/New_York"));
                ZonedDateTime EndEstConversion = zonedDateTimeEnd.withZoneSameInstant(ZoneId.of("America/New_York"));
                    //setting up logical errors so that errors are displayed based upon the times and dates set for appointments
                if (StartESTConversion.toLocalDate().getDayOfWeek().getValue() == (DayOfWeek.SATURDAY.getValue()) || StartESTConversion.toLocalDate().getDayOfWeek().getValue() == (DayOfWeek.SUNDAY.getValue()) || EndEstConversion.toLocalDate().getDayOfWeek().getValue() == (DayOfWeek.SATURDAY.getValue()) || EndEstConversion.toLocalDate().getDayOfWeek().getValue() == (DayOfWeek.SUNDAY.getValue())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "The day selected is outside of our business days (Monday-Friday)");
                    Optional<ButtonType> error = alert.showAndWait();
                    System.out.println("The day is outside of our operational days. Please select a day between (Mon - Fri");
                    return;
                }

                if (StartESTConversion.toLocalTime().isBefore(LocalTime.of(8, 0, 0)) || StartESTConversion.toLocalTime().isAfter(LocalTime.of(22, 0, 0)) || EndEstConversion.toLocalTime().isBefore(LocalTime.of(8, 0, 0)) || EndEstConversion.toLocalTime().isAfter(LocalTime.of(22, 0, 0))) {
                    System.out.println("time is outside of business hours");
                    Alert alert = new Alert(Alert.AlertType.ERROR, "The time you selected is outside of our (8am-10pm EST) business hours " + StartESTConversion.toLocalTime() + " - " + EndEstConversion.toLocalTime() + " EST");
                    Optional<ButtonType> error = alert.showAndWait();
                    return;
                }

                int newAppointmentID = Integer.parseInt(String.valueOf((int) (Math.random() * 50)));
                int customerID = Integer.parseInt(CustomerID.getText());


                if (dtStart.isAfter(dtEnd)) {
                    System.out.println("Appointment has start time after end time");
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment time error: Start time is after the end time");
                    Optional<ButtonType> error = alert.showAndWait();
                    return;
                }

                if (dtStart.isEqual(dtEnd)) {
                    System.out.println("Appointment has same start and end time");
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment has same start and end time");
                    Optional<ButtonType> error = alert.showAndWait();
                    return;
                }

                for (Appointment appointment : getAllAppointments) {
                    LocalDateTime checkStart = appointment.getStart();
                    LocalDateTime checkEnd = appointment.getEnd();


                    if ((customerID == appointment.getCustomerID()) && (newAppointmentID != appointment.getAppointmentID()) &&
                            (dtStart.isBefore(checkStart)) && (dtEnd.isAfter(checkEnd))) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Time Error: Appointment overlaps with existing appointment.");
                        Optional<ButtonType> error = alert.showAndWait();
                        System.out.println("Appointment overlaps with existing appointment.");
                        return;
                    }

                    if ((customerID == appointment.getCustomerID()) && (newAppointmentID != appointment.getAppointmentID()) &&
                            (dtStart.isAfter(checkStart)) && (dtStart.isBefore(checkEnd))) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment time error: Overlap with start time of appointments");
                        Optional<ButtonType> error = alert.showAndWait();
                        System.out.println("The start time overlaps with a different appointment");
                        return;
                    }


                    if (customerID == appointment.getCustomerID() && (newAppointmentID != appointment.getAppointmentID()) &&
                            (dtEnd.isAfter(checkStart)) && (dtEnd.isBefore(checkEnd))) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment time error: Overlap with End time of a different appointment");
                        Optional<ButtonType> error = alert.showAndWait();
                        System.out.println(" The End time overlaps with a different appointment.");
                        return;
                    }
                }

                String datePickerStart = StartDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String startTime = StartTime.getValue();

                String endDatePicker = EndDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String endTime = EndField.getValue();

                String startUTC = convertTOUTC(datePickerStart + " " + startTime + ":00");
                String endUTC = convertTOUTC(endDatePicker + " " + endTime + ":00");
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
                preparedStatement.setString(6, startUTC);
                //for the life of me I could not figure out why my table was showing the incorrect time value after formatting everything
                //turns our i had my prepare statement set to setTimestamp... totally messed up my times I would input
                preparedStatement.setString(7, endUTC);
                preparedStatement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setString(9, "admin");
                preparedStatement.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setInt(11, 1);
                preparedStatement.setInt(12, Integer.parseInt(CustomerID.getText()));
                preparedStatement.setInt(13, Integer.parseInt(ContactDAO.locateContact(Contact.getValue())));
                preparedStatement.setInt(14,Integer.parseInt(ContactDAO.locateContact(UserID.getText())));

                preparedStatement.execute();
            }
            //Blank field errors
            if(Title.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR, " Title field is blank.");
                alert.showAndWait();
                return;
            }
            if(Description.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR, " Description field is blank.");
                alert.showAndWait();
                return;
            }
            if(Location.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR, " Location field is blank.");
                alert.showAndWait();
                return;
            }
            if(Title.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR, " Title field is blank.");
                alert.showAndWait();
                return;
            }
            if(StartTime.getValue().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR, " Start Time is blank.");
                alert.showAndWait();
                return;
            }
            if(EndField.getValue().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR, " End Time is blank.");
                alert.showAndWait();
                return;
            }
            if(CustomerID.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR, " Customer ID field is blank.");
                alert.showAndWait();
                return;
            }
            if(Type.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR, " Type field is blank.");
                alert.showAndWait();
                return;
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

    /**
     * @throws SQLException Initializing the start and end time dropdown boxes and filling in the contact combo box as
     * well so that the user can add them to the main Appointment table. Sets times for appointments into
     * fifteen minute blocks. Also contains a lambda expression to fill contact observable list without a for loop
     */
    @FXML
    public void initialize() throws SQLException {

        ObservableList<Model.Contact> contacts = ContactDAO.getAllContacts();
        ObservableList<String> allContacts = FXCollections.observableArrayList();

        contacts.forEach(contact -> allContacts.add(contact.getContactName()));
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
        AptID.setEditable(false);


    }
}
