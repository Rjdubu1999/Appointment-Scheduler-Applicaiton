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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import static Utilities.Time.convertTOUTC;
/**
 * @Author Ryan Wilkinson
 * C195  - Software II
 */

/**
 * Creating a controller class to enable the user to go to the add appointment screen to add appointments,
 * loads current appointments in the MySQL Database, modify appointments by loading the appointment information into
 * the fields and allows users to save the updated appointments
 */
public class MainAppointmentController {

    @FXML private Button onActionModifyAppointment;
    @FXML
    private TextField AptIDField;
    @FXML
    private TextField UserIDField;
    @FXML
    private TextField LocationField;
    @FXML
    private TextField TitleField;
    @FXML
    private TextField TypeField;
    @FXML
    private TextField DescriptionField;
    @FXML
    private TextField CustomerIDField;
    @FXML
    private TextField ContactField;
    @FXML
    private RadioButton AllAptRadio;
    @FXML
    private RadioButton WeeklyRadio;
    @FXML
    private RadioButton MonthlyRadio;
    @FXML
    private ComboBox<String> StartTimeCombo;
    @FXML
    private ComboBox<String> EndTimeCombo;
    @FXML
    private DatePicker StartDatePicker;
    @FXML
    private DatePicker EndDatePicker;
    @FXML
    private ComboBox<String> ContactCombo;
    @FXML
    private TableColumn<?, ?> CustomerIDColumn;
    @FXML
    private TableColumn<?, ?> AptIDColumn;
    @FXML
    private TableColumn<?, ?> TitleColumn;
    @FXML
    private TableColumn<?, ?> TypeColumn;
    @FXML
    private TableColumn<?, ?> LocationColumn;
    @FXML
    private TableColumn<?, ?> DescriptionCol;
    @FXML
    private TableColumn<?, ?> StartCol;
    @FXML
    private TableColumn<?, ?> EndColumn;
    @FXML
    private TableColumn<?, ?> ContactIDColumn;
    @FXML
    private TableColumn<?, ?> UserIDColumn;
    @FXML
    private
    TableView<Appointment> MainTableView;


    /**
     * @throws SQLException Initializes the controller class and sets the columns in the table to the
     * associated values in the MySQL database where it gets the information from the DAO Appointment class
     * it also sets the radio button of All appointments to be active and the others to be false, which shows all
     * of the appointments in the database
     */
    public void initialize() throws SQLException {
        ObservableList<Appointment> allAppointments = AppointmentDAO.getAllAppointment();
        AptIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        TitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        LocationColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        TypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        StartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        EndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        CustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        ContactIDColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        UserIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));

        MainTableView.setItems(allAppointments);
        AllAptRadio.setSelected(true);
        WeeklyRadio.setSelected(false);
        MonthlyRadio.setSelected(false);

    }

    /**
     * This method will load all of the data from the appointment in the table when clicked into
     * the fields at the bottom of the screen automatically so that the user may modify and update
     * the appointments and any information associated with them
     */
    @FXML
    void MouseClickedLoadAptData() {
        try {
            DataBaseConnection.openConnection();
            Appointment selectedAppointment = MainTableView.getSelectionModel().getSelectedItem();

            if (selectedAppointment != null) {
                ObservableList<Contact> contactObservableList = ContactDAO.getAllContacts();
                ObservableList<String> contactNameList = FXCollections.observableArrayList();
                String onScreenContactName = "";

                contactObservableList.forEach(contact -> contactNameList.add(contact.getContactName()));
                ContactCombo.setItems(contactNameList);
                for (Contact contact : contactObservableList) {
                    if (selectedAppointment.getContactID() == contact.getContactID()) {
                        onScreenContactName = contact.getContactName();
                    }
                }
                AptIDField.setText(String.valueOf(selectedAppointment.getAppointmentID()));
                TitleField.setText(selectedAppointment.getAppointmentTitle());
                LocationField.setText(selectedAppointment.getAppointmentLocation());
                DescriptionField.setText(selectedAppointment.getAppointmentDescription());
                TypeField.setText(selectedAppointment.getAppointmentType());
                CustomerIDField.setText(String.valueOf(selectedAppointment.getCustomerID()));
                StartDatePicker.setValue(selectedAppointment.getStart().toLocalDate());
                EndDatePicker.setValue(selectedAppointment.getEnd().toLocalDate());
                StartTimeCombo.setValue(String.valueOf(selectedAppointment.getStart().toLocalTime()));
                EndTimeCombo.setValue(String.valueOf(selectedAppointment.getEnd().toLocalTime()));
                UserIDField.setText(String.valueOf(selectedAppointment.getUserID()));
                ContactCombo.setValue(onScreenContactName);
               // ContactField.setText(String.valueOf(selectedAppointment.getContactID()));

                ObservableList<String> timesOfAppointments = FXCollections.observableArrayList();

                LocalTime firstApt = LocalTime.MIN.plusHours(8);
                LocalTime lastApt = LocalTime.MAX.minusHours(1).minusMinutes(45);

                 if(!firstApt.equals(0) || !lastApt.equals(0)){
                  while(firstApt.isBefore(lastApt)){
                      timesOfAppointments.add(String.valueOf(firstApt));
                       firstApt = firstApt.plusMinutes(15);
                   }
                   }
                StartTimeCombo.setItems(timesOfAppointments);
                EndTimeCombo.setItems(timesOfAppointments);


            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    /**
     * @param actionEvent this method will take the user to the Addappointment.fxml file
     *                    where they can add new appointments to the main appointment table and the
     *                    MySQL database.
     * @throws IOException
     */
    public void onActionAddAppointment(ActionEvent actionEvent) throws IOException {
        Parent appointmentButton = FXMLLoader.load(Main.class.getResource("AddAppointment.fxml"));
        Scene scene = new Scene(appointmentButton);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    /**
     * @param actionEvent This method takes the user back to main screen and hides the current window
     */
    @FXML
    public void onActionBack(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }


    /**
     * @param actionEvent This method will delete a selected appointment and the information from the database as well
     * @throws Exception
     */
    public void onActionDeleteAppointment(ActionEvent actionEvent) throws Exception {
        try {
            Connection connection = DataBaseConnection.openConnection();
            int deleteAptId = MainTableView.getSelectionModel().getSelectedItem().getAppointmentID();
            String deleteAptType = MainTableView.getSelectionModel().getSelectedItem().getAppointmentType();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure You Want To Delete The Selected Appointment?");
            Optional<ButtonType> confirm = alert.showAndWait();
            if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
                AppointmentDAO.deleteAppointment(deleteAptId, connection);
                ObservableList<Appointment> appointmentObservableList = AppointmentDAO.getAllAppointment();
                MainTableView.setItems(appointmentObservableList);
                System.out.println("Appointment Successfully Deleted.");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * @param event This method allows the user to update and modify the appointment information that is brought
     *              into the fields below the main table by clicking an appointment, loading the information into the fields,
     *              then updating and modifying the fields and clicking save to update the appointment in the main
     *              table, as well as the information in the MySQL database
     */
    @FXML
    void onActionModifyAppointment(ActionEvent event) {
        try {
            Connection connection = DataBaseConnection.openConnection();

            if (!TitleField.getText().isEmpty() && !DescriptionField.getText().isEmpty() && !LocationField.getText().isEmpty() && !TypeField.getText().isEmpty() && StartDatePicker.getValue() != null && EndDatePicker.getValue() != null && !StartTimeCombo.getValue().isEmpty() && !EndTimeCombo.getValue().isEmpty() && !CustomerIDField.getText().isEmpty()) {
                ObservableList<Customer> getAllCustomers = CustomerDAO.getAllCustomers(connection);
                ObservableList<Integer> customerIDIndex = FXCollections.observableArrayList();
                ObservableList<USERDAO> getAllUsers = USERDAO.getAllUser();
                ObservableList<Integer> UserIdIndex = FXCollections.observableArrayList();
                ObservableList<Appointment> getAllAppointments = AppointmentDAO.getAllAppointment();


                getAllCustomers.stream().map(Customer::getCustomerID).forEach(customerIDIndex::add);
                getAllUsers.stream().map(User::getUserID).forEach(UserIdIndex::add);
                //formatting times and dates
                LocalDate ldEnd = EndDatePicker.getValue();
                LocalDate LdStart = StartDatePicker.getValue();

                DateTimeFormatter minHourFormat = DateTimeFormatter.ofPattern("HH:mm");

                LocalTime ltStart = LocalTime.parse(StartTimeCombo.getValue(), minHourFormat);
                LocalTime ltEnd = LocalTime.parse(EndTimeCombo.getValue(), minHourFormat);

                LocalDateTime dateTimeStart = LocalDateTime.of(LdStart, ltStart);
                LocalDateTime dateTimeEnd = LocalDateTime.of(ldEnd, ltEnd);

                ZonedDateTime zonedDateTimeStart = ZonedDateTime.of(dateTimeStart, ZoneId.systemDefault());
                ZonedDateTime zonedDateTimeEnd = ZonedDateTime.of(dateTimeEnd, ZoneId.systemDefault());

                ZonedDateTime StartESTConversion = zonedDateTimeStart.withZoneSameInstant(ZoneId.of("America/New_York"));
                ZonedDateTime EndEstConversion = zonedDateTimeEnd.withZoneSameInstant(ZoneId.of("America/New_York"));
                //logic errors for appointment and date issues
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

                int newCustomerID = Integer.parseInt(CustomerIDField.getText());
                int appointmentID = Integer.parseInt(AptIDField.getText());


                if (dateTimeStart.isAfter(dateTimeEnd)) {
                    System.out.println("Appointment has start time after end time");
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment time error: Start time is after the end time");
                    Optional<ButtonType> error = alert.showAndWait();
                    return;
                }

                if (dateTimeStart.isEqual(dateTimeEnd)) {
                    System.out.println("Appointment has same start and end time");
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment has same start and end time");
                    Optional<ButtonType> error = alert.showAndWait();
                    return;
                }

                for (Appointment appointment : getAllAppointments) {
                    LocalDateTime checkStart = appointment.getStart();
                    LocalDateTime checkEnd = appointment.getEnd();


                    if ((newCustomerID == appointment.getCustomerID()) && (appointmentID != appointment.getAppointmentID()) &&
                            (dateTimeStart.isBefore(checkStart)) && (dateTimeEnd.isAfter(checkEnd))) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Time Error: Appointment overlaps with existing appointment.");
                        Optional<ButtonType> error = alert.showAndWait();
                        System.out.println("Appointment overlaps with existing appointment.");
                        return;
                    }

                    if ((newCustomerID == appointment.getCustomerID()) && (appointmentID != appointment.getAppointmentID()) &&
                            (dateTimeStart.isAfter(checkStart)) && (dateTimeStart.isBefore(checkEnd))) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment time error: Overlap with start time of appointments");
                        Optional<ButtonType> error = alert.showAndWait();
                        System.out.println("The start time overlaps with a different appointment");
                        return;
                    }


                    if (newCustomerID == appointment.getCustomerID() && (appointmentID != appointment.getAppointmentID()) &&
                            (dateTimeEnd.isAfter(checkStart)) && (dateTimeEnd.isBefore(checkEnd))) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment time error: Overlap with End time of a different appointment");
                        Optional<ButtonType> error = alert.showAndWait();
                        System.out.println(" The End time overlaps with a different appointment.");
                        return;
                    }
                }

                String datePickerStart = StartDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String startTime = StartTimeCombo.getValue();

                String endDatePicker = EndDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String endTime = EndTimeCombo.getValue();

                String startUTC = convertTOUTC(datePickerStart + " " + startTime + ":00");
                String endUTC = convertTOUTC(endDatePicker + " " + endTime + ":00");
    //13 params due to not having the created by field
                String updateQuery = "UPDATE appointments SET Appointment_ID = ?, Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";

                DataBaseConnection.setPreparedStatement(DataBaseConnection.getConnection(), updateQuery);
                PreparedStatement preparedStatement = DataBaseConnection.getPreparedStatement();
                preparedStatement.setInt(1, Integer.parseInt(AptIDField.getText()));
                preparedStatement.setString(2, TitleField.getText());
                preparedStatement.setString(3, DescriptionField.getText());
                preparedStatement.setString(4, LocationField.getText());
                preparedStatement.setString(5, TypeField.getText());
                preparedStatement.setString(6, startUTC);
                preparedStatement.setString(7, endUTC);
                preparedStatement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setString(9, "admin");
                preparedStatement.setInt(10, Integer.parseInt(CustomerIDField.getText()));
                preparedStatement.setInt(11, Integer.parseInt(UserIDField.getText()));
                preparedStatement.setInt(12, Integer.parseInt(ContactDAO.locateContact(ContactCombo.getValue())));
                preparedStatement.setInt(13, Integer.parseInt(AptIDField.getText()));
                preparedStatement.execute();

                ObservableList<Appointment> AppointmentObservableList = AppointmentDAO.getAllAppointment();
                MainTableView.setItems(AppointmentObservableList);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }



    /**
     * @param mouseEvent This method will load all of the appointments into the main table when this radio button is
     *                   clicked and will also set the other radio buttons to not selected when it is selected
     * @throws SQLException
     */
    public void OnClickRadioAllApts(MouseEvent mouseEvent) throws SQLException {
        try {
            ObservableList<Appointment> appointmentObservableList = AppointmentDAO.getAllAppointment();
            if (appointmentObservableList != null)
                for (Appointment appointment : appointmentObservableList) {
                    MainTableView.setItems(appointmentObservableList);
                }
            if(AllAptRadio.isSelected()){
                WeeklyRadio.setSelected(false);
                MonthlyRadio.setSelected(false);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * @param mouseEvent This method sets the appointment table to all of the appointments for the week and
     *                   sets the other radio buttons to not selected when it has been selected
     * @throws SQLException
     */
    public void OnClickRadioWeeklyApts(MouseEvent mouseEvent) throws SQLException {
        try {
            ObservableList<Appointment> appointmentObservableList = AppointmentDAO.getAllAppointment();
            ObservableList<Appointment> weeklyAppointment = FXCollections.observableArrayList();
            LocalDateTime startOfWeek = LocalDateTime.now().minusWeeks(1);
            LocalDateTime endOfWeek = LocalDateTime.now().plusWeeks(1);
            if (appointmentObservableList != null)
                appointmentObservableList.forEach(appointment -> {
                    if (appointment.getEnd().isAfter(startOfWeek) && appointment.getEnd().isBefore(endOfWeek)) {
                        weeklyAppointment.add(appointment);
                    }
                    MainTableView.setItems(weeklyAppointment);
                });
            if(WeeklyRadio.isSelected()){
                AllAptRadio.setSelected(false);
                MonthlyRadio.setSelected(false);
            }

        }catch (Exception exception){
            exception.printStackTrace();
        }
    }


    /**
     * @param mouseEvent this method sets the main appointment table to only monthly appointments and sets the
     *                   All and weekly radio buttons to not selected when it is selected
     * @throws SQLException
     */
    public void OnMonthlyRadioAptsClicked(MouseEvent mouseEvent) throws SQLException {
        try{
            ObservableList<Appointment> appointmentObservableList = AppointmentDAO.getAllAppointment();
            ObservableList<Appointment> monthlyAppointments = FXCollections.observableArrayList();
            LocalDateTime monthStart = LocalDateTime.now().minusMonths(1);
            LocalDateTime monthEnd = LocalDateTime.now().plusMonths(1);
            if(appointmentObservableList != null){
                appointmentObservableList.forEach(appointment -> {
                    if(appointment.getEnd().isAfter(monthStart) && appointment.getEnd().isBefore(monthEnd)){
                        monthlyAppointments.add(appointment);
                    }
                    MainTableView.setItems(monthlyAppointments);
                });
            }
            if(MonthlyRadio.isSelected()){
                AllAptRadio.setSelected(false);
                WeeklyRadio.setSelected(false);
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
}
