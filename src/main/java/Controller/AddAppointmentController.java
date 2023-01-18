package Controller;

import Data_Access_Object.AppointmentDAO;
import Data_Access_Object.ContactDAO;
import Data_Access_Object.CustomerDAO;
import Data_Access_Object.USERDAO;
import Model.Appointment;
import Model.Customer;
import Model.DataBaseAppointment;
import Model.User;
import Utilities.DataBaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import static Utilities.Time.convertTOUTC;


public class AddAppointmentController implements Initializable {

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

    private Customer customer;
    private final ObservableList<String> appointmentTimes = FXCollections.observableArrayList("9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00");
    private final ObservableList<String> appointmentTypes = FXCollections.observableArrayList(
            "Insurance Quote", "Prescription List Check", "Plan Change", "Medicare Questions", "Life Insurance");

    private final ObservableList<String> contacts = FXCollections.observableArrayList("Jim", "Jody", "Chris");
    private ObservableList<String> errorTypes = FXCollections.observableArrayList();
    public boolean contactValidator(int appointmentContact){
        if(appointmentContact == -1){
            errorTypes.add("Please select a contact");
            return false;
        }
        return true;
    }
    public boolean typeValidator(int appointmentType){
        if(appointmentType == -1){
            errorTypes.add("Please select an appointment");
            return false;
        }
        else{
            return true;
        }
    }
    public boolean timeValidator(int appointmentTime){
        if(appointmentTime == -1){
            errorTypes.add("Please select and appointment time");
            return false;
        }else return true;
    }
    public boolean dateValidator(LocalDate appointmentDate){
        if(appointmentDate == null){
            errorTypes.add("Please select an appointment date");
            return false;
        }else return true;
    }
    public String displayErrorMessages(){
        String string = "";
        if(errorTypes.size() > 0){
            for(String error : errorTypes){
                string = string.concat(error);
            }
            return string;
        }else {
            string = "Database Error";
            return string;
        }
    }
    public boolean handleAddAppointment(int id){
        errorTypes.clear();
        int appointmentContact = ContactCombo.getSelectionModel().getSelectedIndex();
        int appointmentType = ApTypeCombo.getSelectionModel().getSelectedIndex();
        int appointmentTime = TimeComboBox.getSelectionModel().getSelectedIndex();
        LocalDate localDate = DateField.getValue();
        if(!contactValidator(appointmentContact) || !typeValidator(appointmentType) || !timeValidator(appointmentTime) || dateValidator(localDate)){
            return false;
        }
        if(DataBaseAppointment.appointmentsOverlap(-1, LocationField.getText(), localDate.toString(), appointmentTimes.get(appointmentTime))){
            errorTypes.add("Appointments Overlap");
            return false;
        }
        if(DataBaseAppointment.saveAppointment(id, appointmentTypes.get(appointmentType), contacts.get(appointmentContact), LocationField.getText(), localDate.toString(), appointmentTimes.get(appointmentTime))){
            return true;
        }else {
            errorTypes.add("Database Error");
            return false;
        }
    }



    public void populateCustomerNameColumn(String name){
        CustomerNameField.setText(name);
    }

    @FXML
    public void handleLocation(){
        String string = ContactCombo.getSelectionModel().getSelectedItem().toString();
        if(string.equals("Jim")){
            LocationField.setText("New York");
        }else  if(string.equals("Jodi")) {
            LocationField.setText("Phoenix");
        } else if(string.equals("Chris")){
            LocationField.setText("London");
        }
    }




**/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {/**
        ContactCombo.setItems(contacts);
        TimeComboBox.setItems(appointmentTimes);
        ApTypeCombo.setItems(appointmentTypes);
        DateField.setDayCellFactory(datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate DateField, boolean none) {
                super.updateItem(DateField, none);
                setDisable(none || DateField.getDayOfWeek() == DayOfWeek.SATURDAY ||
                        DateField.getDayOfWeek() == DayOfWeek.SUNDAY ||
                        DateField.isBefore(LocalDate.now()));
                if (DateField.getDayOfWeek() == DayOfWeek.SATURDAY
                        || DateField.getDayOfWeek() == DayOfWeek.SUNDAY ||
                        DateField.isBefore(LocalDate.now())) {
                    setStyle("-fx-background-color: #fa0400");
                }
            }

        }); **/
    }

    public void onActionSave(ActionEvent actionEvent) throws IOException {
        try{
            Connection connection = DataBaseConnection.openConnection();
            if(!AptID.getText().isEmpty() && !Title.getText().isEmpty() && Description.getText().isEmpty() && Location.getText().isEmpty() && Type.getText().isEmpty() && StartDate.getValue() != null && EndDate.getValue() != null && !StartTime.getValue().isEmpty() && !EndField.getValue().isEmpty() && !CustomerID.getText().isEmpty()){
                ObservableList<Customer> getCustomers = CustomerDAO.getAllCustomers(connection);
                ObservableList<Integer> customerIDIndex = FXCollections.observableArrayList();
                ObservableList<USERDAO> getUsers = USERDAO.getAllUser();
                ObservableList<Integer> userIDIndex = FXCollections.observableArrayList();
                ObservableList<Appointment> getAllAppointments = AppointmentDAO.getAllAppointment();

                getCustomers.stream().map(Customer :: getCustomerID).forEach(customerIDIndex::add);
                getUsers.stream().map(User::getUserID).forEach(userIDIndex::add);

                LocalDate localStartDate = StartDate.getValue();
                LocalDate localEndDate = EndDate.getValue();

                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                String startDate = StartDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String  startTime = StartTime.getValue();
                String endDate = EndDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String endTime = EndField.getValue();

                System.out.println("Appointment Date + Appointment Start" + startDate + startTime + ":00");
                String startUTCConvert = convertTOUTC(StartDate + " " + StartTime + ":00");
                String endUTCConvert = convertTOUTC(EndDate + " " + EndField + ":00");

                LocalTime localStart = LocalTime.parse(StartTime.getValue(), timeFormatter);
                LocalTime localEnd = LocalTime.parse(EndField.getValue(), timeFormatter);

                LocalDateTime dtStart = LocalDateTime.of(localStartDate, localStart);
                LocalDateTime dtEnd = LocalDateTime.of(localStartDate, localEnd);

                ZonedDateTime zoneStart = ZonedDateTime.of(dtStart, ZoneId.systemDefault());
                ZonedDateTime zoneEnd = ZonedDateTime.of(dtEnd, ZoneId.systemDefault());

                ZonedDateTime startToEST = zoneStart.withZoneSameInstant(ZoneId.of("America/New_York"));
                ZonedDateTime endToEST = zoneEnd.withZoneSameInstant(ZoneId.of("America/New_York"));

                LocalTime CheckStartTime = startToEST.toLocalTime();
                LocalTime CheckEndTime = endToEST.toLocalTime();

                DayOfWeek ApptStartDayCheck = startToEST.toLocalDate().getDayOfWeek();
                DayOfWeek ApptEndDayCheck = endToEST.toLocalDate().getDayOfWeek();

                int startApptDay = ApptStartDayCheck.getValue();
                int EndApptDay = ApptEndDayCheck.getValue();

                int startOfWeek = DayOfWeek.MONDAY.getValue();
                int endOfWeek = DayOfWeek.FRIDAY.getValue();

                LocalTime startBusinessHours = LocalTime.of(8, 0,0);
                LocalTime endBusinessHours = LocalTime.of(22, 0,0);

                if(startApptDay < startOfWeek || startApptDay > endOfWeek || EndApptDay < startOfWeek || EndApptDay > endOfWeek){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "That day is outside of business hours.");
                    Optional<ButtonType> confirm = alert.showAndWait();
                    System.out.println("Day Is Outside of Business Hours");
                    return;
                }

                if(CheckStartTime.isBefore(startBusinessHours) || CheckStartTime.isAfter(endBusinessHours) || CheckEndTime.isBefore(startBusinessHours) || CheckEndTime.isAfter(endBusinessHours)){
                    System.out.println("The Time selected is outside of business hours");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Business Hours are (8am-10pm EST):" + CheckStartTime + "-" + CheckEndTime + "EST";
                    Optional<ButtonType> confirm = alert.showAndWait();
                    return;
                }
                int newAptID = Integer.parseInt(String.valueOf((int)Math.random()*250));
                int customerID = Integer.parseInt(CustomerID.getText());

                if(dtStart.isAfter(dtEnd)){
                    System.out.println("Appointment start time is after the end. Check Times.");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Appointment start time is after the end time. ");
                    Optional<ButtonType> confirm = alert.showAndWait();
                    return;
                }
                for (Appointment appointment: getAllAppointments)
                {
                    LocalDateTime checkStart = appointment.getStart();
                    LocalDateTime checkEnd = appointment.getEnd();
                    if((customerID == appointment.getCustomerID() && (newAptID != appointment.getAppointmentID() && dtStart.isBefore(checkStart))&& (dtEnd.isAfter(checkEnd)))){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "There is an overlap in existing appointments");
                        Optional<ButtonType> confirm = alert.showAndWait();
                        System.out.println("There is an overlap in time with an existing Appointment.");
                        return;
                    }
                    if(customerID == appointment.getCustomerID() && (newAptID != appointment.getAppointmentID()) && (dtEnd.isAfter(checkStart)) && dtEnd.isBefore(checkEnd)){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The end of an appointment overlaps with existing appointment");
                        Optional<ButtonType> confirm = alert.showAndWait();
                        System.out.println("End Time overlaps with an existing appointment");
                        return;
                    }
                }
                String query = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                DataBaseConnection.setPreparedStatement(DataBaseConnection.getConnection(), query);
                PreparedStatement preparedStatement = DataBaseConnection.getPreparedStatement();
                preparedStatement.setInt(1, newAptID);
                preparedStatement.setString(2, Title.getText());
                preparedStatement.setString(3, Description.getText());
                preparedStatement.setString(4, Location.getText());
                preparedStatement.setString(5, Type.getText());
                preparedStatement.setTimestamp(6, Timestamp.valueOf(startUTCConvert));
                preparedStatement.setTimestamp(7, Timestamp.valueOf(endUTCConvert));
                preparedStatement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setString(9, "admin");
                preparedStatement.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setInt(11,1);
                preparedStatement.setInt(12,Integer.parseInt(CustomerID.getText()));
                preparedStatement.setInt(13, Integer.parseInt(ContactDAO.locateContact(Contact.getValue())));
                preparedStatement.setInt(13, Integer.parseInt(ContactDAO.locateContact(UserID.getText())));


            }
        }
    }

    public void onActionCancel(ActionEvent actionEvent) {
    }
}
